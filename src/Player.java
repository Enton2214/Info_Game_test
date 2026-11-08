import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    private final Main main;

    ArrayList<BufferedImage> sprintUpFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesUp = new ArrayList<>();

    ArrayList<BufferedImage> sprintUpLeftFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesUpLeft = new ArrayList<>();

    ArrayList<BufferedImage> sprintLeftFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesLeft = new ArrayList<>();

    ArrayList<BufferedImage> sprintDownLeftFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesDownLeft = new ArrayList<>();

    ArrayList<BufferedImage> sprintDownFrames = new ArrayList<>();
    public ArrayList<BufferedImage> framesDown = new ArrayList<>();

    ArrayList<BufferedImage> sprintDownRightFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesDownRight = new ArrayList<>();

    ArrayList<BufferedImage> sprintRightFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesRight = new ArrayList<>();

    ArrayList<BufferedImage> sprintUpRightFrames = new ArrayList<>();
    ArrayList<BufferedImage> framesUpRight = new ArrayList<>();

    public BufferedImage peaceFrame;


    private int lastSteppedCol = -1;
    private int lastSteppedRow = -1;
    private Entity lastSteppedEntity = null;

    double playerDirectionX = 0;
    double playerDirectionY = 0;
    double playerSpeed = 0;
    public boolean isMoving = false;
    long lastSpriteChange = 0;
    int currentFrameNum = 0;


    boolean isSprinting = false;
    long lastSprintStartTime = 0;
    public int maxStamina = 10;
    public int stamina = 10;
    long lastStaminaReg = 0;

    PlayerLookDirection playerLookDirection = PlayerLookDirection.down;

    public enum PlayerLookDirection {
        up,
        upLeft,
        left,
        downLeft,
        down,
        downRight,
        right,
        upRight,
    }


    public Player(Main main){
        super(true,10,Type.MainCharacter);
        this.main = main;
        type = Type.MainCharacter;
        loadPlayerFrames();
        main.playerHitBox = new Rectangle(3,16,18,8);


        addToInventory(1);
        addToInventory(2);
        addToInventory(3);
        addToInventory(4);
        addToInventory(5);
        addToInventory(6);
        addToInventory(7);
        addToInventory(8);
        addToInventory(9);
        addToInventory(20);

    }



    public void loadPlayerFrames(){


        invCharacterSprite.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_1.png"));
        invCharacterSprite.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_2.png"));
        invCharacterSprite.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_3.png"));
        invCharacterSprite.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_4.png"));


        framesUp.add(0,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_up_1.png"));
        framesUp.add(1,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_up_2.png"));
        framesUp.add(2,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_up_3.png"));
        framesUp.add(3,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_up_4.png"));

        sprintUpFrames.add(0,main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_up_1.png"));
        sprintUpFrames.add(1,main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_up_2.png"));


        framesUpLeft.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upLeft_1.png"));
        framesUpLeft.add(1,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upLeft_2.png"));
        framesUpLeft.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upLeft_3.png"));
        framesUpLeft.add(3,main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upLeft_4.png"));

        sprintUpLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_upLeft_1.png"));
        sprintUpLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_upLeft_2.png"));


        framesLeft.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_left_1.png"));
        framesLeft.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_left_2.png"));
        framesLeft.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_left_3.png"));
        framesLeft.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_left_4.png"));

        sprintLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_left_1.png"));
        sprintLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_left_2.png"));


        framesDownLeft.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downLeft_1.png"));
        framesDownLeft.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downLeft_2.png"));
        framesDownLeft.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downLeft_3.png"));
        framesDownLeft.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downLeft_4.png"));

        sprintDownLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_downLeft_1.png"));
        sprintDownLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_downLeft_2.png"));


        framesDown.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_1.png"));
        framesDown.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_2.png"));
        framesDown.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_3.png"));
        framesDown.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_down_4.png"));

        sprintDownFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_down_1.png"));
        sprintDownFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_down_2.png"));


        framesDownRight.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downRight_1.png"));
        framesDownRight.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downRight_2.png"));
        framesDownRight.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downRight_3.png"));
        framesDownRight.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_downRight_4.png"));

        sprintDownRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_downRight_1.png"));
        sprintDownRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_downRight_2.png"));


        framesRight.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_right_1.png"));
        framesRight.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_right_2.png"));
        framesRight.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_right_3.png"));
        framesRight.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_right_4.png"));

        sprintRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_right_1.png"));
        sprintRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_right_2.png"));


        framesUpRight.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upRight_1.png"));
        framesUpRight.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upRight_2.png"));
        framesUpRight.add(2, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upRight_3.png"));
        framesUpRight.add(3, main.loadExternalImage("Grafik/Sprites/Timmy/Timmy_upRight_4.png"));

        sprintUpRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_upRight_1.png"));
        sprintUpRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Timmy/Sprint/Timmy_upRight_2.png"));

        peaceFrame = main.loadExternalImage("Grafik/Sprites/Timmy/Peace.png");




    }

    public void update(){

        if (isSprinting){

            if (((System.currentTimeMillis()-lastSprintStartTime)/1000>=1)&&isMoving){
                if (stamina>0){
                    if (!main.infstamina){
                        stamina--;
                        lastSprintStartTime = System.currentTimeMillis();
                    }

                }

            }
            if (stamina<=4){
                isSweating = true;
            }else {
                isSweating = false;
            }

            if (stamina>0){
                playerSpeed = ((main.playerTargetSpeed*15)/main.reachedFPS);
                playerSpeed *= 1.6;
                main.sprinting = true;
                sprinting = true;
            }else {
                playerSpeed = (main.playerTargetSpeed*15)/main.reachedFPS;
                main.sprinting = false;
                sprinting = false;
            }



        }else{
            main.sprinting = false;
            sprinting = false;
            playerSpeed = (main.playerTargetSpeed*15)/main.reachedFPS;
            if ((double) (System.currentTimeMillis() - lastStaminaReg) /1000 >=1.2&&isMoving){
                stamina = Math.min(stamina+=1,maxStamina);
                lastStaminaReg = System.currentTimeMillis();
            }else if ((double) (System.currentTimeMillis() - lastStaminaReg) /1000 >=0.8&&!isMoving){
                stamina = Math.min(stamina+=1,maxStamina);
                lastStaminaReg = System.currentTimeMillis();
            }
            isSweating = false;
        }


        playerDirectionX = 0;
        playerDirectionY = 0;

        if (main.upPressed) {playerDirectionY -=1;}
        if (main.downPressed) {playerDirectionY +=1;}
        if (main.leftPressed) {playerDirectionX -=1;}
        if (main.rightPressed) {playerDirectionX +=1;}

        if (playerDirectionX != 0 || playerDirectionY != 0){
            isMoving = true;

            if (playerDirectionY < 0){
                if (playerDirectionX<0){
                    playerLookDirection = PlayerLookDirection.upLeft;
                }else if (playerDirectionX>0) {
                    playerLookDirection = PlayerLookDirection.upRight;
                }else {
                    playerLookDirection = PlayerLookDirection.up;
                }
            }else if(playerDirectionY > 0){
                if (playerDirectionX<0){
                    playerLookDirection = PlayerLookDirection.downLeft;
                }else if (playerDirectionX>0) {
                    playerLookDirection = PlayerLookDirection.downRight;
                }else {
                    playerLookDirection = PlayerLookDirection.down;
                }
            }else if(playerDirectionX < 0){
                playerLookDirection = PlayerLookDirection.left;
            }else if(playerDirectionX > 0){
                playerLookDirection = PlayerLookDirection.right;
            }

            if (playerDirectionX!= 0&& playerDirectionY!= 0){

                playerDirectionX *= 0.7071;
                playerDirectionY *= 0.7071;
            }
        }else {isMoving = false;}






        if (main.vollbild) {
            main.playerScreenX = ((main.frameWidth / 2.0) * main.scaleFactor) - (main.tileSize / 2.0);
            main.playerScreenY = ((main.frameHeight / 2.0) * main.scaleFactor) - (main.tileSize / 2.0);
        } else {
            main.playerScreenX = (main.frameWidth / 2.0) - (main.tileSize / 2.0);
            main.playerScreenY = (main.frameHeight / 2.0) - (main.tileSize / 2.0);
        }

        main.playerTargetWorldX += playerDirectionX * playerSpeed;
        main.playerTargetWorldY += playerDirectionY * playerSpeed;

        double targetX = main.playerWorldX + playerDirectionX * playerSpeed;
        double targetY = main.playerWorldY + playerDirectionY * playerSpeed;

        if (playerDirectionX != 0) {
            boolean xKollision = main.checkPlayerCollision(targetX, main.playerWorldY);

            if (!xKollision) {
                main.playerWorldX = targetX;
            } else {
                if (playerDirectionY == 0) {
                    if (!main.checkPlayerCollision(targetX, main.playerWorldY - playerSpeed)) {
                        main.playerWorldX = targetX;
                        main.playerWorldY -= playerSpeed;
                    }
                    else if (!main.checkPlayerCollision(targetX, main.playerWorldY + playerSpeed)) {
                        main.playerWorldX = targetX;
                        main.playerWorldY += playerSpeed;
                    }
                }
            }
        }

        if (playerDirectionY != 0) {
            boolean yKollision = main.checkPlayerCollision(main.playerWorldX, targetY);

            if (!yKollision) {
                main.playerWorldY = targetY;
            } else {
                if (playerDirectionX == 0) {

                    if (!main.checkPlayerCollision(main.playerWorldX - playerSpeed, targetY)) {
                        main.playerWorldY = targetY;
                        main.playerWorldX -= playerSpeed;
                    }
                    else if (!main.checkPlayerCollision(main.playerWorldX + playerSpeed, targetY)) {
                        main.playerWorldY = targetY;
                        main.playerWorldX += playerSpeed;
                    }
                }
            }
        }

        main.playerTargetWorldX = main.playerWorldX;
        main.playerTargetWorldY = main.playerWorldY;

        checkOnStep();






    }

    public void repaint(Graphics g2){

        BufferedImage playerSprite = getPlayerSprite();
        double playerTileSizeY = playerSprite.getTileHeight() * main.tileScaleFaktor;
        double playerTileSizeX = playerSprite.getTileWidth() * main.tileScaleFaktor;

        g2.drawImage(playerSprite,(int) main.playerScreenX, (int) main.playerScreenY, (int) playerTileSizeX, (int) playerTileSizeY,null);

        if (isSweating&&main.currentSweatSprite!=null){
            g2.drawImage(main.currentSweatSprite, (int) main.playerScreenX, (int) main.playerScreenY,(int) playerTileSizeX, (int) playerTileSizeY, null);
        }
    }



    public void checkInteraction() {
        double playerFeetX = main.playerWorldX + (main.originalTileSize / 2.0);
        double playerFeetY = main.playerWorldY + main.originalTileSize ;

        double interactX = playerFeetX;
        double interactY = playerFeetY;
        double offset = main.originalTileSize * 0.8;
        double diagOffset = offset * 0.707;

        switch (playerLookDirection) {
            case up:          interactY -= offset; break;
            case down:        interactY += offset; break;
            case left:        interactX -= offset; break;
            case right:       interactX += offset; break;
            case upLeft:      interactY -= diagOffset; interactX -= diagOffset; break;
            case upRight:     interactY -= diagOffset; interactX += diagOffset; break;
            case downLeft:    interactY += diagOffset; interactX -= diagOffset; break;
            case downRight:   interactY += diagOffset; interactX += diagOffset; break;
        }

        boolean entityFound = false;



        double maxDistanceToPlayer = main.originalTileSize * 1.1;
        double maxDistanceToTarget = main.originalTileSize * 1.0;

        for (Entity npc : main.npcList) {
            if (npc != null && npc.interactable) {

                double npcFeetX = npc.worldX + (npc.framesDown.getFirst().getWidth() / 2.0);
                double npcFeetY = npc.worldY + npc.framesDown.getFirst().getHeight();

                double distToPlayer = Math.sqrt(Math.pow(playerFeetX - npcFeetX, 2) + Math.pow(playerFeetY - npcFeetY, 2));
                double distToTarget = Math.sqrt(Math.pow(interactX - npcFeetX, 2) + Math.pow(interactY - npcFeetY, 2));

                if (distToPlayer <= maxDistanceToPlayer && distToTarget <= maxDistanceToTarget) {
                    npc.onInteract(this);
                    entityFound = true;
                    break;
                }
            }
        }

        if (!entityFound) {

            int centerCol = (int) (playerFeetX / main.originalTileSize);
            int centerRow = (int) (playerFeetY / main.originalTileSize);

            for (int row = centerRow - 1; row <= centerRow + 1; row++) {
                for (int col = centerCol - 1; col <= centerCol + 1; col++) {

                    if (row >= 0 && col >= 0 && row < Main.tileManager.mapMG.size() && col < Main.tileManager.mapMG.get(row).size()) {

                        int tileId = Main.tileManager.mapMG.get(row).get(col);

                        if (tileId > 0) {
                            double tileCenterX = (col * main.originalTileSize) + (main.originalTileSize / 2.0);
                            double tileCenterY = (row * main.originalTileSize) + (main.originalTileSize);

                            double distToPlayer = Math.sqrt(Math.pow(playerFeetX - tileCenterX, 2) + Math.pow(playerFeetY - tileCenterY, 2));
                            double distToTarget = Math.sqrt(Math.pow(interactX - tileCenterX, 2) + Math.pow(interactY - tileCenterY, 2));

                            if (distToPlayer <= maxDistanceToPlayer && distToTarget <= maxDistanceToTarget) {
                                String tileName = Main.tileManager.tile[tileId].name.trim().toLowerCase();

                                if (tileName.equals("wackamolemachine3")) {
                                    Main.wamAcardeManager.startGame();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void checkOnStep() {
        double playerFeetX = main.playerWorldX + (main.originalTileSize / 2.0);
        double playerFeetY = main.playerWorldY + main.originalTileSize ;

        int playerCol = (int) (playerFeetX / main.originalTileSize);
        int playerRow = (int) (playerFeetY / main.originalTileSize);

        if (playerCol != lastSteppedCol || playerRow != lastSteppedRow) {

            if (playerRow >= 0 && playerCol >= 0 && playerRow < Main.tileManager.mapBG.size() && playerCol < Main.tileManager.mapBG.get(playerRow).size()) {

                int tileId = Main.tileManager.mapBG.get(playerRow).get(playerCol);

                if (tileId > 0) {
                    String tileName = Main.tileManager.tile[tileId].name.trim().toLowerCase();

                    if (tileName.equals("wasser")) {
                        main.printOnDebug("steht auf Wasser!");
                    } else if (tileName.equals("stacheln")) {
                        main.printOnDebug("Autsch! Auf Stacheln getreten.");
                    }
                }
            }

            lastSteppedCol = playerCol;
            lastSteppedRow = playerRow;
        }

        Rectangle playerHitBox = new Rectangle(
                (int) (main.playerWorldX + main.playerHitBox.x),
                (int) (main.playerWorldY + main.playerHitBox.y),
                main.playerHitBox.width,
                main.playerHitBox.height
        );

        boolean standingOnAnyEntity = false;

        for (Entity entity : main.npcList) {
            if (entity != null && entity.isTrigger) {

                Rectangle entityHitBox = new Rectangle(
                        (int) (entity.worldX),
                        (int) (entity.worldY),
                        entity.framesDown.getFirst().getWidth(),
                        entity.framesDown.getFirst().getHeight()
                );

                if (playerHitBox.intersects(entityHitBox)) {
                    standingOnAnyEntity = true;

                    if (entity != lastSteppedEntity) {
                        entity.onStep(this);
                        lastSteppedEntity = entity;
                    }

                    break;
                }
            }
        }

        if (!standingOnAnyEntity) {
            lastSteppedEntity = null;
        }
    }

    private BufferedImage getPlayerSprite(){
        if (!isMoving) {
            switch (playerLookDirection){

                case up -> {
                    return framesUp.get(0);
                }case upLeft ->  {
                    return framesUpLeft.get(0);
                }case left ->   {
                    return framesLeft.get(0);
                }case downLeft ->  {
                    return framesDownLeft.get(0);
                }case down ->   {
                    return framesDown.get(0);
                }case downRight -> {
                    return framesDownRight.get(0);
                }case right ->   {
                    return framesRight.get(0);
                }case upRight ->  {
                    return framesUpRight.get(0);
                }case null ->  {
                    main.printErrOnDebug("Fehler beim auslesen der Spieler Renderlogik!");
                }

            }

        }else if(!main.sprinting){

            int frame = changeSprite(framesDown.size(),framesDown.size());

            switch (playerLookDirection){
                case up -> {
                    return framesUp.get(frame);
                }case upLeft ->  {
                    return framesUpLeft.get(frame);
                }case left ->   {
                    return framesLeft.get(frame);
                }case downLeft ->  {
                    return framesDownLeft.get(frame);
                }case down ->   {
                    return framesDown.get(frame);
                }case downRight -> {
                    return framesDownRight.get(frame);
                }case right ->   {
                    return framesRight.get(frame);
                }case upRight ->  {
                    return framesUpRight.get(frame);
                }case null ->  {
                    main.printErrOnDebug("Fehler beim auslesen der Spieler Renderlogik!");
                }

            }


        }else {
            int frame = changeSprite(sprintDownFrames.size(),sprintDownFrames.size());

            switch (playerLookDirection){
                case up -> {
                    return sprintUpFrames.get(frame);
                }case upLeft ->  {
                    return sprintUpLeftFrames.get(frame);
                }case left ->   {
                    return sprintLeftFrames.get(frame);
                }case downLeft ->  {
                    return sprintDownLeftFrames.get(frame);
                }case down ->   {
                    return sprintDownFrames.get(frame);
                }case downRight -> {
                    return sprintDownRightFrames.get(frame);
                }case right ->   {
                    return sprintRightFrames.get(frame);
                }case upRight ->  {
                    return sprintUpRightFrames.get(frame);
                }case null ->  {
                    main.printErrOnDebug("Fehler beim auslesen der Spieler Renderlogik!");
                }

            }



        }
        return framesDown.get(0);
    }
    public int changeSprite(int frameSize,int sprintFrameSize){
        if (System.currentTimeMillis()-lastSpriteChange>260&&!main.sprinting){
            if (currentFrameNum+1<frameSize) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }else if(System.currentTimeMillis()-lastSpriteChange>150&&main.sprinting){
            if (currentFrameNum+1<sprintFrameSize) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }



        return currentFrameNum;
    }

}
