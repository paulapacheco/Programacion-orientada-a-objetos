package namedEntities;

import namedEntities.heuristics.CapitalizedWordHeuristic;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComputeNE {

    public static List<NamedEntity> computeNamedEntities(String texto, String heuristicName, String jsonFilePath) throws IOException {

        // Extract the candidates from the text using the heuristic
        List<String> candidatos = getCandidatos(texto, heuristicName);

        // Read the JSON dictionary file
        String dictionary = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray dictArray = new JSONArray(dictionary);

        List<NamedEntity> allEntities = new ArrayList<>();

        for (String candidato : candidatos) {
            // Check if the named entity exists in the JSON file and create the NamedEntity object
            NamedEntity namedEnt = searchNamedEntity(candidato, dictArray);
            if (namedEnt != null) {
                allEntities.add(namedEnt);
            } else {
                namedEnt = categorizedEntity(candidato, "OTHER", Collections.singletonList("OTHER"));
                allEntities.add(namedEnt);
            }
        }
        return allEntities;
    }

    // This method extracts the candidates from the text using the heuristic
    private static List<String> getCandidatos(String texto, String heuristicName) {
        List<String> candidatos = new ArrayList<>();
        switch (heuristicName) {
            case "capitalized":
                candidatos = CapitalizedWordHeuristic.extractCandidates(texto);
                break;
            // TODO: Add more cases for other heuristics
        }
        return candidatos;
    }

    // This method categorizes the named entity based on the category
    private static NamedEntity categorizedEntity(String label, String category, List<String> topics) {
        NamedEntity ne = null;
        // Create the NamedEntity object based on the category
        switch (category) {
            case "LOCATION":
                ne = new LocationNE(label, category, topics);
                break;
            case "PERSON":
                ne = new PersonNE(label, category, topics);
                break;
            case "ORGANIZATION":
                ne = new OrganizationNE(label, category, topics);
                break;
            case "OTHER":
                ne = new OtherNE(label, category, topics);
                break;
            case "EVENT":
                ne = new EventNE(label, category, topics);
                break;
        }
        return ne;
    }

    // TODO: Add a tree search to improve the performance of this method
    // This method checks if the named entity exists in the JSON file
    private static NamedEntity searchNamedEntity(String candidato, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray arrayKeywords = (JSONArray) jsonObject.get("keywords");
            for (int j = 0; j < arrayKeywords.length(); j++) {
                // Check if the candidate exists in the JSONArray of keywords
                if (arrayKeywords.getString(j).equals(candidato)) {
                    String label = jsonObject.getString("label");
                    String category = jsonObject.getString("Category");
                    // Change the topics from JSONArray to List<String>
                    JSONArray arrayTopics = jsonObject.getJSONArray("Topics");
                    List<String> topics = new ArrayList<>();
                    for (int k = 0; k < arrayTopics.length(); k++) {
                        topics.add(arrayTopics.getString(k));
                    }
                    // Create the NamedEntity object
                    NamedEntity namedEnt = categorizedEntity(label, category, topics);
                    return namedEnt;
                }
            }

        }
        return null;
    }

}
