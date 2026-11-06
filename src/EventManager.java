import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private final Main main;
    private Map<String, EventData> events = new HashMap<>();

    public EventManager(Main main) {
        this.main = main;
        loadEventsFromJSON("Miscellaneous/Story/events.json");
    }

    private void loadEventsFromJSON(String path) {
        try (InputStream is = main.loadExternalText(path)) {
            if (is == null) {
                main.printErrOnDebug("JSON-Datei nicht gefunden: " + path);
                return;
            }

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Gson gson = new Gson();

            Type type = new TypeToken<HashMap<String, EventData>>() {}.getType();
            events = gson.fromJson(reader, type);

            main.printOnDebug(events.size() + " Events erfolgreich aus JSON geladen!");

        } catch (Exception e) {
            main.printErrOnDebug("Fehler beim Laden der Event-JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void triggerEvent(String eventId) {
        if (!events.containsKey(eventId)) {
            main.printErrOnDebug("Event '" + eventId + "' nicht in JSON gefunden!");
            return;
        }
        if (!main.onInteraction) {
            main.onInteraction = true;
        }

        EventData event = events.get(eventId);

        java.util.List<String> dialogueLines = event.dialogue;

        if (event.requiredFlag != null && !event.requiredFlag.isEmpty()) {
            boolean hasFlag = Main.storyManager.hasFlag(event.requiredFlag);

            if (hasFlag && event.dialogueIfTrue != null && !event.dialogueIfTrue.isEmpty()) {
                dialogueLines = event.dialogueIfTrue;
            }
        }

        String speaker = replaceVariables(event.speaker);
        if (dialogueLines != null) {
            Main.gameDisplay.addDialog(speaker,dialogueLines);
        }

        if (!Main.gameDisplay.cineBarShow||!Main.gameDisplay.checked) {
            Main.gameDisplay.cineBarShow = true;
            Main.gameDisplay.checked = false;
        }




        if (event.setFlagOnEnd != null && !event.setFlagOnEnd.isEmpty()) {
            Main.storyManager.setFlag(event.setFlagOnEnd, true);
            main.printOnDebug("Story-Flag gesetzt: " + event.setFlagOnEnd);
        }

        if (event.nextEventId != null && !event.nextEventId.isEmpty()) {
            triggerEvent(event.nextEventId);
        }
    }

    public void addAnswer(String eventId) {
        if (!events.containsKey(eventId)) {
            main.printErrOnDebug("Event '" + eventId + "' nicht in JSON gefunden!");
            return;
        }
        EventData event = events.get(eventId);
        java.util.List<String> dialogueLines = event.dialogue;

        if (event.requiredFlag != null && !event.requiredFlag.isEmpty()) {
            boolean hasFlag = Main.storyManager.hasFlag(event.requiredFlag);

            if (hasFlag && event.dialogueIfTrue != null && !event.dialogueIfTrue.isEmpty()) {
                dialogueLines = event.dialogueIfTrue;
            }
        }

        if (dialogueLines != null) {
            for (int i =dialogueLines.size()-1; i >= 0; i--) {
                String line = dialogueLines.get(i);
                Main.gameDisplay.primaryDialogList.addFirst(replaceVariables(line));
            }
        }



        if (event.setFlagOnEnd != null && !event.setFlagOnEnd.isEmpty()) {
            Main.storyManager.setFlag(event.setFlagOnEnd, true);
            main.printOnDebug("Story-Flag gesetzt: " + event.setFlagOnEnd);
        }

        if (event.nextEventId != null && !event.nextEventId.isEmpty()) {
            triggerEvent(event.nextEventId);
        }
    }

    public String replaceVariables(String text) {
        if (text == null) return null;


        text = text.replace("{timmyName}", main.NameTimmy);
        text = text.replace("{finjaName}", main.NameFinja);
        text = text.replace("{kizoName}", main.NameKizo);
        text = text.replace("{hobby}", main.Hobby);
        text = text.replace("{favFood}", main.favFood);



        //Hier DialogVariablen hinzufügen!

        return text;
    }


}