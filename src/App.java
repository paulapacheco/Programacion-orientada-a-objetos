import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import feed.Article;
import feed.FeedParser;
import utils.Config;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;
import namedEntities.NamedEntity;
import namedEntities.ComputeNE;
import namedEntities.ComputeStats;

import java.net.*;
import java.io.*;

public class App {

    public static void main(String[] args) throws Exception {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            // Lista de objetos FeedsData, que contienen la información de los feeds
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(feedsDataArray, args);

        run(config, feedsDataArray);
    }

    // TODO: Change the signature of this function if needed
    private static void run(Config config, List<FeedsData> feedsDataArray) throws Exception {

        // Check if feedsDataArray is empty
        if (feedsDataArray == null || feedsDataArray.isEmpty()) {
            System.out.println("No feeds data found");
            return;
        }

        // Load all articles from the feed(s)
        List<Article> allArticles;
        if (config.getFeedKey() != null) {
            allArticles = FeedParser.getArticlesFromFeeds(feedsDataArray, config.getFeedKey());
        } else {
            allArticles = FeedParser.getArticlesFromFeeds(feedsDataArray, null);
        }

        // Print the articles
        if (config.getPrintFeed() || !config.getComputeNamedEntities()) {
             for (Article article : allArticles) {
                article.printArticle();
            }
            System.out.println(allArticles.size() + " articles printed from " + (config.getFeedKey() != null ? config.getFeedKey() : "all feeds"));
        }


        // Compute named entities
        if (config.getComputeNamedEntities()) {
            // TODO: complete the message with the selected heuristic name
            System.out.println("Computing named entities using " + config.getHeuristicConfig());


            // TODO: compute named entities using the selected heuristic
            String text;
            List<NamedEntity> entities = new ArrayList<>();
            List<NamedEntity> allNamedEntities = new ArrayList<>();

            for (int i = 0; i<allArticles.size() ;i++){

                text = allArticles.get(i).getTitle() + " " +  allArticles.get(i).getDescription();

                entities = ComputeNE.computeNamedEntities(text, config.getHeuristicConfig(), "src/data/dictionary.json");
                
                allNamedEntities.addAll(entities);
            } 

             // TODO: Print stats

            System.out.println("\nStats: ");

            ComputeStats.computeStatistics(allNamedEntities, config.getStatsFormat());
        } 

        System.out.println("-".repeat(80));
    }
        


}

        // TODO Implement the stats format option

        // Maybe we should check the stats format option first and then compute named entities option




   

