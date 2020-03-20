package com.clrvn.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clrvn
 * @description
 * @className RecheckingUtil
 * @date 2019-05-21 23:46
 */
public class RecheckingUtil {


    /**
     * 用户提交的论文和系统论文的比较结果
     *
     * @param userPaperList   用户论文
     * @param systemPaperList 系统论文
     * @return 他们之间相似的段落和具体的内容
     */
    public static List<String> comparePaper(List<String> userPaperList, List<String> systemPaperList) {

        List<String> resultList = new ArrayList<>();

        int count = 1;

        for (int i = 0; i < userPaperList.size(); i++) {

            for (int j = 0; j < systemPaperList.size(); j++) {

                double similarity = similarity(userPaperList.get(i), systemPaperList.get(j));
                if (similarity > 0.3) {
                    /*Map<String, String> resultMap = new HashMap<>(2);
                    resultMap.put("【"+paragraphIndex +"】、用户论文"+i+"段：" + userPaperList.get(i), "重复率为：" + similarity);
                    resultMap.put("出处：系统论文"+j+"段：" + systemPaperList.get(j), "重复率为：" + similarity);*/

                    List<String> similarityList = new ArrayList<>();

                    similarityList.add("【" + count + "】、重复率为：" + similarity * 100 + "%");
                    similarityList.add("用户论文第 " + (i + 1) + " 段：" + userPaperList.get(i));
                    similarityList.add("出处：系统论文第 " + (j + 1) + " 段：" + systemPaperList.get(j));
                    //start 为了美观
                    similarityList.add("");
                    similarityList.add("----------------");
                    similarityList.add("");
                    //end 为了美观
                    resultList.addAll(similarityList);
                    count++;
                }
            }
        }

        return resultList;
    }

    /**
     * 计算两个字符串的相似度
     *
     * @param str1 字符1
     * @param str2 字符2
     * @return 相似度
     */
    public static double similarity(String str1, String str2) {

        CosineSimilarityUtil text1 = new CosineSimilarityUtil(str1);
        CosineSimilarityUtil text2 = new CosineSimilarityUtil(str2);

        return text1.similarityRatio(text2.getMap());
    }
}
