import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import feed.Article;
import feed.FeedParser;
import namedEntities.ComputeNE;
import namedEntities.NamedEntity;
import namedEntities.heuristics.CapitalizedWordHeuristic;
import utils.Config;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;

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
            printAllArticles(allArticles, config);
        }

        // Compute named entities
        if (config.getComputeNamedEntities()) {
            // TODO: complete the message with the selected heuristic name
            String heuristicName = config.getHeuristicConfig();  // "capitalized" setted to use the CapitalizedWordHeuristic
            System.out.println("Computing named entities using " + heuristicName + " heuristic...");
            List<NamedEntity> allNamedEntities = new ArrayList<>();

            for (Article article : allArticles) {
                List<NamedEntity> namedEntities = new ArrayList<>();
                String texto = article.getTitle() + " " + article.getDescription();
                namedEntities = ComputeNE.computeNamedEntities(texto, heuristicName, "src/data/dictionary.json");
                allNamedEntities.addAll(namedEntities);
            }

            // TODO: Print stats
            System.out.println("\nStats: ");
            System.out.println("-".repeat(80));
        }

        // TODO Implement the stats format option

    }

    // Print all articles from the list
    private static void printAllArticles(List<Article> allArticles, Config config) {
        for (Article article : allArticles) {
            article.printArticle();
        }
        System.out.println(allArticles.size() + " articles printed from " + (config.getFeedKey() != null ? config.getFeedKey() : "all feeds"));
    }
}
