package namedEntities;

import namedEntities.entitiesManager.NamedEntity;
import java.util.*;

public abstract class ComputeStats {

    public static void computeStatistics(List<NamedEntity> allEntities, String formatStats){
        if (formatStats == null){
            formatStats = "cat";
        }
        switch (formatStats){
            case "cat":
                computeStatisticsCat(allEntities);
                break;
            case "topic":
                computeStatisticsTopic(allEntities);
                break;
            default:
                System.out.println("Invalid format");
                System.exit(1);
        }
    }

    // Compute the statistics for the categories
    private static void computeStatisticsCat(List<NamedEntity> allEntities) {
        int indice;
        // Create a dictionary to store the statistics
        Map<String, List<EntityStats>> categoriesDict = new HashMap<>();

        // Iterate over all the entities
        for (NamedEntity entity : allEntities) {
            String category = entity.getCategory();
            // Check if the category is already in the dictionary, if not, add it
            if (!categoriesDict.containsKey(category)) {
                categoriesDict.put(category, new ArrayList<>());
            }
            // Check if the EntityStats is already in the list of EntityStats for the category
            // If it is, increase the count, if not, add it to the list
            indice = pertenece(entity, categoriesDict.get(category));
            if (indice == -1) {
                categoriesDict.get(category).add(new EntityStats(entity.getLabel(), 1));
            } else {
                categoriesDict.get(category).get(indice).aumentarUno();
            }
        }
        // Print the statistics
        printCategoryStats(categoriesDict);
    }

    // Compute the statistics for the topics
    private static void computeStatisticsTopic(List<NamedEntity> allEntities) {
        int indice;
        // Create a dictionary to store the statistics
        Map<String, List<EntityStats>> topicsDict = new HashMap<>();

        // Iterate over all the entities
        for (NamedEntity entity : allEntities) {
            for (String topic : entity.getTopics()) {
                // Check if the topic is already in the dictionary, if not, add it
                if (!topicsDict.containsKey(topic)) {
                    topicsDict.put(topic, new ArrayList<>());
                }
                // Check if the EntityStats is already in the list of EntityStats for the topic
                // If it is, increase the count, if not, add it to the list
                indice = pertenece(entity, topicsDict.get(topic));
                if (indice == -1) {
                    topicsDict.get(topic).add(new EntityStats(entity.getLabel(), 1));
                } else {
                    topicsDict.get(topic).get(indice).aumentarUno();
                }
            }
        }
        // Print the statistics
        printTopicStats(topicsDict);
    }

    // Check if the entity is already in the list of EntityStats. If it is, return the index, if not, return -1
    private static int pertenece(NamedEntity entity, List<EntityStats> stats) {
        int indice = -1;
        for (int i = 0; i < stats.size() ;i++){
            if (entity.getLabel() != null) {
                if (entity.getLabel().equals(stats.get(i).getStatLabel())){
                    indice = i;
                    break;
                }
            }
        }
        return indice;
    }

    // Print the statistics for the categories
    private static void printCategoryStats(Map<String, List<EntityStats>> stats) {
        List<String> lista = Arrays.asList("PERSON", "LOCATION", "ORGANIZATION", "EVENT", "OTHER");
        printStats(stats, lista, "Category");
    }

    // Print the statistics for the topics
    private static void printTopicStats(Map<String, List<EntityStats>> stats) {
        List<String> lista = Arrays.asList("POLITICS", "SPORTS", "ECONOMICS", "HEALTH", "TECHNOLOGY", "ENTERTAINMENT", "BUSINESS", "OTHER");
        printStats(stats, lista, "Topic");
    }

    // Print the statistics for the categories or topics
    private static void printStats(Map<String, List<EntityStats>> stats, List<String> keys, String format) {
        for (String key : keys) {
            if (stats.containsKey(key)) {
                System.out.println("\n" + format + " : " + key);
                for (EntityStats entityStats : stats.get(key)) {
                    System.out.println("         " + entityStats.getStatLabel() + " (" + entityStats.getCont() + ")");
                }
            }
        }
    }
}

class EntityStats {
    private String statLabel;
    private int count;

    public EntityStats(String label, int count) {
        this.statLabel = label;
        this.count = count;
    }

    String getStatLabel() {
        return this.statLabel;
    }

    int getCont() {
        return this.count;
    }

    void aumentarUno() {
        this.count++;
    }
}

