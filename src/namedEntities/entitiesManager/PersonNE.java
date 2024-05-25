package namedEntities.entitiesManager;

import java.util.List;

public class PersonNE extends NamedEntity {

    private String name;
    private int age;

    public PersonNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}
