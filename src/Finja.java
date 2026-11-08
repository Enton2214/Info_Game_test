import java.awt.*;
import java.awt.image.BufferedImage;

public class Finja extends Entity{

    private final Main main;



    BufferedImage peaceFrame;



    public Finja(Main main){
        super(true,6,Type.MainCharacter);
        this.main = main;
        interactable = true;
        loadFinjaFrames();
        variants = Variants.Purple;

        addToInventory(24);
        addToInventory(25);
        addToInventory(26);
        addToInventory(27);
        addToInventory(28);
        addToInventory(29);



    }


    public void loadFinjaFrames(){


        invCharacterSprite.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_1.png"));
        invCharacterSprite.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_2.png"));
        invCharacterSprite.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_3.png"));
        invCharacterSprite.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_4.png"));


        framesUp.add(0,main.loadExternalImage("Grafik/Sprites/Finja/Finja_up_1.png"));
        framesUp.add(1,main.loadExternalImage("Grafik/Sprites/Finja/Finja_up_2.png"));
        framesUp.add(2,main.loadExternalImage("Grafik/Sprites/Finja/Finja_up_3.png"));
        framesUp.add(3,main.loadExternalImage("Grafik/Sprites/Finja/Finja_up_4.png"));

        sprintUpFrames.add(0,main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_up_1.png"));
        sprintUpFrames.add(1,main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_up_2.png"));


        framesUpLeft.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upLeft_1.png"));
        framesUpLeft.add(1,main.loadExternalImage("Grafik/Sprites/Finja/Finja_upLeft_2.png"));
        framesUpLeft.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upLeft_3.png"));
        framesUpLeft.add(3,main.loadExternalImage("Grafik/Sprites/Finja/Finja_upLeft_4.png"));

        sprintUpLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_upLeft_1.png"));
        sprintUpLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_upLeft_2.png"));


        framesLeft.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_left_1.png"));
        framesLeft.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_left_2.png"));
        framesLeft.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_left_3.png"));
        framesLeft.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_left_4.png"));

        sprintLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_left_1.png"));
        sprintLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_left_2.png"));


        framesDownLeft.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downLeft_1.png"));
        framesDownLeft.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downLeft_2.png"));
        framesDownLeft.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downLeft_3.png"));
        framesDownLeft.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downLeft_4.png"));

        sprintDownLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_downLeft_1.png"));
        sprintDownLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_downLeft_2.png"));


        framesDown.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_1.png"));
        framesDown.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_2.png"));
        framesDown.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_3.png"));
        framesDown.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_down_4.png"));

        sprintDownFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_down_1.png"));
        sprintDownFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_down_2.png"));


        framesDownRight.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downRight_1.png"));
        framesDownRight.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downRight_2.png"));
        framesDownRight.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downRight_3.png"));
        framesDownRight.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_downRight_4.png"));

        sprintDownRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_downRight_1.png"));
        sprintDownRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_downRight_2.png"));


        framesRight.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_right_1.png"));
        framesRight.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_right_2.png"));
        framesRight.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_right_3.png"));
        framesRight.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_right_4.png"));

        sprintRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_right_1.png"));
        sprintRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_right_2.png"));


        framesUpRight.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upRight_1.png"));
        framesUpRight.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upRight_2.png"));
        framesUpRight.add(2, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upRight_3.png"));
        framesUpRight.add(3, main.loadExternalImage("Grafik/Sprites/Finja/Finja_upRight_4.png"));

        sprintUpRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_upRight_1.png"));
        sprintUpRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Finja/Sprint/Finja_upRight_2.png"));

        peaceFrame = main.loadExternalImage("Grafik/Sprites/Finja/Peace.png");
        setHitBox();


    }


    @Override
    public void onInteract(Player player) {
        Main.eventManager.triggerEvent("event_finja_talk");

        this.lookDirection = main.getOppositeDirection(type,player.playerLookDirection);
    }




}
