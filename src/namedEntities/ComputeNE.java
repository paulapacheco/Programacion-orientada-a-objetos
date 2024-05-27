package namedEntities;

import namedEntities.entitiesManager.*;
import namedEntities.heuristics.CapitalizedWordHeuristic;
import namedEntities.heuristics.CapitalizedWordOneWord;
import namedEntities.heuristics.CapitalizedWordPoint;
import utils.DictData;
import utils.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComputeNE {

    public static List<NamedEntity> computeNamedEntities(String texto, String heuristicName, String jsonFilePath) {

        // Extract the candidates from the text using the heuristic
        List<String> candidatos = getCandidatos(texto, heuristicName);

        // Read the JSON dictionary file and create a list of DictData objects
        List<DictData> dict = new ArrayList<>();
        try {
            dict = JSONParser.parseJsonDictData(jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<NamedEntity> allEntities = new ArrayList<>();

        for (String candidato : candidatos) {
            // Check if the named entity exists in the JSON file and create the NamedEntity object
            NamedEntity entity = searchNamedEntity(candidato, dict);
            if (entity != null) {
                allEntities.add(entity);
            } else {
                entity = categorizedEntity(candidato, "OTHER", Collections.singletonList("OTHER"));
                allEntities.add(entity);
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

    // This method checks if the named entity exists in the dictionary
    private static NamedEntity searchNamedEntity(String candidato, List<DictData> dict) {
        for (DictData elem : dict) {
            for (String keyword : elem.getKeywords()) {
                if (keyword.equals(candidato)) {
                    return categorizedEntity(elem.getLabel(), elem.getCategory(), elem.getTopics());
                }
            }
        }
        return null;
    }
}
