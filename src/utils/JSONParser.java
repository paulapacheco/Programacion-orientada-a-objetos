package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

    static public List<FeedsData> parseJsonFeedsData(String jsonFilePath) throws IOException {
        JSONArray jsonArray = new JSONArray(Files.readString(Paths.get(jsonFilePath)));
        List<FeedsData> feedsList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            String url = jsonObject.getString("url");
            String type = jsonObject.getString("type");
            feedsList.add(new FeedsData(label, url, type));
        }
        return feedsList;
    }

    static public List<DictData> parseJsonDictData(String jsonFilePath) throws IOException {
        JSONArray jsonArray = new JSONArray(Files.readString(Paths.get(jsonFilePath)));
        List<DictData> dictList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            String category = jsonObject.getString("Category");
            List<String> topics = jsonObject.getJSONArray("Topics").toList().stream().map(Object::toString).collect(Collectors.toList());
            List<String> keywords = jsonObject.getJSONArray("keywords").toList().stream().map(Object::toString).toList();
            dictList.add(new DictData(label, category, topics, keywords));
        }
        return dictList;
    }

}