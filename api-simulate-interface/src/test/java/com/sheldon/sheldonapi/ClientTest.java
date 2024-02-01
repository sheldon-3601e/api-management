package com.sheldon.sheldonapi;

import com.sheldon.apiinterface.simulateApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ClientTest
 * @Author 26483
 * @Date 2024/1/8 15:13
 * @Version 1.0
 * @Description ClientTest
 */
@SpringBootTest(classes = simulateApiApplication.class)
public class ClientTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testName() {
//        String accessKey = "sheldon";
//        String secretKey = "sheldon";
//        ApiClient apiClient = new ApiClient(accessKey, secretKey);
//        String res01 = apiClient.getName("sheldon01");
//        String res02 = apiClient.postName("sheldon02");
//        User user = new User();
//        user.setUsername("sheldon03");
//        String res03 = apiClient.postJson(user);
//        System.out.println(res01);
//        System.out.println(res02);
//        System.out.println(res03);

    }

    @Test
    public void testSign() {
        String i = new Random().nextInt(1000) + "";
        // 查询
        System.out.println(stringRedisTemplate.opsForValue().get(i));
    }

    @Test
    public void insertMore() {
        // 设置插入的数据数量
        int numberOfEntries = 1000;

        for (int i = 1; i <= numberOfEntries; i++) {
            // 生成随机毒鸡汤语录
            String chickenSoup = generateRandomChickenSoup();

            // 插入数据到Redis（这里省略了实际插入到Redis的代码）
            stringRedisTemplate.opsForValue().set(i + "", chickenSoup);
//            stringRedisTemplate.delete("chickenSoup" + i);
        }

        System.out.println("Insertion of chicken soup data completed.");
    }

    private static String generateRandomChickenSoup() {

        ArrayList<String> chickenSoupList = new ArrayList<>();

        String data = "1. \"每天努力一点点，离梦想就会越来越近。\"\n" +
                "        2. \"不经历风雨，怎么见彩虹；没有苦痛，哪来幸福。\"\n" +
                "        3. \"世上没有偶然，每一次相遇都是久别重逢。\"\n" +
                "        4. \"人生就像切西瓜，总是甜蜜的人，很容易被切成瓜条。\"\n" +
                "        5. \"努力有什么用，反正天空随便飘着大便。\"\n" +
                "        6. \"别人都在努力拼命，而你，也可以努力一下看看。\"\n" +
                "        7. \"脑子有病得治，像\"\n" +
                "        8. \"成功就像黄瓜，一不小心就从手里滑掉了。\"\n" +
                "        9. \"人生就是一个起落落落落的过程。\"\n" +
                "        10. \"每次努力，都是对失败的延续。\"\n" +
                "        11. \"别抱怨命运不公平，因为你连抱怨都抱不过别人。\"\n" +
                "        12. \"成功就像传说中的宝藏，你永远找不到。\"\n" +
                "        13. \"梦想再大，也敌不过现实的残酷。\"\n" +
                "        14. \"别人的成功都是偶然，而你的失败都是必然。\"\n" +
                "        15. \"你的付出再多，也改变不了别人对你的忽视。\"\n" +
                "        16. \"努力奋斗的人，大部分都是没天赋的人。\"\n" +
                "        17. \"人生如戏，可惜你是个配角。\"\n" +
                "        18. \"别人看起来都很努力，而你，就是个悲剧。\"\n" +
                "        19. \"别人都在奔向成功的路上，而你，在原地踏步。\"\n" +
                "        20. \"失败是成功之母，但你连个孩子都生不出来。\"\n" +
                "        21. \"人生就是一个不断被现实打脸的过程。\"\n" +
                "        22. \"别总是想着改变命运，命运根本不关心你。\"\n" +
                "        23. \"努力不一定成功，但不努力肯定不行。\"\n" +
                "        24. \"世界上没有什么事是一蹴而就的，你是例外。\"\n" +
                "        25. \"每次说努力一点，都是骗自己的借口。\"\n" +
                "        26. \"别人的辉煌都是从努力开始的，而你，始终在起点。\"\n" +
                "        27. \"别为失败找借口，因为成功也不会找你。\"\n" +
                "        28. \"人生就是一个不断努力，然后无果而终的过程。\"\n" +
                "        29. \"成功离你很近，但你离成功很远。\"\n" +
                "        30. \"世界上没有什么事是不可能的，除了你成功。\"\n" +
                "        31. \"努力再多也挡不住你的平庸。\"\n" +
                "        32. \"别总想着改变世界，先改变下自己也挺难的。\"\n" +
                "        33. \"有梦想是好的，但你的梦想太不靠谱了。\"\n" +
                "        34. \"别总是想着追求完美，你连优秀都达不到。\"\n" +
                "        35. \"成功是一种能力，而你连努力都谈不上。\"\n" +
                "        36. \"别总说自己在努力，其实你只是在混日子。\"\n" +
                "        37. \"付出总有回报，但你的回报总是辜负。\"\n" +
                "        38. \"别总是觉得命运对你不公平，其实命运根本不关心你。\"\n" +
                "        39. \"每天都在奔跑的人，大多数都是在原地踏步。\"\n" +
                "        40. \"努力有用吗，成功是靠实力的，不是靠你。\"\n" +
                "        41. \"别总说自己很努力，其实你只是在划水。\"\n" +
                "        42. \"成功就是把简单的事情做到困难的程度，而你是个天生的行家。\"\n" +
                "        43. \"不努力一下，你都不知道什么叫绝望。\"\n" +
                "        44. \"每次都说要努力，最后都是在失望中结束。\"\n" +
                "        45. \"世上本没有绝望，绝望出现是你没能力。\"\n" +
                "        46. \"别总觉得别人幸福，因为别人看你也觉得幸福。\"\n" +
                "        47. \"别人的付出是努力，而你的付出就是碰运气。\"\n" +
                "        48. \"做人要谦虚，因为你根本没有什么好谦虚的。\"\n" +
                "        49. \"成功离你很近，但你离成功很远。\"\n" +
                "        50. \"别总是想着改变世界，先改变下自己也挺难的。\"\n" +
                "        51. \"世界上没有什么事是不可能的，除了你成功。\"\n" +
                "        52. \"努力再多也挡不住你的平庸。\"\n" +
                "        53. \"别总想着改变世界，先改变下自己也挺难的。\"\n" +
                "        54. \"有梦想是好的，但你的梦想太不靠谱了。\"\n" +
                "        55. \"别总是想着追求完美，你连优秀都达不到。\"\n" +
                "        56. \"成功是一种能力，而你连努力都谈不上。\"\n" +
                "        57. \"别总说自己在努力，其实你只是在混日子。\"\n" +
                "        58. \"付出总有回报，但你的回报总是辜负。\"\n" +
                "        59. \"别总是觉得命运对你不公平，其实命运根本不关心你。\"\n" +
                "        60. \"每天都在奔跑的人，大多数都是在原地踏步。\"\n" +
                "        61. \"努力有用吗，成功是靠实力的，不是靠你。\"\n" +
                "        62. \"别总说自己很努力，其实你只是在划水。\"\n" +
                "        63. \"成功就是把简单的事情做到困难的程度，而你是个天生的行家。\"\n" +
                "        64. \"不努力一下，你都不知道什么叫绝望。\"\n" +
                "        65. \"每次都说要努力，最后都是在失望中结束。\"\n" +
                "        66. \"世上本没有绝望，绝望出现是你没能力。\"\n" +
                "        67. \"别总觉得别人幸福，因为别人看你也觉得幸福。\"\n" +
                "        68. \"别人的付出是努力，而你的付出就是碰运气。\"\n" +
                "        69. \"做人要谦虚，因为你根本没有什么好谦虚的。\"\n" +
                "        70. \"成功离你很近，但你离成功很远。\"\n" +
                "        71. \"别总是想着改变世界，先改变下自己也挺难的。\"\n" +
                "        72. \"世界上没有什么事是不可能的，除了你成功。\"\n" +
                "        73. \"努力再多也挡不住你的平庸。\"\n" +
                "        74. \"别总想着改变世界，先改变下自己也挺难的。\"\n" +
                "        75. \"有梦想是好的，但你的梦想太不靠谱了。\"\n" +
                "        76. \"别总是想着追求完美，你连优秀都达不到。\"\n" +
                "        77. \"成功是一种能力，而你连努力都谈不上。\"\n" +
                "        78. \"别总说自己在努力，其实你只是在混日子。\"\n" +
                "        79. \"付出总有回报，但你的回报总是辜负。\"\n" +
                "        80. \"别总是觉得命运对你不公平，其实命运根本不关心你。\"\n" +
                "        81. \"每天都在奔跑的人，大多数都是在原地踏步。\"\n" +
                "        82. \"努力有用吗，成功是靠实力的，不是靠你。\"\n" +
                "        83. \"别总说自己很努力，其实你只是在划水。\"\n" +
                "        84. \"成功就是把简单的事情做到困难的程度，而你是个天生的行家。\"\n" +
                "        85. \"不努力一下，你都不知道什么叫绝望。\"\n" +
                "        86. \"每次都说要努力，最后都是在失望中结束。\"\n" +
                "        87. \"世上本没有绝望，绝望出现是你没能力。\"";
        // 定义正则表达式
        String regex = "\\d+\\. \"(.*?)\"";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建Matcher对象
        Matcher matcher = pattern.matcher(data);

        // 循环匹配并输出结果
        while (matcher.find()) {
            chickenSoupList.add(matcher.group(1));
        }

        // 从数组中随机选择一个毒鸡汤语录
        int randomIndex = new Random().nextInt(chickenSoupList.size());
        return chickenSoupList.get(randomIndex);
    }


}
