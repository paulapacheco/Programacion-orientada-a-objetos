package namedEntities;

import namedEntities.heuristics.CapitalizedWordHeuristic;
import namedEntities.heuristics.CapitalizedWordOneWord;
import namedEntities.heuristics.CapitalizedWordPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return switch (heuristicName) {
            case "capitalized" -> CapitalizedWordHeuristic.extractCandidates(texto);
            case "oneCapitalized" -> CapitalizedWordOneWord.extractCandidates(texto);
            case "capitalizedPoint" -> CapitalizedWordPoint.extractCandidates(texto);
            default -> throw new IllegalArgumentException("Heuristic not found");
        };
    }

    // This method categorizes the named entity based on the category
    private static NamedEntity categorizedEntity(String label, String category, List<String> topics) {
        // Create the NamedEntity object based on the category
        return switch (category) {
            case "PERSON" -> new PersonNE(label, category, topics);
            case "LOCATION" -> new LocationNE(label, category, topics);
            case "ORGANIZATION" -> new OrganizationNE(label, category, topics);
            case "OTHER" -> new OtherNE(label, category, topics);
            case "EVENT" -> new EventNE(label, category, topics);
            default -> null;
        };
    }

    // TODO: Add a tree search to improve the performance of this method
    // This method checks if the named entity exists in the JSON file
    private static NamedEntity searchNamedEntity(String candidato, JSONArray jsonArray) {
        for (Object jsonObject : jsonArray) {
            JSONArray arrayKeywords = (JSONArray) ((JSONObject) jsonObject).get("keywords");
            for (Object keyword : arrayKeywords) {
                if (keyword.toString().equals(candidato)) {
                    String label = ((JSONObject) jsonObject).getString("label");
                    String category = ((JSONObject) jsonObject).getString("Category");
                    List<String> topics = ((JSONObject) jsonObject).getJSONArray("Topics").toList().stream().map(Object::toString).collect(Collectors.toList());
                    return categorizedEntity(label, category, topics);
                }
            }
        }
        return null;
    }
}
