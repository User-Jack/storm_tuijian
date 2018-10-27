package com.qianfeng.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 迄今为止，在个性化推荐系统中，协同过滤技术是应用最成功的技术。
 * 基于用户的协同过滤算法
 * 第一代的协同过滤算法
 */
public class BaseUserRecommenderDemo {
    /**
     * 1、准备数据
     * 2、计算相似度
     * 3、计算最近邻域
     * 4、计算推荐结果
     * @param args
     */
    public static void main(String[] args) throws IOException, TasteException {
        //1、准备数据，这里用的是电影评分数据
        File file = new File("G:\\ml-10M100K\\ratings.dat");

        //2、将数据加载到内存，GroupLensDataModel是针对电影评分数据
        GroupLensDataModel dataModel = new GroupLensDataModel(file);

        //3、计算相似度，相似度的算法有很多，常用的欧几里得、皮尔逊等
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

        //4、计算最近邻域，计算方法有两种：1、固定数量，2、固定阈值，这里使用固定数量的算法
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);

        //5、构造推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的协同过滤算法，
        //这里使用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);

        //6、给用户5推荐10个电影
        List<RecommendedItem> itemList = recommender.recommend(5, 10);

        //7、打印推荐结果
        for (RecommendedItem item : itemList
             ) {
            System.out.println(item);
        }
    }
}
