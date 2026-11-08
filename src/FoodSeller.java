import java.awt.image.BufferedImage;

public class FoodSeller extends Entity{

    private final Main main;
    public FoodSeller(Main main){
        super(false,0,Type.Npc);
        this.main = main;
        type = Type.Npc;
        interactable = true;
        name = "Fastfood Verkäuferin";
        variants = Variants.GrayAndPurple;
        loadFrames();



    }


    public void loadFrames(){

        framesUp.add(0,main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Grau/up_1.png"));
        framesUp.add(1,main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Grau/up_2.png"));

        framesDown.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Grau/down_1.png"));
        framesDown.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Grau/down_2.png"));

        specialUpFrames.add(0,main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Lila/up_1.png"));
        specialUpFrames.add(1,main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Lila/up_2.png"));

        specialDownFrames.add(0, main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Lila/down_1.png"));
        specialDownFrames.add(1, main.loadExternalImage("Grafik/Sprites/Npc/Fast-Food-Verkäuferin/Lila/down_2.png"));


        setHitBox();


    }


    @Override
    public void onInteract(Player player) {

        Main.eventManager.triggerEvent("sellFood");
    }



}
