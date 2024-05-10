package utils;

public class Config {
    private boolean printFeed = false;
    private boolean computeNamedEntities = false;
    private String feedKey;
    // TODO: A reference to the used heuristic and the stat format flag will be needed here
    //private String heuristicConfig;
    //private boolean statsFormat = false;

    // TODO: Add the heuristicConfig and statsFormat to the constructor
    public Config(boolean printFeed, boolean computeNamedEntities, String feedKey) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.feedKey = feedKey;
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

    // TODO: Add the getter methods for the heuristicConfig and statsFormat

}
