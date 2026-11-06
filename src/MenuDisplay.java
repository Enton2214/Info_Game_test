import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;

public class MenuDisplay {

    private final Main main;
    private final GameDisplay gameDisplay;

    boolean areYouSure = false;

    boolean areYouSureleaveNewGame = false;
    boolean changefromQuestreview = false;

    public int[] vorschlagButtonStand = {0,0,0,0,0,0};
    int[] vorschlagDataStand = {0,0,0,0,0,0};

    JPanel menuPanel;
    JPanel menuButtonHolder;
    JPanel settingsPanel;
    JPanel settingsButtonHolder;
    JPanel volumePanel;
    JPanel graphicPanel;
    JPanel keyBindPanel;
    JPanel KeysHolder;
    JPanel auswahlPanel;
    JPanel newGamePanel;

    JPanel backButtonHolderSettings;
    BrikButton brikButton1;
    BrikButton brikButton2;
    BrikButton brikButton3;
    BrikText brikTextPane;
    BrikButton brikButton4;
    BrikButton brikButton5;
    BrikButton brikButton6;
    BrikButton brikButton7;
    BrikText brikTextSettings;

    BrikButton themeButton1;
    BrikButton themeButton2;
    BrikButton themeButton3;
    BrikButton themeButton4;
    BrikButton themeButton5;

    BrikSlider brikSlider1;
    BrikButton graphicsButton1;
    BrikButton graphicsButton2;
    BrikButton graphicsButton3;
    BrikButton graphicsButton4;
    BrikButton graphicBackButton;
    BrikText graphicHeadLine;
    JPanel graphicBackButtonHolder;
    JPanel headlinePanel;

    KeyBinder keyButton1;
    KeyBinder keyButton2;
    KeyBinder keyButton3;
    KeyBinder keyButton4;
    KeyBinder keyButton5;
    KeyBinder keyButton6;
    KeyBinder keyButton7;
    KeyBinder keyButton8;
    BrikButton backButtonKeyBinder;
    JPanel backButtonKeyHolder;
    JScrollPane keysScrollPane;
    BrikText keyBindText;

    BrikText volumeHeadLine;
    JPanel volumeButtonHolder;
    JPanel sliderHolder;
    BrikButton volumeButton1;
    BrikSlider volumeSlider1;
    BrikSlider volumeSlider2;
    BrikSlider volumeSlider3;
    BrikSlider volumeSlider4;
    BrikSlider volumeSlider5;

    JPanel chooseSaveButtonHolder;
    BrikButton backButtonChoose;
    JPanel backButtonChooseHolder;
    JPanel HeadlineHolderDateiChoose;
    BrikText DateiChooseHeadline;
    BrikText DateiChooseLore;
    JPanel DateiChoosePanel1;
    BrikButton loadDataButon1;

    JPanel DateiChoosePanel2;
    BrikButton loadDataButon2;

    JPanel DateiChoosePanel3;
    BrikButton loadDataButon3;

    JPanel panel2Save1;
    BrikText InfoText1Save1;
    BrikText InfoText2Save1;
    BrikText InfoText3Save1;
    BrikText InfoText4Save1;

    JPanel panel2Save2;
    BrikText InfoText1Save2;
    BrikText InfoText2Save2;
    BrikText InfoText3Save2;
    BrikText InfoText4Save2;

    JPanel panel2Save3;
    BrikText InfoText1Save3;
    BrikText InfoText2Save3;
    BrikText InfoText3Save3;
    BrikText InfoText4Save3;


    CardLayout NewGameCardLayout;
    JPanel cardPanel;

    BrikText NewGameConfigHeadline;


    JPanel NewGamequest1panel;
    BrikText NewGamequest1;
    JTextField newGameNameDatei;
    JPanel newGameButtonHolder1;
    BrikButton newGameConfigVorschlagbutton1;

    JPanel NewGamequest2panel;
    BrikText NewGamequest2;
    JTextField newGameFavFood;
    JPanel newGameButtonHolder2;
    BrikButton newGameConfigVorschlagbutton2;

    JPanel NewGamequest3panel;
    BrikText NewGamequest3;
    JTextField newGameHobby;
    JPanel newGameButtonHolder3;
    BrikButton newGameConfigVorschlagbutton3;

    JPanel NewGamequest4panel;
    BrikText NewGamequest4;
    JTextField newGameNameTimmy;
    JPanel newGameButtonHolder4;
    BrikButton newGameConfigVorschlagbutton4;

    JPanel NewGamequest5panel;
    BrikText NewGamequest5;
    JTextField newGameNameFinja;
    JPanel newGameButtonHolder5;
    BrikButton newGameConfigVorschlagbutton5;

    JPanel NewGamequest6panel;
    BrikText NewGamequest6;
    JTextField newGameNameKizo;
    JPanel newGameButtonHolder6;
    BrikButton newGameConfigVorschlagbutton6;

    JPanel NewGameQuestReviewpanel;
    JPanel holderPanel;
    BrikText NewGameQuestRewiev;
    JButton reviewquestbutton1;
    JButton reviewquestbutton2;
    JButton reviewquestbutton3;
    JButton reviewquestbutton4;
    JButton reviewquestbutton5;
    JButton reviewquestbutton6;


    JPanel panelbottom;
    BrikButton newGameConfigbreakupButton;
    BrikButton newGameConfigstartGamebutton;

    public NewGameQuestState newGameQuestState = NewGameQuestState.Quest1;

    public enum NewGameQuestState{
        Quest1,
        Quest2,
        Quest3,
        Quest4,
        Quest5,
        Quest6,
        Questreview

    }

    public void setNewGameQuestState(NewGameQuestState newGameQuestState){
        this.newGameQuestState = newGameQuestState;
    }




    SimpleAttributeSet center = new SimpleAttributeSet();


    public MenuDisplay(Main main){
        this.main = main;
        this.gameDisplay = Main.gameDisplay;

    }

    public void loadMenu(){
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(main.themeColor[0]);
        menuPanel.setLayout(new BorderLayout());

        JPanel menuButtonHolder = new JPanel();
        menuButtonHolder.setOpaque(false);
        menuButtonHolder.setLayout(new BoxLayout(menuButtonHolder, BoxLayout.PAGE_AXIS));



        BrikButton brikButton1 = new BrikButton(main);
        brikButton1.setText("Spielen");
        brikButton1.addActionListener(e -> {

            main.setGameStatus(Main.GameState.DATEIAUSWAHL);


        });

        BrikButton brikButton2 = new BrikButton(main);
        brikButton2.setText("Einstellungen");
        brikButton2.addActionListener(e -> {

           main.setGameStatus(Main.GameState.SETTINGS);
        });


        BrikButton brikButton3 = new BrikButton(main);
        brikButton3.setText("Spiel verlassen");
        brikButton3.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        brikButton3.addActionListener(e -> {
            brikButton3.setText("Bist du Sicher?");

            if (areYouSure){
                main.loop = false;
                main.printOnDebug("Spiel verlassen");
                Main.variableManager.saveAllVars();
                System.exit(3);
            }

            areYouSure = true;




        });

        this.brikButton1 = brikButton1;
        this.brikButton2 = brikButton2;
        this.brikButton3 = brikButton3;

        this.menuPanel = menuPanel;
        this.menuButtonHolder = menuButtonHolder;


        menuButtonHolder.add(brikButton1);
        menuButtonHolder.add(brikButton2);
        menuButtonHolder.add(brikButton3);

        BrikText textPane = new BrikText(main);
        if (main.devMode) {
            textPane.setText(main.version+" - Developer Mode");
        }else {
            textPane.setText(main.version);
        }

        this.brikTextPane = textPane;

        menuPanel.add(textPane, BorderLayout.SOUTH);
        menuPanel.add(menuButtonHolder, BorderLayout.WEST);

        menuButtonHolder.setBorder(new EmptyBorder(main.frameHeight/3,main.frameWidth/16,0,0));



    }


    public void loadSettings(){
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(main.themeColor[0]);
        settingsPanel.setLayout(new BorderLayout());
        JPanel settingsButtonHolder = new JPanel();
        settingsButtonHolder.setOpaque(false);
        settingsButtonHolder.setLayout(new BoxLayout(settingsButtonHolder, BoxLayout.PAGE_AXIS));

        BrikButton brikButton4 = new BrikButton(main);
        brikButton4.setText("Lautstärke");
        brikButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
        brikButton4.addActionListener(e -> {
            main.setGameStatus(Main.GameState.VOLUMESETTINGS);

        });

        BrikButton brikButton5 = new BrikButton(main);
        brikButton5.setText("Grafik");
        brikButton5.setAlignmentX(Component.CENTER_ALIGNMENT);
        brikButton5.addActionListener(e -> {
            main.setGameStatus(Main.GameState.GRAPHICSETTINGS);
        });

        backButtonHolderSettings = new JPanel();
        backButtonHolderSettings.setOpaque(false);
        backButtonHolderSettings.setLayout(new BorderLayout());

        BrikButton brikButton6 = new BrikButton(main);
        brikButton6.setText("Zurück");
        brikButton6.setAlignmentX(Component.CENTER_ALIGNMENT);
        brikButton6.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        brikButton6.addActionListener(e -> {
            if (main.gameIsPaused&&main.gameIsActive){
                main.setGameStatus(Main.GameState.PLAYING);
            }else {
                main.setGameStatus(Main.GameState.MAIN_MENU);
            }


        });
        backButtonHolderSettings.add(brikButton6);
        backButtonHolderSettings.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));

        BrikButton brikButton7 = new BrikButton(main);
        brikButton7.setText("Tastenbelegung");
        brikButton7.setAlignmentX(Component.CENTER_ALIGNMENT);
        brikButton7.addActionListener(e -> {
           main.setGameStatus(Main.GameState.KEY_BINDING);
        });

        BrikText textPane2 = new BrikText(main);
        textPane2.setText("EINSTELLUNGEN");
        textPane2.SetFont(main.headLineFont);


        textPane2.getStyledDocument().setParagraphAttributes(0, textPane2.getText().length(), center, false);

        this.brikTextSettings = textPane2;

        this.brikButton4 = brikButton4;
        this.brikButton5 = brikButton5;
        this.brikButton6 = brikButton6;
        this.brikButton7 = brikButton7;

        this.settingsPanel = settingsPanel;
        this.settingsButtonHolder = settingsButtonHolder;


        settingsButtonHolder.add(brikButton4);
        settingsButtonHolder.add(brikButton5);
        settingsButtonHolder.add(brikButton7);


        settingsPanel.add(textPane2, BorderLayout.NORTH);
        settingsPanel.add(settingsButtonHolder, BorderLayout.CENTER);
        settingsPanel.add(backButtonHolderSettings, BorderLayout.SOUTH);

        textPane2.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));


    }


    public void loadVolumeSettings(){
        JPanel volumePanel = new JPanel();
        volumePanel.setBackground(main.themeColor[0]);
        volumePanel.setLayout(new BorderLayout());

        volumeHeadLine = new BrikText(main);
        volumeHeadLine.SetFont(main.headLineFont);
        volumeHeadLine.setText("LAUTSTÄRKE EINSTELLUNGEN");
        volumeHeadLine.getStyledDocument().setParagraphAttributes(0, volumeHeadLine.getText().length(), center, false);
        volumeHeadLine.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));

        BrikButton volumebutton1 = new BrikButton(main);
        volumebutton1.setText("Zurück");
        volumebutton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumebutton1.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        volumebutton1.addActionListener(e -> {
            main.setGameStatus(Main.GameState.SETTINGS);
        });
        volumeButtonHolder = new JPanel();
        volumeButtonHolder.setOpaque(false);
        volumeButtonHolder.setLayout(new BorderLayout());
        volumeButtonHolder.add(volumebutton1,BorderLayout.CENTER);
        volumeButtonHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));

        sliderHolder = new JPanel(new GridLayout(1, 2));
        sliderHolder.setOpaque(false);
        JPanel volumeSilderHolderLeft = new JPanel();
        volumeSilderHolderLeft.setOpaque(false);
        volumeSilderHolderLeft.setLayout(new BoxLayout(volumeSilderHolderLeft, BoxLayout.PAGE_AXIS));
        JPanel volumeSilderHolderRight = new JPanel();
        volumeSilderHolderRight.setOpaque(false);
        volumeSilderHolderRight.setLayout(new BoxLayout(volumeSilderHolderRight, BoxLayout.PAGE_AXIS));


        volumeSlider1 = new BrikSlider(main);
        volumeSlider1.setLabelText("UI");
        volumeSlider1.setValue((int) (main.volumes[0]*30));
        volumeSlider1.addChangeListener(e -> {
            main.volumes[0] = (double) volumeSlider1.getValue()/60;

        });

        volumeSlider2 = new BrikSlider(main);
        volumeSlider2.setLabelText("Musik");
        volumeSlider2.setValue((int) (main.volumes[1]*30));
        volumeSlider2.addChangeListener(e -> {
            main.volumes[1] = (double) volumeSlider2.getValue()/30;

        });

        this.volumeButton1 = volumebutton1;

        this.volumePanel = volumePanel;

        volumeSilderHolderLeft.add(volumeSlider1);
        volumeSilderHolderRight.add(volumeSlider2);

        volumePanel.add(volumeHeadLine, BorderLayout.NORTH);
        volumePanel.add(sliderHolder, BorderLayout.CENTER);
        volumePanel.add(volumeButtonHolder, BorderLayout.SOUTH);

        sliderHolder.add(volumeSilderHolderLeft);
        sliderHolder.add(volumeSilderHolderRight);
    }

    public void loadGraphicSettings(){


        JPanel graphicPanel = new JPanel();
        graphicPanel.setBackground(main.themeColor[0]);
        graphicPanel.setLayout(new BorderLayout());




        JWindow graphicWindowSmall = new JWindow();
        graphicWindowSmall.setVisible(false);
        JPanel graphicPanelSmall = new JPanel();
        graphicPanelSmall.setLayout(new BorderLayout());
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.PAGE_AXIS));

        graphicHeadLine = new BrikText(main);
        graphicHeadLine.SetFont(main.headLineFont);
        graphicHeadLine.setText("GRAFIK EINSTELLUNGEN");
        graphicHeadLine.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));
        graphicHeadLine.getStyledDocument().setParagraphAttributes(0, graphicHeadLine.getText().length(), center, false);

        JPanel graphicButtonHolder = new JPanel();
        graphicButtonHolder.setLayout(new GridLayout(1, 2));
        graphicButtonHolder.setOpaque(false);

        JPanel graphicButtonHolderLeft = new JPanel();
        graphicButtonHolderLeft.setLayout(new BoxLayout(graphicButtonHolderLeft, BoxLayout.PAGE_AXIS));
        graphicButtonHolderLeft.setOpaque(false);
        graphicButtonHolder.add(graphicButtonHolderLeft);

        JPanel graphicButtonHolderRight = new JPanel();
        graphicButtonHolderRight.setLayout(new BoxLayout(graphicButtonHolderRight, BoxLayout.PAGE_AXIS));
        graphicButtonHolderRight.setOpaque(false);
        graphicButtonHolder.add(graphicButtonHolderRight);

        themePanel.setBackground(main.themeColor[0]);
        graphicPanelSmall.setBackground(main.themeColor[0]);


        JScrollPane graphicScrollPane = new JScrollPane(themePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        graphicScrollPane.setBackground(Color.BLACK);
        graphicScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        graphicScrollPane.setBorder(BorderFactory.createEmptyBorder(1,1,1,0));

        graphicWindowSmall.setSize(400, 400);
        graphicWindowSmall.setLocationRelativeTo(null);
        graphicWindowSmall.add(graphicPanelSmall);

        BrikButton themeButton1 = new BrikButton(main);
        themeButton1.setText("Theme 1");
        themeButton1.setButtonSize(main.buttonWidth,main.buttonHeight);
        themeButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeButton1.setColors(new ColorUIResource(0, 68, 69),new ColorUIResource(224, 176, 255),new ColorUIResource(255, 255, 255));

        BrikButton themeButton2 = new BrikButton(main);
        themeButton2.setText("Theme 2");
        themeButton2.setButtonSize(main.buttonWidth,main.buttonHeight);
        themeButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeButton2.setColors(new ColorUIResource(74, 46, 27), new ColorUIResource(255, 179, 138), new ColorUIResource(255, 248, 231));

        BrikButton themeButton3 = new BrikButton(main);
        themeButton3.setText("Theme 3");
        themeButton3.setButtonSize(main.buttonWidth,main.buttonHeight);
        themeButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeButton3.setColors(new ColorUIResource(92, 0, 17),new ColorUIResource(255, 0, 127), new ColorUIResource(255, 255, 153));

        BrikButton themeButton4 = new BrikButton(main);
        themeButton4.setText("Theme 4");
        themeButton4.setButtonSize(main.buttonWidth, main.buttonHeight);
        themeButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeButton4.setColors(new ColorUIResource(40, 40, 90),new ColorUIResource(112, 255, 112), new ColorUIResource(255, 255, 255));

        BrikButton themeButton5 = new BrikButton(main);
        themeButton5.setText("Theme 5");
        themeButton5.setButtonSize(main.buttonWidth, main.buttonHeight);
        themeButton5.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeButton5.setColors(new ColorUIResource(7, 53, 53),new ColorUIResource(0, 253, 253), new ColorUIResource(255, 255, 255));

        themePanel.add(themeButton1);
        themePanel.add(themeButton2);
        themePanel.add(themeButton3);
        themePanel.add(themeButton4);
        themePanel.add(themeButton5);

        this.themeButton1 = themeButton1;
        this.themeButton2 = themeButton2;
        this.themeButton3 = themeButton3;
        this.themeButton4 = themeButton4;
        this.themeButton5 = themeButton5;

        graphicPanelSmall.add(graphicScrollPane, BorderLayout.CENTER);

        themeButton1.addActionListener(e1 -> {

            main.themeColor[0] =themeButton1.getPrimaryColor().darker();
            main.themeColor[1] =themeButton1.getPrimaryColor();
            main.themeColor[2] =themeButton1.getWaveColor();
            main.themeColor[3] =themeButton1.getTextColor();
            main.themeColor[4] =new ColorUIResource(16, 78, 172);
            main.themeColor[5] =new ColorUIResource(37, 40, 220);
            main.themeColor[6] =new ColorUIResource(10, 10, 143);

            main.refreshAllThemes();
            graphicWindowSmall.setVisible(false);
        });



        themeButton2.addActionListener(e1 -> {

            main.themeColor[0] =themeButton2.getWaveColor().darker().darker().darker();
            main.themeColor[1] =themeButton2.getPrimaryColor();
            main.themeColor[2] =themeButton2.getWaveColor();
            main.themeColor[3] =themeButton2.getTextColor();
            main.themeColor[4] =new ColorUIResource(104, 14, 47);
            main.themeColor[5] =new ColorUIResource(186, 82, 24);
            main.themeColor[6] =new ColorUIResource(103, 17, 6);

            main.refreshAllThemes();
            graphicWindowSmall.setVisible(false);
        });


        themeButton3.addActionListener(e1 -> {

            main.themeColor[0] =new ColorUIResource(80, 19, 34);
            main.themeColor[1] =themeButton3.getPrimaryColor();
            main.themeColor[2] =themeButton3.getWaveColor();
            main.themeColor[3] =themeButton3.getTextColor();
            main.themeColor[4] =new ColorUIResource(129, 24, 223);
            main.themeColor[5] =new ColorUIResource(121, 19, 147);
            main.themeColor[6] =new ColorUIResource(42, 4, 87);

            main.refreshAllThemes();
            graphicWindowSmall.setVisible(false);
        });


        themeButton4.addActionListener(e1 -> {

            main.themeColor[0] =new ColorUIResource(11, 67, 54);
            main.themeColor[1] =themeButton4.getPrimaryColor();
            main.themeColor[2] =themeButton4.getWaveColor();
            main.themeColor[3] =themeButton4.getTextColor();
            main.themeColor[4] =new ColorUIResource(177, 52, 7);
            main.themeColor[5] =new ColorUIResource(214, 135, 56);
            main.themeColor[6] =new ColorUIResource(108, 40, 13);



            main.refreshAllThemes();
            graphicWindowSmall.setVisible(false);
        });

        themeButton5.addActionListener(e1 -> {

            main.themeColor[0] =themeButton5.getWaveColor().darker().darker().darker();
            main.themeColor[1] =themeButton5.getPrimaryColor();
            main.themeColor[2] =themeButton5.getWaveColor();
            main.themeColor[3] =themeButton5.getTextColor();
            main.themeColor[4] =new ColorUIResource(158, 21, 79);
            main.themeColor[5] =new ColorUIResource(158, 12, 68);
            main.themeColor[6] =new ColorUIResource(78, 4, 38);



            main.refreshAllThemes();
            graphicWindowSmall.setVisible(false);
        });


        BrikButton graphicbutton1 = new BrikButton(main);
        graphicbutton1.setText("Themes");
        graphicbutton1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        graphicbutton1.addActionListener(e -> {

            themePanel.setBackground(main.themeColor[0]);
            graphicPanelSmall.setBackground(main.themeColor[0]);
            graphicWindowSmall.setLocationRelativeTo(main.mainPanel);
            graphicWindowSmall.setAlwaysOnTop(true);
            graphicWindowSmall.setVisible(true);

        });


        BrikButton graphicbutton2 = new BrikButton(main);
        graphicbutton2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        if (main.vollbild) {
            graphicbutton2.setText("Vollbild: AN");
        }else{
            graphicbutton2.setText("Vollbild: AUS");
        }
        graphicbutton2.addActionListener(e -> {
            if (main.vollbild) {
                //Vollbild deaktivieren
                main.vollbild = false;
                graphicbutton2.setText("Vollbild: AUS");
                main.switchVollbild();
            }else{
                //Vollbild Aktivieren
                main.vollbild = true;
                graphicbutton2.setText("Vollbild: AN");
                main.switchVollbild();
            }


        });


        BrikButton graphicbutton3 = new BrikButton(main);
        graphicbutton3.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (main.buttonWaveSpeed >0){
            graphicbutton3.setText("Augenschoner: AUS");
        }else{
            graphicbutton3.setText("Augenschoner: AN");

        }
        graphicbutton3.addActionListener(e -> {
           if (main.buttonWaveSpeed >0){
               main.buttonWaveSpeed = 0;
               graphicbutton3.setText("Augenschoner: AN");
               main.refreshAllThemes();
           }else{
               main.buttonWaveSpeed = 2.0;
               graphicbutton3.setText("Augenschoner: AUS");
               main.refreshAllThemes();
           }

        });

        BrikButton graphicbutton4 = new BrikButton(main);
        graphicbutton4.setAlignmentX(Component.LEFT_ALIGNMENT);


        if (main.showFPS){
            graphicbutton4.setText("Debug Infos: AN");
        }else{
            graphicbutton4.setText("Debug Infos: AUS");
        }
        graphicbutton4.addActionListener(e -> {
            if (main.showFPS){
                main.showFPS(false);
                graphicbutton4.setText("Debug Infos: AUS");
            }else {
                main.showFPS(true);
                graphicbutton4.setText("Debug Infos: AN");
            }

        });




        BrikSlider brikSlider1 = new BrikSlider(main);
        this.brikSlider1 = brikSlider1;
        brikSlider1.setAlignmentX(Component.CENTER_ALIGNMENT);
        brikSlider1.setLabelText("Max. Framerate");
        brikSlider1.setValue(main.targetFPS);
        brikSlider1.setRange(10,80);
        brikSlider1.setStep(1);

        //Fps Slider
        brikSlider1.addChangeListener(e -> {
            main.setFrameRate(brikSlider1.getValue());
        });

        graphicBackButtonHolder = new JPanel();
        graphicBackButtonHolder.setLayout(new BorderLayout());
        graphicBackButtonHolder.setOpaque(false);


        BrikButton graphicBackButton = new BrikButton(main);
        graphicBackButton.setText("Zurück");
        graphicBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        graphicBackButton.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        graphicBackButtonHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
        graphicBackButton.addActionListener(e -> {
            graphicWindowSmall.setVisible(false);
            main.setGameStatus(Main.GameState.SETTINGS);

        });


        headlinePanel = new JPanel();
        headlinePanel.setOpaque(false);
        headlinePanel.setLayout(new BoxLayout(headlinePanel, BoxLayout.PAGE_AXIS));

        headlinePanel.add(graphicHeadLine);
        headlinePanel.add(brikSlider1);

        headlinePanel.setBorder(new EmptyBorder(0, 0, main.frameHeight/30, 0));

        this.graphicsButton1 = graphicbutton1;
        this.graphicsButton2 = graphicbutton2;
        this.graphicsButton3 = graphicbutton3;
        this.graphicsButton4 = graphicbutton4;
        this.graphicBackButton = graphicBackButton;

        this.graphicPanel = graphicPanel;


        graphicButtonHolderLeft.add(graphicbutton1);
        graphicButtonHolderLeft.add(graphicbutton2);
        graphicButtonHolderRight.add(graphicbutton3);
        graphicButtonHolderRight.add(graphicbutton4);
        graphicBackButtonHolder.add(graphicBackButton, BorderLayout.CENTER);


        graphicPanel.add(headlinePanel,BorderLayout.NORTH);
        graphicPanel.add(graphicButtonHolder,BorderLayout.CENTER);
        graphicPanel.add(graphicBackButtonHolder, BorderLayout.SOUTH);

    }

    public void loadKeyBindSettings(){

        JPanel keyBindPanel = new JPanel();
        keyBindPanel.setBackground(main.themeColor[0]);
        keyBindPanel.setLayout(new BorderLayout());
        KeysHolder = new JPanel();
        KeysHolder.setBackground(main.themeColor[0]);
        KeysHolder.setLayout(new BoxLayout(KeysHolder, BoxLayout.PAGE_AXIS));
        keysScrollPane = new JScrollPane(KeysHolder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        keysScrollPane.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/80));
        keysScrollPane.getVerticalScrollBar().setUnitIncrement(14);
        keyBindText = new BrikText(main);
        keyBindText.setBorder(new EmptyBorder(main.frameHeight/20,0, main.frameHeight/30, 0));
        keyBindText.setText("TASTENBELEGUNG");
        keyBindText.SetFont(main.headLineFont);

        keyBindText.getStyledDocument().setParagraphAttributes(0, keyBindText.getText().length(), center, false);

        backButtonKeyBinder = new BrikButton(main);
        backButtonKeyBinder.setText("Zurück");
        backButtonKeyBinder.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        backButtonKeyBinder.addActionListener(e -> {
            main.setGameStatus(Main.GameState.SETTINGS);
        });

        keyButton1 = new KeyBinder(main.keyNameVor,0,main);
        keyButton2 = new KeyBinder(main.keyNameLinks, 1, main);
        keyButton3 = new KeyBinder(main.keyNameHinten,2, main);
        keyButton4 = new KeyBinder(main.keyNameRechts, 3, main);
        keyButton5 = new KeyBinder(main.keyNameInventar, 4, main);
        keyButton6 = new KeyBinder(main.keyNameInteraktion, 5, main);
        keyButton7 = new KeyBinder(main.keyNameopenMap, 6, main);
        keyButton8 = new KeyBinder(main.keyNameSprint , 7, main);

        backButtonKeyHolder = new JPanel(new BorderLayout());
        backButtonKeyHolder.setOpaque(false);
        backButtonKeyHolder.add(backButtonKeyBinder, BorderLayout.CENTER);
        backButtonKeyHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));


        KeysHolder.add(keyButton1);
        KeysHolder.add(keyButton2);
        KeysHolder.add(keyButton3);
        KeysHolder.add(keyButton4);
        KeysHolder.add(keyButton5);
        KeysHolder.add(keyButton6);
        KeysHolder.add(keyButton7);
        KeysHolder.add(keyButton8);

        keyBindPanel.add(keyBindText, BorderLayout.NORTH);
        keyBindPanel.add(keysScrollPane, BorderLayout.CENTER);
        keyBindPanel.add(backButtonKeyHolder, BorderLayout.SOUTH);

        this.keyBindPanel = keyBindPanel;



    }



    public void loadAuswahlSettings(){
        auswahlPanel = new JPanel();
        auswahlPanel.setBackground(main.themeColor[0]);
        auswahlPanel.setLayout(new BorderLayout());

        chooseSaveButtonHolder = new JPanel();
        chooseSaveButtonHolder.setLayout(new GridLayout(1, 3,main.frameWidth/40,0));

        chooseSaveButtonHolder.setOpaque(false);

        backButtonChoose = new BrikButton(main);
        backButtonChoose.setText("Zurück");
        backButtonChoose.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        backButtonChoose.addActionListener(e -> {
            main.setGameStatus(Main.GameState.MAIN_MENU);
        });

        backButtonChooseHolder = new JPanel(new BorderLayout());
        backButtonChooseHolder.setOpaque(false);
        backButtonChooseHolder.add(backButtonChoose, BorderLayout.CENTER);
        backButtonChooseHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));

        HeadlineHolderDateiChoose = new JPanel();
        HeadlineHolderDateiChoose.setLayout(new BoxLayout(HeadlineHolderDateiChoose, BoxLayout.PAGE_AXIS));
        HeadlineHolderDateiChoose.setOpaque(false);


        DateiChooseHeadline = new BrikText(main);
        DateiChooseHeadline.SetFont(main.headLineFont);
        DateiChooseHeadline.setText("DATEIAUSWAHL");
        DateiChooseHeadline.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/40,0));
        DateiChooseHeadline.getStyledDocument().setParagraphAttributes(0, DateiChooseHeadline.getText().length(), center, false);

        DateiChooseLore =  new BrikText(main);
        DateiChooseLore.setText("Wähle einen Spielstand aus, den du laden möchtest. Wenn der Spielstand leer ist, beginnst du automatisch ein neues Spiel.");
        DateiChooseLore.getStyledDocument().setParagraphAttributes(0, DateiChooseLore.getText().length(), center, false);
        DateiChooseLore.setBorder(new EmptyBorder(0,0,main.frameHeight/20,0));

        HeadlineHolderDateiChoose.add(DateiChooseHeadline);
        HeadlineHolderDateiChoose.add(DateiChooseLore);

        DateiChoosePanel1 = new JPanel();
        DateiChoosePanel1.setOpaque(false);
        DateiChoosePanel1.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));
        DateiChoosePanel1.setLayout(new BorderLayout());

        JPanel panel1Save1 = new JPanel();
        panel1Save1.setOpaque(false);
        panel1Save1.setLayout(new GridLayout(1, 1));

        panel2Save1 = new JPanel();
        panel2Save1.setOpaque(false);
        panel2Save1.setLayout(new GridLayout(3,1));

        if (main.saveGameBooleans[0][0]){
            panel2Save1.setVisible(false);
        }

        InfoText1Save1 = new BrikText(main);
        InfoText1Save1.SetFont(main.dialogFont);
        InfoText1Save1.setText(main.saveGameStrings[0][0]);
        InfoText1Save1.getStyledDocument().setParagraphAttributes(0, InfoText1Save1.getText().length(), center, false);
        InfoText1Save1.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));
        panel1Save1.add(InfoText1Save1);

        InfoText2Save1 = new BrikText(main);
        InfoText2Save1.SetFont(main.textfont);
        InfoText2Save1.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[0][1]).substring(0,3)+" Std.");
        panel2Save1.add(InfoText2Save1);

        InfoText3Save1 = new BrikText(main);
        InfoText3Save1.SetFont(main.textfont);
        InfoText3Save1.setText("Geld: "+main.saveGameDoubles[0][0] +" $");
        panel2Save1.add(InfoText3Save1);

        InfoText4Save1 = new BrikText(main);
        InfoText4Save1.SetFont(main.textfont);
        InfoText4Save1.setText("Standort: "+getLocation(main.saveGameDoubles[0][3],main.saveGameDoubles[0][4]));
        panel2Save1.add(InfoText4Save1);



        loadDataButon1 = new BrikButton(main);
        if (main.saveGameBooleans[0][0]){
            loadDataButon1.setText("Neues Spiel starten");
        }else {
            loadDataButon1.setText("Spielstand laden");
        }
        loadDataButon1.addActionListener(e -> {
            main.loadedGame = 0;

            if (main.saveGameBooleans[0][0]){
                main.printOnDebug("Starte neues Spiel auf Datei: "  + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                startNewGame(0);
                main.setGameStatus(Main.GameState.Start_New_Game);

            }else  {
                main.printOnDebug("Lade alten Speicherstand von Datei: " + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                main.loadGameOnVars(false);


            }

        });

        DateiChoosePanel1.add(panel1Save1,BorderLayout.NORTH);
        DateiChoosePanel1.add(panel2Save1,BorderLayout.CENTER);
        DateiChoosePanel1.add(loadDataButon1,BorderLayout.SOUTH);


        DateiChoosePanel2 = new JPanel();
        DateiChoosePanel2.setOpaque(false);
        DateiChoosePanel2.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));
        DateiChoosePanel2.setLayout(new BorderLayout());


        JPanel panel1Save2 = new JPanel();
        panel1Save2.setOpaque(false);
        panel1Save2.setLayout(new GridLayout(1, 1));

        panel2Save2 = new JPanel();
        panel2Save2.setOpaque(false);
        panel2Save2.setLayout(new GridLayout(3,1));

        if (main.saveGameBooleans[1][0]){
            panel2Save2.setVisible(false);
        }

        InfoText1Save2 = new BrikText(main);
        InfoText1Save2.SetFont(main.dialogFont);
        InfoText1Save2.setText(main.saveGameStrings[1][0]);
        InfoText1Save2.getStyledDocument().setParagraphAttributes(0, InfoText1Save2.getText().length(), center, false);
        InfoText1Save2.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));
        panel1Save2.add(InfoText1Save2);

        InfoText2Save2 = new BrikText(main);
        InfoText2Save2.SetFont(main.textfont);
        InfoText2Save2.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[1][1]).substring(0,3)+" Std.");
        panel2Save2.add(InfoText2Save2);

        InfoText3Save2 = new BrikText(main);
        InfoText3Save2.SetFont(main.textfont);
        InfoText3Save2.setText("Geld: "+main.saveGameDoubles[1][0] +" $");
        panel2Save2.add(InfoText3Save2);

        InfoText4Save2 = new BrikText(main);
        InfoText4Save2.SetFont(main.textfont);
        InfoText4Save2.setText("Standort: "+getLocation(main.saveGameDoubles[1][3],main.saveGameDoubles[1][4]));
        panel2Save2.add(InfoText4Save2);



        loadDataButon2 = new BrikButton(main);
        if (main.saveGameBooleans[1][0]){
            loadDataButon2.setText("Neues Spiel starten");
        }else {
            loadDataButon2.setText("Spielstand laden");
        }
        loadDataButon2.addActionListener(e -> {
            main.loadedGame = 1;

            if (main.saveGameBooleans[1][0]){
                main.printOnDebug("Starte neues Spiel auf Datei: "  + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                startNewGame(1);
                main.setGameStatus(Main.GameState.Start_New_Game);

            }else  {
                main.printOnDebug("Lade alten Speicherstand von Datei: " + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                main.loadGameOnVars(false);

            }

        });

        DateiChoosePanel2.add(panel1Save2,BorderLayout.NORTH);
        DateiChoosePanel2.add(panel2Save2,BorderLayout.CENTER);
        DateiChoosePanel2.add(loadDataButon2,BorderLayout.SOUTH);


        DateiChoosePanel3 = new JPanel();
        DateiChoosePanel3.setOpaque(false);
        DateiChoosePanel3.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));
        DateiChoosePanel3.setLayout(new BorderLayout());

        JPanel panel1Save3 = new JPanel();
        panel1Save3.setOpaque(false);
        panel1Save3.setLayout(new GridLayout(1, 1));

        panel2Save3 = new JPanel();
        panel2Save3.setOpaque(false);
        panel2Save3.setLayout(new GridLayout(3,1));

        if (main.saveGameBooleans[2][0]){
            panel2Save3.setVisible(false);
        }

        InfoText1Save3 = new BrikText(main);
        InfoText1Save3.SetFont(main.dialogFont);
        InfoText1Save3.setText(main.saveGameStrings[2][0]);
        InfoText1Save3.getStyledDocument().setParagraphAttributes(0, InfoText1Save3.getText().length(), center, false);
        InfoText1Save3.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));
        panel1Save3.add(InfoText1Save3);

        InfoText2Save3 = new BrikText(main);
        InfoText2Save3.SetFont(main.textfont);
        InfoText2Save3.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[2][1]).substring(0,3)+" Std.");
        panel2Save3.add(InfoText2Save3);

        InfoText3Save3 = new BrikText(main);
        InfoText3Save3.SetFont(main.textfont);
        InfoText3Save3.setText("Geld: "+main.saveGameDoubles[2][0] +" $");
        panel2Save3.add(InfoText3Save3);

        InfoText4Save3 = new BrikText(main);
        InfoText4Save3.SetFont(main.textfont);
        InfoText4Save3.setText("Standort: "+getLocation(main.saveGameDoubles[2][3],main.saveGameDoubles[2][4]));
        panel2Save3.add(InfoText4Save3);

        loadDataButon3 = new BrikButton(main);
        if (main.saveGameBooleans[2][0]){

            loadDataButon3.setText("Neues Spiel starten");
        }else {
            loadDataButon3.setText("Spielstand laden");
        }
        loadDataButon3.addActionListener(e -> {
            main.loadedGame = 2;

            if (main.saveGameBooleans[2][0]){
                main.printOnDebug("Starte neues Spiel auf Datei: "  + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                startNewGame(2);
                main.setGameStatus(Main.GameState.Start_New_Game);

            }else  {
                main.printOnDebug("Lade alten Speicherstand von Datei: " + (main.loadedGame+1)+"/index: "+main.loadedGame);
                main.transferGameVariables();
                main.loadGameOnVars(false);

            }

        });

        DateiChoosePanel3.add(panel1Save3,BorderLayout.NORTH);
        DateiChoosePanel3.add(panel2Save3,BorderLayout.CENTER);
        DateiChoosePanel3.add(loadDataButon3,BorderLayout.SOUTH);


        chooseSaveButtonHolder.setBorder(new EmptyBorder(0,main.frameHeight/40,0,main.frameHeight/40));
        chooseSaveButtonHolder.add(DateiChoosePanel1);
        chooseSaveButtonHolder.add(DateiChoosePanel2);
        chooseSaveButtonHolder.add(DateiChoosePanel3);


        auswahlPanel.add(HeadlineHolderDateiChoose, BorderLayout.NORTH);
        auswahlPanel.add(chooseSaveButtonHolder, BorderLayout.CENTER);
        auswahlPanel.add(backButtonChooseHolder, BorderLayout.SOUTH);


    }

    public void loadNewGameMenu(){
        newGamePanel = new JPanel();
        newGamePanel.setBackground(main.themeColor[0]);
        NewGameCardLayout = new CardLayout();
        newGamePanel.setLayout(new BorderLayout());

        cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(NewGameCardLayout);

        NewGameConfigHeadline = new BrikText(main);
        NewGameConfigHeadline.SetFont(main.headLineFont);
        NewGameConfigHeadline.setText("SPIEL KONFIGURATION");
        NewGameConfigHeadline.getStyledDocument().setParagraphAttributes(0, NewGameConfigHeadline.getText().length(), center, false);
        NewGameConfigHeadline.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));




        NewGamequest1panel = new JPanel();
        NewGamequest1panel.setOpaque(false);
        NewGamequest1panel.setLayout(new BorderLayout());

        NewGamequest1 = new BrikText(main);
        NewGamequest1.SetFont(main.dialogFont);
        NewGamequest1.getStyledDocument().setParagraphAttributes(0, NewGamequest1.getText().length(), center, false);
        NewGamequest1.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));



        newGameNameDatei = new JTextField();
        newGameNameDatei.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameNameDatei.setBackground(main.themeColor[1]);
        newGameNameDatei.setFont(main.newGameFont);
        newGameNameDatei.setForeground(main.themeColor[3]);
        newGameNameDatei.setHorizontalAlignment(JTextField.CENTER);
        newGameNameDatei.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.saveGameStrings[main.loadedGame][0] = e.getActionCommand();

                if (!changefromQuestreview) {
                    setNewGameQuestState(NewGameQuestState.Quest2);
                }else  {
                    setNewGameQuestState(NewGameQuestState.Questreview);
                }
            }
        });

        newGameButtonHolder1 = new JPanel();
        newGameButtonHolder1.setOpaque(false);
        newGameButtonHolder1.setLayout(new BorderLayout());
        newGameButtonHolder1.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));


        newGameConfigVorschlagbutton1 = new BrikButton(main);
        newGameConfigVorschlagbutton1.setText(main.vorschlagButtonNames[vorschlagButtonStand[0]]);
        newGameConfigVorschlagbutton1.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[0]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[0]++;
                    newGameConfigVorschlagbutton1.setText(main.vorschlagButtonNames[vorschlagButtonStand[0]]);
                }
            }


            newGameNameDatei.setText(main.vorschlagDateiNames[vorschlagDataStand[0]]);

            if (vorschlagDataStand[0]<main.vorschlagDateiNames.length-1) {
                vorschlagDataStand[0]++;
            }else  {
                vorschlagDataStand[0]=0;
            }
        });

        newGameButtonHolder1.add(newGameConfigVorschlagbutton1, BorderLayout.CENTER);

        NewGamequest1panel.add(NewGamequest1,BorderLayout.NORTH);
        NewGamequest1panel.add(newGameNameDatei,BorderLayout.CENTER);
        NewGamequest1panel.add(newGameButtonHolder1,BorderLayout.SOUTH);




        NewGamequest2panel = new JPanel();
        NewGamequest2panel.setOpaque(false);
        NewGamequest2panel.setLayout(new BorderLayout());

        NewGamequest2 = new BrikText(main);
        NewGamequest2.SetFont(main.dialogFont);
        NewGamequest2.getStyledDocument().setParagraphAttributes(0, NewGamequest2.getText().length(), center, false);
        NewGamequest2.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));

        newGameFavFood = new JTextField();
        newGameFavFood.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameFavFood.setBackground(main.themeColor[1]);
        newGameFavFood.setFont(main.newGameFont);
        newGameFavFood.setForeground(main.themeColor[3]);
        newGameFavFood.setHorizontalAlignment(JTextField.CENTER);
        newGameFavFood.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.favFood = e.getActionCommand();
                if (!changefromQuestreview) {
                    setNewGameQuestState(NewGameQuestState.Quest3);
                }else  {
                    setNewGameQuestState(NewGameQuestState.Questreview);
                }
            }
        });

        newGameButtonHolder2 = new JPanel();
        newGameButtonHolder2.setOpaque(false);
        newGameButtonHolder2.setLayout(new BorderLayout());
        newGameButtonHolder2.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

        newGameConfigVorschlagbutton2 = new BrikButton(main);
        newGameConfigVorschlagbutton2.setText(main.vorschlagButtonNames[vorschlagButtonStand[1]]);
        newGameConfigVorschlagbutton2.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[1]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[1]++;
                    newGameConfigVorschlagbutton2.setText(main.vorschlagButtonNames[vorschlagButtonStand[1]]);
                }
            }


            newGameFavFood.setText(main.vorschlagFavFood[vorschlagDataStand[1]]);

            if (vorschlagDataStand[1]<main.vorschlagFavFood.length-1) {
                vorschlagDataStand[1]++;
            }else  {
                vorschlagDataStand[1]=0;
            }
        });

        newGameButtonHolder2.add(newGameConfigVorschlagbutton2, BorderLayout.CENTER);

        NewGamequest2panel.add(NewGamequest2,BorderLayout.NORTH);
        NewGamequest2panel.add(newGameFavFood,BorderLayout.CENTER);
        NewGamequest2panel.add(newGameButtonHolder2,BorderLayout.SOUTH);



        NewGamequest3panel = new JPanel();
        NewGamequest3panel.setOpaque(false);
        NewGamequest3panel.setLayout(new BorderLayout());

        NewGamequest3 = new BrikText(main);
        NewGamequest3.SetFont(main.dialogFont);
        NewGamequest3.getStyledDocument().setParagraphAttributes(0, NewGamequest3.getText().length(), center, false);
        NewGamequest3.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));

        newGameHobby = new JTextField();
        newGameHobby.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameHobby.setBackground(main.themeColor[1]);
        newGameHobby.setFont(main.newGameFont);
        newGameHobby.setForeground(main.themeColor[3]);
        newGameHobby.setHorizontalAlignment(JTextField.CENTER);
        newGameHobby.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.Hobby = e.getActionCommand();
                if (!changefromQuestreview) {
                    setNewGameQuestState(NewGameQuestState.Quest4);
                }else  {
                    setNewGameQuestState(NewGameQuestState.Questreview);
                }
            }
        });

        newGameButtonHolder3 = new JPanel();
        newGameButtonHolder3.setOpaque(false);
        newGameButtonHolder3.setLayout(new BorderLayout());
        newGameButtonHolder3.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

        newGameConfigVorschlagbutton3 = new BrikButton(main);
        newGameConfigVorschlagbutton3.setText(main.vorschlagButtonNames[vorschlagButtonStand[2]]);
        newGameConfigVorschlagbutton3.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[2]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[2]++;
                    newGameConfigVorschlagbutton3.setText(main.vorschlagButtonNames[vorschlagButtonStand[2]]);
                }
            }


            newGameHobby.setText(main.vorschlagHobby[vorschlagDataStand[2]]);

            if (vorschlagDataStand[2]<main.vorschlagHobby.length-1) {
                vorschlagDataStand[2]++;
            }else  {
                vorschlagDataStand[2]=0;
            }
        });

        newGameButtonHolder3.add(newGameConfigVorschlagbutton3, BorderLayout.CENTER);

        NewGamequest3panel.add(NewGamequest3,BorderLayout.NORTH);
        NewGamequest3panel.add(newGameHobby,BorderLayout.CENTER);
        NewGamequest3panel.add(newGameButtonHolder3,BorderLayout.SOUTH);




        NewGamequest4panel = new JPanel();
        NewGamequest4panel.setOpaque(false);
        NewGamequest4panel.setLayout(new BorderLayout());

        NewGamequest4 = new BrikText(main);
        NewGamequest4.SetFont(main.dialogFont);
        NewGamequest4.getStyledDocument().setParagraphAttributes(0, NewGamequest4.getText().length(), center, false);
        NewGamequest4.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));

        newGameNameTimmy = new JTextField();
        newGameNameTimmy.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameNameTimmy.setBackground(main.themeColor[1]);
        newGameNameTimmy.setFont(main.newGameFont);
        newGameNameTimmy.setForeground(main.themeColor[3]);
        newGameNameTimmy.setHorizontalAlignment(JTextField.CENTER);
        newGameNameTimmy.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.NameTimmy = e.getActionCommand();
                if (!changefromQuestreview) {
                    setNewGameQuestState(NewGameQuestState.Quest5);
                }else  {
                    setNewGameQuestState(NewGameQuestState.Questreview);
                }
            }
        });

        newGameButtonHolder4 = new JPanel();
        newGameButtonHolder4.setOpaque(false);
        newGameButtonHolder4.setLayout(new BorderLayout());
        newGameButtonHolder4.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

        newGameConfigVorschlagbutton4 = new BrikButton(main);
        newGameConfigVorschlagbutton4.setText(main.vorschlagButtonNames[vorschlagButtonStand[3]]);
        newGameConfigVorschlagbutton4.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[3]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[3]++;
                    newGameConfigVorschlagbutton4.setText(main.vorschlagButtonNames[vorschlagButtonStand[3]]);
                }
            }


            newGameNameTimmy.setText(main.vorschlagNameTimmy[vorschlagDataStand[3]]);

            if (vorschlagDataStand[3]<main.vorschlagNameTimmy.length-1) {
                vorschlagDataStand[3]++;
            }else  {
                vorschlagDataStand[3]=0;
            }
        });

        newGameButtonHolder4.add(newGameConfigVorschlagbutton4, BorderLayout.CENTER);

        NewGamequest4panel.add(NewGamequest4,BorderLayout.NORTH);
        NewGamequest4panel.add(newGameNameTimmy,BorderLayout.CENTER);
        NewGamequest4panel.add(newGameButtonHolder4,BorderLayout.SOUTH);





        NewGamequest5panel = new JPanel();
        NewGamequest5panel.setOpaque(false);
        NewGamequest5panel.setLayout(new BorderLayout());

        NewGamequest5 = new BrikText(main);
        NewGamequest5.SetFont(main.dialogFont);
        NewGamequest5.getStyledDocument().setParagraphAttributes(0, NewGamequest5.getText().length(), center, false);
        NewGamequest5.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));

        newGameNameFinja = new JTextField();
        newGameNameFinja.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameNameFinja.setBackground(main.themeColor[1]);
        newGameNameFinja.setFont(main.newGameFont);
        newGameNameFinja.setForeground(main.themeColor[3]);
        newGameNameFinja.setHorizontalAlignment(JTextField.CENTER);
        newGameNameFinja.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.NameFinja = e.getActionCommand();
                if (!changefromQuestreview) {
                    setNewGameQuestState(NewGameQuestState.Quest6);
                }else  {
                    setNewGameQuestState(NewGameQuestState.Questreview);
                }
            }
        });

        newGameButtonHolder5 = new JPanel();
        newGameButtonHolder5.setOpaque(false);
        newGameButtonHolder5.setLayout(new BorderLayout());
        newGameButtonHolder5.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

        newGameConfigVorschlagbutton5 = new BrikButton(main);
        newGameConfigVorschlagbutton5.setText(main.vorschlagButtonNames[vorschlagButtonStand[4]]);
        newGameConfigVorschlagbutton5.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[4]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[4]++;
                    newGameConfigVorschlagbutton5.setText(main.vorschlagButtonNames[vorschlagButtonStand[4]]);
                }
            }


            newGameNameFinja.setText(main.vorschlagNameFinja[vorschlagDataStand[4]]);

            if (vorschlagDataStand[4]<main.vorschlagNameFinja.length-1) {
                vorschlagDataStand[4]++;
            }else  {
                vorschlagDataStand[4]=0;
            }
        });

        newGameButtonHolder5.add(newGameConfigVorschlagbutton5, BorderLayout.CENTER);

        NewGamequest5panel.add(NewGamequest5,BorderLayout.NORTH);
        NewGamequest5panel.add(newGameNameFinja,BorderLayout.CENTER);
        NewGamequest5panel.add(newGameButtonHolder5,BorderLayout.SOUTH);




        NewGamequest6panel = new JPanel();
        NewGamequest6panel.setOpaque(false);
        NewGamequest6panel.setLayout(new BorderLayout());

        NewGamequest6 = new BrikText(main);
        NewGamequest6.SetFont(main.dialogFont);
        NewGamequest6.getStyledDocument().setParagraphAttributes(0, NewGamequest6.getText().length(), center, false);
        NewGamequest6.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));

        newGameNameKizo = new JTextField();
        newGameNameKizo.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
        newGameNameKizo.setBackground(main.themeColor[1]);
        newGameNameKizo.setFont(main.newGameFont);
        newGameNameKizo.setForeground(main.themeColor[3]);
        newGameNameKizo.setHorizontalAlignment(JTextField.CENTER);
        newGameNameKizo.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");

            if (e.getActionCommand().isBlank()){

            }else {
                main.NameKizo = e.getActionCommand();
                setNewGameQuestState(NewGameQuestState.Questreview);
            }
        });

        newGameButtonHolder6 = new JPanel();
        newGameButtonHolder6.setOpaque(false);
        newGameButtonHolder6.setLayout(new BorderLayout());
        newGameButtonHolder6.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

        newGameConfigVorschlagbutton6 = new BrikButton(main);
        newGameConfigVorschlagbutton6.setText(main.vorschlagButtonNames[vorschlagButtonStand[4]]);
        newGameConfigVorschlagbutton6.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            int z = (int) (Math.random()*10);

            if (z>8){
                if (vorschlagButtonStand[5]<main.vorschlagButtonNames.length-1) {

                    vorschlagButtonStand[5]++;
                    newGameConfigVorschlagbutton6.setText(main.vorschlagButtonNames[vorschlagButtonStand[5]]);
                }
            }


            newGameNameKizo.setText(main.vorschlagNameKizo[vorschlagDataStand[5]]);

            if (vorschlagDataStand[5]<main.vorschlagNameKizo.length-1) {
                vorschlagDataStand[5]++;
            }else  {
                vorschlagDataStand[5]=0;
            }
        });

        newGameButtonHolder6.add(newGameConfigVorschlagbutton6, BorderLayout.CENTER);

        NewGamequest6panel.add(NewGamequest6,BorderLayout.NORTH);
        NewGamequest6panel.add(newGameNameKizo,BorderLayout.CENTER);
        NewGamequest6panel.add(newGameButtonHolder6,BorderLayout.SOUTH);




        cardPanel.add(NewGamequest1panel,"quest1");
        cardPanel.add(NewGamequest2panel,"quest2");
        cardPanel.add(NewGamequest3panel,"quest3");
        cardPanel.add(NewGamequest4panel,"quest4");
        cardPanel.add(NewGamequest5panel,"quest5");
        cardPanel.add(NewGamequest6panel,"quest6");



        panelbottom = new JPanel();
        panelbottom.setOpaque(false);
        panelbottom.setLayout(new GridLayout(1,2,main.frameWidth/4,0));
        panelbottom.setBorder(BorderFactory.createEmptyBorder(main.frameHeight/25,0,0,0));

        newGameConfigbreakupButton = new BrikButton(main);
        newGameConfigbreakupButton.setText("Abbrechen");
        newGameConfigbreakupButton.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        newGameConfigbreakupButton.addActionListener(e -> {
            newGameConfigbreakupButton.setText("Bist du Sicher?");

            if (areYouSureleaveNewGame){
                main.resetGameVariables(main.loadedGame);
                main.setGameStatus(Main.GameState.DATEIAUSWAHL);
                areYouSureleaveNewGame= false;
            }else {
                areYouSureleaveNewGame = true;
            }

        });



        newGameConfigstartGamebutton = new BrikButton(main);
        newGameConfigstartGamebutton.setText("Weiter");
        newGameConfigstartGamebutton.addActionListener(e -> {
            areYouSureleaveNewGame = false;
            newGameConfigbreakupButton.setText("Abbrechen");
            switch (newGameQuestState){
                case Quest1 ->{
                    if (newGameNameDatei.getText().isBlank()){
                    }else {
                        main.saveGameStrings[main.loadedGame][0] = newGameNameDatei.getText();

                        if (!changefromQuestreview) {
                            setNewGameQuestState(NewGameQuestState.Quest2);
                        }else  {
                            setNewGameQuestState(NewGameQuestState.Questreview);
                        }
                    }

                }case Quest2 ->{

                    if (newGameFavFood.getText().isBlank()){
                    }else {
                        main.favFood = newGameFavFood.getText();
                        if (!changefromQuestreview) {
                            setNewGameQuestState(NewGameQuestState.Quest3);
                        }else  {
                            setNewGameQuestState(NewGameQuestState.Questreview);
                        }
                    }

                }case  Quest3 ->{

                    if (newGameHobby.getText().isBlank()){
                    }else {
                        main.Hobby = newGameHobby.getText();
                        if (!changefromQuestreview) {
                            setNewGameQuestState(NewGameQuestState.Quest4);
                        }else  {
                            setNewGameQuestState(NewGameQuestState.Questreview);
                        }
                    }

                }case  Quest4 ->{

                    if (newGameNameTimmy.getText().isBlank()){
                    }else {
                        main.NameTimmy = newGameNameTimmy.getText();
                        if (!changefromQuestreview) {
                            setNewGameQuestState(NewGameQuestState.Quest5);
                        }else  {
                            setNewGameQuestState(NewGameQuestState.Questreview);
                        }
                    }

                }case   Quest5 ->{

                    if (newGameNameFinja.getText().isBlank()){
                    }else {
                        main.NameFinja = newGameNameFinja.getText();
                        if (!changefromQuestreview) {
                            setNewGameQuestState(NewGameQuestState.Quest6);
                        }else  {
                            setNewGameQuestState(NewGameQuestState.Questreview);
                        }
                    }

                }case   Quest6 ->{

                    if (newGameNameKizo.getText().isBlank()){
                    }else {
                        main.NameKizo = newGameNameKizo.getText();
                        setNewGameQuestState(NewGameQuestState.Questreview);
                    }

                }case Questreview -> {
                    main.firstLoad=false;
                    main.loadGameOnVars(true);
                }



            }


        });

        NewGameQuestReviewpanel = new JPanel();
        NewGameQuestReviewpanel.setOpaque(false);
        NewGameQuestReviewpanel.setLayout(new GridLayout(3, 2));

        holderPanel = new JPanel();
        holderPanel.setOpaque(false);
        holderPanel.setLayout(new BorderLayout());

        NewGameQuestRewiev = new BrikText(main);
        NewGameQuestRewiev.setText("Das hast du ausgewählt. Klicke auf den gewünschten DiaButton um etwas zu ändern");
        NewGameQuestRewiev.SetFont(main.textfont);
        NewGameQuestRewiev.getStyledDocument().setParagraphAttributes(0, NewGameQuestRewiev.getText().length(), center, false);
        NewGameQuestRewiev.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));



        reviewquestbutton1 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton1.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton1.setContentAreaFilled(false);
        reviewquestbutton1.setOpaque(true);
        reviewquestbutton1.setFont(main.dialogFont);
        reviewquestbutton1.setBackground(main.themeColor[1]);
        reviewquestbutton1.setForeground(main.themeColor[3]);
        reviewquestbutton1.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
        reviewquestbutton1.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest1);

        });
        reviewquestbutton2 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton2.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton2.setContentAreaFilled(false);
        reviewquestbutton2.setOpaque(true);
        reviewquestbutton2.setFont(main.dialogFont);
        reviewquestbutton2.setBackground(main.themeColor[1]);
        reviewquestbutton2.setForeground(main.themeColor[3]);
        reviewquestbutton2.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200 ));
        reviewquestbutton2.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest2);

        });
        reviewquestbutton3 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton3.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton3.setContentAreaFilled(false);
        reviewquestbutton3.setOpaque(true);
        reviewquestbutton3.setFont(main.dialogFont);
        reviewquestbutton3.setBackground(main.themeColor[1]);
        reviewquestbutton3.setForeground(main.themeColor[3]);
        reviewquestbutton3.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200 ));
        reviewquestbutton3.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest3);
        });
        reviewquestbutton4 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton4.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton4.setContentAreaFilled(false);
        reviewquestbutton4.setOpaque(true);
        reviewquestbutton4.setFont(main.dialogFont);
        reviewquestbutton4.setBackground(main.themeColor[1]);
        reviewquestbutton4.setForeground(main.themeColor[3]);
        reviewquestbutton4.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
        reviewquestbutton4.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest4);
        });
        reviewquestbutton5 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton5.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton5.setContentAreaFilled(false);
        reviewquestbutton5.setOpaque(true);
        reviewquestbutton5.setFont(main.dialogFont);
        reviewquestbutton5.setBackground(main.themeColor[1]);
        reviewquestbutton5.setForeground(main.themeColor[3]);
        reviewquestbutton5.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
        reviewquestbutton5.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest5);
        });
        reviewquestbutton6 = new JButton("wenn du das siehst, restarte");
        reviewquestbutton6.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        reviewquestbutton6.setContentAreaFilled(false);
        reviewquestbutton6.setOpaque(true);
        reviewquestbutton6.setFont(main.dialogFont);
        reviewquestbutton6.setBackground(main.themeColor[1]);
        reviewquestbutton6.setForeground(main.themeColor[3]);
        reviewquestbutton6.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
        reviewquestbutton6.addActionListener(e -> {
            setNewGameQuestState(NewGameQuestState.Quest6);
        });


        NewGameQuestReviewpanel.add(reviewquestbutton1);
        NewGameQuestReviewpanel.add(reviewquestbutton2);
        NewGameQuestReviewpanel.add(reviewquestbutton3);
        NewGameQuestReviewpanel.add(reviewquestbutton4);
        NewGameQuestReviewpanel.add(reviewquestbutton5);
        NewGameQuestReviewpanel.add(reviewquestbutton6);

        holderPanel.add(NewGameQuestRewiev,BorderLayout.NORTH);
        holderPanel.add(NewGameQuestReviewpanel,BorderLayout.CENTER);

        cardPanel.add(holderPanel,"questreview");


        panelbottom.add(newGameConfigbreakupButton);
        panelbottom.add(newGameConfigstartGamebutton);

        newGamePanel.add(NewGameConfigHeadline,BorderLayout.NORTH);
        newGamePanel.add(cardPanel,BorderLayout.CENTER);
        newGamePanel.add(panelbottom,BorderLayout.SOUTH);





    }







    public void refreshTheme() {
        menuPanel.setBackground(main.themeColor[0]);
        settingsPanel.setBackground(main.themeColor[0]);
        volumePanel.setBackground(main.themeColor[0]);
        graphicPanel.setBackground(main.themeColor[0]);
        keyBindPanel.setBackground(main.themeColor[0]);
        KeysHolder.setBackground(main.themeColor[0]);
        auswahlPanel.setBackground(main.themeColor[0]);
        newGamePanel.setBackground(main.themeColor[0]);

        brikButton1.repaintButton();
        brikButton2.repaintButton();
        brikButton3.repaintButton();
        brikButton3.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        brikTextPane.repaintText();
        brikTextSettings.repaintText();

        brikButton4.repaintButton();
        brikButton5.repaintButton();
        brikButton6.repaintButton();
        brikButton6.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        brikButton7.repaintButton();
        brikTextSettings.repaintText();

        themeButton1.repaintWaveSpeed();
        themeButton2.repaintWaveSpeed();
        themeButton3.repaintWaveSpeed();
        themeButton4.repaintWaveSpeed();
        themeButton5.repaintWaveSpeed();
        graphicsButton1.repaintButton();
        graphicsButton2.repaintButton();
        graphicsButton3.repaintButton();
        graphicsButton4.repaintButton();
        brikSlider1.repaintSlider();
        graphicBackButton.repaintButton();
        graphicBackButton.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        graphicHeadLine.repaintText();

        volumeButton1.repaintButton();
        volumeButton1.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        volumeHeadLine.repaintText();
        volumeSlider1.repaintSlider();
        volumeSlider2.repaintSlider();

        keyButton1.repaintKeyBinder();
        keyButton2.repaintKeyBinder();
        keyButton3.repaintKeyBinder();
        keyButton4.repaintKeyBinder();
        keyButton5.repaintKeyBinder();
        keyButton6.repaintKeyBinder();
        keyButton7.repaintKeyBinder();
        keyButton8.repaintKeyBinder();
        backButtonKeyBinder.repaintButton();
        backButtonKeyBinder.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        backButtonChoose.repaintButton();
        backButtonChoose.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        keyBindText.repaintText();

        refreshDateiChoosePanels();

        NewGameConfigHeadline.repaintText();

        newGameConfigbreakupButton.repaintButton();
        newGameConfigbreakupButton.setColors(main.themeColor[5],main.themeColor[6],main.themeColor[3]);
        newGameConfigstartGamebutton.repaintButton();


        newGameNameDatei.setBackground(main.themeColor[1]);
        newGameNameDatei.setForeground(main.themeColor[3]);
        NewGamequest1.repaintText();
        newGameConfigVorschlagbutton1.repaintButton();

        newGameFavFood.setBackground(main.themeColor[1]);
        newGameFavFood.setForeground(main.themeColor[3]);
        NewGamequest2.repaintText();
        newGameConfigVorschlagbutton2.repaintButton();

        newGameHobby.setBackground(main.themeColor[1]);
        newGameHobby.setForeground(main.themeColor[3]);
        NewGamequest3.repaintText();
        newGameConfigVorschlagbutton3.repaintButton();

        newGameNameTimmy.setBackground(main.themeColor[1]);
        newGameNameTimmy.setForeground(main.themeColor[3]);
        NewGamequest4.repaintText();
        newGameConfigVorschlagbutton4.repaintButton();

        newGameNameFinja.setBackground(main.themeColor[1]);
        newGameNameFinja.setForeground(main.themeColor[3]);
        NewGamequest5.repaintText();
        newGameConfigVorschlagbutton5.repaintButton();

        newGameNameKizo.setBackground(main.themeColor[1]);
        newGameNameKizo.setForeground(main.themeColor[3]);
        NewGamequest6.repaintText();
        newGameConfigVorschlagbutton6.repaintButton();

        NewGameQuestRewiev.repaintText();


        reviewquestbutton1.setBackground(main.themeColor[1]);
        reviewquestbutton1.setForeground(main.themeColor[3]);
        reviewquestbutton1.repaint();

        reviewquestbutton2.setBackground(main.themeColor[1]);
        reviewquestbutton2.setForeground(main.themeColor[3]);
        reviewquestbutton2.repaint();

        reviewquestbutton3.setBackground(main.themeColor[1]);
        reviewquestbutton3.setForeground(main.themeColor[3]);
        reviewquestbutton3.repaint();

        reviewquestbutton4.setBackground(main.themeColor[1]);
        reviewquestbutton4.setForeground(main.themeColor[3]);
        reviewquestbutton4.repaint();

        reviewquestbutton5.setBackground(main.themeColor[1]);
        reviewquestbutton5.setForeground(main.themeColor[3]);
        reviewquestbutton5.repaint();

        reviewquestbutton6.setBackground(main.themeColor[1]);
        reviewquestbutton6.setForeground(main.themeColor[3]);
        reviewquestbutton6.repaint();




        if (main.vollbild) {
            keysScrollPane.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/80)*main.scaleFactor));
            DateiChoosePanel1.setBorder(new LineBorder(main.themeColor[4],(main.frameWidth/180)*main.scaleFactor,true));
            DateiChoosePanel2.setBorder(new LineBorder(main.themeColor[4],(main.frameWidth/180)*main.scaleFactor,true));
            DateiChoosePanel3.setBorder(new LineBorder(main.themeColor[4],(main.frameWidth/180)*main.scaleFactor,true));

            newGameNameDatei.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));
            newGameFavFood.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));
            newGameHobby.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));
            newGameNameTimmy.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));
            newGameNameFinja.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));
            newGameNameKizo.setBorder(new LineBorder(main.themeColor[4], (main.frameHeight/100)+main.scaleFactor, true));

            NewGameQuestRewiev.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            reviewquestbutton1.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));
            reviewquestbutton2.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));
            reviewquestbutton3.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));
            reviewquestbutton4.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));
            reviewquestbutton5.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));
            reviewquestbutton6.setBorder(new LineBorder(main.themeColor[4],(main.frameHeight/200)*main.scaleFactor));

        }else {
            keysScrollPane.setBorder(new LineBorder(main.themeColor[4], main.frameHeight / 80));
            DateiChoosePanel1.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));
            DateiChoosePanel2.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));
            DateiChoosePanel3.setBorder(new LineBorder(main.themeColor[4],main.frameWidth/180,true));

            newGameNameDatei.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
            newGameFavFood.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
            newGameHobby.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
            newGameNameTimmy.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
            newGameNameFinja.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));
            newGameNameKizo.setBorder(new LineBorder(main.themeColor[4], main.frameHeight/100, true));

            NewGameQuestRewiev.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            reviewquestbutton1.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
            reviewquestbutton2.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
            reviewquestbutton3.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
            reviewquestbutton4.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
            reviewquestbutton5.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));
            reviewquestbutton6.setBorder(new LineBorder(main.themeColor[4],main.frameHeight/200));


        }

    }

public void refreshVollbildComponenten(boolean extendedFrame) {

        if (extendedFrame) {
            //extended
            menuButtonHolder.setBorder(new EmptyBorder((main.frameHeight/3)* main.scaleFactor,(main.frameWidth/16)* main.scaleFactor,0,0));
            brikTextSettings.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0,(main.frameHeight/20)*main.scaleFactor,0));
            graphicHeadLine.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0,(main.frameHeight/20)*main.scaleFactor,0));
            volumeHeadLine.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0,(main.frameHeight/20)*main.scaleFactor,0));
            NewGameConfigHeadline.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0,(main.frameHeight/20)*main.scaleFactor,0));
            brikTextPane.SetFont(main.extendettextfont);
            brikTextSettings.SetFont(main.extendedHeadLineFont);
            graphicHeadLine.SetFont(main.extendedHeadLineFont);
            keyBindText.SetFont(main.extendedHeadLineFont);
            volumeHeadLine.SetFont(main.extendedHeadLineFont);
            DateiChooseHeadline.SetFont(main.extendedHeadLineFont);
            DateiChooseLore.SetFont(main.extendettextfont);
            NewGameConfigHeadline.SetFont(main.extendedHeadLineFont);
            graphicsButton2.setText("Vollbild: AN");





            InfoText1Save1.SetFont(main.extendedDialogFont);
            InfoText2Save1.SetFont(main.extendettextfont);
            InfoText3Save1.SetFont(main.extendettextfont);
            InfoText4Save1.SetFont(main.extendettextfont);

            InfoText1Save2.SetFont(main.extendedDialogFont);
            InfoText2Save2.SetFont(main.extendettextfont);
            InfoText3Save2.SetFont(main.extendettextfont);
            InfoText4Save2.SetFont(main.extendettextfont);

            InfoText1Save3.SetFont(main.extendedDialogFont);
            InfoText2Save3.SetFont(main.extendettextfont);
            InfoText3Save3.SetFont(main.extendettextfont);
            InfoText4Save3.SetFont(main.extendettextfont);

            keyBindText.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0, (main.frameHeight/30)*main.scaleFactor, 0));
            graphicBackButtonHolder.setBorder(new EmptyBorder(0, (main.frameWidth/3)*main.scaleFactor, 0, (main.frameWidth/3)*main.scaleFactor));
            backButtonKeyHolder.setBorder(new EmptyBorder(0, (main.frameWidth/3)*main.scaleFactor, 0, (main.frameWidth/3)*main.scaleFactor));
            volumeButtonHolder.setBorder(new EmptyBorder(0, (main.frameWidth/3)*main.scaleFactor, 0, (main.frameWidth/3)*main.scaleFactor));
            backButtonHolderSettings.setBorder(new EmptyBorder(0, (main.frameWidth/3)*main.scaleFactor, 0, (main.frameWidth/3)*main.scaleFactor));
            backButtonChooseHolder.setBorder(new EmptyBorder(0, (main.frameWidth/3)*main.scaleFactor, 0, (main.frameWidth/3)*main.scaleFactor));
            headlinePanel.setBorder(new EmptyBorder(0, 0, (main.frameHeight/30)*main.scaleFactor, 0));
            DateiChooseLore.setBorder(new EmptyBorder(0,0,(main.frameHeight/20)*main.scaleFactor,0));
            DateiChooseHeadline.setBorder(new EmptyBorder((main.frameHeight/20)*main.scaleFactor,0,(main.frameHeight/40)*main.scaleFactor,0));
            chooseSaveButtonHolder.setBorder(new EmptyBorder(0,(main.frameHeight/40)*main.scaleFactor,0,(main.frameHeight/40)*main.scaleFactor));
            chooseSaveButtonHolder.setLayout(new GridLayout(1, 3,(main.frameWidth/40)*main.scaleFactor,0));

            InfoText1Save1.setBorder(new EmptyBorder((main.frameHeight/60)*main.scaleFactor,0,(main.frameHeight/20)*(main.scaleFactor*2),0));
            InfoText1Save2.setBorder(new EmptyBorder((main.frameHeight/60)*main.scaleFactor,0,(main.frameHeight/20)*(main.scaleFactor*2),0));
            InfoText1Save3.setBorder(new EmptyBorder((main.frameHeight/60)*main.scaleFactor,0,(main.frameHeight/20)*(main.scaleFactor*2),0));


            panelbottom.setLayout(new GridLayout(1,2,(main.frameWidth/4)*main.scaleFactor,0));
            panelbottom.setBorder(BorderFactory.createEmptyBorder((main.frameHeight/25)*main.scaleFactor,0,0,0));


            NewGamequest1.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest1.SetFont(main.extendedDialogFont);
            newGameNameDatei.setFont(main.extendetNewGameFont);
            newGameButtonHolder1.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGamequest2.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest2.SetFont(main.extendedDialogFont);
            newGameFavFood.setFont(main.extendetNewGameFont);
            newGameButtonHolder2.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGamequest3.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest3.SetFont(main.extendedDialogFont);
            newGameHobby.setFont(main.extendetNewGameFont);
            newGameButtonHolder3.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGamequest4.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest4.SetFont(main.extendedDialogFont);
            newGameNameTimmy.setFont(main.extendetNewGameFont);
            newGameButtonHolder4.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGamequest5.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest5.SetFont(main.extendedDialogFont);
            newGameNameFinja.setFont(main.extendetNewGameFont);
            newGameButtonHolder5.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGamequest6.setBorder(new EmptyBorder(0,0,(main.frameHeight/30)*main.scaleFactor,0));
            NewGamequest6.SetFont(main.extendedDialogFont);
            newGameNameKizo.setFont(main.extendetNewGameFont);
            newGameButtonHolder6.setBorder(new EmptyBorder(0,((main.frameWidth/2)+(main.frameWidth/6))*main.scaleFactor,0,0));

            NewGameQuestRewiev.SetFont(main.extendettextfont);
            reviewquestbutton1.setFont(main.extendedDialogFont);
            reviewquestbutton2.setFont(main.extendedDialogFont);
            reviewquestbutton3.setFont(main.extendedDialogFont);
            reviewquestbutton4.setFont(main.extendedDialogFont);
            reviewquestbutton5.setFont(main.extendedDialogFont);
            reviewquestbutton6.setFont(main.extendedDialogFont);


        }else{
            //nicht extended
            menuButtonHolder.setBorder(new EmptyBorder(main.frameHeight/3,main.frameWidth/16,0,0));
            brikTextSettings.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));
            graphicHeadLine.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));
            volumeHeadLine.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));
            NewGameConfigHeadline.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/20,0));
            brikTextPane.SetFont(main.textfont);
            brikTextSettings.SetFont(main.headLineFont);
            graphicHeadLine.SetFont(main.headLineFont);
            keyBindText.SetFont(main.headLineFont);
            volumeHeadLine.SetFont(main.headLineFont);
            DateiChooseHeadline.SetFont(main.headLineFont);
            DateiChooseLore.SetFont(main.textfont);
            NewGameConfigHeadline.SetFont(main.headLineFont);
            graphicsButton2.setText("Vollbild: AUS");

            InfoText1Save1.SetFont(main.dialogFont);
            InfoText2Save1.SetFont(main.textfont);
            InfoText3Save1.SetFont(main.textfont);
            InfoText4Save1.SetFont(main.textfont);

            InfoText1Save2.SetFont(main.dialogFont);
            InfoText2Save2.SetFont(main.textfont);
            InfoText3Save2.SetFont(main.textfont);
            InfoText4Save2.SetFont(main.textfont);

            InfoText1Save3.SetFont(main.dialogFont);
            InfoText2Save3.SetFont(main.textfont);
            InfoText3Save3.SetFont(main.textfont);
            InfoText4Save3.SetFont(main.textfont);

            keyBindText.setBorder(new EmptyBorder(main.frameHeight/20,0, main.frameHeight/30, 0));
            graphicBackButtonHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
            backButtonKeyHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
            volumeButtonHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
            backButtonHolderSettings.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
            backButtonChooseHolder.setBorder(new EmptyBorder(0, main.frameWidth/3, 0, main.frameWidth/3));
            headlinePanel.setBorder(new EmptyBorder(0, 0, main.frameHeight/30, 0));
            DateiChooseLore.setBorder(new EmptyBorder(0,0,main.frameHeight/20,0));
            DateiChooseHeadline.setBorder(new EmptyBorder(main.frameHeight/20,0,main.frameHeight/40,0));
            chooseSaveButtonHolder.setBorder(new EmptyBorder(0,main.frameHeight/40,0,main.frameHeight/40));
            chooseSaveButtonHolder.setLayout(new GridLayout(1, 3,main.frameWidth/40,0));

            InfoText1Save1.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));
            InfoText1Save2.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));
            InfoText1Save3.setBorder(new EmptyBorder(main.frameHeight/60,0,main.frameHeight/20,0));


            panelbottom.setLayout(new GridLayout(1,2,main.frameWidth/4,0));
            panelbottom.setBorder(BorderFactory.createEmptyBorder(main.frameHeight/25,0,0,0));


            NewGamequest1.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest1.SetFont(main.dialogFont);
            newGameNameDatei.setFont(main.newGameFont);
            newGameButtonHolder1.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGamequest2.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest2.SetFont(main.dialogFont);
            newGameFavFood.setFont(main.newGameFont);
            newGameButtonHolder2.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGamequest3.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest3.SetFont(main.dialogFont);
            newGameHobby.setFont(main.newGameFont);
            newGameButtonHolder3.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGamequest4.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest4.SetFont(main.dialogFont);
            newGameNameTimmy.setFont(main.newGameFont);
            newGameButtonHolder4.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGamequest5.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest5.SetFont(main.dialogFont);
            newGameNameFinja.setFont(main.newGameFont);
            newGameButtonHolder5.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGamequest6.setBorder(new EmptyBorder(0,0,main.frameHeight/30,0));
            NewGamequest6.SetFont(main.dialogFont);
            newGameNameKizo.setFont(main.newGameFont);
            newGameButtonHolder6.setBorder(new EmptyBorder(0,(main.frameWidth/2)+(main.frameWidth/6),0,0));

            NewGameQuestRewiev.SetFont(main.textfont);
            reviewquestbutton1.setFont(main.dialogFont);
            reviewquestbutton2.setFont(main.dialogFont);
            reviewquestbutton3.setFont(main.dialogFont);
            reviewquestbutton4.setFont(main.dialogFont);
            reviewquestbutton5.setFont(main.dialogFont);
            reviewquestbutton6.setFont(main.dialogFont);


        }


}

public void refreshDateiChoosePanels(){

    DateiChooseHeadline.repaintText();
    DateiChooseLore.repaintText();



    loadDataButon1.repaintButton();
    loadDataButon2.repaintButton();
    loadDataButon3.repaintButton();

    InfoText1Save1.repaintText();
    InfoText2Save1.repaintText();
    InfoText3Save1.repaintText();
    InfoText4Save1.repaintText();

    InfoText1Save1.setText(main.saveGameStrings[0][0]);
    InfoText2Save1.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[0][1]).substring(0,3)+" Std.");
    InfoText3Save1.setText("Geld: "+main.saveGameDoubles[0][0] +" $");
    InfoText4Save1.setText("Standort: "+getLocation(main.saveGameDoubles[0][3],main.saveGameDoubles[0][4]));

    if (main.saveGameBooleans[0][0]){
        loadDataButon1.setText("Neues Spiel starten");
    }else {
        loadDataButon1.setText("Spielstand laden");
    }
    if (main.saveGameBooleans[0][0]){
        panel2Save1.setVisible(false);
    }else {
        panel2Save1.setVisible(true);
    }

    InfoText1Save2.repaintText();
    InfoText2Save2.repaintText();
    InfoText3Save2.repaintText();
    InfoText4Save2.repaintText();

    InfoText1Save2.setText(main.saveGameStrings[1][0]);
    InfoText2Save2.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[1][1]).substring(0,3)+" Std.");
    InfoText3Save2.setText("Geld: "+main.saveGameDoubles[1][0] +" $");
    InfoText4Save2.setText("Standort: "+getLocation(main.saveGameDoubles[1][3],main.saveGameDoubles[1][4]));

    if (main.saveGameBooleans[1][0]){
        loadDataButon2.setText("Neues Spiel starten");
    }else {
        loadDataButon2.setText("Spielstand laden");
    }
    if (main.saveGameBooleans[1][0]){
        panel2Save2.setVisible(false);
    }else {
        panel2Save2.setVisible(true);
    }

    InfoText1Save3.repaintText();
    InfoText2Save3.repaintText();
    InfoText3Save3.repaintText();
    InfoText4Save3.repaintText();

    InfoText1Save3.setText(main.saveGameStrings[2][0]);
    InfoText2Save3.setText("Spielzeit: "+String.valueOf(main.saveGameDoubles[2][1]).substring(0,3)+" Std.");
    InfoText3Save3.setText("Geld: "+main.saveGameDoubles[2][0] +" $");
    InfoText4Save3.setText("Standort: "+getLocation(main.saveGameDoubles[2][3],main.saveGameDoubles[2][4]));

    if (main.saveGameBooleans[2][0]){
        loadDataButon3.setText("Neues Spiel starten");
    }else {
        loadDataButon3.setText("Spielstand laden");
    }
    if (main.saveGameBooleans[2][0]){
        panel2Save3.setVisible(false);
    }else {
        panel2Save3.setVisible(true);
    }

}



public void startNewGame(int loadedGame){

        vorschlagButtonStand[0]=0;
        vorschlagButtonStand[1]=0;
        vorschlagButtonStand[2]=0;
        vorschlagButtonStand[3]=0;
        vorschlagButtonStand[4]=0;
        vorschlagButtonStand[5]=0;

        vorschlagDataStand[0]=0;
        vorschlagDataStand[1]=0;
        vorschlagDataStand[2]=0;
        vorschlagDataStand[3]=0;
        vorschlagDataStand[4]=0;
        vorschlagDataStand[5]=0;

        newGameConfigVorschlagbutton1.setText(main.vorschlagButtonNames[0]);
        newGameConfigVorschlagbutton2.setText(main.vorschlagButtonNames[0]);
        newGameConfigVorschlagbutton3.setText(main.vorschlagButtonNames[0]);
        newGameConfigVorschlagbutton4.setText(main.vorschlagButtonNames[0]);
        newGameConfigVorschlagbutton5.setText(main.vorschlagButtonNames[0]);
        newGameConfigVorschlagbutton6.setText(main.vorschlagButtonNames[0]);


        newGameNameDatei.setText("");
        newGameFavFood.setText("");
        newGameHobby.setText("");
        newGameNameTimmy.setText("");
        newGameNameFinja.setText("");
        newGameNameKizo.setText("");


        newGameConfigbreakupButton.setText("Abbrechen");

        changefromQuestreview= false;

        setNewGameQuestState(NewGameQuestState.Quest1);
        NewGamequest1.startTypewrtiting("Benenne deine ganz eigene Speicherdatei.");
        if (changefromQuestreview){
            newGameConfigstartGamebutton.setText("Zurück zur Übersicht");
        }else {
            newGameConfigstartGamebutton.setText("Weiter");
        }

}

public String getLocation(Double x, Double y){

        //Anhand der aktuellen Position wird die jeweilige stadt returnt

        Dimension dim = new Dimension(x.intValue(),y.intValue());




        return "Zentrum";

}


}
