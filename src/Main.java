import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public String version = "Info Game v1.12";
    public String shortVersion = " v1.12";
    public boolean vollbild = false; //bestimmt, ob das Spiel im Vollbild gestartet wird
    public GameState gameStatus = GameState.MAIN_MENU;
    public boolean devMode = true; //starte das Spiel mit debug frame
    public boolean showFPS = true; // Am Ende Ändern
    public boolean infstamina = true; // Am Ende ändern!
    public boolean gameInIntelliJ = false;

    public int extendedFrameHeight = ge.getMaximumWindowBounds().height;
    public int extendedFrameWidth = ge.getMaximumWindowBounds().width;
    public int frameHeight = extendedFrameHeight/2;
    public int frameWidth = extendedFrameWidth/2;

    public int scaleFactor = 2;
    public int actualScaleFactor = 1;


    public int buttonHeight = frameHeight / 6;
    public int buttonWidth = (int) (frameWidth / 4.5);
    public int sliderHeight = frameHeight / 25;
    public int sliderWidth = frameWidth / 3;

    public int targetFPS = 30;
    public int reachedFPS = 30;
    public long usedRam = 0;
    public long maxRam = 3000;
    public int cpuPercent = 0;



    //ButtonSettings
    public double buttonWaveSpeed = 2.0;

    //Lautstärke
    //                         UI ,Music,SFX,Ambient
    public double[] volumes = {0.7,0.5  ,1.0,1.0    };


    //Tile and Player Settings

    public BufferedImage currentSweatSprite;
    public final int maxTiles = 500;
    public final int originalTileSize = 24;
    public int tileScaleFaktor = frameHeight/200;
    public int tileSize = originalTileSize*tileScaleFaktor;

    public double playerTargetWorldX = 0;
    public double playerTargetWorldY = 0;

    public double playerWorldX = 0;
    public double playerWorldY = 0;

    public double playerScreenX = 0;
    public double playerScreenY = 0;

    public Rectangle playerHitBox;
    boolean onInteraction = false;

    public ArrayList<Entity> npcList = new ArrayList<>();
    public  ArrayList<Item> items;


    //                            vollbild,showFPS,Augenschoner
    public boolean[] menuBools = {false   ,true   ,false       };


    //SpeicherSettings

                                        //0-Save Name , 1-favourite food, 2-Hobby       , 3-Name Timmy, 4-Name Finja, 5-Name Kizo   ,6-equippedWeaponName,7-equippedHatName,8-equippedChestName,9-equippedPantsName,10-equippedShoesName,11-equippedWeaponName,12-equippedHatName,13-equippedChestName,14-equippedPantsName,15-equippedShoesName,16-equippedWeaponName,17-equippedHatName,18-equippedChestName,19-equippedPantsName,20-equippedShoesName
    public String[][] saveGameStrings ={{"Neue Datei"  ,"nichts"        ,"Hobbylos"     ,"Timmy"      ,"Finja"      ,"K.i.z.o"      ,"nichts"            ,"nichts"         ,"nichts"           ,"nichts"           ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"              }, // Save 0
                                        {"Neue Datei"  ,"nichts"        ,"Hobbylos"     ,"Timmy"      ,"Finja"      ,"K.i.z.o"      ,"nichts"            ,"nichts"         ,"nichts"           ,"nichts"           ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"              }, // Save 1
                                        {"Neue Datei"  ,"nichts"        ,"Hobbylos"     ,"Timmy"      ,"Finja"      ,"K.i.z.o"      ,"nichts"            ,"nichts"         ,"nichts"           ,"nichts"           ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"               }, // Save 2
                                        {"Neue Datei"  ,"nichts"        ,"Hobbylos"     ,"Timmy"      ,"Finja"      ,"K.i.z.o"      ,"nichts"            ,"nichts"         ,"nichts"           ,"nichts"           ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"            ,"nichts"             ,"nichts"          ,"nichts"            ,"nichts"            ,"nichts"               }};// 3-Default Setting


                                        //0-Money      , 1-PlayTime     , 2-Player speed, 3-X-Position, 4-Y-Position
    public double[][] saveGameDoubles ={{20.00         ,0.0             ,4.0            ,320.0        ,310.0         }, // Save 0
                                        {20.00         ,0.0             ,4.0            ,0.0          ,0.0           }, // Save 1
                                        {20.00         ,0.0             ,4.0            ,0.0          ,0.0           }, // Save 2
                                        {20.00         ,0.0             ,4.0            ,0.0          ,0.0           }};// 3-Default Setting

                                        //0-Text speed , 1-Level Timmy  , 2-Story Status, 3-Timmy HP, 4-Finja HP, 5-Kizo HP, 6-Timmy PP, 7-Finja PP, 8-Kizo PP, 9-Finja lvl, 10-Kizo lvl,11-equippedWeaponID,12-equippedHatID,13-equippedChestID,14-equippedPantsID,15-equippedShoesID,16-equippedWeaponID,17-equippedHatID,18-equippedChestID,19-equippedPantsID,20-equippedShoesID,21-equippedWeaponID,22-equippedHatID,23-equippedChestID,24-equippedPantsID,25-equippedShoesID,26-IQ,27-IQ,28-IQ,29-maxInvSp ,30-maxInvSp ,31-maxInvSp ,32-usedInvSp,33-usedInvSp,34-usedInvSp,35-maxHP,36-maxHP,37-maxHP,38-maxPP,39-maxPP,40-maxPP,41-luck,42-luck,43-luck,44-nxtLvlXP,45-nxtLvlXP,46-nxtLvlXP,47-xp,48xp,49-xp
    public int[][] saveGameInts =       {{2            ,1               ,1              ,20         ,20         ,20        ,10         ,10         ,10        ,1           ,1           ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,50   ,50   ,50   ,10          ,10          ,10          ,10          ,10          ,10          ,20      ,20      ,20      ,10      ,10      ,10      ,15     ,15     ,15     ,20         ,20         ,20         ,0    ,0   ,0    }, // Save 0
                                         {2            ,1               ,1              ,20         ,20         ,20        ,10         ,10         ,10        ,1           ,1           ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,50   ,50   ,50   ,10          ,10          ,10          ,10          ,10          ,10          ,20      ,20      ,20      ,10      ,10      ,10      ,15     ,15     ,15     ,20         ,20         ,20         ,0    ,0   ,0    }, // Save 1
                                         {2            ,1               ,1              ,20         ,20         ,20        ,10         ,10         ,10        ,1           ,1           ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,50   ,50   ,50   ,10          ,10          ,10          ,10          ,10          ,10          ,20      ,20      ,20      ,10      ,10      ,10      ,15     ,15     ,15     ,20         ,20         ,20         ,0    ,0   ,0    }, // Save 2
                                         {2            ,1               ,1              ,20         ,20         ,20        ,10         ,10         ,10        ,1           ,1           ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,-1                 ,-1              ,-1                ,-1                ,-1                ,50   ,50   ,50   ,10          ,10          ,10          ,10          ,10          ,10          ,20      ,20      ,20      ,10      ,10      ,10      ,15     ,15     ,15     ,20         ,20         ,20         ,0    ,0   ,0    }};// 3-Default Setting

    public ArrayList<Integer> invTimmy0 = new ArrayList<>();
    public ArrayList<Integer> invTimmy1 = new ArrayList<>();
    public ArrayList<Integer> invTimmy2 = new ArrayList<>();
    public ArrayList<Integer> invFinja0 = new ArrayList<>();
    public ArrayList<Integer> invFinja1 = new ArrayList<>();
    public ArrayList<Integer> invFinja2 = new ArrayList<>();
    public ArrayList<Integer> invKizo0 = new ArrayList<>();
    public ArrayList<Integer> invKizo1 = new ArrayList<>();
    public ArrayList<Integer> invKizo2 = new ArrayList<>();

                                        //0-first load , 1-FinjaUnlocked, 2-KizoUnlocked,
    public boolean[][]saveGameBooleans={{true          ,true           ,true         ,}, // Save 0
                                        {true          ,false           ,false          ,}, // Save 1
                                        {true          ,false           ,false          ,}, // Save 2
                                        {true          ,false           ,false          ,}};// 3-Default Setting







    public int loadedGame; // 0,1,2
    public boolean gameIsActive = false;

    public String favFood;
    public String Hobby;
    public String NameTimmy;
    public String NameFinja;
    public String NameKizo;

    public double playTime;
    public double playerTargetSpeed;

    public int storyState;

    public boolean firstLoad;



    public void transferGameVariables() {

        player.dequipWeapon();
        for (int i=0; i<=3;i++){
            player.dequipArmor(i);
        }

        finja.dequipWeapon();
        for (int i=0; i<=3;i++){
            finja.dequipArmor(i);
        }

        kizo.dequipWeapon();
        for (int i=0; i<=3;i++){
            kizo.dequipArmor(i);
        }
        favFood = saveGameStrings[loadedGame][1];
        Hobby = saveGameStrings[loadedGame][2];
        NameTimmy = saveGameStrings[loadedGame][3];
        NameFinja = saveGameStrings[loadedGame][4];
        NameKizo = saveGameStrings[loadedGame][5];
        player.equippedWeaponName  = saveGameStrings[loadedGame][6];
        player.equippedHatName  = saveGameStrings[loadedGame][7];
        player.equippedChestName  = saveGameStrings[loadedGame][8];
        player.equippedPantsName  = saveGameStrings[loadedGame][9];
        player.equippedShoesName  = saveGameStrings[loadedGame][10];
        finja.equippedWeaponName  = saveGameStrings[loadedGame][11];
        finja.equippedHatName  = saveGameStrings[loadedGame][12];
        finja.equippedChestName  = saveGameStrings[loadedGame][13];
        finja.equippedPantsName  = saveGameStrings[loadedGame][14];
        finja.equippedShoesName  = saveGameStrings[loadedGame][15];
        kizo.equippedWeaponName = saveGameStrings[loadedGame][16];
        kizo.equippedHatName = saveGameStrings[loadedGame][17];
        kizo.equippedChestName = saveGameStrings[loadedGame][18];
        kizo.equippedPantsName = saveGameStrings[loadedGame][19];
        kizo.equippedShoesName = saveGameStrings[loadedGame][20];

        player.money = saveGameDoubles[loadedGame][0];
        playTime = saveGameDoubles[loadedGame][1];
        playerTargetSpeed = saveGameDoubles[loadedGame][2];
        playerWorldX = saveGameDoubles[loadedGame][3];
        playerWorldY = saveGameDoubles[loadedGame][4];

        player.lvl = saveGameInts[loadedGame][1];
        storyState = saveGameInts[loadedGame][2];
        player.targetHP = saveGameInts[loadedGame][3];
        finja.targetHP = saveGameInts[loadedGame][4];
        kizo.targetHP = saveGameInts[loadedGame][5];
        player.targetPP = saveGameInts[loadedGame][6];
        finja.targetPP = saveGameInts[loadedGame][7];
        kizo.targetPP = saveGameInts[loadedGame][8];
        finja.lvl = saveGameInts[loadedGame][9];
        kizo.lvl = saveGameInts[loadedGame][10];
        for (int i=11;i<=15;i++){
            if (saveGameInts[loadedGame][i] >= 0){
                Item item = items.get(saveGameInts[loadedGame][i]);
                if (item.itemType == Item.ItemType.Waffe){
                    player.equipWeapon(item);
                }else if (item.itemType == Item.ItemType.Rüstung){
                    player.equipArmor(item);
                }

            }
        }
        for (int i=16;i<=20;i++){
            if (saveGameInts[loadedGame][i] >= 0){
                Item item = items.get(saveGameInts[loadedGame][i]);
                if (item.itemType == Item.ItemType.Waffe){
                    finja.equipWeapon(item);
                }else if (item.itemType == Item.ItemType.Rüstung){
                    finja.equipArmor(item);
                }

            }
        }
        for (int i=21;i<=25;i++){
            if (saveGameInts[loadedGame][i] >= 0){
                Item item = items.get(saveGameInts[loadedGame][i]);
                if (item.itemType == Item.ItemType.Waffe){
                    kizo.equipWeapon(item);
                }else if (item.itemType == Item.ItemType.Rüstung){
                    kizo.equipArmor(item);
                }

            }
        }


        if (loadedGame==0){
            player.inventory = invTimmy0;
            finja.inventory = invFinja0;
            kizo.inventory = invKizo0;
        }else if (loadedGame==1){
            player.inventory = invTimmy1;
            finja.inventory = invFinja1;
            kizo.inventory = invKizo1;
        }else if (loadedGame==2){
            player.inventory = invTimmy2;
            finja.inventory = invFinja2;
            kizo.inventory = invKizo2;
        }





        player.IQ = saveGameInts[loadedGame][26];
        finja.IQ = saveGameInts[loadedGame][27];
        kizo.IQ = saveGameInts[loadedGame][28];
        player.maxInvSpace = saveGameInts[loadedGame][29];
        finja.maxInvSpace = saveGameInts[loadedGame][30];
        kizo.maxInvSpace = saveGameInts[loadedGame][31];
        player.usedInvSpace = saveGameInts[loadedGame][32];
        finja.usedInvSpace = saveGameInts[loadedGame][33];
        kizo.usedInvSpace = saveGameInts[loadedGame][34];
        player.maxHP = saveGameInts[loadedGame][35];
        finja.maxHP = saveGameInts[loadedGame][36];
        kizo.maxHP = saveGameInts[loadedGame][37];
        player.maxPP = saveGameInts[loadedGame][38];
        finja.maxPP = saveGameInts[loadedGame][39];
        kizo.maxPP = saveGameInts[loadedGame][40];
        player.luck = saveGameInts[loadedGame][41];
        finja.luck = saveGameInts[loadedGame][42];
        kizo.luck = saveGameInts[loadedGame][43];
        player.nxtLevelXp = saveGameInts[loadedGame][44];
        finja.nxtLevelXp = saveGameInts[loadedGame][45];
        kizo.nxtLevelXp = saveGameInts[loadedGame][46];
        player.xp =  saveGameInts[loadedGame][47];
        finja.xp = saveGameInts[loadedGame][48];
        kizo.xp = saveGameInts[loadedGame][49];


        firstLoad = saveGameBooleans[loadedGame][0];

        printOnDebug("First Load: " + firstLoad);
        printOnDebug("Tile Scale Factor: " + tileScaleFaktor);
    }

    public void transferGameVariablesToArray() {
        saveGameStrings[loadedGame][1] = favFood;
        saveGameStrings[loadedGame][2] = Hobby;
        saveGameStrings[loadedGame][3] = NameTimmy;
        saveGameStrings[loadedGame][4] = NameFinja;
        saveGameStrings[loadedGame][5] = NameKizo;
        saveGameStrings[loadedGame][6] = player.equippedWeaponName;
        saveGameStrings[loadedGame][7] = player.equippedHatName;
        saveGameStrings[loadedGame][8] = player.equippedChestName;
        saveGameStrings[loadedGame][9] = player.equippedPantsName;
        saveGameStrings[loadedGame][10] = player.equippedShoesName;
        saveGameStrings[loadedGame][11] = finja.equippedWeaponName;
        saveGameStrings[loadedGame][12] = finja.equippedHatName;
        saveGameStrings[loadedGame][13] = finja.equippedChestName;
        saveGameStrings[loadedGame][14] = finja.equippedPantsName;
        saveGameStrings[loadedGame][15] = finja.equippedShoesName;
        saveGameStrings[loadedGame][16] = kizo.equippedWeaponName;
        saveGameStrings[loadedGame][17] = kizo.equippedHatName;
        saveGameStrings[loadedGame][18] = kizo.equippedChestName;
        saveGameStrings[loadedGame][19] = kizo.equippedPantsName;
        saveGameStrings[loadedGame][20] = kizo.equippedShoesName;

        saveGameDoubles[loadedGame][0] = player.money;
        saveGameDoubles[loadedGame][1] = playTime;
        saveGameDoubles[loadedGame][2] = playerTargetSpeed;
        saveGameDoubles[loadedGame][3] = playerWorldX;
        saveGameDoubles[loadedGame][4] = playerWorldY;

        saveGameInts[loadedGame][1] = player.lvl;
        saveGameInts[loadedGame][2] = storyState;
        saveGameInts[loadedGame][3] = player.targetHP;
        saveGameInts[loadedGame][4] = finja.targetHP;
        saveGameInts[loadedGame][5] = kizo.targetHP;
        saveGameInts[loadedGame][6] = player.targetPP;
        saveGameInts[loadedGame][7] = finja.targetPP;
        saveGameInts[loadedGame][8] = kizo.targetPP;
        saveGameInts[loadedGame][9] = finja.lvl;
        saveGameInts[loadedGame][10] = kizo.lvl;



        for (int i = 11; i <= 15; i++) {
            Item item = null;
            if (i == 11) item = player.equippedWeapon;
            else if (i == 12) item = player.equippedHat;
            else if (i == 13) item = player.equippedChest;
            else if (i == 14) item = player.equippedPants;
            else if (i == 15) item = player.equippedShoes;

            if (item != null) {
                saveGameInts[loadedGame][i] = items.indexOf(item);
            } else {
                saveGameInts[loadedGame][i] = -1;
            }
        }



        for (int i = 16; i <= 20; i++) {
            Item item = null;
            if (i == 16) item = finja.equippedWeapon;
            else if (i == 17) item = finja.equippedHat;
            else if (i == 18) item = finja.equippedChest;
            else if (i == 19) item = finja.equippedPants;
            else if (i == 20) item = finja.equippedShoes;

            if (item != null) {
                saveGameInts[loadedGame][i] = items.indexOf(item);
            } else {
                saveGameInts[loadedGame][i] = -1;
            }
        }



        for (int i = 21; i <= 25; i++) {
            Item item = null;
            if (i == 21) item = kizo.equippedWeapon;
            else if (i == 22) item = kizo.equippedHat;
            else if (i == 23) item = kizo.equippedChest;
            else if (i == 24) item = kizo.equippedPants;
            else if (i == 25) item = kizo.equippedShoes;

            if (item != null) {
                saveGameInts[loadedGame][i] = items.indexOf(item);
            } else {
                saveGameInts[loadedGame][i] = -1;
            }

        }

        if (loadedGame==0){
             invTimmy0=player.inventory;
             invFinja0=finja.inventory;
             invKizo0=kizo.inventory;
        }else if (loadedGame==1){
             invTimmy1=player.inventory;
             invFinja1=finja.inventory;
             invKizo1=kizo.inventory;
        }else if (loadedGame==2){
             invTimmy2=player.inventory;
             invFinja2=finja.inventory;
             invKizo2=kizo.inventory;
        }

        saveGameInts[loadedGame][26] = player.IQ;
        saveGameInts[loadedGame][27] = finja.IQ;
        saveGameInts[loadedGame][28] = kizo.IQ;

        saveGameInts[loadedGame][29] = player.maxInvSpace;
        saveGameInts[loadedGame][30] = finja.maxInvSpace;
        saveGameInts[loadedGame][31] = kizo.maxInvSpace;

        saveGameInts[loadedGame][32] = player.usedInvSpace;
        saveGameInts[loadedGame][33] = finja.usedInvSpace;
        saveGameInts[loadedGame][34] = kizo.usedInvSpace;

        saveGameInts[loadedGame][35] = player.maxHP;
        saveGameInts[loadedGame][36] = finja.maxHP;
        saveGameInts[loadedGame][37] = kizo.maxHP;

        saveGameInts[loadedGame][38] = player.maxPP;
        saveGameInts[loadedGame][39] = finja.maxPP;
        saveGameInts[loadedGame][40] = kizo.maxPP;

        saveGameInts[loadedGame][41] = player.luck;
        saveGameInts[loadedGame][42] = finja.luck;
        saveGameInts[loadedGame][43] = kizo.luck;

        saveGameInts[loadedGame][44] = player.nxtLevelXp;
        saveGameInts[loadedGame][45] = finja.nxtLevelXp;
        saveGameInts[loadedGame][46] = kizo.nxtLevelXp;

        saveGameInts[loadedGame][47] = player.xp;
        saveGameInts[loadedGame][48] = finja.xp;
        saveGameInts[loadedGame][49] = kizo.xp;

        saveGameBooleans[loadedGame][0] = firstLoad;

        variableManager.saveAllVars();

    }

    public void resetGameVariables(int index) {


        saveGameStrings[index][0] = saveGameStrings[3][0];
        saveGameStrings[index][1] = saveGameStrings[3][1];
        saveGameStrings[index][2] = saveGameStrings[3][2];
        saveGameStrings[index][3] = saveGameStrings[3][3];
        saveGameStrings[index][4] = saveGameStrings[3][4];
        saveGameStrings[index][5] = saveGameStrings[3][5];

        saveGameDoubles[index][0] = saveGameDoubles[3][0];
        saveGameDoubles[index][1] = saveGameDoubles[3][1];
        saveGameDoubles[index][2] = saveGameDoubles[3][2];
        saveGameDoubles[index][3] = saveGameDoubles[3][3];
        saveGameDoubles[index][4] = saveGameDoubles[3][4];

        saveGameInts[index][0] = saveGameInts[3][0];
        saveGameInts[index][1] = saveGameInts[3][1];
        saveGameInts[index][2] = saveGameInts[3][2];
        saveGameInts[index][3] = saveGameInts[3][3];
        saveGameInts[index][4] = saveGameInts[3][4];
        saveGameInts[index][5] = saveGameInts[3][5];
        saveGameInts[index][6] = saveGameInts[3][6];
        saveGameInts[index][7] = saveGameInts[3][7];
        saveGameInts[index][8] = saveGameInts[3][8];
        saveGameInts[index][9] = saveGameInts[3][9];
        saveGameInts[index][10] = saveGameInts[3][10];


        saveGameBooleans[index][0] = saveGameBooleans[3][0];

        printOnDebug("Datei: " + (index+1)+"/index: "+index+" resettet");

    }

    public Color[] themeColor = {new ColorUIResource(0, 253, 253).darker().darker().darker(),new ColorUIResource(7, 53, 53),new ColorUIResource(0, 253, 253), new ColorUIResource(255, 255, 255), new ColorUIResource(158, 21, 79),new ColorUIResource(158, 12, 68), new ColorUIResource(78, 4, 38)};
    // 0-Background, 1-DiaButton Primary, 2-DiaButton Wave, 3-DiaButton Text, 4-Slider thumb, 5-BackButton Primary, 6-Backbutton Wave

    //Fonts
    Font textfont = new Font("Century", Font.BOLD, frameHeight/30);
    Font extendettextfont = new Font("Century", Font.BOLD, textfont.getSize()*scaleFactor);
    Font newGameFont = new Font("Century", Font.BOLD, frameHeight/20);
    Font extendetNewGameFont = new Font("Century", Font.BOLD, newGameFont.getSize()*scaleFactor);
    Font dialogFont = new Font("Arial", Font.BOLD, frameHeight/23);
    Font extendedDialogFont = new Font("Arial", Font.BOLD, dialogFont.getSize()*scaleFactor);
    Font headerDialogFont = new Font("Arial", Font.BOLD, frameHeight/16);
    Font extendedHeaderDialogFont = new Font("Arial", Font.BOLD, headerDialogFont.getSize()*scaleFactor);
    Font headLineFont = new Font("Arial", Font.BOLD, frameHeight/15);
    Font extendedHeadLineFont = new Font("Arial", Font.BOLD, headLineFont.getSize()*scaleFactor);

    //KeyBinds

    public int[] keyBinds = {KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D,KeyEvent.VK_E,KeyEvent.VK_Q,KeyEvent.VK_M,KeyEvent.VK_SPACE};
                            // 0-Vor      , 1-Links     , 2-Hinten    , 3-Rechts    , 4-Inventar  , 5-Interagieren  , 6-Karte öffnen, 7-Sprinten
    public String keyNameSprint = "Sprinten";
    public String keyNameVor = "Nach oben laufen";
    public String keyNameLinks = "Nach links laufen";
    public String keyNameRechts = "Nach rechts laufen";
    public String keyNameHinten = "Nach unten laufen";
    public String keyNameInventar = "Inventar öffnen";
    public String keyNameInteraktion = "Interagieren";
    public String keyNameopenMap = "Karte öffnen";

    public boolean upPressed = false;
    public boolean leftPressed = false;
    public boolean downPressed = false;
    public boolean rightPressed = false;
    public boolean sprinting = false;
    public boolean gameIsPaused = false;
    public boolean invIsOpen = false;



    public String[] vorschlagButtonNames = {"Vorschlag bitte","Ich bin unkreativ", "In Kunst stand ich 4", "Es ist okay, drück mich","Hast du deinen Autoklicker angelassen?","Die Vorschläge wiederholen sich übrigens"};

    public String[] vorschlagDateiNames = {"Coole Datei", "[Name]'s coole Datei", "Ich liebe diese Datei", "Die coolste Datei", "Meine eigene Datei"};
    public String[] vorschlagFavFood = {"Pizza", "Burger", "Omas Apfelkuchen", "Spaghetti", "Burrito", "Sushi"};
    public String[] vorschlagHobby = {"Das Rätsel um die 25. Stunde spielen", "schlafen", "Basketball spielen", "Volleyball spielen", "Earthbound spielen", "Lego bauen"};
    public String[] vorschlagNameTimmy = {"Timmy", "Marty", "Jeff", "Sebastian", "Georg", "Pablo"};
    public String[] vorschlagNameFinja = {"Finja", "Yelena", "Katherine", "Paula", "Carmen", "Jassy"};
    public String[] vorschlagNameKizo = {"K.i.z.o", "Wall-i", "Jack", "Robi", "R4ZE", "Lil-k"};

    public boolean loop;

    JFrame debugFrame;
    JTextArea debugPanel;

    JPanel mainPanel;
    JFrame frame;

    CardLayout cardLayout;

    JPanel FPSHolderPanel;
    BrikText FPSAnzeiger;

    public static VariableManager variableManager;
    public static GameManager gameManager;
    public static MenuDisplay menuDisplay;
    public static GameDisplay gameDisplay;
    public static FightDisplay fightDisplay;
    public static TileManager tileManager;
    public static Player player;
    public static Finja finja;
    public static Kizo kizo;
    public static CollisionChecker collisionChecker;
    public static Transform transform;
    public static StoryManager storyManager;
    public static EventManager eventManager;







    public enum GameState {
        MAIN_MENU,
        PLAYING,
        PAUSED,
        Inventory,
        GAME_OVER,
        FIGHTING,
        SETTINGS,
        VOLUMESETTINGS,
        GRAPHICSETTINGS,
        DATEIAUSWAHL,
        KEY_BINDING,
        Start_New_Game

    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        Main infoGame = new Main();
        infoGame.starteProgramm();
    }


    public void starteProgramm() {

        if (devMode) {
            debugFrame = new JFrame("Info_Game_Debug");
            debugFrame.setSize(frameWidth, (int) (frameHeight*1.5));
            debugFrame.setLocationRelativeTo(null);
            debugFrame.setLayout(new BorderLayout());
            debugFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            debugPanel = new JTextArea();
            debugPanel.setEditable(false);
            debugPanel.setFont(new Font("Monospaced", Font.PLAIN, dialogFont.getSize()-4));
            debugPanel.setLineWrap(true);

            JScrollPane scrollPane = new JScrollPane(debugPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.getVerticalScrollBar().setUnitIncrement(12);

            JTextField debugEingabe = new JTextField();
            debugEingabe.setFont(new Font("Monospaced", Font.PLAIN, dialogFont.getSize()-4));
            debugEingabe.addActionListener(e -> {
                String eingabe = debugEingabe.getText().trim().toLowerCase();

                List<Integer> ints;
                List<String> words;
                words = new ArrayList<>();
                ints = new ArrayList<>();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(eingabe);
                while (matcher.find()) {
                    ints.add(Integer.parseInt(matcher.group()));
                }

                Pattern pattern1 = Pattern.compile("\\b\\w+\\b");
                Matcher matcher1 = pattern1.matcher(eingabe);
                while (matcher1.find()) {
                    words.add(matcher1.group());
                    System.out.println("Gefundenes Wort: " + matcher1.group());
                }



                if (eingabe.equals("close")) {
                    variableManager.saveAllVars();
                    printOnDebug("Closing Game...");
                    System.exit(11);
                }else if (eingabe.equals("help")) {

                    printAllCommands();
                }else if (eingabe.equals("getpos")){
                    if (gameIsActive){
                        printOnDebug("X-Pos: "+playerWorldX+", Y-Pos: "+playerWorldY);
                    }else {printErrOnDebug("Kein Spiel geladen!");}
                }else if (eingabe.equals("main menu")) {

                    if (gameIsActive){
                        gameIsActive = false;
                        transferGameVariablesToArray();
                    }
                    setGameStatus(GameState.MAIN_MENU);
                    printOnDebug("Zum Hauptmenü gesprungen!");

                }else if (eingabe.contains("tp")) {
                    if (gameIsActive){
                        if (ints.size()>2||ints.size()<2) {
                            printErrOnDebug("Falsche Anzahl an Koordinaten. Erwartet: x, y (2)");
                        }else {
                            printOnDebug("Teleportiere "+NameTimmy+" an Position x: "+ints.get(0)+", y: "+ints.get(1) );
                            playerWorldX = ints.get(0);
                            playerWorldY = ints.get(1);
                        }

                    }else {printErrOnDebug("Kein Spiel geladen!");}


                }else if (eingabe.equals("save")) {
                    if (gameIsActive){
                        gameIsActive = false;
                        transferGameVariablesToArray();
                        setGameStatus(GameState.DATEIAUSWAHL);
                        printOnDebug("Spiel auf Save "+(loadedGame+1)+" gespeichert!" );

                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.equals("justsave")) {

                    if (gameIsActive){
                        transferGameVariablesToArray();
                        printOnDebug("Spiel auf Save "+(loadedGame+1)+" gespeichert!" );

                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.contains("set")) {
                    if (gameIsActive){
                        if (eingabe.contains("speed")) {
                            printOnDebug("Player Speed auf "+ints.get(0)+ " gesetzt!");
                            playerTargetSpeed = ints.get(0);

                        }else if (eingabe.contains("hp")) {
                            if (eingabe.contains("timmy")) {
                                printOnDebug("HP von Timmy auf "+ints.get(0)+ " gesetzt!");
                                if (ints.get(0)>player.maxHP){
                                    player.maxHP = ints.get(0);
                                }
                                player.targetHP = ints.get(0);

                            }else if (eingabe.contains("finja")) {
                                printOnDebug("HP von Finja auf "+ints.get(0)+ " gesetzt!");
                                if (ints.get(0)>finja.maxHP){
                                    finja.maxHP = ints.get(0);
                                }
                                finja.targetHP = ints.get(0);

                            }else if (eingabe.contains("kizo")) {
                                printOnDebug("HP von kizo auf "+ints.get(0)+ " gesetzt!");
                                if (ints.get(0)>kizo.maxHP){
                                    kizo.maxHP = ints.get(0);
                                }
                                kizo.targetHP = ints.get(0);

                            }
                        }else if (eingabe.contains("pp")) {
                            if (eingabe.contains("timmy")) {
                                printOnDebug("PP von Timmy auf "+ints.get(0)+ " gesetzt!");
                                if (ints.get(0)>player.maxPP){
                                    player.maxPP = ints.get(0);
                                }
                                player.targetPP = ints.get(0);

                            }else if (eingabe.contains("finja")) {
                                printOnDebug("PP von Finja auf "+ints.get(0)+ " gesetzt!");
                                if (ints.get(0)>finja.maxPP){
                                    finja.maxPP = ints.get(0);
                                }
                                finja.targetPP = ints.get(0);

                            }else if (eingabe.contains("kizo")) {
                                printOnDebug("PP von kizo auf " + ints.get(0) + " gesetzt!");
                                if (ints.get(0) > kizo.maxPP) {
                                    kizo.maxPP = ints.get(0);
                                }
                                kizo.targetPP = ints.get(0);

                            }
                        }
                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.contains("money")) {
                    if (gameIsActive){

                        player.money = (double) ints.get(0) /100;
                        printOnDebug("Geld auf $ "+ player.money+" gesetzt!");
                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.contains("unlock")) {

                    if (gameIsActive){
                        if (eingabe.contains("finja")) {
                            if (saveGameBooleans[loadedGame][1]){
                                saveGameBooleans[loadedGame][1] = false;
                                printOnDebug("Finja entfernt!");
                            }else {
                                saveGameBooleans[loadedGame][1] = true;
                                printOnDebug("Finja freigeschaltet!");
                            }
                        } else if (eingabe.contains("kizo")) {
                            if (saveGameBooleans[loadedGame][2]){
                                saveGameBooleans[loadedGame][2] = false;
                                printOnDebug("kizo entfernt!");
                            }else {
                                saveGameBooleans[loadedGame][2] = true;
                                printOnDebug("kizo freigeschaltet!");
                            }
                        }

                    }else {printErrOnDebug("Kein Spiel geladen!");}



                }else if (eingabe.contains("pauseoverlay")) {
                    if (gameIsActive){
                        if (ints.get(0)<4&&ints.get(0)>-1){
                            gameDisplay.pauseOverlayNum = ints.get(0);
                            printOnDebug("Pauseoverlay auf Index "+ints.get(0)+" gesetzt!");
                        }else {
                            printErrOnDebug("Zahl out of bounds! (0-3)");
                        }


                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.contains("summon")) {
                    if (gameIsActive){
                        if (ints.size() != 2){
                            printErrOnDebug("keine Koordinaten angegeben!");

                        }else {
                            if (words.size()<2){
                                printErrOnDebug("kein Name angegeben!");
                            }else {
                                String npcName = words.get(1);
                                boolean found = false;

                                for (Class<? extends Entity> entityClass : allEntityClasses) {
                                    if (entityClass.getName().equalsIgnoreCase(npcName)) {
                                        try {
                                            Entity entity = entityClass.getDeclaredConstructor(Main.class).newInstance(this);
                                            entity.worldX = ints.get(0);
                                            entity.worldY = ints.get(1);
                                            printOnDebug(npcName+" an x: " + ints.get(0) + ", y: " + ints.get(1) + " erschaffen!");
                                            npcList.add(entity);
                                            found = true;
                                            break;
                                        } catch (InstantiationException ex) {
                                            throw new RuntimeException(ex);
                                        } catch (IllegalAccessException ex) {
                                            throw new RuntimeException(ex);
                                        } catch (InvocationTargetException ex) {
                                            throw new RuntimeException(ex);
                                        } catch (NoSuchMethodException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }


                                }
                                if (!found) {
                                    printErrOnDebug("Keine Entität mit dem Namen "+npcName+" gefunden!");
                                }


                            }
                        }


                    }else {printErrOnDebug("Kein Spiel geladen!");}

                }else if (eingabe.contains("infstamina")) {
                    if (gameIsActive){
                        if (eingabe.contains("true")) {
                            infstamina = true;
                            printOnDebug("Infinite Stamina auf true gesetzt!");
                        }else if (eingabe.contains("false")) {
                            infstamina = false;
                            printOnDebug("Infinite Stamina auf false gesetzt!");
                        }
                    }else {printErrOnDebug("Kein Spiel geladen!");}
                }else if (eingabe.contains("getentitys")) {
                    printOnDebug("");
                    printOnDebug("Liste aller Entity-Namen:");
                    for (Class<? extends Entity> entityClass : allEntityClasses) {
                        printOnDebug("-'"+entityClass.getName()+"'");

                    }
                }

                debugEingabe.setText("");

            });

            debugFrame.add(scrollPane, BorderLayout.CENTER);
            debugFrame.add(debugEingabe, BorderLayout.SOUTH);

            printAllCommands();
            debugFrame.setVisible(true);
        }

        printOnDebug("Starte Version"+shortVersion+"...");

        extractAllAssets();
        initFonts();
        loadItems();
        Main.variableManager = new VariableManager(this);
        variableManager.loadVarsFromJSON();






        printOnDebug("Erkannte Bildpunkte für dieses Ausgabegerät: " + extendedFrameWidth + " x " + extendedFrameHeight);
        registerAll();
        createClasses();




        JFrame frame = new JFrame("Info_Game");
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setUndecorated(vollbild);




        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.frame = frame;


        menuDisplay.loadMenu();
        menuDisplay.loadSettings();
        menuDisplay.loadVolumeSettings();
        menuDisplay.loadGraphicSettings();
        menuDisplay.loadKeyBindSettings();
        menuDisplay.loadAuswahlSettings();
        menuDisplay.loadNewGameMenu();
        gameDisplay.loadGamePanel();
        fightDisplay.loadFightPanel();
        gameManager.setUpControls();
        createPFSAnzeiger();




        mainPanel.add(menuDisplay.menuPanel, "Menu");
        mainPanel.add(menuDisplay.settingsPanel, "Settings");
        mainPanel.add(menuDisplay.volumePanel, "Volume settings");
        mainPanel.add(menuDisplay.graphicPanel, "Graphic settings");
        mainPanel.add(menuDisplay.keyBindPanel, "Key binding");
        mainPanel.add(menuDisplay.auswahlPanel, "Datei auswahl");
        mainPanel.add(menuDisplay.newGamePanel, "Config New Game");
        mainPanel.add(gameDisplay, "Game");
        mainPanel.add(fightDisplay.fightPanel, "Fight");



        frame.add(mainPanel);


        if (vollbild) {
            switchVollbild();
        }




        frame.setVisible(true);
        loop = true;
        gameManager.startGameLoop();



    }

    public static List<Class<? extends Entity>> allEntityClasses = new ArrayList<>();

    public static void registerAll() {
        allEntityClasses.add(Finja.class);
        allEntityClasses.add(Kizo.class);
        allEntityClasses.add(FoodSeller.class);
        allEntityClasses.add(Postbote.class);
        //Alle weiteren Entitäten
    }

    public void printAllCommands() {
        printOnDebug("");
        printOnDebug("Befehle:");
        printOnDebug("close - Schließt das Game");
        printOnDebug("help - Befehlsauflistung");
        printOnDebug("main menu - springt zum Hauptmenü");
        printOnDebug("tp + x + y - teleportiert den Spieler zur Koordinate xy");
        printOnDebug("getpos - gibt die aktuelle Position vom Spieler zurück");
        printOnDebug("save - Speichert das Spiel und Springt zur Dateiauswahl");
        printOnDebug("just save - Speichert das Spiel ohne es zu verlassen");
        printOnDebug("set + speed/ [hp+Timmy/Finja/Kizo]/ [pp+Timmy/Finja/Kizo] + Zahl - setz die gewünschte variable auf den eingegebenen Wert");
        printOnDebug("money + Zahl - setzt Geld auf eingegebenen Wert");
        printOnDebug("unlock + finja/kizo - unlocked/locked Kizo oder Finja im Game");
        printOnDebug("pauseoverlay + (0-3) - setzt das Pause-Overlay auf gesetzten Wert");
        printOnDebug("summon + Name der Entity + x + y - Erschafft die gewünschte Kreatur an den Koordinaten xy");
        printOnDebug("infstamina + true/false - setzt unendliche Ausdauer auf wahr oder falsch");
        printOnDebug("getentitys - gibt eine Liste aller Entity-Namen zurück");
        printOnDebug("");
    }



    private static Main instance;
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }






    void createClasses() {
        SoundManager.getInstance().initMain(this);
        Main.storyManager = new StoryManager(this);
        Main.eventManager = new EventManager(this);
        Main.transform = new Transform(this);
        Main.player = new Player(this);
        Main.finja = new Finja(this);
        Main.kizo = new Kizo(this);
        Main.menuDisplay = new MenuDisplay(this);
        Main.fightDisplay = new FightDisplay(this);
        Main.tileManager = new TileManager(this);
        Main.gameDisplay = new GameDisplay(this);
        Main.gameManager = new GameManager(this);
        Main.collisionChecker = new CollisionChecker(this);


    }
    public void setGameStatus(GameState gameStatus) {

        this.gameStatus = gameStatus;
    }

    public void updateKeyBinds() {
        gameManager.updateKeyBindings();
    }
    public boolean checkPlayerCollision(double targetX, double targetY) {

        return  collisionChecker.checkPlayerCollision(targetX, targetY);
    }




    public void switchVollbild() {
        frame.setVisible(false);
        frame.dispose();



        if(vollbild){

            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            actualScaleFactor = scaleFactor;
            tileScaleFaktor *=scaleFactor;
            tileSize = originalTileSize*tileScaleFaktor;
            refreshAllVollbildComponenten();
            gameDisplay.updateAll();
            frame.setVisible(true);



        }else{


        frame.setUndecorated(false);

        vollbild = false;
        actualScaleFactor = 1;
        tileScaleFaktor /=scaleFactor;
        tileSize = originalTileSize*tileScaleFaktor;
        refreshAllVollbildComponenten();
        frame.setExtendedState(JFrame.NORMAL);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        gameDisplay.updateAll();
        frame.setVisible(true);
        }


    }

    public void loadGameOnVars(boolean newGame){
        setupGame(newGame);
        //Game mit Variablen starten
        player.name = NameTimmy;
        finja.name = NameFinja;
        kizo.name = NameKizo;
        player.isMoving = false;
        player.isSprinting = false;

        gameDisplay.cineBarShow = false;
        gameIsPaused = false;
        invIsOpen = false;


        setGameStatus(GameState.PLAYING);
        gameIsActive = true;

    }
    public void setupGame(boolean newGame) {

        npcList.clear();


        if (newGame) {
            // StartSetup auslesen und alle Entitys danach sezten
        }else {
            // geladenes Setup auslesen und danach setzten
        }



        gameDisplay.setUpGame();
        //Testzweck
        finja.worldX = 273;
        finja.worldY = 207;
        kizo.worldX = 320;
        kizo.worldY = 207;
        kizo.isMoving = true;
        kizo.lookDirection = Entity.LookDirection.upLeft;

        npcList.add(finja);
        npcList.add(kizo);
    }


    public void leaveGame(){
        gameIsActive = false;
        setGameStatus(GameState.MAIN_MENU);
    }


    public void refreshAllVollbildComponenten(){
        menuDisplay.refreshVollbildComponenten(vollbild);
        refreshAllThemes();
        //restlichen Klassen gleiche methode

        printOnDebug("Refreshed Vollbild Komponenten sucsessfully! Vollbild: "+vollbild);
    }


    public void refreshAllThemes(){
        menuDisplay.refreshTheme();
        gameDisplay.reLoadPauseUI();
        gameDisplay.reloadInventoryUI();
        gameDisplay.reloadDialogUI();
        //restlichen Klassen gleiche methode

        printOnDebug("Refreshed Themes sucsessfully!");
    }
    public void setFrameRate(int frameRate) {
        this.targetFPS = frameRate;
        gameManager.refreshFrameRate();

    }
    public void showFPS(boolean show) {
        this.showFPS = show;
    }
    public void uptdateStory(int storyState){
        this.storyState = storyState;
        gameDisplay.pauseOverlayNum = 1; // HIER gesamteStoryPunke/4 oder so

    }





    public void createPFSAnzeiger() {

        JPanel FPSHolderPanel = new JPanel();
        FPSHolderPanel.setOpaque(false);
        BrikText FPSAnzeiger = new BrikText(this);
        FPSHolderPanel.setVisible(showFPS);
        this.FPSAnzeiger = FPSAnzeiger;
        this.FPSHolderPanel = FPSHolderPanel;
        FPSHolderPanel.add(FPSAnzeiger);
        gameDisplay.add(FPSAnzeiger);

    }

    public void printOnDebug(String message) {
        if (devMode&&debugFrame!=null) {
            debugPanel.append(message + "\n");
            debugPanel.setCaretPosition(debugPanel.getDocument().getLength());
        }
        System.out.println(message);
    }

    public void printErrOnDebug(String message) {
        if (devMode&&debugFrame!=null) {
            debugPanel.append("ERROR: "+message + "\n");
            debugPanel.setCaretPosition(debugPanel.getDocument().getLength());
        }
        System.err.println(message);
    }

    public void initFonts() {
        dialogFont = loadCustomFont("Miscellaneous/Fonts/editundo.ttf",(float) ((float) dialogFont.getSize()*1.15));
        extendedDialogFont = loadCustomFont("Miscellaneous/Fonts/editundo.ttf", (float) ((float) extendedDialogFont.getSize()*1.15));
        headLineFont = loadCustomFont("Miscellaneous/Fonts/HomeVideo-Regular.ttf", (float) ((float) headLineFont.getSize()*1.5));
        extendedHeadLineFont = loadCustomFont("Miscellaneous/Fonts/HomeVideo-Regular.ttf", (float) ((float) extendedHeadLineFont.getSize()*1.5));
        textfont = loadCustomFont("Miscellaneous/Fonts/editundo.ttf", (float) ((float) textfont.getSize()*1.5));
        extendettextfont = loadCustomFont("Miscellaneous/Fonts/editundo.ttf", (float) ((float) extendettextfont.getSize()*1.5));
        newGameFont = loadCustomFont("Miscellaneous/Fonts/HomeVideo-Regular.ttf", (float) ((float) newGameFont.getSize()*1.4));
        extendetNewGameFont = loadCustomFont("Miscellaneous/Fonts/HomeVideo-Regular.ttf", (float) ((float) extendetNewGameFont.getSize()*1.4));
        headerDialogFont = loadCustomFont("Miscellaneous/Fonts/editundo.ttf",(float) ((float) headerDialogFont.getSize()*1.15));
        extendedHeaderDialogFont =  loadCustomFont("Miscellaneous/Fonts/editundo.ttf", (float) ((float) extendedHeaderDialogFont.getSize()*1.15));
    }

    public Font loadCustomFont(String filePath, float fontSize) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return customFont.deriveFont(fontSize);

        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) fontSize);
        }
    }

    public void loadItems(){
        items = new ArrayList<>();
        try {
            items.add(0,new Item(loadExternalImage("Grafik/Sprites/Finja/Finja_down_1.png"),"Fehler","Fehler",0));


            items.add(1,new Item(loadExternalImage("Grafik/Item/Salami_Pizza.png"),"Salami Pizza","Heiß und fettig! Das perfekte Gericht für den großen Hunger. Leider Nicht sehr gesund.",7.50));
            items.get(1).setFoodEffect(7,1,0,0,false);
            items.add(2,new Item(loadExternalImage("Grafik/Item/Pizza_Magherita.png"),"Pizza Magherita","Heiß und fettig! Das perfekte Gericht für den großen Hunger und unsere vegetarischen Freunde.",8));
            items.get(2).setFoodEffect(5,0,0,0,false);
            items.add(3,new Item(loadExternalImage("Grafik/Item/Hamburger.png"),"Hamburger","Der Klassiker mit extra Beef.",6));
            items.get(3).setFoodEffect(6,0,0,0,false);
            items.add(4,new Item(loadExternalImage("Grafik/Item/Cheese_Burger.png"),"Cheese Burger","Wie ein Hamburger nur mit Käse und sehr fettig.",8.50));
            items.get(4).setFoodEffect(8,1,0,0,false);
            items.add(5,new Item(loadExternalImage("Grafik/Item/Candy_Burger.png"),"Candy Burger","Zuckersüß und Karies Fördernd.",5));
            items.get(5).setFoodEffect(2,6,3,10,false);
            items.add(6,new Item(loadExternalImage("Grafik/Item/Apfel.png"),"Apfel","Knackig und Saftig!",3));
            items.get(6).setFoodEffect(2,3,2,5,false);

            items.add(7,new Item(loadExternalImage("Grafik/Item/Lilapfel.png"),"Lilapfel","Ein Lila Apfel! Ob der schmeckt?",4.50));
            items.get(7).setFoodEffect(8,1,0,0,true);
            items.add(8,new Item(loadExternalImage("Grafik/Item/Kalter Döner.png"),"Kalter Döner","Schmeckt am nächsten Morgen komischerweise doppelt so gut!",8));
            items.get(8).setFoodEffect(6,2,0,0,false);
            items.add(8,new Item(loadExternalImage("Grafik/Item/Zeitschleifen Pizza.png"),"Zeitschleifen Pizza","Wird niemals kalt, egal wie lange du sie im Rucksack vergisst.",7));
            items.get(8).setFoodEffect(12,5,0,0,false);

            items.add(9,new Item(loadExternalImage("Grafik/Item/Krümel.png"),"Krümel","Ein Krümel vom Knick Brot. Krümel das nächste mal nicht so rum!",0));
            items.get(9).setFoodEffect(1,0,0,0,false);
            items.add(10,new Item(loadExternalImage("Grafik/Item/Trockener Keks.png"),"Trockener Keks","Staubtrocken, aber rettet dich im Notfall vorm Verhungern!",10));
            items.get(10).setFoodEffect(10,0,1,5,false);

            items.add(11,new Item(loadExternalImage("Grafik/Item/Dosen Milchshake.png"),"Dosen Milchshake","Die Zukunft ruft! Einfach nur schütteln und schlürfen",15));
            items.get(11).setDrinkEffect(20,8,0,0,false);
            items.add(12,new Item(loadExternalImage("Grafik/Item/Pixel Cola.png"),"Pixel Cola","Sprudelt so extrem, dass deine Zunge kurz aufpixelt.",10));
            items.get(12).setDrinkEffect(0,10,5,15,false);
            items.add(13,new Item(loadExternalImage("Grafik/Item/Schatten Limo.png"),"Schatten Limo","Schmeckt nach Mitternacht und leuchtet leicht im Magen.",10));
            items.get(13).setDrinkEffect(0,8,5,15,false);
            items.add(14,new Item(loadExternalImage("Grafik/Item/Vierfacher Espresso.png"),"Vierfacher Espresso","So stark gebraut, dass dein Herzschlag den Takt der Welt angibt!",12));
            items.get(14).setDrinkEffect(0,10,7,30,false);

            items.add(15,new Item(loadExternalImage("Grafik/Item/Holzschwert.png"),"Holzschwert","Gibt dir Holzsplitter in den Fingern und deinen Gegnern kleine Beulen!",20));
            items.get(15).setWeaponEffect(3,85);
            items.add(16,new Item(loadExternalImage("Grafik/Item/Baseballschläger.png"),"Baseballschläger","Schnell und Schwungvoll, jedoch nicht sehr präzise",28));
            items.get(16).setWeaponEffect(6,60);
            items.add(17,new Item(loadExternalImage("Grafik/Item/Rostige Bratpfanne.png"),"Rostige Bratpfanne","Teilt soliden Schaden aus und macht ein bestechendes PONG!",30));
            items.get(17).setWeaponEffect(20,90);
            items.add(18,new Item(loadExternalImage("Grafik/Item/Neon Klinge.png"),"Neon Klinge","Leuchtet verdammt cool, verrät dein Versteck aber auf einen Kilometer.",80));
            items.get(18).setWeaponEffect(35,85);
            items.add(19,new Item(loadExternalImage("Grafik/Item/Ölige Bratpfanne.png"),"Ölige Bratpfanne","Schön schmierig und riecht nach altem Bratfett. Chance auf ausrutschen im Kampf",35));
            items.get(19).setWeaponEffect(20,90);

            items.add(20,new Item(loadExternalImage("Grafik/Item/Aluhut.png"),"Alu Hut","Setz ihn auf! Schützt verlässlich vor Gedankenlesen, aber nicht vor schiefen Blicken!",10));
            items.get(20).setArmorEffect(3, Item.ArmorType.Kopf);
            items.add(21,new Item(loadExternalImage("Grafik/Item/Baseballhelm.png"),"Baseballhelm","Schützt vor runterfallenden Steinen und vor allem vor Baseball-Bällen.",14));
            items.get(21).setArmorEffect(5, Item.ArmorType.Kopf);
            items.add(22,new Item(loadExternalImage("Grafik/Item/Lederjacke.png"),"Lederjacke","Verleiht dir +10 Coolness, schützt aber nur gegen leichten Wind.",20));
            items.get(22).setArmorEffect(7, Item.ArmorType.Brust);
            items.add(23,new Item(loadExternalImage("Grafik/Item/Lederhose.png"),"Lederhose","Verleiht dir +10 Coolness, schützt aber nur gegen leichten Wind.",20));
            items.get(23).setArmorEffect(7, Item.ArmorType.Beine);
            items.add(24,new Item(loadExternalImage("Grafik/Item/Pappkarton Rüstung.png"),"Pappkarton Rüstung","Garantiert nicht feuerfest und beim ersten Regen komplett aufgelöst!",15));
            items.get(24).setArmorEffect(4, Item.ArmorType.Brust);
            items.add(25,new Item(loadExternalImage("Grafik/Item/Pappkarton Stiefel.png"),"Pappkarton Stiefel","Garantiert nicht feuerfest und beim ersten Regen komplett aufgelöst!",12));
            items.get(25).setArmorEffect(4, Item.ArmorType.Füße);
            items.add(26,new Item(loadExternalImage("Grafik/Item/Phasen Socken.png"),"Phasen Socken","Verhindern ausrutschen, stinken dafür aber dreidimensional!",18));
            items.get(26).setArmorEffect(6, Item.ArmorType.Füße);

            items.add(27,new Item(loadExternalImage("Grafik/Item/Büroklammer.png"),"Büroklammer","Knackt mit viel Glück Schlösser oder hält deine Notizen zusammen.",0));
            items.get(27).setKramEffect();
            items.add(28,new Item(loadExternalImage("Grafik/Item/Kaugummi Papier.png"),"Kaugummi Papier","Völlig wertlos, klebt aber seit Stunden nervig an deinen Fingern.",0));
            items.get(28).setKramEffect();
            // vor dem teleportieren
            items.add(29,new Item(loadExternalImage("Grafik/Item/Taschenuhr1.png"),"Taschenuhr","Eine mysteriöse Taschenuhr.",0));
            items.get(29).setKramEffect();
            //nach dem teleportieren
            items.add(30,new Item(loadExternalImage("Grafik/Item/Taschenuhr Kaputt.png"),"Kaputte Taschenuhr","Starke Risse, sie könnte aber repariert werden.",0));
            items.get(30).setKramEffect();
            items.add(31,new Item(loadExternalImage("Grafik/Item/Quietscheente.png"),"Quietscheente","Macht laut QUIEK! und bringt dir mentalen Beistand in dunklen Höhlen.",0));
            items.get(31).setKramEffect();

            items.add(32,new Item(loadExternalImage("Grafik/Item/Kaugummi.png"),"Kaugummi","Hilft sehr gut gegen stress und erfrischt deinen Kopf.",10));
            items.get(32).setFoodEffect(0,14,0,0,false);
            items.add(33,new Item(loadExternalImage("Grafik/Item/Glühender Kaugummi.png"),"Glühender Kaugummi","Völlig ungefährlich. Lass dich durch das Leuchten nicht verunsichern.",15));
            items.get(33).setFoodEffect(0,30,0,0,false);
            items.add(33,new Item(loadExternalImage("Grafik/Item/Knick Brot.png"),"Knick Brot","Knuspert laut im Mund und krümelt dir das gesamte Inventar voll.",4));
            items.get(33).setFoodEffect(0,10,0,0,false);

            items.add(34,new Item(loadExternalImage("Grafik/Item/Ingwer Shot.png"),"Ingwer Shot","Schwupp! Mit einem Shot ist das gesamte Immunsystem gestärkt.",15));
            items.get(34).setDrinkEffect(0,4,0,0,true);

            items.add(35,new Item(loadExternalImage("Grafik/Item/Schlüssel.png"),"Schlüssel","Was der wohl öffnet?",0));
            items.get(35).setKramEffect();
            items.add(36,new Item(loadExternalImage("Grafik/Item/Pokal.png"),"Pokal","Sieh nur wie er glänzt und funkelt!",0));
            items.get(36).setKramEffect();
            items.add(37,new Item(loadExternalImage("Grafik/Item/Laserpointer.png"),"Laserpointer","Ist im Kampf hilfreich, um den Gegner zu blenden.",20));
            items.get(37).setKramEffect();
            items.add(38,new Item(loadExternalImage("Grafik/Item/Wasserrakete.png"),"Wasserrakete","Wuush! Ist im Kampf hilfreich um auf Gegner abzufeuern.",15));
            items.get(38).setKramEffect();
            items.add(39,new Item(loadExternalImage("Grafik/Item/Dreifache Wasserrakete.png"),"Dreifache Wasserrakete","Wuush! Auch die stärkere Version ist im Kampf hilfreich um auf Gegner abzufeuern.",15));
            items.get(39).setKramEffect();

            printOnDebug("Items geladen!");





        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public InputStream loadExternalText(String relativePath) {
        try {
            File externalFile = new File("assets"+shortVersion+"/" + relativePath);

            if (externalFile.exists()) {
                return externalFile.toURI().toURL().openStream();
            }

            InputStream internalStream = getClass().getClassLoader().getResourceAsStream(relativePath);
            if (internalStream != null) {
                return internalStream;
            }else {
                printErrOnDebug("Textdatei oder Json Datei existiert nicht oder ist falsch benannt: " + relativePath);
            }

        } catch (Exception e) {
            printErrOnDebug("Text oder Json konnte nicht geladen werden: " + relativePath);

        }
        return null;
    }

    public String getExternalPath(String relativePath) {
        String finalPath;
        try {
            File externalFile = new File("assets"+shortVersion+"/" + relativePath);

            if (externalFile.exists()) {
                finalPath = "assets"+shortVersion+"/" + relativePath;
                return finalPath;
            }else {
                finalPath = "assets/" + relativePath;
                return finalPath;
            }



        } catch (Exception e) {
            printErrOnDebug("Text oder Json konnte nicht geladen werden: " + relativePath);

        }
        return null;
    }

    public boolean checkExternalPath(String relativePath) {
        File externalFile = new File("assets"+shortVersion+"/" + relativePath);
        try {
            if (externalFile.exists()) {
                if (ImageIO.read(externalFile)!=null) {
                    return true;
                }
            }
            InputStream internalStream = getClass().getClassLoader().getResourceAsStream(relativePath);
            if (internalStream != null) {
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public BufferedImage loadExternalImage(String relativePath) {
        try {
            File externalFile = new File("assets"+shortVersion+"/" + relativePath);

            if (externalFile.exists()) {
                return ImageIO.read(externalFile);
            }

            InputStream internalStream = getClass().getClassLoader().getResourceAsStream(relativePath);
            if (internalStream != null) {
                return ImageIO.read(internalStream);
            }else {
                printErrOnDebug("Bild existiert nicht oder ist falsch benannt: " + relativePath);
                return ImageIO.read(getClass().getClassLoader().getResourceAsStream("Grafik/missing_texture.png"));
            }

        } catch (Exception e) {
            printErrOnDebug("Bild konnte nicht geladen werden: " + relativePath);

        }
        return null;
    }



    public void showOptionPane(){
        Object[] options = {"ja", "abbrechen"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Das Programm ist in Begriff alle älteren Assets Ordner-Versionen zu löschen und zu erneuern. Fortfahren?",
                "Meldung",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            deleteConfirmed = true;
            extractAllAssets();
        } else if (choice == 1) {
            System.exit(2);
        }
    }




    boolean deleteConfirmed = false;
    public void extractAllAssets() {
        try {
            File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

            boolean isRunningAsJar = jarFile.isFile() && jarFile.getName().endsWith(".jar");

            if (!isRunningAsJar) {
                printOnDebug("Spiel läuft in IntelliJ. Keine automatische Extraktion oder Bereinigung nötig");
                gameInIntelliJ = true;
                return;
            }else {
                gameInIntelliJ = false;
            }


            File externalAssetsFolder = new File("assets" + shortVersion);
            String currentAssetsFolderName = externalAssetsFolder.getName();

            File currentDir = new File(".");
            File[] filesInDir = currentDir.listFiles();

            if (filesInDir != null) {
                for (File file : filesInDir) {
                    if (file.isDirectory() && file.getName().startsWith("assets") && !file.getName().equals(currentAssetsFolderName)) {
                        if (deleteConfirmed) {
                            printOnDebug("Veralteter Assets-Ordner gefunden: " + file.getName() + ". Wird bereinigt...");
                            deleteDirectoryRecursively(file);
                        }else  {
                            showOptionPane();
                            return;
                        }

                    }
                }
            }

            if (externalAssetsFolder.exists()) {
                return;
            }

            printOnDebug("Erster Start dieser Version erkannt. Erstelle externe Assets...");

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();

                    if (!entryName.endsWith(".class") && !entryName.startsWith("META-INF/") && !entry.isDirectory()) {
                        File destFile = new File(externalAssetsFolder, entryName);

                        destFile.getParentFile().mkdirs();

                        try (InputStream in = jar.getInputStream(entry)) {
                            Files.copy(in, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            printOnDebug("Extrahiert: " + entryName);
                        }
                    }
                }
            }
            printOnDebug("Alle Standard-Dateien erfolgreich exportiert");

        } catch (Exception e) {
            printErrOnDebug("Fehler beim automatischen Entpacken der Assets: " + e.getMessage());
        }
    }

    private void deleteDirectoryRecursively(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file);
            }
        }
        directoryToBeDeleted.delete();
    }


    public Entity.LookDirection getOppositeDirection(Enum type,Player.PlayerLookDirection lookDirection){

        boolean mainCharacter = false;
        if (type == Entity.Type.MainCharacter) {
            mainCharacter = true;
        }

        if (mainCharacter){
            return switch (lookDirection) {
                case upLeft -> Entity.LookDirection.downRight;
                case upRight -> Entity.LookDirection.downLeft;
                case downLeft -> Entity.LookDirection.upRight;
                case downRight -> Entity.LookDirection.upLeft;
                case up -> Entity.LookDirection.down;
                case down -> Entity.LookDirection.up;
                case left -> Entity.LookDirection.right;
                case right -> Entity.LookDirection.left;
            };

        }else {
            return switch (lookDirection){
                case upLeft -> Entity.LookDirection.down;
                case upRight -> Entity.LookDirection.down;
                case downLeft -> Entity.LookDirection.up;
                case downRight -> Entity.LookDirection.up;
                case up -> Entity.LookDirection.down;
                case down -> Entity.LookDirection.up;
                case left -> Entity.LookDirection.right;
                case right -> Entity.LookDirection.left;
            };
        }
    }




}
