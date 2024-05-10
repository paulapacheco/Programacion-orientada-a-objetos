import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import feed.Article;
import feed.FeedParser;
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

        List<Article> Allarticles;
        if (config.getPrintFeed()) {
            Allarticles = loadArticles();
            if (Allarticles == null) {
                System.out.println("No articles found");
                System.exit(1);
            } else {
                System.out.println("Printing feed(s): ");
                for (Article article : Allarticles) {
                    article.printArticle();
                }
                System.out.println(Allarticles.size() + " articles printed");
            }
        } else if (config.getComputeNamedEntities()) {
            // TODO: Compute named entities
        } else {
            Allarticles = run(config, feedsDataArray);
            saveArticles(Allarticles);
        }
    }

    // TODO: Change the signature of this function if needed
    private static List<Article> run(Config config, List<FeedsData> feedsDataArray) throws Exception {

        if (feedsDataArray == null || feedsDataArray.isEmpty()) {
            System.out.println("No feeds data found");
            return null;
        }

        List<Article> allArticles = FeedParser.getArticlesFromFeeds(feedsDataArray, config.getFeedKey());

        if (config.getComputeNamedEntities()) {
            // TODO: complete the message with the selected heuristic name
            System.out.println("Computing named entities using ");

            // TODO: compute named entities using the selected heuristic

            // TODO: Print stats
            System.out.println("\nStats: ");
            System.out.println("-".repeat(80));
        }
        return allArticles;
    }

    private static void saveArticles(List<Article> articles) {
        try {
            FileOutputStream fileOut = new FileOutputStream("articles.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(articles);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Article> loadArticles() {
        List<Article> articles = null;
        try {
            try {
                File file = new File("articles.ser");
                if (!file.exists()) {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            FileInputStream fileIn = new FileInputStream("articles.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            articles = (List<Article>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Article class not found");
            c.printStackTrace();
            return null;
        }
        return articles;
    }
}
