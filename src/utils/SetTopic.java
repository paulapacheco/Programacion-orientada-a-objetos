package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetTopic {
    private Set<Topic> topics;

    // Constructor
    public SetTopic() {
        this.topics = new HashSet<>();
    }

    // Add a topic to the set
    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }

    // Create a SetTopic object from a list of topics (strings)
    public static SetTopic topicSetFromList(List<String> listTopics) {
        SetTopic topicSet = new SetTopic();
        for (String topic : listTopics) {
            topicSet.addTopic(new Topic(topic));
        }
        return topicSet;
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
