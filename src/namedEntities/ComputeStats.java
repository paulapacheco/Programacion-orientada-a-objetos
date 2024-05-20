package namedEntities;

import java.util.ArrayList;
import java.util.List;


public class ComputeStats {

    private String label; 
    private int cont = 0;

    public ComputeStats(String label, int cont) {
        this.label = label;
        this.cont = cont;
    }

    public String getName() {
        return this.label;
    }

    public int getCont() {
        return this.cont;
    }

    public void aumentarUno(){
        this.cont ++;
    }


    public static void computeStatistics(List<NamedEntity> allEntities, String formatStats){
        switch (formatStats){
            case "cat":
                computeStatisticsCat(allEntities);
                break;

            case "topic":
                computeStatisticsTopic(allEntities);
                break;
        }
    }


    private static void computeStatisticsCat(List<NamedEntity> allEntities){
            
        NamedEntity entity;
        int indice;
        String option;

        List<ComputeStats> person  = new ArrayList<>();  
        List<ComputeStats> location  = new ArrayList<>();  
        List<ComputeStats> organization  = new ArrayList<>();  
        List<ComputeStats> otherCategory  = new ArrayList<>();  


        for (int i = 0; i < allEntities.size() ;i++){
                    
            entity = allEntities.get(i);

            option = entity.getCategory();

            switch (option){

                case "PERSON":
                    indice = pertenece(entity, person);
                    if (indice == -1){
                        ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                        person.add(newEntity);
                    }
                    else{
                        person.get(indice).aumentarUno();
                    }
                    break;

                case "LOCATION":
                    indice = pertenece(entity, location);
                    if (indice == -1){
                        ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                        location.add(newEntity);
                    }
                    else{
                        location.get(indice).aumentarUno();
                    }
                    break;


                case "ORGANIZATION":
                    indice = pertenece(entity, organization);
                    if (indice == -1){
                        ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                        organization.add(newEntity);
                    }
                    else{
                        organization.get(indice).aumentarUno();                  
                    }
                    break;        

                case "OTHER":
                    indice = pertenece(entity, otherCategory);
                    if (indice == -1){
                        ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                        otherCategory.add(newEntity);
                    }
                    else{
                        otherCategory.get(indice).aumentarUno();
                    }
                    break;
                }

            }

            System.out.println("Category: PERSON");
            for (int i = 0; i < person.size() ; i++){
                System.out.println("         " + person.get(i).getName() + " (" + Integer.toString(person.get(i).getCont()) + ")");
            }  

            System.out.println("\n Category: LOCATION");
            for (int i = 0; i < location.size() ; i++){
                System.out.println("         " + location.get(i).getName() + " (" + Integer.toString(location.get(i).getCont()) + ")");
               
            }  

            System.out.println("\n Category: ORGANIZATION");
            for (int i = 0; i < organization.size() ; i++){
                System.out.println("         " + organization.get(i).getName() + " (" + Integer.toString(organization.get(i).getCont()) + ")");
            }  

            System.out.println("\n Category: OTHER");
            for (int i = 0; i < otherCategory.size() ; i++){
                System.out.println("         " + otherCategory.get(i).getName() + " (" + Integer.toString(otherCategory.get(i).getCont()) + ")");
            }       
    }      


    private static void computeStatisticsTopic(List<NamedEntity> allEntities){
            
        NamedEntity entity;
        int indice;
        String option;

        List<ComputeStats> politics  = new ArrayList<>();  
        List<ComputeStats> sports  = new ArrayList<>();  
        List<ComputeStats> economy = new ArrayList<>();  
        List<ComputeStats> health  = new ArrayList<>();  
        List<ComputeStats> technology  = new ArrayList<>();  
        List<ComputeStats> culture = new ArrayList<>();  
        List<ComputeStats> otherTopics  = new ArrayList<>(); 


        for (int i = 0; i < allEntities.size() ;i++){              
            entity = allEntities.get(i);
            for (int j = 0; j < entity.getTopics().size(); j++){
                option = entity.getTopics().get(j);
                switch (option){

                    case "POLITICS":
                        indice = pertenece(entity, politics);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            politics.add(newEntity);
                        }
                        else{
                            politics.get(indice).aumentarUno();
                        }
                        break;

                    case "SPORTS":
                        indice = pertenece(entity, sports);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            sports.add(newEntity);
                        }
                        else{
                            sports.get(indice).aumentarUno();
                        }
                        break;

                    case "ECONOMY":
                        indice = pertenece(entity, economy);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            economy.add(newEntity);
                        }
                        else{
                            economy.get(indice).aumentarUno();                            
                        }
                        break; 

                    case "HEALTH":
                        indice = pertenece(entity, health);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            health.add(newEntity);
                        }
                        else{
                            health.get(indice).aumentarUno();                            
                        }
                        break;  
                    
                    case "TECHNOLOGY":
                        indice = pertenece(entity, technology);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            technology.add(newEntity);
                        }
                        else{
                            technology.get(indice).aumentarUno();
                        }
                        break; 

                    case "CULTURE":
                        indice = pertenece(entity, culture);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            culture.add(newEntity);
                        }
                        else{
                            culture.get(indice).aumentarUno();
                        }
                        break;  

                    case "OTHER":

                        indice = pertenece(entity, otherTopics);
                        if (indice == -1){
                            ComputeStats newEntity = new ComputeStats(entity.getLabel(), 1);
                            otherTopics.add(newEntity);
                        }
                        else{
                            otherTopics.get(indice).aumentarUno();
                        }
                        break;
                    }

                }
            }

            System.out.println("Topic: POLITICS");
            for (int i = 0; i < politics.size() ; i++){
                System.out.println("         " + politics.get(i).getName() + " (" + Integer.toString(politics.get(i).getCont()) + ")");
            }  

            System.out.println("\n Topic SPORTS");
            for (int i = 0; i < sports.size() ; i++){
                System.out.println("         " + sports.get(i).getName() + " (" + Integer.toString(sports.get(i).getCont()) + ")");
            }  

            System.out.println("\n Topic ECONOMY");
            for (int i = 0; i < economy.size() ; i++){
                System.out.println("         " + economy.get(i).getName() + " (" + Integer.toString(economy.get(i).getCont()) + ")");
            }  

            System.out.println("\n Topic HEALTH");
            for (int i = 0; i < health.size() ; i++){
                System.out.println("         " + health.get(i).getName() + " (" + Integer.toString(health.get(i).getCont()) + ")");
            }  

            System.out.println("\n Topic TECHNOLOGY");
            for (int i = 0; i < technology.size() ; i++){
                System.out.println("         " + technology.get(i).getName() + " (" + Integer.toString(technology.get(i).getCont()) + ")");
            }  

            System.out.println("\n Topic CULTURE");
            for (int i = 0; i < culture.size() ; i++){
                System.out.println("         " + culture.get(i).getName() + " (" + Integer.toString(culture.get(i).getCont()) + ")");
            }  

            System.out.println("\n Category: OTHER");
            for (int i = 0; i < otherTopics.size() ; i++){
                System.out.println("         " + otherTopics.get(i).getName() + " (" + Integer.toString(otherTopics.get(i).getCont()) + ")");
            }       

    }          
    

    private static int pertenece(NamedEntity entity, List<ComputeStats> stats){

        int indice = -1;

        for (int i = 0; i < stats.size() ;i++){
            if (entity.getLabel() != null){
                 if (entity.getLabel().equals(stats.get(i).getName())){
                    indice = i;
                    break;
                }
            }
        }
        return indice;
    }
}

