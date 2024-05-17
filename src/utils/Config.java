package utils;

public class Config {
    private boolean printFeed = false;
    private boolean computeNamedEntities = false;
    private String feedKey;
    private String heuristicConfig;
    // TODO: A reference to the stat format flag will be needed here
    //private boolean statsFormat = false;

    // TODO: Add statsFormat to the constructor
    public Config(boolean printFeed, boolean computeNamedEntities, String feedKey, String heuristicConfig) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.feedKey = feedKey;
        this.heuristicConfig = heuristicConfig;
    }

    public boolean getPrintFeed() {
        return printFeed;
    }

    public boolean getComputeNamedEntities() {
        return computeNamedEntities;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getHeuristicConfig() {
        return heuristicConfig;
    }

    // TODO: Add the getter method for statsFormat

}
