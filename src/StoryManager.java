import java.util.HashMap;

public class StoryManager {
    private final Main main;

    public StoryManager(Main main) {
        this.main = main;
    }
    private HashMap<String, Boolean> flags = new HashMap<>();
    private HashMap<String, Integer> variables = new HashMap<>();
    public void setFlag(String flagName, boolean value) {
        flags.put(flagName, value);
    }
    public boolean hasFlag(String flagName) {
        return flags.getOrDefault(flagName, false);
    }

}
