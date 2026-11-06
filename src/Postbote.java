import java.awt.image.BufferedImage;

public class Postbote extends Entity{

    private final Main main;
    public Postbote(Main main){
        super(false,0,Type.Npc);
        this.main = main;
        type = Type.Npc;
        interactable = true;
        name = "Postbote";
        variants = Variants.GrayAndPurple;
        loadFrames();



    }


    public void loadFrames(){

        framesUp.add(0,main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/up_1.png"));
        framesUp.add(1,main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/up_2.png"));

        framesLeft.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/left_1.png"));
        framesLeft.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/left_2.png"));

        framesDown.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/down_1.png"));
        framesDown.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/down_2.png"));

        framesRight.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/right_1.png"));
        framesRight.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Grau/right_2.png"));

        specialUpFrames.add(0,main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/up_1.png"));
        specialUpFrames.add(1,main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/up_2.png"));

        specialLeftFrames.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/left_1.png"));
        specialLeftFrames.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/left_2.png"));

        specialDownFrames.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/down_1.png"));
        specialDownFrames.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/down_2.png"));

        specialRightFrames.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/right_1.png"));
        specialRightFrames.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Postbote/Lila/right_2.png"));


        setHitBox();


    }

    public void update() {


    }

    @Override
    public void onInteract(Player player) {

        this.lookDirection = main.getOppositeDirection(type,player.playerLookDirection);
    }



}
