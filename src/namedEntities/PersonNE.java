package namedEntities;

import java.util.List;

public class PersonNE extends NamedEntity{

    // TODO: Add other attributes
    private String name;
    private int age;

    public PersonNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}
