import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;


public class GameManager {

    private final Main main;
    private final MenuDisplay menuDisplay;
    private final GameDisplay gameDisplay;
    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    public MenuDisplay.NewGameQuestState lastProcessedQuest = null;
    double timePerFrame;

    public int fpsCounter = 0;

    private final Map<String, KeyStroke[]> activeKeyStrokes = new HashMap<>();







    public GameManager(Main main) {
        this.main = main;
        this.menuDisplay = Main.menuDisplay;
        this.gameDisplay = Main.gameDisplay;


    }



    public void startGameLoop() {

        refreshFrameRate();
        long lastTime = System.nanoTime();
        double delta = 0;


        long timer = System.currentTimeMillis();




        while (main.loop){
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerFrame;
            lastTime = now;


            while (delta >= 1) {

                switch (main.gameStatus) {
                    case PLAYING -> {
                        main.cardLayout.show(main.mainPanel, "Game");


                        if (main.gameIsPaused){
                            main.setGameStatus(Main.GameState.PAUSED);
                            gameDisplay.pauseStartTime = System.currentTimeMillis();
                            gameDisplay.neonstartTime = 0f;
                        }
                        if (main.invIsOpen){
                            main.setGameStatus(Main.GameState.Inventory);
                            gameDisplay.invstartTime = System.currentTimeMillis();
                        }

                        if (!main.onInteraction){
                            gameDisplay.updateAll();
                            if (Main.player.isSweating){
                                updateSweatSprite();
                            }
                        }

                        gameDisplay.repaint();

                    }
                    case Inventory -> {
                        if (!main.invIsOpen){
                            gameDisplay.canStartAnimation = 0;
                            gameDisplay.invChoosedCharacter = 0;
                            gameDisplay.invRealChoosedCharacter =0;
                            gameDisplay.changeCharacterInv(0);
                            gameDisplay.chosedInvCharacter = Main.player;
                            gameDisplay.itemStartScroll = 0;
                            gameDisplay.hoveredItem = -1;

                            main.setGameStatus(Main.GameState.PLAYING);
                        }
                        if (Main.player.isMoving){
                            Main.player.isMoving = false;
                        }
                        gameDisplay.repaint();

                    }
                    case PAUSED -> {
                        if (!main.gameIsPaused){
                            main.setGameStatus(Main.GameState.PLAYING);
                        }
                        if (Main.player.isMoving){
                            Main.player.isMoving = false;
                        }
                        gameDisplay.repaint();

                    }
                    case GAME_OVER -> {
                        main.printOnDebug("Game over...");


                    }
                    case MAIN_MENU -> {
                        main.cardLayout.show(main.mainPanel, "Menu");



                    }
                    case FIGHTING -> {
                        main.printOnDebug("Fighting...");
                        main.cardLayout.show(main.mainPanel, "Fight");

                    }
                    case SETTINGS -> {
                        main.cardLayout.show(main.mainPanel, "Settings");
                        if(menuDisplay.areYouSure){
                            menuDisplay.areYouSure = false;
                            menuDisplay.brikButton3.setText("Spiel verlassen");
                        }

                    }
                    case VOLUMESETTINGS -> {
                        main.cardLayout.show(main.mainPanel, "Volume settings");

                    }
                    case GRAPHICSETTINGS -> {
                        main.cardLayout.show(main.mainPanel, "Graphic settings");
                    }

                    case DATEIAUSWAHL -> {

                        if (!menuDisplay.auswahlPanel.isVisible()){
                            menuDisplay.refreshDateiChoosePanels();
                            if(menuDisplay.areYouSure){
                                menuDisplay.areYouSure = false;
                                menuDisplay.brikButton3.setText("Spiel verlassen");
                            }

                            main.cardLayout.show(main.mainPanel, "Datei auswahl");
                        }




                    }
                    case KEY_BINDING -> {
                        main.cardLayout.show(main.mainPanel, "Key binding");

                    }
                    case Start_New_Game -> {
                        main.cardLayout.show(main.mainPanel, "Config New Game");
                        switch (menuDisplay.newGameQuestState){

                            case Quest1 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest1) {
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest1;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest1");



                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest1.setText("Benenne deine ganz eigene Speicherdatei.");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest1.startTypewrtiting("Benenne deine ganz eigene Speicherdatei.");
                                    }



                                }
                                menuDisplay.newGameNameDatei.requestFocusInWindow();



                            }
                            case Quest2 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest2){
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest2;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest2");


                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest2.setText("Was ist dein Lieblingsessen?");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest2.startTypewrtiting("Was ist dein Lieblingsessen?");
                                    }



                                }
                                menuDisplay.newGameFavFood.requestFocusInWindow();

                            }
                            case Quest3 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest3){
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest3;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest3");


                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest3.setText("Was ist dein Hobby?");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest3.startTypewrtiting("Was ist dein Hobby?");
                                    }



                                }
                                menuDisplay.newGameHobby.requestFocusInWindow();

                            }
                            case Quest4 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest4){
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest4;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest4");


                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest4.setText("Du schreibst ein Buch, wie würde dein junger männlicher Hauptcharakter heißen?");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest4.startTypewrtiting("Du schreibst ein Buch, wie würde dein junger männlicher Hauptcharakter heißen?");
                                    }


                                }

                                menuDisplay.newGameNameTimmy.requestFocusInWindow();

                            }
                            case Quest5 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest5){
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest5;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest5");


                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest5.setText(main.NameTimmy+" findet eine Freundin, wie nennst du sie?");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest5.startTypewrtiting(main.NameTimmy+" findet eine Freundin, wie nennst du sie?");
                                    }


                                }

                                menuDisplay.newGameNameFinja.requestFocusInWindow();

                            }
                            case Quest6 ->  {

                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Quest6){
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Quest6;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "quest6");



                                    if (menuDisplay.changefromQuestreview){
                                        menuDisplay.newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
                                        menuDisplay.NewGamequest6.setText(main.NameTimmy+" und "+main.NameFinja+" finden einen Roboter. Gebe ihm ebenfalls einen Namen.");
                                    }else {
                                        menuDisplay.newGameConfigstartGamebutton.setText("Weiter");
                                        menuDisplay.NewGamequest6.startTypewrtiting(main.NameTimmy+" und "+main.NameFinja+" finden einen Roboter. Gebe ihm ebenfalls einen Namen.");
                                    }


                                }

                                menuDisplay.newGameNameKizo.requestFocusInWindow();

                            }
                            case Questreview -> {


                                if (lastProcessedQuest != MenuDisplay.NewGameQuestState.Questreview){
                                    menuDisplay.changefromQuestreview = true;
                                    lastProcessedQuest = MenuDisplay.NewGameQuestState.Questreview;
                                    menuDisplay.NewGameCardLayout.show(menuDisplay.cardPanel, "questreview");
                                    menuDisplay.reviewquestbutton1.setText("Datei Name: "+main.saveGameStrings[main.loadedGame][0]);
                                    menuDisplay.reviewquestbutton2.setText("Lieblingsessen: "+main.favFood);
                                    menuDisplay.reviewquestbutton3.setText("Hobby: "+main.Hobby);
                                    menuDisplay.reviewquestbutton4.setText("Name des Jungen: "+main.NameTimmy);
                                    menuDisplay.reviewquestbutton5.setText("Name des Mädchen: "+main.NameFinja);
                                    menuDisplay.reviewquestbutton6.setText("Name des Roboters: "+main.NameKizo);




                                    menuDisplay.newGameConfigstartGamebutton.setText("Auf ins Abenteuer!");

                                }



                            }




                        }

                    }


                    case null, default -> {
                        main.printErrOnDebug("Invalid Input: GameStatus is null");
                        System.exit(-1);
                    }
                }


                fpsCounter++;

                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000) {
                if (fpsCounter>1){
                    main.reachedFPS = fpsCounter;
                }else {
                    main.reachedFPS = 1;
                }

                fpsCounter = 0;
                timer += 1000;
                if (main.showFPS) {
                    Runtime runtime = Runtime.getRuntime();
                    long mb = 1024L * 1024L;
                    main.usedRam = (runtime.totalMemory() - runtime.freeMemory()) / mb;
                    main.maxRam = runtime.maxMemory() / mb;
                    double cpuValue = osBean.getProcessCpuLoad();
                    main.cpuPercent = (cpuValue < 0) ? 0 : (int) Math.round(cpuValue * 100);
                }
                if (main.gameIsActive){
                    main.playTime+=((double) 1 /3600);
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void refreshFrameRate() {

        timePerFrame = 1000000000.0 / main.targetFPS;
    }

    public void setUpControls() {
        ActionMap actionMap = main.mainPanel.getActionMap();

        actionMap.put("sprint_start", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manualKeyInput) {
                    Main.player.isSprinting = true;
                }
            }
        });
        actionMap.put("sprint_stop",  new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manualKeyInput) {
                    Main.player.isSprinting = false;
                }
                if (main.onInteraction){
                    gameDisplay.skipDialog = true;
                    gameDisplay.finishDialog = true;
                }
            }
        });
        actionMap.put("start_up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.upPressed = true;
            }
        });
        actionMap.put("stop_up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.upPressed = false;
            }
        });
        actionMap.put("start_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.leftPressed = true;
            }
        });
        actionMap.put("stop_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.leftPressed = false;
            }
        });
        actionMap.put("start_down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.downPressed = true;
            }
        });
        actionMap.put("stop_down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.downPressed = false;
            }
        });
        actionMap.put("start_right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.rightPressed = true;
            }
        });
        actionMap.put("stop_right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.rightPressed = false;
            }
        });
        actionMap.put("toggleVollbild",  new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.vollbild) {
                    //Vollbild deaktivieren
                    main.vollbild = false;
                    main.switchVollbild();
                }else{
                    //Vollbild Aktivieren
                    main.vollbild = true;
                    main.switchVollbild();
                }
            }
        });
        actionMap.put("toggleGamePaused",  new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.gameStatus == Main.GameState.SETTINGS) {
                    if (main.gameIsActive) {
                        main.setGameStatus(Main.GameState.PLAYING);
                    } else {
                        main.setGameStatus(Main.GameState.MAIN_MENU);
                    }
                } else if (main.gameStatus == Main.GameState.PLAYING) {
                    if (!main.gameIsPaused) {
                        main.gameIsPaused = true;
                    }
                }else if(main.gameStatus == Main.GameState.PAUSED){
                    if (main.gameIsPaused) {
                        main.gameIsPaused = false;
                        main.setGameStatus(Main.GameState.PLAYING);
                    }

                }else if(main.gameStatus == Main.GameState.Inventory&&!gameDisplay.itemInfoShown&&!gameDisplay.invOpenCauseDialog) {
                    main.invIsOpen = false;
                }else if(main.gameStatus == Main.GameState.Inventory&&gameDisplay.itemInfoShown){
                    gameDisplay.itemInfoShown = false;
                }else if (main.gameStatus == Main.GameState.GRAPHICSETTINGS){
                    main.setGameStatus(Main.GameState.SETTINGS);
                }else if (main.gameStatus == Main.GameState.VOLUMESETTINGS){
                    main.setGameStatus(Main.GameState.SETTINGS);
                }else if (main.gameStatus == Main.GameState.DATEIAUSWAHL){
                    main.setGameStatus(Main.GameState.MAIN_MENU);
                }
            }
        });

        actionMap.put("toggleInventory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.invIsOpen&&!gameDisplay.invOpenCauseDialog) {
                    main.invIsOpen = false;
                    gameDisplay.itemInfoShown = false;
                }else if(main.gameStatus == Main.GameState.PLAYING){
                    main.invIsOpen = true;
                }
            }
        });
        actionMap.put("toggleDebug", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.showFPS){
                    main.showFPS = false;
                }else {main.showFPS = true;}
            }
        });
        actionMap.put("triggerInteraction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!main.gameIsPaused&&!main.invIsOpen){
                    Main.player.checkInteraction();
                }

            }
        });





        updateKeyBindings();
    }

    public void updateKeyBindings() {
        InputMap inputMap = main.mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        for (KeyStroke[] strokes : activeKeyStrokes.values()) {
            for (KeyStroke stroke : strokes) {
                inputMap.remove(stroke);
            }
        }
        activeKeyStrokes.clear();

        KeyStroke startSprint = KeyStroke.getKeyStroke(main.keyBinds[7],0,false);
        KeyStroke stopSprint = KeyStroke.getKeyStroke(main.keyBinds[7],0,true);
        KeyStroke upPressed = KeyStroke.getKeyStroke(main.keyBinds[0], 0, false);
        KeyStroke upReleased = KeyStroke.getKeyStroke(main.keyBinds[0], 0, true);
        KeyStroke leftPressed = KeyStroke.getKeyStroke(main.keyBinds[1], 0, false);
        KeyStroke leftReleased = KeyStroke.getKeyStroke(main.keyBinds[1], 0, true);
        KeyStroke downPressed = KeyStroke.getKeyStroke(main.keyBinds[2], 0, false);
        KeyStroke downReleased = KeyStroke.getKeyStroke(main.keyBinds[2], 0, true);
        KeyStroke rightPressed = KeyStroke.getKeyStroke(main.keyBinds[3], 0, false);
        KeyStroke rightReleased = KeyStroke.getKeyStroke(main.keyBinds[3], 0, true);
        KeyStroke toggleVollbild = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, true);
        KeyStroke toggleGamePaused = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
        KeyStroke toggleInventory = KeyStroke.getKeyStroke(main.keyBinds[4], 0, true);
        KeyStroke toggleDebug = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true);
        KeyStroke triggerInteraction = KeyStroke.getKeyStroke(main.keyBinds[5], 0, true);

        inputMap.put(startSprint,"sprint_start");
        inputMap.put(stopSprint,"sprint_stop");
        inputMap.put(upPressed, "start_up");
        inputMap.put(upReleased, "stop_up");
        inputMap.put(leftPressed, "start_left");
        inputMap.put(leftReleased, "stop_left");
        inputMap.put(downPressed, "start_down");
        inputMap.put(downReleased, "stop_down");
        inputMap.put(rightPressed, "start_right");
        inputMap.put(rightReleased, "stop_right");
        inputMap.put(toggleVollbild, "toggleVollbild");
        inputMap.put(toggleGamePaused, "toggleGamePaused");
        inputMap.put(toggleInventory, "toggleInventory");
        inputMap.put(toggleDebug, "toggleDebug");
        inputMap.put(triggerInteraction, "triggerInteraction");


        activeKeyStrokes.put("sprint", new KeyStroke[]{startSprint,stopSprint});
        activeKeyStrokes.put("up", new KeyStroke[]{upPressed, upReleased});
        activeKeyStrokes.put("left", new KeyStroke[]{leftPressed, leftReleased});
        activeKeyStrokes.put("down", new KeyStroke[]{downPressed, downReleased});
        activeKeyStrokes.put("right", new KeyStroke[]{rightPressed, rightReleased});
        activeKeyStrokes.put("pause",new KeyStroke[]{toggleGamePaused});
        activeKeyStrokes.put("Inventory", new KeyStroke[]{toggleInventory});
        activeKeyStrokes.put("triggerInteraction",new KeyStroke[]{triggerInteraction});

        main.mainPanel.revalidate();
    }



    public boolean manualKeyInput = true;

    public void manualKeyInput(boolean input){

        manualKeyInput = input;
        if(!input){
            InputMap inputMap = main.mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

            for (KeyStroke[] strokes : activeKeyStrokes.values()) {
                for (KeyStroke stroke : strokes) {
                    if (stroke.getKeyCode() != main.keyBinds[7]) {
                        inputMap.remove(stroke);
                    }
                }
            }
            activeKeyStrokes.clear();

            KeyStroke toggleVollbild = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, true);
            inputMap.put(toggleVollbild, "toggleVollbild");

            main.mainPanel.revalidate();

            main.upPressed = false;
            main.downPressed = false;
            main.leftPressed = false;
            main.rightPressed = false;
            main.sprinting = false;
            Main.player.isMoving = false;

        }else {
            updateKeyBindings();
        }


    }
    long lastSpriteChange = 0;
    int currentFrameNum = 0;

    public void updateSweatSprite(){

        if (System.currentTimeMillis()-lastSpriteChange>80){
            if (currentFrameNum+1<Main.player.sweatFrames.size()) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }

        main.currentSweatSprite = Main.player.sweatFrames.get(currentFrameNum);
    }




}
