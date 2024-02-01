package com.sheldon.apigateway.filter;

import com.sheldon.apicommon.model.entity.InterfaceInfo;
import com.sheldon.apicommon.model.entity.User;
import com.sheldon.apicommon.service.InnerInterfaceInfoService;
import com.sheldon.apicommon.service.InnerUserInterfaceInfoService;
import com.sheldon.apicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.sheldon.apiclientsdk.utils.SignUtils.genSign;


/**
 * @ClassName CustomGlobalFilter
 * @Author sheldon
 * @Date 2024/1/12 12:10
 * @Version 1.0
 * @Description 全局过滤器
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WEITE_LIST = Arrays.asList("127.0.0.1");

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    /**
     * 用户统一鉴权
     *
     * @param exchange 路由交换机，包含当前请求和响应的ServerWebExchange对象
     * @param chain    用于继续过滤器链的GatewayFilterChain对象
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 取出请求体和响应体
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String url = request.getPath().value();
        String method = request.getMethod().toString();
        // 1.请求日志
        log.info("CustomGlobalFilter...");
        log.info("请求标识: {}", request.getId());
        log.info("请求地址: {}", url);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        String sourceIp = request.getLocalAddress().getHostString();
        log.info("请求来源: {}", sourceIp);
        log.info("请求来源: {}", request.getRemoteAddress());

        // 2.黑白名单
        // 使用白名单，只允许白名单中的IP访问
        if (!IP_WEITE_LIST.contains(sourceIp)) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }

        // 3.用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String interfaceInfoId = headers.getFirst("interfaceInfoId");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");

        // 查询用户信息是否存在
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);

        } catch (Exception e) {
            log.error("invokeUser error", e);
        }
        if (invokeUser == null) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }
        // 需要验证时间戳，时间不超过五分钟
        long currentTimestamp = System.currentTimeMillis();
        final long FIVE_MINUTES = 5 * 60 * 1000L;
        if (timestamp == null || Long.parseLong(timestamp) + FIVE_MINUTES < currentTimestamp) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }
        // TODO 需要验证nonce，nonce不能重复
        if (nonce == null) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }
        // 需要验证sign，sign需要根据 body 和 secretKey 生成, secretKey也需要到数据库查询
        String secretKey = invokeUser.getSecretKey();
        String serverSign = genSign(timestamp, nonce, secretKey);
        if (!serverSign.equals(sign)) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }

        // 4.判断请求接口是否存在
        InterfaceInfo invokeInterfaceInfo = null;
        try {
            if (interfaceInfoId != null) {
                invokeInterfaceInfo = innerInterfaceInfoService.getInvokeInterfaceInfo(Long.valueOf(interfaceInfoId));
            }
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (invokeInterfaceInfo == null) {
            return handleError(response, HttpStatus.FORBIDDEN);
        }
        // 5.请求转发，调用模拟接口

        // 6.响应日志
        HttpStatus statusCode = response.getStatusCode();
        log.info("响应状态码: {}", statusCode);

        return handleResponse(exchange, chain, invokeInterfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理网关响应的GatewayFilter。
     *
     * @param exchange 包含当前请求和响应的ServerWebExchange对象
     * @param chain    用于继续过滤器链的GatewayFilterChain对象
     * @return Mono<Void> 表示异步的完成响应处理
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceInfoId, Long userId) {
        try {
            // 获取原始响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 原始响应对象的数据容器工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            // 获取原始响应的HTTP状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 如果HTTP状态码为OK
            if (statusCode == HttpStatus.OK) {
                // 创建一个装饰过的响应对象，用于拦截和修改响应数据
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    /**
                     * 重写writeWith方法，用于处理响应数据。
                     *
                     * @param body 响应体数据流
                     * @return Mono<Void> 表示异步的完成响应处理
                     */
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 对响应体进行处理，例如记录日志
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 7.调用成功，调用次数+1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(userId, interfaceInfoId);
                                } catch (Exception e) {
                                    log.error("invokeCount error", e);
                                }
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer); // 释放掉内存
                                // 构建日志
                                String data = new String(content, StandardCharsets.UTF_8);
                                log.info("响应数据: {}", data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("响应异常: {}", getStatusCode());
                        }
                        // 如果不是Flux类型的响应体，则直接交给下一个过滤器处理
                        return super.writeWith(body);
                    }
                };
                // 继续过滤器链，并使用装饰过的响应对象
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            } else {
                // 如果HTTP状态码不是OK，则交给下一个过滤器处理（降级处理返回数据）
                return chain.filter(exchange);
            }
        } catch (Exception e) {
            // 捕捉异常并记录日志
            log.error("网关处理异常：" + e);
            // 继续过滤器链
            return chain.filter(exchange);
        }
    }


    private static Mono<Void> handleError(ServerHttpResponse response, HttpStatus httpStatus) {
        log.info("非法请求");
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
