package feed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class FeedParserTest {

    @Test
    public void testParseXML() {
        String xmlData = "<rss><channel><item><title>Test Title</title><description>Test Description</description><link>http://test.com</link><pubDate>Test Date</pubDate></item></channel></rss>";
        List<Article> articles = FeedParser.parseXML(xmlData);

        if (articles.size() == 1) {
            System.out.println("Funciona!");
        }

        Article article = articles.get(0);
        assertEquals("Test Title", article.getTitle());
        assertEquals("Test Description", article.getDescription());
        assertEquals("http://test.com", article.getLink());
        assertEquals("Test Date", article.getPubDate());
    }
}
