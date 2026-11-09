import java.awt.image.BufferedImage;

public class Kizo extends Entity{

    private final Main main;



    public BufferedImage peaceFrame;


    public Kizo(Main main){
        super(true,14,Type.MainCharacter);
        this.main = main;
        type = Type.MainCharacter;
        interactable = true;
        variants = Variants.Purple;
        loadKizoFrames();

        addToInventory(11);
        addToInventory(12);
        addToInventory(13);
        addToInventory(14);
        addToInventory(15);
        addToInventory(16);
        addToInventory(17);
        addToInventory(18);
        addToInventory(19);
        addToInventory(20);
        addToInventory(21);
        addToInventory(22);
        addToInventory(23);

    }

    public void loadKizoFrames(){



        invCharacterSprite.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_1.png"));
        invCharacterSprite.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_2.png"));
        invCharacterSprite.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_3.png"));
        invCharacterSprite.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_4.png"));

        framesUp.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_up_1.png"));
        framesUp.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_up_2.png"));
        framesUp.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_up_3.png"));
        framesUp.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_up_4.png"));

        framesUpLeft.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upLeft_1.png"));
        framesUpLeft.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upLeft_2.png"));
        framesUpLeft.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upLeft_3.png"));
        framesUpLeft.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upLeft_4.png"));

        framesLeft.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_left_1.png"));
        framesLeft.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_left_2.png"));
        framesLeft.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_left_3.png"));
        framesLeft.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_left_4.png"));

        framesDownLeft.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downLeft_1.png"));
        framesDownLeft.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downLeft_2.png"));
        framesDownLeft.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downLeft_3.png"));
        framesDownLeft.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downLeft_4.png"));

        framesDown.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_1.png"));
        framesDown.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_2.png"));
        framesDown.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_3.png"));
        framesDown.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_down_4.png"));

        framesDownRight.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downRight_1.png"));
        framesDownRight.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downRight_2.png"));
        framesDownRight.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downRight_3.png"));
        framesDownRight.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_downRight_4.png"));

        framesRight.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_right_1.png"));
        framesRight.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_right_2.png"));
        framesRight.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_right_3.png"));
        framesRight.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_right_4.png"));

        framesUpRight.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upRight_1.png"));
        framesUpRight.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upRight_2.png"));
        framesUpRight.add(2, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upRight_3.png"));
        framesUpRight.add(3, main.loadExternalImage("Grafik/Sprites/Kizo/Kizo_upRight_4.png"));

        sprintUpFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_up_1.png"));
        sprintUpFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_up_2.png"));

        sprintUpLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_upLeft_1.png"));
        sprintUpLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_upLeft_2.png"));

        sprintLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_left_1.png"));
        sprintLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_left_2.png"));

        sprintDownLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_downLeft_1.png"));
        sprintDownLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_downLeft_2.png"));

        sprintDownFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_down_1.png"));
        sprintDownFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_down_2.png"));

        sprintDownRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_downRight_1.png"));
        sprintDownRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_downRight_2.png"));

        sprintRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_right_1.png"));
        sprintRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_right_2.png"));

        sprintUpRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_upRight_1.png"));
        sprintUpRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Kizo/Sprint/Kizo_upRight_2.png"));

        peaceFrame = main.loadExternalImage("Grafik/Sprites/Kizo/Peace.png");

        setHitBox();


    }


    @Override
    public void onInteract(Player player) {

        this.lookDirection = main.getOppositeDirection(type,player.playerLookDirection);
    }
}
