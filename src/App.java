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
            System.out.println("Computing named entities using " + config.getHeuristicConfig());
            List<NamedEntity> entities = new ArrayList<>();
            List<NamedEntity> allNamedEntities = new ArrayList<>();

            for (Article article : allArticles) {
                String text = article.getTitle() + ". " + article.getDescription();
                entities = ComputeNE.computeNamedEntities(text, config.getHeuristicConfig(), "src/data/dictionary.json");
                allNamedEntities.addAll(entities);
            }

            System.out.println("\nStats: ");
            ComputeStats.computeStatistics(allNamedEntities, config.getStatsFormat());
        }
        System.out.println("-".repeat(80));
    }
}