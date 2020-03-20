package com.clrvn.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clrvn
 * @description 我的Similarity类
 * @className Similarity
 * @date 2019-04-30 18:25
 */
public class CosineSimilarityUtil {

    /**
     * 存放所有的值
     */
    private Map<String, BigInteger> myMap = new HashMap<>(0);

    /**
     * 所有的词数，初始化0
     */
    private BigInteger wordsNum = BigInteger.valueOf(0);

    /**
     * 行数
     */
    private Integer linesNum = 0;


    public CosineSimilarityUtil(String content) {
        fillMyMap(content);
    }

    /**
     * 填充数据
     *
     * @param string 字符串
     */
    private void fillMyMap(String string) {
        //使用中文分词框架word
        List<String> words = splitAndFilterStr(string);
        for (String word : words) {
            if (myMap.containsKey(word)) {
                myMap.put(word, myMap.get(word).add(BigInteger.valueOf(1)));
            } else {
                myMap.put(word, BigInteger.valueOf(1));
            }
            wordsNum = wordsNum.add(BigInteger.valueOf(1));
        }
    }

    /**
     * 分词
     *
     * @param content 内容
     * @return 11种分词结果叠加
     */
    private List<String> splitAndFilterStr(String content) {
        return SegmentationAlgorithmUtil.segmentationAlgorithm(content);
    }


    /**
     * 欧几里得范数
     *
     * @return （求向量的长度）
     */
    private double euclideanNorm() {
        return euclideanNormByMap(myMap);
    }

    /**
     * 通过map取欧几里得范数
     *
     * @param map map
     * @return （向量的长度）
     */
    private double euclideanNormByMap(Map<String, BigInteger> map) {

        BigInteger euclidean = BigInteger.ZERO;

        if (map == null || map.isEmpty()) {
            return 0d;
        }

        for (String key : map.keySet()) {
            euclidean = euclidean.add(map.get(key).multiply(map.get(key)));
        }

        return Math.sqrt(euclidean.doubleValue());
    }

    /**
     * 求点积
     *
     * @param map map内容
     * @return 点击
     */
    private double dotProduct(Map<String, BigInteger> map) {
        double result = 0d;

        if (myMap == null || map == null) {
            return result;
        }

        for (String word : myMap.keySet()) {
            if (map.containsKey(word)) {
                result += myMap.get(word).intValue() * map.get(word).intValue();
            }
        }
        return result;
    }

    /**
     * 距离
     *
     * @param map 另外一个
     * @return 两个不同map的距离
     */
    public double distance(Map<String, BigInteger> map) {

        //任何一个为null或为空都是完全不相等
        if (map == null || map.isEmpty() || myMap == null || myMap.isEmpty()) {
            return Math.PI / 2;
        }

        //取反余弦函数
        return Math.acos(dotProduct(map) / (
                euclideanNorm() * euclideanNormByMap(map)));
    }


    /**
     * 求相似比率
     *
     * @param map 另外一个map
     * @return 两者的相似度
     */
    public double similarityRatio(Map<String, BigInteger> map) {
        return dotProduct(map) / (
                euclideanNorm() * euclideanNormByMap(map));
    }


    /**
     * 取内容
     *
     * @return cosine similarity;
     */
    public Map<String, BigInteger> getMap() {
        return this.myMap == null ? new HashMap<>(0) : new HashMap<>(this.myMap);
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
