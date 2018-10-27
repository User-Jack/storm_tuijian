package com.qianfeng.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 基于物品的协同过滤算法
 * 第二代协同过滤算法
 */
public class BaseItemRecommenderDemo {
    /**
     * 1、得到基于物品的历史评分数据
     * 2、针对物品进行物品的相似度计算，找到物品的最近邻居
     * 3、计算推荐结果
     * @param args
     */
    public static void main(String[] args) throws IOException, TasteException {
        //1、准备数据，这里用的是电影评分数据
        File file = new File("F:\\BigData\\ml-10M100K\\ratings.dat");

        //2、将数据加载到内存，GroupLensDataModel是针对电影评分数据
        GroupLensDataModel dataModel = new GroupLensDataModel(file);

        //3、计算物品的相似度
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

        //4、构造推荐器，这里使用基于物品的协同过滤推荐
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

        //5、给用户5推荐10个与2198相似的商品
        List<RecommendedItem> itemList = recommender.recommendedBecause(5, 2198, 10);

        //6、打印推荐结果
        for (RecommendedItem item : itemList
             ) {
            System.out.println(item);
        }
    }
}
