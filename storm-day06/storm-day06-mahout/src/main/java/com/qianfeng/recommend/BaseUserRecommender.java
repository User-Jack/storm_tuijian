package com.qianfeng.recommend;

import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 迄今为止在个性化推荐系统中，协同过滤（Collaborative Filtering）技术是应用最成功的技术。
 * 目前国内外互联网上有许多大型网站已经应用这项技术为用户更加智能的推荐内容。
 * 如果你想要研究协同过滤，一定不能错过MovieLens（http://movielens.umn.edu/）。它是协同过滤最著名的研究项目之一。
 * 第一代协同过滤算法
 * Author:   chenfenggao
 * Domain:   www.1000phone.com
 * Data:     2015/11/26.
 */
public class BaseUserRecommender {
    /**
     * 1、准备数据   2、计算相似度  3、选择最近邻域 4、计算推荐结果
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //准备数据 这里是电影评分数据
        File file = new File("F:\\BigData\\ml-10M100K\\ratings.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        long start = System.currentTimeMillis();
        //给用户ID等于5的用户推荐10部电影
        List<RecommendedItem> recommendedItemList = recommender.recommend(5, 10);
        //打印推荐的结果
        System.out.println("使用基于用户的协同过滤算法");
        System.out.println("为用户5推荐10个商品");
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis()-start);
        start = System.currentTimeMillis();
        List<RecommendedItem> recommendedItemList1 = recommender.recommend(6, 10);
        //打印推荐的结果
        System.out.println("使用基于用户的协同过滤算法");
        System.out.println("为用户6推荐10个商品");
        for (RecommendedItem recommendedItem : recommendedItemList1) {
            System.out.println(recommendedItem);
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
