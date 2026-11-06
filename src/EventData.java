import java.util.List;

public class EventData {
    public String id;
    public String speaker;
    public List<String> dialogue;
    public String requiredFlag;     // Optionale Bedingung (z.B. "met_finja")
    public List<String> dialogueIfTrue; // Alternativdialog, wenn requiredFlag == true
    public String setFlagOnEnd;     // Flag, das nach dem Gespräch auf true gesetzt wird
    public String nextEventId;      // Für Event-Ketten / Folge-Events

}
