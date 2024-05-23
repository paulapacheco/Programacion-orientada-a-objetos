package namedEntities;

import namedEntities.heuristics.CapitalizedWordHeuristic;
import namedEntities.heuristics.CapitalizedWordOneWord;
import namedEntities.heuristics.CapitalizedWordPoint;
import namedEntities.heuristics.OpenAIHeuristic;
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

    public static List<NamedEntity> computeNamedEntities(String texto, String heuristicName, String jsonFilePath) throws Exception {

        // Extract the candidates from the text using the heuristic
        List<String> candidatos = getCandidatos(texto, heuristicName);

        // Read the JSON dictionary file
        String dictionary = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray dictArray = new JSONArray(dictionary);

        // Create the binary search tree and insert the entities from the JSON file
        BinarySearchTree tree = new BinarySearchTree();
        for (Object entityObject : dictArray) {
            tree.insert((JSONObject) entityObject);
        }

        List<NamedEntity> allEntities = new ArrayList<>();

        for (String candidato : candidatos) {
            // Check if the named entity exists in the JSON file and create the NamedEntity object
            NamedEntity entity = searchNamedEntity(candidato, tree);
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
    private static List<String> getCandidatos(String texto, String heuristicName) throws Exception {
        return switch (heuristicName) {
            case "capitalized" -> CapitalizedWordHeuristic.extractCandidates(texto);
            case "oneCapitalized" -> CapitalizedWordOneWord.extractCandidates(texto);
            case "capitalizedPoint" -> CapitalizedWordPoint.extractCandidates(texto);
            case "openAI" -> OpenAIHeuristic.extractCandidates(texto);
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

    // This method checks if the named entity exists in the JSON file
    private static NamedEntity searchNamedEntity(String candidato, BinarySearchTree tree) {
        JSONObject entityObject = tree.search(candidato);
        if (entityObject != null) {
            String label = entityObject.getString("label");
            String category = entityObject.getString("Category");
            List<String> topics = entityObject.getJSONArray("Topics").toList().stream().map(Object::toString).collect(Collectors.toList());
            return categorizedEntity(label, category, topics);
        }
        return null;
    }
}

class TreeNode {
    JSONObject entityObject;
    TreeNode left;
    TreeNode right;

    TreeNode(JSONObject entityObject) {
        this.entityObject = entityObject;
        this.left = null;
        this.right = null;
    }
}

class BinarySearchTree {
    TreeNode root;

    // Método para insertar nodos en el árbol
    public void insert(JSONObject entityObject) {
        root = insertRec(root, entityObject);
    }

    // Método recursivo para insertar un nuevo nodo en el árbol de búsqueda binario
    TreeNode insertRec(TreeNode root, JSONObject entityObject) {
        if (root == null) {
            root = new TreeNode(entityObject);
            return root;
        }
        int compareResult = entityObject.getString("label").compareTo(root.entityObject.getString("label"));
        if (compareResult < 0) {
            root.left = insertRec(root.left, entityObject);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, entityObject);
        }
        return root;
    }

    // Método para buscar un nodo en el árbol
    public JSONObject search(String label) {
        return searchRec(root, label);
    }

    // Método recursivo para buscar un nodo en el árbol
    JSONObject searchRec(TreeNode root, String label) {
        if (root == null || root.entityObject.getString("label").equals(label)) {
            return root != null ? root.entityObject : null;
        }
        if (root.entityObject.getString("label").compareTo(label) > 0) {
            return searchRec(root.left, label);
        }
        return searchRec(root.right, label);
    }
}
