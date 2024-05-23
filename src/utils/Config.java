package utils;

public class Config {
    private boolean printFeed = false;
    private boolean computeNamedEntities = false;
    private String feedKey;
    private String heuristicConfig;
    private String statsFormat;

    public Config(boolean printFeed, boolean computeNamedEntities, String feedKey, String heuristicConfig, String statsFormat) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.feedKey = feedKey;
        this.heuristicConfig = heuristicConfig;
        this.statsFormat = statsFormat; 
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

    public String getStatsFormat() {
        return statsFormat;
    }
}