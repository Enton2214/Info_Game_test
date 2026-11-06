import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableManager {

    private final Main main;

    private Map<String, SaveVar> varList = new HashMap<>();

    public VariableManager(Main main) {
        this.main = main;
    }

    public void loadVarsFromJSON() {
        main.printOnDebug("Speicherstände Laden...");
        try (InputStream is = Main.getInstance().loadExternalText("Miscellaneous/WorldSettings/Speicherstand.json")) {
            if (is == null) {
                main.printErrOnDebug("JSON-Speicherstände nicht gefunden: " + "Miscellaneous/WorldSettings/Speicherstand.json");
                System.exit(9);
            }

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder()
                    .create();

            Type type = new TypeToken<HashMap<String, SaveVar>>() {}.getType();
            varList = gson.fromJson(reader, type);



        } catch (Exception e) {
            main.printErrOnDebug("Fehler beim Laden der Speicherstände aus JSON: " + e.getMessage());
            e.printStackTrace();
        }

        refreshMainVars();

    }

    public void refreshMainVars() {

        for (int g=0; g<3;g++) {

            List<String>fileStrings = getVar("strings"+g).strings;
            for (int i = 0; i < main.saveGameStrings[g].length; i++) {
                String fileString = fileStrings.get(i);
                main.saveGameStrings[g][i] = fileString;

            }

            List<Integer>fileInts = getVar("ints"+g).integers;
            for (int i = 0; i < main.saveGameInts[g].length; i++) {
                int fileInt = fileInts.get(i);
                main.saveGameInts[g][i] = fileInt;
            }
            SaveVar invVar = getVar("ints"+g);
            if (g==0){
                 main.invTimmy0=invVar.timmyInventory;
                 main.invFinja0=invVar.finjaInventory;
                 main.invKizo0=invVar.kizoInventory;
            } else if (g==1) {
                 main.invTimmy1=invVar.timmyInventory;
                 main.invFinja1=invVar.finjaInventory;
                 main.invKizo1=invVar.kizoInventory;
            }else if (g==2) {
                 main.invTimmy2=invVar.timmyInventory;
                 main.invFinja2=invVar.finjaInventory;
                 main.invKizo2=invVar.kizoInventory;
            }

            List<Double>filedoubles = getVar("doubles"+g).doubles;
            for (int i = 0; i < main.saveGameDoubles[g].length; i++) {
                double filedouble = filedoubles.get(i);
                main.saveGameDoubles[g][i] = filedouble;
            }

            List<Boolean>fileBooleans = getVar("booleans"+g).booleans;
            for (int i = 0; i < main.saveGameBooleans[g].length; i++) {
                boolean fileBoolean = fileBooleans.get(i);
                main.saveGameBooleans[g][i] = fileBoolean;
            }



        }
        List<Integer>keyBinds = getVar("keyBinds").integers;
        for (int i = 0; i < main.keyBinds.length; i++) {
            int keyBind = keyBinds.get(i);
            main.keyBinds[i] = keyBind;
        }
        List<Double>volumes = getVar("volumes").doubles;
        for (int i = 0; i < main.volumes.length; i++) {
            double volume = volumes.get(i);
            main.volumes[i] = volume;
        }
        List<Integer>themeColors = getVar("themeColors").colors;
        for (int i = 0; i < main.themeColor.length; i++) {
            int themeColor = themeColors.get(i);
            Color color = new Color(themeColor,true);
            main.themeColor[i] = color;
        }
        main.targetFPS = getVar("frameRate").integers.get(0);

        List<Boolean>menuBools = getVar("menuBools").booleans;
        for (int i = 0; i < main.menuBools.length; i++) {
            boolean menuBool = menuBools.get(i);
            main.menuBools[i] = menuBool;
        }
        main.vollbild=main.menuBools[0];
        main.showFPS=main.menuBools[1];
        if (main.menuBools[2]) {
            main.buttonWaveSpeed=0.0;
        }else {
            main.buttonWaveSpeed=2.0;
        }



        main.printOnDebug("Speicherstände erfolgreich geladen!");
    }

    public SaveVar getVar(String varName) {
        for (SaveVar saveVar : varList.values()) {
            if (saveVar.name.equals(varName)) {
                return saveVar;
            }
        }
        main.printErrOnDebug("Speicherstand beschädigt!");
        //zurücksetzen/reparieren des Speicherstandes
        System.exit(9);
        return null;
    }

    public void saveAllVars(){
        main.printOnDebug("Speichern des Spiels...");

        for (int g=0; g<3;g++) {
            List<String>fileStrings = new ArrayList<>();
            for (int i = 0; i < main.saveGameStrings[g].length; i++) {
                fileStrings.add(i,main.saveGameStrings[g][i]);
            }
            SaveVar stringVar = getVar("strings"+g);
            stringVar.strings = fileStrings;
            varList.replace("strings"+g,stringVar);

            List<Integer>fileInts = new ArrayList<>();
            for (int i = 0; i < main.saveGameInts[g].length; i++) {
                fileInts.add(i,main.saveGameInts[g][i]);
            }
            SaveVar intVar = getVar("ints"+g);
            intVar.integers = fileInts;

            if (g==0){
                intVar.timmyInventory = main.invTimmy0;
                intVar.finjaInventory = main.invFinja0;
                intVar.kizoInventory = main.invKizo0;
            } else if (g==1) {
                intVar.timmyInventory = main.invTimmy1;
                intVar.finjaInventory = main.invFinja1;
                intVar.kizoInventory = main.invKizo1;
            }else if (g==2) {
                intVar.timmyInventory = main.invTimmy2;
                intVar.finjaInventory = main.invFinja2;
                intVar.kizoInventory = main.invKizo2;
            }

            varList.replace("ints"+g,intVar);

            List<Double>fileDoubles = new ArrayList<>();
            for (int i = 0; i < main.saveGameDoubles[g].length; i++) {
                fileDoubles.add(i,main.saveGameDoubles[g][i]);
            }
            SaveVar doubleVar = getVar("doubles"+g);
            doubleVar.doubles = fileDoubles;
            varList.replace("doubles"+g,doubleVar);

            List<Boolean>fileBoolean = new ArrayList<>();
            for (int i = 0; i < main.saveGameBooleans[g].length; i++) {
                fileBoolean.add(i,main.saveGameBooleans[g][i]);
            }
            SaveVar booleanVar = getVar("booleans"+g);
            booleanVar.booleans = fileBoolean;
            varList.replace("booleans"+g,booleanVar);


        }
        List<Integer>keyBinds = new ArrayList<>();
        for (int i = 0; i < main.keyBinds.length; i++) {
            keyBinds.add(i,main.keyBinds[i]);
        }
        SaveVar keyBindVar = getVar("keyBinds");
        keyBindVar.integers = keyBinds;
        varList.replace("keyBinds",keyBindVar);

        List<Double>volumes = new ArrayList<>();
        for (int i = 0; i < main.volumes.length; i++) {
            volumes.add(i,main.volumes[i]);
        }
        SaveVar volumeVar = getVar("volumes");
        volumeVar.doubles = volumes;
        varList.replace("volumes",volumeVar);

        List<Integer>themeColors = new ArrayList<>();
        for (int i = 0; i < main.themeColor.length; i++) {
            themeColors.add(i,main.themeColor[i].getRGB());
        }
        SaveVar themeColor = getVar("themeColors");
        themeColor.colors = themeColors;
        varList.replace("themeColors",themeColor);

        List<Integer>frameRate = new ArrayList<>();
        frameRate.add(0,main.targetFPS);
        SaveVar fps = getVar("frameRate");
        fps.integers = frameRate;
        varList.replace("frameRate",fps);

        main.menuBools[0]= main.vollbild;
        main.menuBools[1]= main.showFPS;
        if (main.buttonWaveSpeed>0){
            main.menuBools[2]= false;
        }else {
            main.menuBools[2]= true;
        }
        List<Boolean>menuBools = new ArrayList<>();
        for (int i = 0; i < main.menuBools.length; i++) {
            menuBools.add(i,main.menuBools[i]);
        }
        SaveVar menuBoolVar = getVar("menuBools");
        menuBoolVar.booleans = menuBools;
        varList.replace("menuBools",menuBoolVar);









        saveVarsToJSON();
    }


    public void saveVarsToJSON() {

        try {
            String path;
            if (main.gameInIntelliJ) {
                path = "assets/Miscellaneous/WorldSettings/Speicherstand.json";
            }else {
                path  = "assets"+main.shortVersion+"/Miscellaneous/WorldSettings/Speicherstand.json";
            }
            Path filePath = Paths.get(path);
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            try (Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                gson.toJson(varList, writer);
                if (main != null && main.devMode) {
                    main.printOnDebug("Speicherstände erfolgreich überschrieben!");
                }
            }

        } catch (Exception e) {
            main.printErrOnDebug("Fehler beim Speichern der Speicherstände in JSON: " + e.getMessage());
            e.printStackTrace();
            if (main != null && main.devMode) {
                main.printErrOnDebug("Fehler beim Speichern der JSON: " + e.getMessage());
            }
        }
    }
}
