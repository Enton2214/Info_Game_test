import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameDisplay extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    Graphics2D g2;

    private final Main main;
    private final Player player;
    private final Finja finja;
    private final Kizo kizo;
    private final TileManager tileManager;
    private final Transform transform;


    public BufferedImage[] pauseOverlaypics;
    public BufferedImage pausePanel;
    public BufferedImage pausePanelBg;
    public BufferedImage pauseHeadline;
    public BufferedImage pauseHeadlineShadow;
    public BufferedImage pauseBoxBG;
    public BufferedImage pauseBoxFG;
    public BufferedImage pokal;
    public BufferedImage pokalGlanz;
    public BufferedImage pauseButtonBG;
    public BufferedImage pauseButtonFG;


    //dialog
    boolean cineBarShow = false;
    boolean isAnswerTime = false;
    ArrayList<DiaButton> Dialogbuttons = new ArrayList<>();
    int hoveredDiaButton = -1;
    boolean invOpenCauseDialog = false;

    // Pause
    public long pauseStartTime = 0;
    public float neonstartTime = 0f;
    public long flickerCd =0;
    long startTime=0;
    int duration =0;
    float brightness = 0f;
    int pauseOverlayNum = 0; // Der Rand soll je nach Story fortschritt mehr und mehr "angefressen bzw zerstört sein"
                                // 0-3 je nach Storystand
    int hoveredPauseButton = 0;
    boolean areyousure = false;
    String leaveButton = "Verlassen";

    //Inventar
    int invChoosedCharacter = 0; //0-Timmy, 1-Finja, 2-Kizo
    int invRealChoosedCharacter = 0;
    int canStartAnimation = 0;
    int img1X = 0;
    int img2X = 0;
    int img3X = 0;
    int CiImgHeight = 0;
    int CiImgWidth = 0;
    double pixelLength = 0;
    double actualPixelHeight = 0;
    double actualPixelHeight1 = 0;
    double actualPixelHeight2 = 0;
    double actualPixelHeight3 = 0;
    BufferedImage[] invCharacterSprite = new BufferedImage[]{Main.player.framesDown.get(0),Main.player.framesDown.get(1),Main.player.framesDown.get(2),Main.player.framesDown.get(3)};
    Entity chosedInvCharacter;
    public int hoveredItem =-1;
    double scaleX = 0;
    double scaleY = 0;
    double invItemY = 0;
    double invItemX = 0;
    int itemStartScroll = 0;

    boolean itemInfoShown = false;
    String itemName = "";
    String itemDescribe = "";
    BufferedImage itemIcon;
    double itemWorth = 0;
    int HPEffect = 0;
    int PPEffect = 0;
    int speedEffect = 0;
    long speedDuration = 0;
    public Item.ItemType itemType;
    int weaponDamage =0;
    int weaponPrecision =0;
    int armorBars=0;
    boolean itemEquipped = false;
    int hoveredButton =0;
    int chosedItemButton =0;
    int itemNum =0;
    int itemChooseHovered = 0;
    public Item choosenItem;

    public long invstartTime = 0;

    long lastHPTime =0;

    ArrayList<EventData> dialogList = new ArrayList<>();


    Picture[] invUI = new Picture[8];
    Picture BGAlpha = new Picture();

    Picture cinePicUp = new Picture();
    Picture cinePicDown = new Picture();
    Picture cinePicUpBG = new Picture();
    Picture cinePicDownBG = new Picture();

    public GameDisplay(Main main){
        this.main = main;
        this.player = Main.player;
        this.finja = Main.finja;
        this.kizo = Main.kizo;
        this.tileManager = Main.tileManager;
        this.transform = Main.transform;


        invUI[0] = new Picture();
        invUI[1] = new Picture();
        invUI[2] = new Picture();
        invUI[3] = new Picture();
        invUI[4] = new Picture();
        invUI[5] = new Picture();
        invUI[6] = new Picture();
        invUI[7] = new Picture();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        chosedInvCharacter = player;



        reLoadPauseUI();
        reloadInventoryUI();
        reloadDialogUI();
    }

    public void loadGamePanel(){
        setBackground(Color.BLACK);




    }

    public void setUpGame(){
        scaleY = (double) getHeight() / 90;
        cinePicUp.y= (int) (93*scaleY);
        cinePicDown.y= (int) (90*scaleY);
    }

    public void updateAll(){
        //Entity Positionen und so updaten
        transform.updateTimmy();
        player.update();
        //Entitys Updaten
    }

    public void reloadDialogUI(){

        cinePicUp.image = colorizeImage(main.loadExternalImage("Grafik/UI/Dialog UI/CineBarUp.png"),main.themeColor[4]);
        cinePicDown.image = colorizeImage(main.loadExternalImage("Grafik/UI/Dialog UI/CineBarDown.png"),main.themeColor[4]);
        cinePicUpBG.image = colorizeImage(main.loadExternalImage("Grafik/UI/Dialog UI/CineBarUpBG.png"),main.themeColor[1].darker().darker().darker().darker());
        cinePicDownBG.image = colorizeImage(main.loadExternalImage("Grafik/UI/Dialog UI/CineBarDownBG.png"),main.themeColor[1].darker().darker().darker().darker());



    }

    public void reLoadPauseUI(){


        pausePanel = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/pause_Overlay_1.png"),main.themeColor[4]);
        pausePanelBg = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/pause_Overlay_1.png"),main.themeColor[4].darker().darker().darker().darker());
        pauseHeadline = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/pause_headline.png"),main.themeColor[3]);
        pauseHeadlineShadow = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/pause_headline_shadow.png"),main.themeColor[5].darker().darker());
        pauseBoxBG = main.loadExternalImage("Grafik/UI/Pause UI/kasten_klein_BG.png");
        pauseBoxFG = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/kasten_klein_FG.png"),main.themeColor[4]);
        pokal = main.loadExternalImage("Grafik/Item/Pokal.png");
        pokalGlanz = main.loadExternalImage("Grafik/Item/Pokal_glänzend.png");
        pauseButtonBG = main.loadExternalImage("Grafik/UI/Pause UI/pause_button_BG.png");
        pauseButtonFG = colorizeImage(main.loadExternalImage("Grafik/UI/Pause UI/pause_button_FG.png"),main.themeColor[4]);



        pauseOverlaypics = new BufferedImage[]{main.loadExternalImage("Grafik/Tiles/air.png"),
                main.loadExternalImage("Grafik/UI/Pause UI/pause_Overlay_2.png"),
                main.loadExternalImage("Grafik/UI/Pause UI/pause_Overlay_3.png"),
                main.loadExternalImage("Grafik/UI/Pause UI/pause_Overlay_4.png")};

         String rgbColor = String.format("\u001B[38;2;%d;%d;%dm",main.themeColor[2].getRed(),main.themeColor[2].getGreen(),main.themeColor[2].getBlue());
         System.out.println("Pause UI auf aktuellem Theme "+rgbColor+"███"+"\u001B[0m"+" reloaded!");



    }

    public void reloadInventoryUI(){

        invUI[0].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_inv_FG.png"),main.themeColor[4]);



        invUI[1].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[4]);
        invUI[1].secondImage = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[5]);
        invUI[2].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[4]);
        invUI[2].secondImage = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[5]);
        invUI[3].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[4]);
        invUI[3].secondImage = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_FG.png"),main.themeColor[5]);
        invUI[4].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_inv_BG.png"),main.themeColor[1].darker().darker().darker().darker().darker());
        invUI[5].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_CI_Inv_BG.png"),main.themeColor[1].darker().darker().darker().darker().darker());
        invUI[6].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_inv_item_FG.png"),main.themeColor[4]);
        invUI[7].image = colorizeImage(main.loadExternalImage("Grafik/UI/Inventory UI/UI_inv_item_BG.png"),main.themeColor[1].darker().darker().darker().darker().darker());



        String rgbColor = String.format("\u001B[38;2;%d;%d;%dm",main.themeColor[2].getRed(),main.themeColor[2].getGreen(),main.themeColor[2].getBlue());
        System.out.println("Inventar UI auf aktuellem Theme "+rgbColor+"███"+"\u001B[0m"+" reloaded!");


    }


    public BufferedImage colorizeImage(BufferedImage originalImage, Color newColor) {

        BufferedImage tintedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = tintedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(newColor);
        g2d.fillRect(0, 0, tintedImage.getWidth(), tintedImage.getHeight());
        g2d.dispose();
        return tintedImage;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        tileManager.repaint(g2,tileManager.mapBG);
        // y-sorting
        tileManager.repaint(g2,tileManager.mapMG);
        player.repaint(g2);

        repaintEntitys(g2);



        tileManager.repaint(g2,tileManager.mapFG);



        //potenziell hier einen farbfilter rüberlegen für den ersten story part





        checkDialog();
        showCineBar();

        //UI

        int frame = changeSprite(player.invCharacterSprite.size());
        scaleX = (double) getWidth() / invUI[0].image.getWidth();
        scaleY = (double) getHeight() / invUI[0].image.getHeight();
        long deltaHPTime = System.currentTimeMillis()-lastHPTime;

        if (deltaHPTime>120){
            player.updateEffects();
            if (main.saveGameBooleans[main.loadedGame][1]){
                finja.updateEffects();
            }
            if (main.saveGameBooleans[main.loadedGame][2]){
                kizo.updateEffects();
            }

            lastHPTime = System.currentTimeMillis();
        }


        if (main.gameStatus == Main.GameState.PAUSED) {


            int boxWidth = (int) (pauseBoxBG.getWidth()*scaleX);
            int boxHeight = (int) (pauseBoxBG.getHeight()*scaleY);
            int buttonWidth = (int) (pauseButtonBG.getWidth()*scaleX);
            int buttonHeight = (int) (pauseButtonBG.getHeight()*scaleY);

            long currentTime = System.currentTimeMillis();
            long timeSinceOpen = currentTime - pauseStartTime;

            float alpha = 1.0f;


            if (timeSinceOpen < 600) {

                if (neonstartTime < 0.9f) {
                    double targetAlpha = 2 * (1.0/main.reachedFPS);
                    neonstartTime+= (float) targetAlpha;
                    alpha = neonstartTime;
                }

            }else {

                long deltaTime =0;



                if(System.currentTimeMillis()-flickerCd>10) {
                    if (Math.random() * 10 < (0.1 + (double) pauseOverlayNum / 16) && startTime == 0) {
                        duration = new Random().nextInt(80, 300);
                        brightness = new Random().nextFloat(0.7f - ((float) pauseOverlayNum / 12), 0.90f);

                        alpha = brightness;

                        //Neonröhren ton abspielen
                        startTime = System.currentTimeMillis();
                    }

                }

                    if (startTime != 0) {
                        deltaTime = System.currentTimeMillis() - startTime;
                    }
                    if (deltaTime < duration && deltaTime != 0) {

                        alpha = brightness;
                    } else if (deltaTime > duration) {
                        deltaTime = 0;
                        startTime = 0;
                    }
                    flickerCd = System.currentTimeMillis();



            }
            g2.setColor(new Color(main.themeColor[4].getRed(), main.themeColor[4].getGreen(), main.themeColor[4].getBlue(), 150).darker().darker().darker().darker());
            g2.fillRect(0,0,getWidth(),getHeight());
            g2.drawImage(pausePanelBg,0,0,getWidth(),getHeight(),null);

            if (timeSinceOpen<600){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            }
            g2.drawImage(pauseBoxBG, (int) (20*scaleX), (int) (16*scaleY), boxWidth, boxHeight,null);
            g2.drawImage(pauseBoxBG, (int) (120*scaleX), (int) (16*scaleY), boxWidth, boxHeight,null);
            g2.drawImage(pauseButtonBG, (int) (55*scaleX), (int) (24*scaleY), buttonWidth, buttonHeight,null);
            g2.drawImage(pauseButtonBG, (int) (55*scaleX), (int) (44*scaleY), buttonWidth, buttonHeight,null);
            g2.drawImage(pauseButtonBG, (int) (55*scaleX), (int) (64*scaleY), buttonWidth, buttonHeight,null);


            if (main.saveGameBooleans[main.loadedGame][1]){
                g2.drawImage(pauseBoxBG, (int) (120*scaleX), (int) (38*scaleY), boxWidth, boxHeight,null);
            }
            if (main.saveGameBooleans[main.loadedGame][2]){
                g2.drawImage(pauseBoxBG, (int) (120*scaleX), (int) (60*scaleY), boxWidth, boxHeight,null);
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2.drawImage(pausePanel, 0,0 , getWidth(), getHeight(), null);
            g2.drawImage(pauseBoxFG, (int) (20*scaleX), (int) (16*scaleY), boxWidth, boxHeight,null);
            g2.drawImage(pauseBoxFG, (int) (120*scaleX), (int) (16*scaleY), boxWidth, boxHeight,null);
            if (main.saveGameBooleans[main.loadedGame][1]) {

                g2.drawImage(pauseBoxFG, (int) (120*scaleX), (int) (38*scaleY), boxWidth, boxHeight,null);
            }
            if (main.saveGameBooleans[main.loadedGame][2]) {

                g2.drawImage(pauseBoxFG, (int) (120*scaleX), (int) (60*scaleY), boxWidth, boxHeight,null);
            }

             if (hoveredPauseButton==2){
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (24*scaleY), buttonWidth, buttonHeight,null);
             }else {
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (24*scaleY), buttonWidth, buttonHeight,null);
             }
             if (hoveredPauseButton==3){
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (44*scaleY), buttonWidth, buttonHeight,null);
             }else {
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (44*scaleY), buttonWidth, buttonHeight,null);
             }
             if (hoveredPauseButton==4){
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (64*scaleY), buttonWidth, buttonHeight,null);
             }else {
                 g2.drawImage(pauseButtonFG, (int) (55*scaleX), (int) (64*scaleY), buttonWidth, buttonHeight,null);
             }



            if (timeSinceOpen >600){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }


            if (main.saveGameBooleans[main.loadedGame][1]) {
                if (hoveredPauseButton==6){
                    g2.drawImage(finja.peaceFrame, (int) (124*scaleX), (int) (42*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
                }else {
                    g2.drawImage(finja.framesDown.get(frame), (int) (124*scaleX), (int) (42*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
                }


            }
            if (main.saveGameBooleans[main.loadedGame][2]) {
                if (hoveredPauseButton==7){
                    g2.drawImage(kizo.peaceFrame, (int) (124*scaleX), (int) (64*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
                }else {
                    g2.drawImage(kizo.framesDown.get(frame), (int) (124*scaleX), (int) (64*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
                }

            }

            if (main.vollbild){
                g2.setFont(main.extendedHeaderDialogFont);
                g2.setColor(main.themeColor[5].darker().darker());
            }else {
                g2.setFont(main.headerDialogFont);
                g2.setColor(main.themeColor[5].darker().darker());
            }
            if (hoveredPauseButton ==5){
                g2.drawImage(player.peaceFrame, (int) (124*scaleX), (int) (20*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
            }else {
                g2.drawImage(player.framesDown.get(frame), (int) (124*scaleX), (int) (20*scaleY), (int) ((12) * scaleX), (int) ((12) * scaleY), null);
            }
            if (hoveredPauseButton==1){
                g2.drawImage(pokalGlanz, (int) (23*scaleX), (int) (19*scaleY), (int) ((14) * scaleX), (int) ((14) * scaleY),null);
            }else {
                g2.drawImage(pokal, (int) (23*scaleX), (int) (19*scaleY), (int) ((14) * scaleX), (int) ((14) * scaleY),null);
            }


            if (hoveredPauseButton!=2){
                g2.drawString("Weiter",(int) (71.7*scaleX), (int) (34.7*scaleY));
            }


            if (hoveredPauseButton!=3){
                g2.drawString("Einstellungen",(int) (61.2*scaleX), (int) (54.7*scaleY));
            }


            if (hoveredPauseButton!=4){
                g2.drawString(leaveButton,(int) (67.2*scaleX), (int) (74.7*scaleY));
            }




            g2.setColor(main.themeColor[3]);


            if(hoveredPauseButton==2){
                g2.drawString("Weiter",(int) (71.7*scaleX), (int) (34.7*scaleY));
            }else {
                g2.drawString("Weiter",(int) (71*scaleX), (int) (34*scaleY));
            }
            if(hoveredPauseButton==3){
                g2.drawString("Einstellungen",(int) (61.2*scaleX), (int) (54.7*scaleY));
            }else {
                g2.drawString("Einstellungen",(int) (60.5*scaleX), (int) (54*scaleY));
            }
            if (hoveredPauseButton==4){
                g2.drawString(leaveButton,(int) (67.2*scaleX), (int) (74.7*scaleY));
            }else {
                g2.drawString(leaveButton,(int) (66.5*scaleX), (int) (74*scaleY));
            }



            g2.drawImage(pauseHeadlineShadow,0,0,getWidth(),getHeight(),null);
            g2.drawImage(pauseHeadline,0,0,getWidth(),getHeight(),null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.drawImage(pauseOverlaypics[pauseOverlayNum],0,0,getWidth(),getHeight(),null);




        }else if (main.gameStatus == Main.GameState.Inventory) {




            pixelLength = scaleX;
            CiImgHeight = (int) (invUI[1].image.getHeight() * scaleY);
            CiImgWidth  = (int) (invUI[1].image.getWidth() * scaleX);

            img1X = (int) (getWidth() - (7 * scaleX + 3 * CiImgWidth));
            img2X = (int) (getWidth() - (5 * scaleX + 2 * CiImgWidth));
            img3X = (int) (getWidth() - (3 * scaleX + CiImgWidth));

            long timeSinceOpen = System.currentTimeMillis() - invstartTime;


            int characterX = (int) (69 * scaleX);
            int characterY = (int) (34 * scaleY);

            invItemY =41.5*scaleY;
            invItemX =14*scaleX;

            transform.changeTransparency(BGAlpha,0,150,5);
            g2.setColor(new Color(main.themeColor[4].getRed(), main.themeColor[4].getGreen(), main.themeColor[4].getBlue(), BGAlpha.Alpha).darker().darker().darker().darker());
            g2.fillRect(0,0,getWidth(),getHeight());

            if (main.vollbild){
                g2.setFont(main.extendedDialogFont);
            }else{g2.setFont(main.dialogFont);}
            g2.setColor(main.themeColor[3]);



            transform.changePosition(invUI[1], 0,-CiImgHeight,0, (int) (4*pixelLength),12);
            if (invChoosedCharacter ==0&& canStartAnimation ==1){
                transform.changePosition(invUI[1], 0, (int) (4*pixelLength),0, (int) (2*pixelLength),5);
            }
            g2.drawImage(invUI[5].image,img1X,invUI[1].y,CiImgWidth,CiImgHeight,null);
            if (invRealChoosedCharacter ==0) {
                g2.drawImage(invUI[1].secondImage, img1X, invUI[1].y, CiImgWidth, CiImgHeight, null);
            }else{
                g2.drawImage(invUI[1].image, img1X, invUI[1].y, CiImgWidth, CiImgHeight, null);
            }

            transform.drawShortedText(g2,player.name, (int) (9*pixelLength), (int) (invUI[1].y+8*scaleY), (int) (24*scaleX));
            g2.drawString("Level: "+player.lvl, (int) (9*pixelLength), (int) (invUI[1].y+14*scaleY));
            g2.drawString("HP: "+player.HP, (int) (34*pixelLength), (int) (invUI[1].y+8*scaleY));
            g2.drawString("PP: "+player.PP, (int) (34*pixelLength), (int) (invUI[1].y+14*scaleY));




            if (timeSinceOpen > 100&&main.saveGameBooleans[main.loadedGame][1]) {
                transform.changePosition(invUI[2], 0,-CiImgHeight,0, (int) (4*pixelLength),12);
                if (invChoosedCharacter ==1&& canStartAnimation ==2){
                    transform.changePosition(invUI[2], 0, (int) (4*pixelLength),0, (int) (2*pixelLength),5);
                }
                g2.drawImage(invUI[5].image,img2X,invUI[2].y,CiImgWidth,CiImgHeight,null);
                if (invRealChoosedCharacter ==1){
                    g2.drawImage(invUI[2].secondImage, img2X, invUI[2].y, CiImgWidth, CiImgHeight, null);
                }else {
                    g2.drawImage(invUI[2].image, img2X, invUI[2].y, CiImgWidth, CiImgHeight, null);
                }

                transform.drawShortedText(g2,finja.name, (int) (61*pixelLength), (int) (invUI[2].y+8*scaleY), (int) (24*scaleX));
                g2.drawString("Level: "+finja.lvl, (int) (61*pixelLength), (int) (invUI[2].y+14*scaleY));
                g2.drawString("HP: "+finja.HP, (int) (86*pixelLength), (int) (invUI[2].y+8*scaleY));
                g2.drawString("PP: "+finja.PP, (int) (86*pixelLength), (int) (invUI[2].y+14*scaleY));
            }



            if (timeSinceOpen > 200&&main.saveGameBooleans[main.loadedGame][2]) {
                transform.changePosition(invUI[3], 0,-CiImgHeight,0, (int) (4*pixelLength),12);
                if (invChoosedCharacter ==2&& canStartAnimation ==3){
                    transform.changePosition(invUI[3], 0, (int) (4*pixelLength),0, (int) (2*pixelLength),5);
                }
                g2.drawImage(invUI[5].image,img3X,invUI[3].y,CiImgWidth,CiImgHeight,null);
                if (invRealChoosedCharacter ==2) {
                    g2.drawImage(invUI[3].secondImage,img3X,invUI[3].y,CiImgWidth,CiImgHeight,null);
                }else {
                    g2.drawImage(invUI[3].image,img3X,invUI[3].y,CiImgWidth,CiImgHeight,null);
                }

                transform.drawShortedText(g2,kizo.name,  (int) (113*pixelLength), (int) (invUI[3].y+8*scaleY), (int) (24*scaleX));
                g2.drawString("Level: "+kizo.lvl, (int) (113*pixelLength), (int) (invUI[3].y+14*scaleY));
                g2.drawString("HP: "+kizo.HP, (int) (138*pixelLength), (int) (invUI[3].y+8*scaleY));
                g2.drawString("PP: "+kizo.PP, (int) (138*pixelLength), (int) (invUI[3].y+14*scaleY));
            }

            transform.changeTransparency(invUI[4], 0,255,7);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) invUI[4].Alpha /255)));
            g2.drawImage(invUI[4].image, 0,0,getWidth(),getHeight(),null);
            g2.drawImage(invUI[0].image,0,0,getWidth(),getHeight(),null);
            g2.drawImage(invCharacterSprite[frame], characterX, characterY, (int) ((invCharacterSprite[frame].getWidth() - 2) * scaleX), (int) ((invCharacterSprite[frame].getHeight() - 2) * scaleY), null);




            g2.setColor(main.themeColor[3]);
            g2.drawString("Inventar    ("+chosedInvCharacter.usedInvSpace+"/"+chosedInvCharacter.maxInvSpace+")", (int) (9*scaleX), (int) (33*scaleY));
            g2.drawString("Status/Ausrüstung:", (int) (113*scaleX), (int) (33*scaleY));
            g2.drawString("HP: "+chosedInvCharacter.HP+"/"+chosedInvCharacter.maxHP, (int) (111*scaleX), (int) (41.5*scaleY));
            g2.drawString("PP: "+chosedInvCharacter.PP+"/"+chosedInvCharacter.maxPP, (int) (111*scaleX), (int) (46.5*scaleY));
            g2.drawString("IQ: "+chosedInvCharacter.IQ, (int) (134.8*scaleX), (int) (41.5*scaleY));
            g2.drawString("Glück: "+chosedInvCharacter.luck, (int) (134.8*scaleX), (int) (46.5*scaleY));

            g2.drawString("Offensive: +"+chosedInvCharacter.offensive, (int) (112 * scaleX), (int) (52.5 * scaleY));
            g2.drawString("Defensive: +"+chosedInvCharacter.armorBars, (int) (112 * scaleX), (int) (56.5 * scaleY));
            g2.drawString("-------------------", (int) (111.4 * scaleX), (int) (59.5 * scaleY));
            transform.drawShortedText(g2,"Waffe: " + chosedInvCharacter.equippedWeaponName, (int) (112 * scaleX), (int) (62.5 * scaleY), (int) (44*scaleX));
            transform.drawShortedText(g2,"Kopf: " + chosedInvCharacter.equippedHatName, (int) (112 * scaleX), (int) (68 * scaleY), (int) (44*scaleX));
            transform.drawShortedText(g2,"Brust: " + chosedInvCharacter.equippedChestName, (int) (112 * scaleX), (int) (73 * scaleY), (int) (44*scaleX));
            transform.drawShortedText(g2,"Beine: " + chosedInvCharacter.equippedPantsName, (int) (112 * scaleX), (int) (78 * scaleY), (int) (44*scaleX));
            transform.drawShortedText(g2,"Füße: " + chosedInvCharacter.equippedShoesName, (int) (112 * scaleX), (int) (83 * scaleY), (int) (44*scaleX));


            if (player.money!=0) {
                int lastTwoNum = (int) (player.money*100);
                g2.drawString(String.valueOf((lastTwoNum)).substring(String.valueOf(lastTwoNum).length() - 2, String.valueOf(lastTwoNum).length()), (int) (92 * scaleX), (int) (71 * scaleY));
            }else { g2.drawString("00", (int) (92 * scaleX), (int) (71 * scaleY));}
            g2.drawString("__", (int) (92 * scaleX), (int) (72 * scaleY));
            if (main.vollbild){
                g2.setFont(main.extendedHeaderDialogFont);
            }else {
                g2.setFont(main.headerDialogFont);

            }
            FontMetrics fm = g2.getFontMetrics();
            int x = fm.stringWidth(String.valueOf((int)player.money));
            g2.drawString("$ " + (int) player.money , (int) (84.5 * scaleX)-x, (int) (72.4 * scaleY));


            if(main.vollbild){
                g2.setFont(main.extendedDialogFont);
            }else {
                g2.setFont(main.dialogFont);
            }

            int currentItemY = (int) invItemY;
            int currentItemNum = 0;
            for (int i = itemStartScroll; i<chosedInvCharacter.inventory.size(); i++) {

                if (chosedInvCharacter.inventory.get(i) == 0 || chosedInvCharacter.inventory.get(i) == null) {
                    continue;
                }
                if (i>itemStartScroll+7){
                    break;
                }
                int item = chosedInvCharacter.inventory.get(i);
                String itemName = main.items.get(item).name;

                if (hoveredItem == currentItemNum){
                    g2.setColor(main.themeColor[3].darker().darker());
                    transform.drawShortedText(g2,itemName, (int) ((int) invItemX-scaleX), currentItemY, (int) (32*scaleX));
                    g2.drawImage(main.items.get(chosedInvCharacter.inventory.get(i)).itemIcon, (int) (invItemX -7*scaleX), (int) (currentItemY -3.3*scaleY),(int) ((invCharacterSprite[frame].getWidth() - 20) * scaleX),(int) ((invCharacterSprite[frame].getHeight() - 20) * scaleY),null);

                }else {
                    g2.setColor(main.themeColor[3]);
                    transform.drawShortedText(g2,itemName, (int) invItemX, currentItemY, (int) (32*scaleX));
                    g2.drawImage(main.items.get(chosedInvCharacter.inventory.get(i)).itemIcon, (int) (invItemX -6*scaleX), (int) (currentItemY -3.3*scaleY),(int) ((invCharacterSprite[frame].getWidth() - 20) * scaleX),(int) ((invCharacterSprite[frame].getHeight() - 20) * scaleY),null);
                }
                currentItemY += 5.9*scaleY;
                currentItemNum++;

            }


            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            if (itemInfoShown){


                g2.drawImage(invUI[7].image, 0, 0,getWidth(),getHeight(),null);
                g2.drawImage(invUI[6].image, 0, 0,getWidth(),getHeight(),null);
                g2.setColor(main.themeColor[3]);

                g2.drawImage(itemIcon,(int) (54*scaleX), (int) (37*scaleY), (int) (10*scaleX), (int) (10*scaleY),null);
                g2.drawString("------------------------------------", (int) (54 * scaleX), (int) (60 * scaleY));

                if (chosedItemButton==0) {
                    transform.drawWrappedText(g2, itemDescribe, (int) (66 * scaleX), (int) (40 * scaleY), (int) (67 * scaleX));


                    if (itemWorth>0){
                        g2.drawString("Wert: "+itemWorth+"$", (int) (56 * scaleX), (int) (57.5 * scaleY));
                    }else {
                        g2.drawString("Unverkäuflich", (int) (56 * scaleX), (int) (57.5 * scaleY));
                    }

                    g2.drawString("Typ: "+itemType, (int) (100 * scaleX), (int) (57.5 * scaleY));
                    g2.drawString("Effekte: ", (int) (56 * scaleX), (int) (63 * scaleY));

                    if (itemType== Item.ItemType.Essen||itemType== Item.ItemType.Trinken){
                        if (speedEffect != 0) {
                            g2.drawString("Speed: +" + speedEffect, (int) (73 * scaleX), (int) (68 * scaleY));
                            g2.drawString("für: " + speedDuration + " s.", (int) (73 * scaleX), (int) (73 * scaleY));

                        }
                        if (HPEffect != 0 && HPEffect > 0) {
                            g2.drawString("HP: +" + HPEffect, (int) (56 * scaleX), (int) (68 * scaleY));
                        } else if (HPEffect != 0) {
                            g2.drawString("HP: " + HPEffect, (int) (56 * scaleX), (int) (68 * scaleY));
                        }
                        if (PPEffect != 0 && PPEffect > 0) {
                            g2.drawString("PP: +" + PPEffect, (int) (56 * scaleX), (int) (73 * scaleY));
                        } else if (PPEffect != 0) {
                            g2.drawString("PP: " + PPEffect, (int) (56 * scaleX), (int) (73 * scaleY));
                        }
                        if (speedEffect == 0 && HPEffect == 0 && PPEffect == 0 && !choosenItem.curesPoison) {
                            g2.drawString("Dieses Item hat keine Effekte.", (int) (60 * scaleX), (int) (70 * scaleY));
                        }
                        if (choosenItem.curesPoison){
                            g2.drawString("Heilt Vergiftung", (int) (73 * scaleX), (int) (68 * scaleY));
                        }
                    }else if (itemType== Item.ItemType.Waffe){
                        g2.drawString("Schaden: +" + weaponDamage, (int) (56 * scaleX), (int) (68 * scaleY));
                        g2.drawString("Trefferquote: " + weaponPrecision+"%", (int) (56 * scaleX), (int) (73 * scaleY));
                    }else if (itemType== Item.ItemType.Rüstung){
                        g2.drawString("Defensive: +" + armorBars, (int) (56 * scaleX), (int) (68 * scaleY));
                    }else {
                        g2.drawString("Dieses Item hat keine Effekte.", (int) (60 * scaleX), (int) (70 * scaleY));
                    }

                }else if (chosedItemButton==1){
                    if (invOpenCauseDialog){
                        g2.drawString("Willst du dieses Item ",(int) (66 * scaleX), (int) (40 * scaleY));
                        g2.drawString(speaker+" geben?",(int) (66 * scaleX), (int) (44.5 * scaleY));
                    }else if (itemType== Item.ItemType.Essen){
                        g2.drawString("Willst du dieses Item Essen?",(int) (66 * scaleX), (int) (40 * scaleY));
                    }else if (itemType== Item.ItemType.Trinken){
                        g2.drawString("Willst du dieses Item Trinken?",(int) (66 * scaleX), (int) (40 * scaleY));
                    } else if (itemType == Item.ItemType.Rüstung ||itemType == Item.ItemType.Waffe) {
                        if (choosenItem.itemEquipped){
                            g2.drawString("Du scheinst dieses Item",(int) (66 * scaleX), (int) (40 * scaleY));
                            g2.drawString("Bereits ausgerüstet zu haben.",(int) (66 * scaleX), (int) (44.5 * scaleY));
                            g2.drawString("Willst du es wieder ausziehen?",(int) (66 * scaleX), (int) (48.5 * scaleY));
                        }else {
                            g2.drawString("Willst du dieses Item",(int) (66 * scaleX), (int) (40 * scaleY));
                            g2.drawString("Ausrüsten?",(int) (66 * scaleX), (int) (44.5 * scaleY));
                        }

                    }else {g2.drawString("Willst du dieses Item Benutzen?",(int) (66 * scaleX), (int) (40 * scaleY));}

                    if (main.vollbild){
                        g2.setFont(main.extendedHeaderDialogFont);
                    }else {
                        g2.setFont(main.headerDialogFont);

                    }

                    if (itemChooseHovered ==1){
                        g2.setColor(main.themeColor[3].darker().darker());
                        g2.drawString("Ja", (int) (70 * scaleX), (int) (69.5 * scaleY));
                    }else{
                        g2.setColor(main.themeColor[3]);
                        g2.drawString("Ja", (int) (70 * scaleX), (int) (69 * scaleY));
                    }
                    if (itemChooseHovered ==2){
                        g2.setColor(main.themeColor[3].darker().darker());
                        g2.drawString("Nein", (int) (100 * scaleX), (int) (69.5 * scaleY));
                    }else{
                        g2.setColor(main.themeColor[3]);
                        g2.drawString("Nein", (int) (100 * scaleX), (int) (69 * scaleY));
                    }



                }else if (chosedItemButton==2){
                    g2.drawString("Wer soll dieses Item nehmen?",(int) (66 * scaleX), (int) (40 * scaleY));

                    if (main.vollbild){
                        g2.setFont(main.extendedHeaderDialogFont);
                    }else {
                        g2.setFont(main.headerDialogFont);

                    }

                    //Noch Finishen!!
                    if (invRealChoosedCharacter!=0){
                        g2.drawString(main.NameTimmy, (int) (60 * scaleX), (int) (69.5 * scaleY));
                    }
                    if (main.saveGameBooleans[main.loadedGame][1]&&invRealChoosedCharacter!=1){
                        g2.drawString(main.NameFinja, (int) (60 * scaleX), (int) (69.5 * scaleY));
                    }
                    if (main.saveGameBooleans[main.loadedGame][2]&&invRealChoosedCharacter!=2){
                        g2.drawString(main.NameKizo, (int) (60 * scaleX), (int) (69.5 * scaleY));
                    }
                }else if (chosedItemButton==3){
                    g2.drawString("Willst du dieses Item",(int) (66 * scaleX), (int) (40 * scaleY));
                    g2.drawString("Wegwerfen?",(int) (66 * scaleX), (int) (44.5 * scaleY));
                    g2.drawString("(Es verschwindet für immer)",(int) (66 * scaleX), (int) (49 * scaleY));

                    if (main.vollbild){
                        g2.setFont(main.extendedHeaderDialogFont);
                    }else {
                        g2.setFont(main.headerDialogFont);

                    }

                    if (itemChooseHovered ==1){
                        g2.setColor(main.themeColor[3].darker().darker());
                        g2.drawString("Ja", (int) (70 * scaleX), (int) (69.5 * scaleY));
                    }else{
                        g2.setColor(main.themeColor[3]);
                        g2.drawString("Ja", (int) (70 * scaleX), (int) (69 * scaleY));
                    }
                    if (itemChooseHovered ==2){
                        g2.setColor(main.themeColor[3].darker().darker());
                        g2.drawString("Nein", (int) (100 * scaleX), (int) (69.5 * scaleY));
                    }else{
                        g2.setColor(main.themeColor[3]);
                        g2.drawString("Nein", (int) (100 * scaleX), (int) (69 * scaleY));
                    }


                }
                if (main.vollbild){
                    g2.setFont(main.extendedDialogFont);
                }else{g2.setFont(main.dialogFont);}

                if (hoveredButton==1){
                    g2.setColor(main.themeColor[3].darker().darker());

                    if (invOpenCauseDialog){
                        g2.drawString("Wählen", (int) (136.3*scaleX), (int) (35*scaleY));
                    }else if (itemType== Item.ItemType.Essen){
                        g2.drawString("Essen", (int) (136.3*scaleX), (int) (35*scaleY));
                    }else if (itemType== Item.ItemType.Trinken){
                        g2.drawString("Trinken", (int) (136.3*scaleX), (int) (35*scaleY));
                    } else if (itemType == Item.ItemType.Rüstung ||itemType == Item.ItemType.Waffe) {
                        g2.drawString("Equippen", (int) (136.3*scaleX), (int) (35*scaleY));
                    }else {g2.drawString("Benutzen", (int) (136.3*scaleX), (int) (35*scaleY));}
                }else {
                    g2.setColor(main.themeColor[3]);
                    if (invOpenCauseDialog){
                        g2.drawString("Wählen", (int) (136.3*scaleX), (int) (34.5*scaleY));
                    }else if (itemType== Item.ItemType.Essen){
                        g2.drawString("Essen", (int) (136.3*scaleX), (int) (34.5*scaleY));
                    }else if (itemType== Item.ItemType.Trinken){
                        g2.drawString("Trinken", (int) (136.3*scaleX), (int) (34.5*scaleY));
                    } else if (itemType == Item.ItemType.Rüstung ||itemType == Item.ItemType.Waffe) {
                        g2.drawString("Equippen", (int) (136.3*scaleX), (int) (34.5*scaleY));
                    }else {g2.drawString("Benutzen", (int) (136.3*scaleX), (int) (34.5*scaleY));}
                }


                if (hoveredButton==2){
                    g2.setColor(main.themeColor[3].darker().darker());
                    g2.drawString("Wechseln", (int) (136.3*scaleX), (int) (41*scaleY));
                }else {
                    g2.setColor(main.themeColor[3]);
                    g2.drawString("Wechseln", (int) (136.3*scaleX), (int) (40.5*scaleY));
                }


                if (hoveredButton==3){
                    g2.setColor(main.themeColor[3].darker().darker());
                    g2.drawString("Droppen", (int) (136.3*scaleX), (int) (47*scaleY));
                }else {
                    g2.setColor(main.themeColor[3]);
                    g2.drawString("Droppen", (int) (136.3*scaleX), (int) (46.5*scaleY));
                }


                g2.setColor(main.themeColor[3]);











                if (main.vollbild){
                    g2.setFont(main.extendedHeaderDialogFont);
                }else {
                    g2.setFont(main.headerDialogFont);

                }

                g2.drawString(itemName, (int) (56*scaleX), (int) (34.5*scaleY));



            }


            if (!invUI[invChoosedCharacter+1].active){
                canStartAnimation = invChoosedCharacter+1;
            }

            g2.drawImage(pauseOverlaypics[pauseOverlayNum],0,0,getWidth(),getHeight(),null);

        }


        if (main.showFPS){
            g2.setColor(Color.WHITE);
            if (main.vollbild){
                g2.setFont(main.extendedDialogFont);
            }else {
                g2.setFont(main.dialogFont);
            }
            g2.setColor(new Color(0, 0, 0, 174));
            g2.fillRect(0, 0, (int) (main.frameWidth/2.5*main.actualScaleFactor), main.frameHeight/4*main.actualScaleFactor);
            g2.setColor(main.themeColor[3]);
            g2.drawString("RAM: " + main.usedRam + "MB / " + main.maxRam + "MB ( "+ (main.usedRam*100)/main.maxRam +" % )",main.frameWidth/40*main.actualScaleFactor, main.frameHeight/13*main.actualScaleFactor);
            g2.drawString("Framerate: "+main.reachedFPS+" FPS",main.frameWidth/40*main.actualScaleFactor, main.frameHeight/8*main.actualScaleFactor);
            g2.drawString("CPU: "+main.cpuPercent +" %",main.frameWidth/40*main.actualScaleFactor, (int) (main.frameHeight/5.7*main.actualScaleFactor));
            g2.drawString("Stamina: "+player.stamina,main.frameWidth/40*main.actualScaleFactor,(int) (main.frameHeight/4.4*main.actualScaleFactor));
        }



        g2.dispose();


    }




    public void changeCharacterInv(int characterNum){


        if (characterNum ==0){

            for (int i=0;i<invCharacterSprite.length;i++){
                invCharacterSprite[i] = player.invCharacterSprite.get(i);
            }

        }else if (characterNum==1){

            for (int i=0;i<invCharacterSprite.length;i++){
                invCharacterSprite[i] = finja.invCharacterSprite.get(i);
            }

        }else if (characterNum==2){

            for (int i=0;i<invCharacterSprite.length;i++){
                invCharacterSprite[i] = kizo.invCharacterSprite.get(i);
            }

        }



    }




    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mouseX = e.getX();
        int mouseY = e.getY();


        if (main.invIsOpen) {

            int img1Y = invUI[1].y;
            int img2Y = invUI[2].y;
            int img3Y = invUI[3].y;

            if (invChoosedCharacter != 0) {
                actualPixelHeight1 = 0;
            } else {
                actualPixelHeight1 = pixelLength;
            }
            if (invChoosedCharacter != 1) {
                actualPixelHeight2 = 0;
            } else {
                actualPixelHeight2 = pixelLength;
            }
            if (invChoosedCharacter != 2) {
                actualPixelHeight3 = 0;
            } else {
                actualPixelHeight3 = pixelLength;
            }

            if (mouseX < 50 * scaleX || mouseX > 136 * scaleX || mouseY < 26 * scaleY || mouseY > 79 * scaleY) {
                if (itemInfoShown) {
                    if (mouseX < 154 * scaleX && mouseX > 136 * scaleX && mouseY < 50 * scaleY && mouseY > 29 * scaleY) {
                        chosedItemButton = hoveredButton;
                    } else {
                        itemInfoShown = false;
                    }
                }
            }


            if (mouseY < 27 * scaleY && main.invIsOpen) {
                if (mouseX < img1X + CiImgWidth && mouseX > img1X && mouseY < img1Y + CiImgHeight + 2 * actualPixelHeight1 && mouseY > img1Y) {
                    if (invRealChoosedCharacter != 0) {
                        changeCharacterInv(0);
                        itemStartScroll = 0;
                        hoveredItem = -1;
                    }
                    invRealChoosedCharacter = 0;
                    invChoosedCharacter = 0;
                    chosedInvCharacter = player;

                    //Ton abspielen
                } else if (mouseX < img2X + CiImgWidth && mouseX > img2X && mouseY < img2Y + CiImgHeight + 2 * actualPixelHeight2 && mouseY > img2Y && main.saveGameBooleans[main.loadedGame][1]) {
                    if (invRealChoosedCharacter != 1) {
                        changeCharacterInv(1);
                        itemStartScroll = 0;
                        hoveredItem = -1;
                    }
                    invRealChoosedCharacter = 1;
                    invChoosedCharacter = 1;
                    chosedInvCharacter = finja;

                    //Ton abspielen
                } else if (mouseX < img3X + CiImgWidth && mouseX > img3X && mouseY < img3Y + CiImgHeight + 2 * actualPixelHeight3 && mouseY > img3Y && main.saveGameBooleans[main.loadedGame][2]) {
                    if (invRealChoosedCharacter != 2) {
                        changeCharacterInv(2);
                        itemStartScroll = 0;
                        hoveredItem = -1;
                    }
                    invRealChoosedCharacter = 2;
                    invChoosedCharacter = 2;
                    chosedInvCharacter = kizo;


                    //Ton abspielen
                }
            } else if (mouseX < 50 * scaleX && mouseX > 6 * scaleX && mouseY < 84 * scaleY && mouseY > 37 * scaleY) {

                if (isItem()) {
                    int chosenItem = chosedInvCharacter.inventory.get(hoveredItem + itemStartScroll);
                    itemInfoShown = true;
                    itemName = main.items.get(chosenItem).name;
                    itemDescribe = main.items.get(chosenItem).describe;
                    itemIcon = main.items.get(chosenItem).itemIcon;
                    speedEffect = main.items.get(chosenItem).speedEffect;
                    speedDuration = main.items.get(chosenItem).speedDuration;
                    HPEffect = main.items.get(chosenItem).HPEffect;
                    PPEffect = main.items.get(chosenItem).PPEffect;
                    weaponDamage = main.items.get(chosenItem).weaponDamage;
                    itemEquipped = main.items.get(chosenItem).itemEquipped;
                    armorBars = main.items.get(chosenItem).armorBars;
                    itemType = main.items.get(chosenItem).itemType;
                    itemWorth = main.items.get(chosenItem).worth;
                    weaponPrecision = main.items.get(chosenItem).precision;
                    itemNum = hoveredItem + itemStartScroll;
                    choosenItem = main.items.get(chosenItem);
                    chosedItemButton = 0;
                }


            } else if (mouseX < 78 * scaleX && mouseX > 68 * scaleX && mouseY < 71 * scaleY && mouseY > 64 * scaleY && itemInfoShown) {
                if (chosedItemButton == 1) {
                    useItem();
                }
                if (chosedItemButton == 3) {
                    dropItem();
                }
            } else if (mouseX < 111 * scaleX && mouseX > 99 * scaleX && mouseY < 71 * scaleY && mouseY > 64 * scaleY && itemInfoShown) {
                if (chosedItemButton == 1 || chosedItemButton == 3) {
                    abbrechen();
                }

            }

        }else if (main.gameIsPaused){
            if (mouseX<38*scaleX&&mouseX>22*scaleX&&mouseY<34*scaleY&&mouseY>18*scaleY){
                main.printOnDebug("Open Statistics!");
                leaveButton = "Verlassen";
                areyousure = false;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<38*scaleY&&mouseY>26*scaleY){
                main.gameIsPaused = false;
                leaveButton = "Verlassen";
                areyousure = false;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<58*scaleY&&mouseY>46*scaleY){
                main.setGameStatus(Main.GameState.SETTINGS);
                leaveButton = "Verlassen";
                areyousure = false;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<78*scaleY&&mouseY>66*scaleY){
                if (!areyousure){
                    leaveButton = "Sicher?";
                    areyousure = true;
                }else {
                    leaveButton = "Verlassen";
                    areyousure = false;
                    main.leaveGame();
                }

            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<34*scaleY&&mouseY>18*scaleY){
                hoveredPauseButton = 5;
                leaveButton = "Verlassen";
                areyousure = false;
            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<56*scaleY&&mouseY>40*scaleY&&main.saveGameBooleans[main.loadedGame][2]){
                hoveredPauseButton = 6;
                leaveButton = "Verlassen";
                areyousure = false;
            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<78*scaleY&&mouseY>62*scaleY&&main.saveGameBooleans[main.loadedGame][2]){
                hoveredPauseButton = 7;
                leaveButton = "Verlassen";
                areyousure = false;
            }else {
                leaveButton = "Verlassen";
                areyousure = false;
            }
        }else if (main.onInteraction){

            if (isAnswerTime){
                for (DiaButton button: Dialogbuttons){
                    if (mouseX>8*scaleX+button.x*main.actualScaleFactor&&mouseX<8*scaleX+button.x*main.actualScaleFactor+button.width*main.actualScaleFactor&&mouseY>button.y&&mouseY<button.y+button.height*main.actualScaleFactor){
                        dialogLines.removeLast();
                        addDialogEffect(button.effect);
                        skipDialog = false;
                        finishDialog = false;
                    }
                }
            }else {
                skipDialog = true;
                finishDialog = true;
            }


        }


    }
    private long lastSpriteChange =0;
    int currentFrameNum = 0;

    public int changeSprite(int frameSize){
        if (System.currentTimeMillis()-lastSpriteChange>260){
            if (currentFrameNum+1<frameSize) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }

        return currentFrameNum;
    }

    public void addDialogEffect(String effect){

        if (effect.equals("openInv")){
            main.onInteraction = false;
            invOpenCauseDialog = true;
            cineBarShow = false;
            checked = false;
            dialogCD = System.currentTimeMillis();
            main.invIsOpen = true;
        }else {
            Main.eventManager.addAnswer(effect);
        }

        isAnswerTime = false;
        canContinue = true;
    }

    public void useItem(){
        Item item = main.items.get(chosedInvCharacter.inventory.get(itemNum));


        if (invOpenCauseDialog){
            if (!item.name.equals("Cheese Burger")){
                primaryDialogList.addFirst("[Inventar;openInv]");
                primaryDialogList.addFirst("Tut mir leid, ich kann damit nichts anfangen.");

            }
            main.onInteraction = true;
            invOpenCauseDialog = false;
            cineBarShow = true;
            checked = false;
            main.invIsOpen = false;
        }else if (itemType == Item.ItemType.Essen|| itemType == Item.ItemType.Trinken){
            chosedInvCharacter.targetHP = Math.min(chosedInvCharacter.targetHP+item.HPEffect,chosedInvCharacter.maxHP);
            chosedInvCharacter.targetPP = Math.min(chosedInvCharacter.targetPP+item.PPEffect,chosedInvCharacter.maxPP);
            chosedInvCharacter.Speed += item.speedEffect;
            chosedInvCharacter.speedDuration += item.speedDuration;

            chosedInvCharacter.removeFromInventory(itemNum);
            itemStartScroll = Math.max(itemStartScroll-1,0);
        }else if (itemType == Item.ItemType.Waffe){
            chosedInvCharacter.equipWeapon(item);
        }else if (itemType == Item.ItemType.Rüstung){
            chosedInvCharacter.equipArmor(item);
        }



        itemInfoShown = false;


    }
    public void dropItem(){
        chosedInvCharacter.removeFromInventory(itemNum);
        itemInfoShown = false;
        itemStartScroll = Math.max(itemStartScroll-1,0);
        //dropSound abspielen
    }
    public void abbrechen() {
        chosedItemButton =0;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }




    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int mouseX = e.getX();
        int mouseY = e.getY();



        if (main.invIsOpen){



            int img1Y = invUI[1].y;
            int img2Y = invUI[2].y;
            int img3Y = invUI[3].y;

            if (invChoosedCharacter !=0){
                actualPixelHeight = 0;
            }else {
                actualPixelHeight = pixelLength;
            }
            itemChooseHovered =0;

            if (mouseY<27*scaleY) {
                if (mouseX < img1X + CiImgWidth && mouseX > img1X && mouseY < img1Y + CiImgHeight && mouseY > img1Y) {
                    invChoosedCharacter = 0;
                    //Ton abspielen
                } else if (mouseX < img2X + CiImgWidth && mouseX > img2X && mouseY < img2Y + CiImgHeight && mouseY > img2Y && main.saveGameBooleans[main.loadedGame][1]) {
                    invChoosedCharacter = 1;
                    //Ton abspielen
                } else if (mouseX < img3X + CiImgWidth && mouseX > img3X && mouseY < img3Y + CiImgHeight && mouseY > img3Y && main.saveGameBooleans[main.loadedGame][2]) {
                    invChoosedCharacter = 2;
                    //Ton abspielen
                } else if (mouseY > img1Y + CiImgHeight + (2 * actualPixelHeight)) {
                    invChoosedCharacter = invRealChoosedCharacter;
                }

            }else if (mouseX<50*scaleX&&mouseX>6*scaleX&&mouseY<84*scaleY&&mouseY>37*scaleY){
                int pixelY = (int) ((int) (mouseY/scaleY)-((invItemY-5*scaleY)/scaleY));
                hoveredItem = (int) (pixelY/5.9);
            }else if (mouseX<154*scaleX&&mouseX>136*scaleX&&mouseY<50*scaleY&&mouseY>29*scaleY&&itemInfoShown){

                int pixelY = (int) (mouseY/scaleY-24);
                hoveredButton = Math.min(3,Math.max(1,pixelY/6));
            }else if(mouseX<133*scaleX&&mouseX>53*scaleX&&mouseY<76*scaleY&&mouseY>59*scaleY&&chosedItemButton!=0&&itemInfoShown){
                if (chosedItemButton!=2){
                    if (mouseX<78*scaleX&&mouseX>68*scaleX&&mouseY<71*scaleY&&mouseY>64*scaleY){
                        itemChooseHovered =1;
                    }else if (mouseX<111*scaleX&&mouseX>99*scaleX&&mouseY<71*scaleY&&mouseY>64*scaleY){
                        itemChooseHovered =2;
                    }

                }else if(chosedItemButton==1) {

                }

            }



            if (mouseX>154*scaleX||mouseX<136*scaleX||mouseY>50*scaleY||mouseY<29*scaleY){hoveredButton=-1;}
            if(mouseX>50*scaleX||mouseX<6*scaleX||mouseY>84*scaleY||mouseY<37*scaleY){hoveredItem = -1;}
        }else if (main.gameIsPaused){

            if (mouseX<38*scaleX&&mouseX>22*scaleX&&mouseY<34*scaleY&&mouseY>18*scaleY){
                hoveredPauseButton = 1;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<38*scaleY&&mouseY>26*scaleY){
                hoveredPauseButton = 2;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<58*scaleY&&mouseY>46*scaleY){
                hoveredPauseButton = 3;
            }else if (mouseX<103*scaleX&&mouseX>57*scaleX&&mouseY<78*scaleY&&mouseY>66*scaleY){
                hoveredPauseButton = 4;
            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<34*scaleY&&mouseY>18*scaleY){
                hoveredPauseButton = 5;
            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<56*scaleY&&mouseY>40*scaleY&&main.saveGameBooleans[main.loadedGame][2]){
                hoveredPauseButton = 6;
            }else if (mouseX<138*scaleX&&mouseX>122*scaleX&&mouseY<78*scaleY&&mouseY>62*scaleY&&main.saveGameBooleans[main.loadedGame][2]){
                hoveredPauseButton = 7;
            }else {
                hoveredPauseButton = 0;
            }


        }else if (main.onInteraction){
            if (isAnswerTime){
                boolean anythingHovered = false;
                for (DiaButton button: Dialogbuttons){
                    if (mouseX>8*scaleX+button.x*main.actualScaleFactor&&mouseX<8*scaleX+button.x*main.actualScaleFactor+button.width*main.actualScaleFactor&&mouseY>button.y&&mouseY<button.y+button.height*main.actualScaleFactor){
                        hoveredDiaButton = Dialogbuttons.indexOf(button);
                        anythingHovered = true;
                    }
                }
                if (!anythingHovered){
                    hoveredDiaButton = -1;
                }
            }

        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int wheelDir = e.getWheelRotation();

        if (main.invIsOpen){
            if (mouseX<51*scaleX&&mouseX>5*scaleX&&mouseY<86*scaleY&&mouseY>36*scaleY){
                if (itemStartScroll + wheelDir >=0&&itemStartScroll + wheelDir+8 <=chosedInvCharacter.usedInvSpace ){
                    itemStartScroll += wheelDir;
                }
            }


        }
    }

    public boolean isItem(){


        int currentItemNum = 0;
        for (int i = itemStartScroll; i<chosedInvCharacter.inventory.size(); i++) {

            if (chosedInvCharacter.inventory.get(i) == 0 || chosedInvCharacter.inventory.get(i) == null) {
                continue;
            }
            if (i>itemStartScroll+7){
                break;
            }
            if (hoveredItem == currentItemNum){
                return true;
            }
            int item = chosedInvCharacter.inventory.get(i);
            itemName = main.items.get(item).name;

            currentItemNum++;

        }

        return false;
    }




    public void repaintEntitys(Graphics2D g2) {


        for (Entity npc : main.npcList) {
            if (npc != null) {

                int tileSize = npc.framesDown.getFirst().getHeight() * main.tileScaleFaktor;

                double deltaX = npc.worldX - main.playerWorldX;
                double deltaY = npc.worldY - main.playerWorldY;

                int screenX = (int) (deltaX * main.tileScaleFaktor) + (int) main.playerScreenX;
                int screenY = (int) (deltaY * main.tileScaleFaktor) + (int) main.playerScreenY;

                if (npc.worldX + tileSize > main.playerWorldX - main.playerScreenX &&
                        npc.worldX - tileSize < main.playerWorldX + main.playerScreenX &&
                        npc.worldY + tileSize > main.playerWorldY - (main.playerScreenY * 1.2) &&
                        npc.worldY - tileSize < main.playerWorldY + (main.playerScreenY * 1.2)) {

                    BufferedImage image = npc.getImage(npc.lookDirection);

                    if (image != null) {

                        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
                        if (npc.leader!=null&&npc.leader.isSweating&&main.currentSweatSprite!=null){
                            g2.drawImage(main.currentSweatSprite, screenX, screenY, tileSize, tileSize, null);
                        }
                    } else {
                        main.printErrOnDebug("Entity Image not Found!");
                    }
                }
            }
        }
    }


    ArrayList<String> primaryDialogList = new ArrayList<>();
    String speaker = "";
    public void addDialog(String speaker, java.util.List<String> dialogueLines) {
        for (String line : dialogueLines) {
            primaryDialogList.add(Main.eventManager.replaceVariables(line));
        }
        this.speaker = speaker;
    }

    boolean canContinue = true;
    long endDiaCD = 0;
    boolean skipDialog=false;
    boolean finishDialog=false;
    long lastInteractionBreak =0;
    public void checkDialog() {

        if (main.onInteraction) {

            if (transform.keyTypeIsFinished&&!primaryDialogList.isEmpty()&&canContinue) {
                String text = primaryDialogList.getFirst();
                if (main.vollbild) {
                    g2.setFont(main.extendedDialogFont);
                } else {
                    g2.setFont(main.dialogFont);
                }

                if (primaryDialogList.getFirst().contains("[")){
                    hoveredDiaButton = -1;
                    canContinue = false;
                    int lastSlashIndex =-1;
                    int lastSemikolIndex = -1;
                    ArrayList <String> answers = new ArrayList<>();
                    ArrayList <String> effects = new ArrayList<>();
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == '[') {
                            lastSlashIndex = i;
                        }else if (text.charAt(i) == ']') {
                            effects.add(text.substring(lastSemikolIndex + 1, i));

                        }else if (text.charAt(i) == ';'&&lastSlashIndex != -1) {
                            answers.add(text.substring(lastSlashIndex+1,i));
                            lastSemikolIndex = i;
                        }
                    }
                    Dialogbuttons =transform.setUpDialogAnswers(g2,(int) (146 * scaleX), answers,effects);
                    dialogLines.add("/answer");
                }else {
                    transform.startTypewrtiting(text);
                }

                primaryDialogList.removeFirst();
            }

            if (finishDialog) {
                if (primaryDialogList.isEmpty()&&transform.keyTypeIsFinished&&System.currentTimeMillis()-endDiaCD>350) {
                    cineBarShow=false;
                    checked = false;
                    dialogCD = System.currentTimeMillis();
                    main.onInteraction=false;
                    finishDialog=false;
                    dialogLines.clear();
                    lastInteractionBreak = System.currentTimeMillis();
                    return;
                }else {
                    finishDialog=false;
                }

            }

            if (Main.gameManager.manualKeyInput) {
                Main.gameManager.manualKeyInput(false);
                main.upPressed = false;
                main.downPressed = false;
                main.leftPressed = false;
                main.rightPressed = false;
                main.sprinting = false;
            }





        }else {
            if (!Main.gameManager.manualKeyInput) {
                Main.gameManager.manualKeyInput(true);
            }
            if (skipDialog){
                skipDialog=false;
            }
        }


    }


    public long dialogCD = 0;
    public boolean checked = true;
    public ArrayList<String> dialogLines = new ArrayList<>();

    public void showCineBar(){

        int widthUp = (int) (cinePicUp.image.getWidth()*scaleX);
        int heightUp = (int) (cinePicUp.image.getHeight()*scaleY);
        int heightDown = (int) (cinePicDown.image.getHeight()*scaleY);
        int heightUpBG = (int) (cinePicUpBG.image.getHeight()*scaleY);
        int heightDownBG = (int) (cinePicDownBG.image.getHeight()*scaleY);
        int textX = (int) (8*scaleX);
        int textY = 0;
        if (cineBarShow){
            if (!checked){
                cinePicUp.y= (int) (-3*scaleY);
                cinePicDown.y= 0;
                checked = true;
            }


            transform.changePosition(cinePicUp,0, (int) (-3*scaleY),0, (int) (12*scaleY),4.5);
            transform.changePosition(cinePicDown,0,0,0, (int) (35*scaleY),10);

            g2.drawImage(cinePicUpBG.image,0, (int) (cinePicUp.y-12*scaleY),widthUp,heightUpBG,null);
            g2.drawImage(cinePicDownBG.image,0, (int) (90*scaleY-cinePicDown.y),widthUp,heightDownBG,null);
            g2.drawImage(cinePicUp.image,0,cinePicUp.y,widthUp,heightUp,null);
            g2.drawImage(cinePicDown.image,0, (int) (90*scaleY-cinePicDown.y),widthUp,heightDown,null);





            textY = (int) (90*scaleY-cinePicDown.y+17*scaleY);


        }else {
            if (!checked){
                cinePicUp.y= (int) (78*scaleY);
                cinePicDown.y= (int) (55*scaleY);
                checked = true;
            }

            if ((System.currentTimeMillis()-dialogCD)/1000<1){
                transform.changePosition(cinePicUp,0, (int) (90*scaleY-cinePicUp.y),0, (int) (93*scaleY),9);
                transform.changePosition(cinePicDown,0,(int) (55*scaleY),0, (int) (90*scaleY),13);

                g2.drawImage(cinePicUpBG.image,0,(int) ((90*scaleY-cinePicUp.y)-12*scaleY),widthUp,heightUpBG,null);
                g2.drawImage(cinePicDownBG.image,0,cinePicDown.y,widthUp,heightDownBG,null);
                g2.drawImage(cinePicUp.image,0,(int) (90*scaleY-cinePicUp.y),widthUp,heightUp,null);
                g2.drawImage(cinePicDown.image,0, cinePicDown.y,widthUp,heightDown,null);
            }


            textY = (int) (cinePicDown.y*main.actualScaleFactor+17*scaleY);





        }

        if (main.onInteraction||System.currentTimeMillis()-lastInteractionBreak<1000) {
            g2.setColor(main.themeColor[3]);

            if (main.vollbild) {
                g2.setFont(main.extendedHeaderDialogFont);
            } else {
                g2.setFont(main.headerDialogFont);

            }

            transform.drawShortedText(g2, speaker, textX, (int) (textY - 9 * scaleY), (int) (25 * scaleX));

            if (main.vollbild) {
                g2.setFont(main.extendedDialogFont);
            } else {
                g2.setFont(main.dialogFont);
            }
            int lineHeight = (int) (5 * scaleY);
            int currentSlot = 4;

            for (int i = dialogLines.size() - 1; i >= 0; i--) {

                String currentText = "";
                if (dialogLines.get(i).equals("/answer")){
                    if (!canContinue) {
                        isAnswerTime = true;
                    }

                    currentSlot -= 1;
                    int drawY = textY + (currentSlot * lineHeight);

                    for (DiaButton button: Dialogbuttons){
                        button.y = drawY-button.height;

                        if (hoveredDiaButton == Dialogbuttons.indexOf(button)){
                            g2.setColor(main.themeColor[2].darker());
                            g2.drawString(button.name, textX+button.x*main.actualScaleFactor, (int) (drawY+scaleY));
                        }else {
                            g2.setColor(main.themeColor[2]);
                            g2.drawString(button.name, textX+button.x*main.actualScaleFactor, drawY);
                        }

                    }

                    g2.setColor(main.themeColor[3]);
                    continue;
                }else {
                    currentText = "• " + dialogLines.get(i);
                }






                int requiredLines = transform.checkWrappedText(g2, currentText, textX, textY, (int) (146 * scaleX));


                if (currentSlot - requiredLines < 0) {
                    if (currentSlot <= 0) {
                        break;
                    }
                    ArrayList<String> lines = transform.getWrappedTextLines(g2, currentText, textX, textY, (int) (146 * scaleX));
                    int linesToDraw = currentSlot;
                    int startIndex = lines.size() - linesToDraw;
                    if (startIndex < 0) startIndex = 0;
                    for (int k = startIndex; k < lines.size(); k++) {
                        String line = lines.get(k);
                        int slotIndex = k - startIndex;
                        int drawY = textY + (slotIndex * lineHeight);
                        transform.drawWrappedText(g2, line, textX, drawY, (int) (146 * scaleX));
                    }
                    break;
                }

                currentSlot -= requiredLines;
                int drawY = textY + (currentSlot * lineHeight);
                transform.drawWrappedText(g2, currentText, textX, drawY, (int) (146 * scaleX));
            }
        }

    }

}
