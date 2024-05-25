package namedEntities.entitiesManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Topic {
    private final String topic;

    // Constructor
    public Topic (String topic) {
        this.topic = topic;
    }

    // Returns the topic
    public String getTopicString() {
        return this.topic;
    }
}

class SetTopic {
    private Set<Topic> topics;

    // Constructor
    public SetTopic() {
        this.topics = new HashSet<>();
    }

    // Add a topic to the set
    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }

    // Returns the list of topics
    public List<String> getListTopics() {
        List<String> listTopics = new ArrayList<>();
        for (Topic topic : this.topics) {
            listTopics.add(topic.getTopicString());
        }
        return listTopics;
    }

    // Check if the set contains a topic
    public boolean containsTopic(String topic) {
        for (Topic t : this.topics) {
            if (t.getTopicString().equals(topic)) {
                return true;
            }
        }
        return false;
    }

    // Remove a topic from the set
    public void removeTopic(String topic) {
        for (Topic t : this.topics) {
            if (t.getTopicString().equals(topic)) {
                this.topics.remove(t);
                return;
            }
        }
    }
}
