package feed;

import org.junit.jupiter.api.Test;

import java.util.List;

import utils.JSONParser;

public class ParseXMLFromURLTest {
    // quiero un codigo de unit test para probar el metodo parseXMLFromURL
    @Test
    public void testParseXMLFromURL() throws Exception {
        // Llamar al metodo parseXMLFromURL
        String claveFeed = "lmgral";
        List<Article> articles = FeedParser.getArticlesFromFeeds(JSONParser.parseJsonFeedsData("src/data/feeds.json"), claveFeed);
        // Verificar que la lista de articulos no este vacia
        if (!articles.isEmpty()) {
            System.out.println("Funciona parseXMLFromURL!");
            System.out.println("Cantidad de articulos: " + articles.size());
        }
    }
}
