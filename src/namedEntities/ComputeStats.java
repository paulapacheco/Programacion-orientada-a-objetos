package namedEntities;

import java.util.ArrayList;
import java.util.List;

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

    private static void computeStatisticsCat(List<NamedEntity> allEntities) {
        int indice;

        List<EntityStats> person  = new ArrayList<>();
        List<EntityStats> location  = new ArrayList<>();
        List<EntityStats> organization  = new ArrayList<>();
        List<EntityStats> event = new ArrayList<>();
        List<EntityStats> otherCategory  = new ArrayList<>();

        for (NamedEntity entity : allEntities) {
            String option = entity.getCategory();
            switch (option) {
                case "PERSON":
                    indice = pertenece(entity, person);
                    if (indice == -1) {
                        person.add(new EntityStats(entity.getLabel(), 1));
                    } else {
                        person.get(indice).aumentarUno();
                    }
                    break;
                case "LOCATION":
                    indice = pertenece(entity, location);
                    if (indice == -1) {
                        location.add(new EntityStats(entity.getLabel(), 1));
                    } else {
                        location.get(indice).aumentarUno();
                    }
                    break;
                case "ORGANIZATION":
                    indice = pertenece(entity, organization);
                    if (indice == -1) {
                        organization.add(new EntityStats(entity.getLabel(), 1));
                    } else {
                        organization.get(indice).aumentarUno();
                    }
                    break;
                case "EVENT":
                    indice = pertenece(entity, event);
                    if (indice == -1) {
                        event.add(new EntityStats(entity.getLabel(), 1));
                    } else {
                        event.get(indice).aumentarUno();
                    }
                    break;
                case "OTHER":
                    indice = pertenece(entity, otherCategory);
                    if (indice == -1) {
                        otherCategory.add(new EntityStats(entity.getLabel(), 1));
                    } else {
                        otherCategory.get(indice).aumentarUno();
                    }
                    break;
            }
        }

        printCategoryStats(person, "PERSON");
        printCategoryStats(location, "LOCATION");
        printCategoryStats(organization, "ORGANIZATION");
        printCategoryStats(event, "EVENT");
        printCategoryStats(otherCategory, "OTHER");
    }

    private static void computeStatisticsTopic(List<NamedEntity> allEntities) {

        int indice;

        List<EntityStats> politics  = new ArrayList<>();
        List<EntityStats> sports  = new ArrayList<>();
        List<EntityStats> economics = new ArrayList<>();
        List<EntityStats> health  = new ArrayList<>();
        List<EntityStats> technology  = new ArrayList<>();
        List<EntityStats> entertainment = new ArrayList<>();
        List<EntityStats> business = new ArrayList<>();
        List<EntityStats> otherTopics  = new ArrayList<>();

        for (NamedEntity entity : allEntities) {
            for (String option : entity.getTopics()) {
                switch (option) {
                    case "POLITICS":
                        indice = pertenece(entity, politics);
                        if (indice == -1) {
                            politics.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            politics.get(indice).aumentarUno();
                        }
                        break;

                    case "SPORTS":
                        indice = pertenece(entity, sports);
                        if (indice == -1) {
                            sports.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            sports.get(indice).aumentarUno();
                        }
                        break;

                    case "ECONOMICS":
                        indice = pertenece(entity, economics);
                        if (indice == -1) {
                            economics.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            economics.get(indice).aumentarUno();
                        }
                        break;

                    case "HEALTH":
                        indice = pertenece(entity, health);
                        if (indice == -1) {
                            health.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            health.get(indice).aumentarUno();
                        }
                        break;

                    case "TECHNOLOGY":
                        indice = pertenece(entity, technology);
                        if (indice == -1) {
                            technology.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            technology.get(indice).aumentarUno();
                        }
                        break;

                    case "BUSINESS":
                        indice = pertenece(entity, business);
                        if (indice == -1) {
                            business.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            business.get(indice).aumentarUno();
                        }
                        break;

                    case "ENTERTAINMENT":
                        indice = pertenece(entity, entertainment);
                        if (indice == -1) {
                            entertainment.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            entertainment.get(indice).aumentarUno();
                        }
                        break;

                    case "OTHER":
                        indice = pertenece(entity, otherTopics);
                        if (indice == -1) {
                            otherTopics.add(new EntityStats(entity.getLabel(), 1));
                        } else {
                            otherTopics.get(indice).aumentarUno();
                        }
                        break;
                }
            }
        }

        printTopicStats(politics, "POLITICS");
        printTopicStats(sports, "SPORTS");
        printTopicStats(economics, "ECONOMICS");
        printTopicStats(health, "HEALTH");
        printTopicStats(technology, "TECHNOLOGY");
        printTopicStats(entertainment, "ENTERTAINMENT");
        printTopicStats(business, "BUSINESS");
        printTopicStats(otherTopics, "OTHER");
    }

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

    private static void printCategoryStats(List<EntityStats> stats, String category) {
        System.out.println("\n Category: " + category);
        for (EntityStats entityStats : stats) {
            System.out.println("         " + entityStats.getStatLabel() + " (" + Integer.toString(entityStats.getCont()) + ")");
        }
    }

    private static void printTopicStats(List<EntityStats> stats, String topic) {
        System.out.println("\n Topic: " + topic);
        for (EntityStats entityStats : stats) {
            System.out.println("         " + entityStats.getStatLabel() + " (" + Integer.toString(entityStats.getCont()) + ")");
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

    public String getStatLabel() {
        return this.statLabel;
    }

    public int getCont() {
        return this.count;
    }

    public void aumentarUno() {
        this.count++;
    }
}

