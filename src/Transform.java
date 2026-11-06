import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.util.ArrayList;

public class Transform{

private final Main main;



    public Transform(Main main) {
    this.main = main;
    }

    boolean TimmyIsActive = false;

    long startTime = 0;
    long targetTime = 0;
    int i =0;
    int[] durationList;
    String[] walkList;
    int[] sprintlist;
    boolean newKey = false;




    public void changePosition(Picture picture, int startX, int startY, int targetX, int targetY, double speed) {

        if (System.currentTimeMillis() - picture.lastFrameTime > (4*1000/main.reachedFPS)) {
            picture.active = false;
            picture.resetTriggerPos();
        }

        if (!picture.active && !picture.triggered) {
            picture.x = startX;
            picture.y = startY;
            picture.active = true;
            picture.triggered = true;
        }

        double dx = targetX - picture.x;
        double dy = targetY - picture.y;
        double distance = Math.hypot(dx, dy);

        speed *= 100;
        speed *= (1.0/main.reachedFPS);
        if (main.vollbild){speed *= main.scaleFactor;}


        if (distance <= speed) {
            picture.x = targetX;
            picture.y = targetY;
            picture.active = false;
        } else {
            picture.x += (int) ((dx / distance) * speed);
            picture.y += (int) ((dy / distance) * speed);
        }

        if (picture.x > targetX || picture.y > targetY) {
            picture.x = targetX;
            picture.y = targetY;
        }

        picture.lastFrameTime = System.currentTimeMillis();


    }
    public void changeTransparency(Picture picture, int startA, int targetA, double speed) {

        if (System.currentTimeMillis() - picture.lastAlphaTime > (4*1000/main.reachedFPS)) {
            picture.activeAlpha = false;
            targetA = Math.min(255, targetA);
            picture.resetTriggerAlpha();
        }

        if (!picture.activeAlpha&& !picture.triggeredAlpha) {
            picture.Alpha = startA;
            picture.activeAlpha = true;
            picture.triggeredAlpha = true;
        }

        double distance = targetA - picture.Alpha;

        speed *= 100;
        speed *= (1.0/main.reachedFPS);

        if (distance <= speed) {
            picture.Alpha = targetA;
            picture.activeAlpha = false;
        }else{
            picture.Alpha += (int) speed;
        }
        if (picture.Alpha > targetA) {
            picture.Alpha = targetA;
        }

        picture.lastAlphaTime = System.currentTimeMillis();


    }

    public void changeSize(Picture picture, int startHeight, int startWidth, int targetHeight, int targetWidth, double speed) {

        if (System.currentTimeMillis() - picture.lastFrameSizeTime > (4*1000/main.reachedFPS)) {
            picture.activeSize = false;
            picture.resetTriggerSize();
        }

        if (!picture.activeSize && !picture.triggeredSize) {
            picture.height = startHeight;
            picture.width = startWidth;
            picture.activeSize = true;
            picture.triggeredSize = true;

        }

        double dh = targetHeight - picture.height;
        double dw = targetWidth - picture.width;
        double distance = Math.hypot(dw, dh);

        speed *= 100;
        speed *= (1.0/main.reachedFPS);
        if (main.vollbild){speed *= main.scaleFactor;}

        if (distance <= speed) {
            picture.height = targetHeight;
            picture.width = targetWidth;
            picture.activeSize = false;
        }else{
            picture.width += (int) ((dw / distance) * speed);
            picture.height += (int) ((dh / distance) * speed);
        }
        if (picture.height > targetHeight || picture.width > targetWidth) {
            picture.height = targetHeight;
            picture.width = targetWidth;
        }

        picture.lastFrameSizeTime = System.currentTimeMillis();



    }


    public ArrayList<DiaButton> setUpDialogAnswers(Graphics2D g2, int maxWidth, ArrayList<String> answers, ArrayList<String> effects){
        maxWidth/=main.actualScaleFactor;
        ArrayList<DiaButton> buttons = new ArrayList<>();
        FontMetrics fm = g2.getFontMetrics();
        int height = fm.getHeight()/main.actualScaleFactor;

        for (int i = 0; i < answers.size(); i++) {
            DiaButton button = new DiaButton();
            button.width = fm.stringWidth(answers.get(i))/main.actualScaleFactor;
            button.effect = effects.get(i);
            button.name = answers.get(i);
            button.height = height;
            buttons.add(button);
        }
        int buttonsWitdh =0;
        for (DiaButton button : buttons) {
            buttonsWitdh += button.width;
        }
        int gap = 0;
        if (buttonsWitdh < maxWidth) {
            int difference = maxWidth - buttonsWitdh;
            gap = difference / buttons.size();
        }else {
            main.printErrOnDebug("Button Overload bei der Dialog Logik!");
        }
        int currentWitdh = 0;
        for (DiaButton button : buttons) {
            if (currentWitdh == 0){
                button.x = gap/2;
            }else {
                button.x = currentWitdh + gap;
            }

            currentWitdh += button.x;
        }


        return buttons;
    }



    private Timer typewriterTimer;
    public boolean keyTypeIsFinished = true;
    private int delayMs;
    private int targetDelay = 1200;

    public void startTypewrtiting(String text) {
        keyTypeIsFinished = false;

        if (typewriterTimer != null && typewriterTimer.isRunning()) {
            typewriterTimer.stop();
        }

        final int[] currentCharIndex = {0};
        delayMs = (targetDelay/2) / Math.max(text.length(),1);
        Main.gameDisplay.dialogLines.add("");

        typewriterTimer = new Timer(delayMs, e -> {
            int lastIndex = Main.gameDisplay.dialogLines.size() - 1;

            if (lastIndex >= 0 && currentCharIndex[0] <= text.length()) {
                String partText = text.substring(0, currentCharIndex[0]);
                if (Main.gameDisplay.skipDialog&&System.currentTimeMillis()-Main.gameDisplay.endDiaCD>200) {
                    Main.gameDisplay.skipDialog = false;
                    Main.gameDisplay.dialogLines.set(lastIndex,text);
                    typewriterTimer.stop();
                    keyTypeIsFinished = true;
                    Main.gameDisplay.endDiaCD = System.currentTimeMillis();
                    return;
                }
                Main.gameDisplay.dialogLines.set(lastIndex, partText);
                currentCharIndex[0]++;
            } else {
                typewriterTimer.stop();
                keyTypeIsFinished = true;
                Main.gameDisplay.endDiaCD = System.currentTimeMillis();
            }
        });

        typewriterTimer.start();
    }

    public void drawShortedText(Graphics2D g2, String text, int x,int y, int maxWidth){
        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i<text.length(); i++){
            String cuttetText = text.substring(0, i);
            int testWidth = fm.stringWidth(cuttetText);
            if (testWidth > maxWidth){
                text= cuttetText.substring(0,Math.max(0,i)).concat("..");
                break;
            }


        }
        g2.drawString(text,x,y);


    }

    public ArrayList<String> getWrappedTextLines(Graphics2D g2, String text, int x, int y, int maxWidth){
        ArrayList<String> returnLines = new ArrayList<>();

        FontMetrics fm = g2.getFontMetrics();

        int lineHeight = fm.getHeight();

        int lineNum =0;
        String[] lines = text.split("\n");

        for (String line : lines) {
            String[] words = line.split(" ");
            String currentLine = "";

            for (String word : words) {
                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
                int testWidth = fm.stringWidth(testLine);

                if (testWidth > maxWidth && !currentLine.isEmpty()) {
                    y += lineHeight;
                    lineNum ++;
                    returnLines.add(currentLine);
                    currentLine = word;
                } else {
                    currentLine = testLine;
                }
            }

            lineNum ++;
            y += lineHeight;
            returnLines.add(currentLine);
        }

        return returnLines;
    }


    public void drawWrappedText(Graphics2D g2, String text, int x, int y, int maxWidth) {
        FontMetrics fm = g2.getFontMetrics();

        int lineHeight = fm.getHeight();

        String[] lines = text.split("\n");

        for (String line : lines) {
            String[] words = line.split(" ");
            String currentLine = "";

            for (String word : words) {
                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
                int testWidth = fm.stringWidth(testLine);

                if (testWidth > maxWidth && !currentLine.isEmpty()) {
                    g2.drawString(currentLine, x, y);
                    y += lineHeight;
                    currentLine = word;
                } else {
                    currentLine = testLine;
                }
            }

            g2.drawString(currentLine, x, y);
            y += lineHeight;
        }
    }

    public int checkWrappedText(Graphics2D g2, String text, int x, int y, int maxWidth) {

        FontMetrics fm = g2.getFontMetrics();

        int lineHeight = fm.getHeight();

        int lineNum =0;
        String[] lines = text.split("\n");

        for (String line : lines) {
            String[] words = line.split(" ");
            String currentLine = "";

            for (String word : words) {
                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
                int testWidth = fm.stringWidth(testLine);

                if (testWidth > maxWidth && !currentLine.isEmpty()) {
                    y += lineHeight;
                    lineNum ++;
                    currentLine = word;
                } else {
                    currentLine = testLine;
                }
            }

            lineNum ++;
            y += lineHeight;
        }

        return lineNum;


    }





    public void moveTimmy(String[] walkList, int[] durationList, int[] sprintlist){


        TimmyIsActive = true;
        main.infstamina = true;
        main.gameIsPaused = false;

        Main.gameManager.manualKeyInput(false);

        this.durationList = durationList;
        this.walkList = walkList;
        this.sprintlist = sprintlist;
        newKey = true;
        i =0;


    }



    public void updateTimmy() {

        if (TimmyIsActive) {
            if(i<walkList.length){

                if (newKey){
                    main.upPressed = false;
                    main.downPressed = false;
                    main.leftPressed = false;
                    main.rightPressed = false;
                    main.sprinting = false;


                    for (int x =0; x<walkList[i].length(); x++){
                        char ch = walkList[i].charAt(x);

                        switch (ch){


                            case 'w' -> {
                                main.upPressed = true;
                            }
                            case  'a' -> {
                                main.leftPressed = true;
                            }
                            case  's' -> {
                                main.downPressed = true;
                            }
                            case  'd' -> {
                                main.rightPressed = true;
                            }
                        }

                        if (sprintlist == null){
                            main.sprinting = false;
                        }else if (sprintlist[i]==1){
                            main.sprinting = true;
                        }
                        startTime = System.currentTimeMillis();
                        targetTime = durationList[i];
                        newKey = false;

                    }


                }

                main.printOnDebug("warte "+((targetTime+startTime)-System.currentTimeMillis())+" ms. Key: "+ walkList[i]);

                if(System.currentTimeMillis() >= targetTime+startTime){
                    i++;
                    newKey = true;
                }

            }else {

                main.upPressed = false;
                main.downPressed = false;
                main.leftPressed = false;
                main.rightPressed = false;
                main.sprinting = false;

                Main.gameManager.manualKeyInput(true);

                main.infstamina = false;
                TimmyIsActive = false;

            }


        }

    }

}
