import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity implements Interactable{


    ArrayList<BufferedImage> sweatFrames = new ArrayList<>();

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

    ArrayList<BufferedImage> specialUpFrames = new ArrayList<>();
    ArrayList<BufferedImage> specialDownFrames = new ArrayList<>();
    ArrayList<BufferedImage> specialLeftFrames = new ArrayList<>();
    ArrayList<BufferedImage> specialRightFrames = new ArrayList<>();




    public LookDirection lookDirection = LookDirection.down;

    public enum LookDirection{
        up,
        upLeft,
        left,
        downLeft,
        down,
        downRight,
        right,
        upRight,
    }

    Variants variants = Variants.GrayAndPurple;

    public enum Variants {
        GrayAndPurple,
        Gray,
        Purple,
    }

    UsedVariant usedVariant = UsedVariant.Gray;

    public enum UsedVariant{
        Gray,
        Purple
    }

    boolean isSweating = false;
    public Entity leader = null;
    boolean interactable = false;
    public Rectangle hitBox;
    public String name = "";
    public double Speed = 0;
    public long speedDuration =0;
    public boolean hasInventory = false;
    public int maxInvSpace = 0;
    public ArrayList<Integer> inventory;
    public ArrayList<BufferedImage> invCharacterSprite;
    public int usedInvSpace = 0;
    public int HP = 20;
    public int targetHP = 10;
    public int maxHP = 40;
    public int PP = 10;
    public int targetPP = 20;
    public int maxPP = 30;
    public double money = 0;
    public int IQ = 50;
    public int luck = 15;
    public int lvl = 0;
    public int xp =0;
    public int nxtLevelXp = 10;
    public String equippedWeaponName = "nichts";
    public String equippedHatName = "nichts";
    public String equippedChestName = "nichts";
    public String equippedPantsName = "nichts";
    public String equippedShoesName = "nichts";
    int armorBars =0;
    int offensive =0;
    double worldX = 0;
    double worldY = 0;
    public Type type= Type.Npc;

    public enum Type{
        MainCharacter,
        Enemy,
        Npc
    }
    boolean sprinting = false;
    boolean isMoving = false;
    public boolean isTrigger = false;

    public Item equippedWeapon = null;
    public Item equippedHat = null;
    public Item equippedChest = null;
    public Item equippedPants = null;
    public Item equippedShoes = null;

    public Entity(boolean hasInventory,int maxInvSpace, Type type) {
        this.hasInventory = hasInventory;
        if (hasInventory) {
            inventory = new ArrayList<>(maxInvSpace);
            this.maxInvSpace = maxInvSpace;
        }
        invCharacterSprite = new ArrayList<>();

        for (int i = 0; i < maxInvSpace; i++) {
            inventory.add(0);
        }

        this.type = type;

        if (type == Type.MainCharacter) {
            sweatFrames.add(0, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_1.png"));
            sweatFrames.add(1, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_2.png"));
            sweatFrames.add(2, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_3.png"));
            sweatFrames.add(3, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_4.png"));
            sweatFrames.add(4, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_5.png"));
            sweatFrames.add(5, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_6.png"));
            sweatFrames.add(6, Main.getInstance().loadExternalImage("Grafik/Sprites/Sweat/sweat_7.png"));
        }



    }


    public void onStep(Player player) {

    }

    public void setHitBox() {
        if (!framesDown.isEmpty()) {
            this.hitBox = new Rectangle(framesDown.getFirst().getWidth()/8, (int) (framesDown.getFirst().getHeight()/1.5), (int) (framesDown.getFirst().getWidth()/1.33),framesDown.getFirst().getHeight()/3);
        }else if (!specialDownFrames.isEmpty()) {
            this.hitBox = new Rectangle(specialDownFrames.getFirst().getWidth()/8, (int) (specialDownFrames.getFirst().getHeight()/1.5), (int) (specialDownFrames.getFirst().getWidth()/1.33),specialDownFrames.getFirst().getHeight()/3);
        }else {Main.getInstance().printErrOnDebug("Hitbox von "+name+" konnte nicht erstellt werden!");}
    }


    public void addToInventory(int itemId){

        for (int i  = 0; i<maxInvSpace; i++){

            if (inventory.get(i) == 0||inventory.get(i)==null){
                inventory.set(i,itemId);
                break;
            }

        }
        refreshUsedInvSpace();


    }

    public void equipWeapon(Item item){
        if (equippedWeapon!=null){
            if (equippedWeapon == item){
                dequipWeapon();
                return;
            }
            dequipWeapon();
        }
        equippedWeapon = item;
        offensive += item.weaponDamage;
        equippedWeapon.itemEquipped = true;
        equippedWeaponName = equippedWeapon.name;
    }

    public void dequipWeapon(){
        if (equippedWeapon!=null) {
            equippedWeapon.itemEquipped = false;
            offensive -= equippedWeapon.weaponDamage;
            equippedWeapon = null;
            equippedWeaponName = "nichts";
        }
    }

    public void equipArmor(Item item){

        if (item.armorType == Item.ArmorType.Kopf){
            if (equippedHat!=null){
                if (equippedHat == item){
                    dequipArmor(0);

                    return;
                }
                dequipArmor(0);
            }
            equippedHat = item;
            armorBars+= equippedHat.armorBars;
            equippedHatName = equippedHat.name;
            equippedHat.itemEquipped = true;
            Main.getInstance().printErrOnDebug("aufsetzen: "+item.name);

        }else if (item.armorType == Item.ArmorType.Brust){
            if (equippedChest!=null){
                if (equippedChest == item){
                    dequipArmor(1);
                    return;
                }
                dequipArmor(1);
            }
            equippedChest = item;
            armorBars+= equippedChest.armorBars;
            equippedChestName = equippedChest.name;
            equippedChest.itemEquipped = true;
        }else if (item.armorType == Item.ArmorType.Beine){
            if (equippedPants!=null){
                if (equippedPants == item){
                    dequipArmor(2);
                    return;
                }
                dequipArmor(2);
            }
            equippedPants = item;
            armorBars+= equippedPants.armorBars;
            equippedPantsName = equippedPants.name;
            equippedPants.itemEquipped = true;
        }else if (item.armorType == Item.ArmorType.Füße){
            if (equippedShoes!=null){
                if (equippedShoes == item){
                    dequipArmor(3);
                    return;
                }
                dequipArmor(3);
            }
            equippedShoes = item;
            armorBars+= equippedShoes.armorBars;
            equippedShoesName = equippedShoes.name;
            equippedShoes.itemEquipped = true;
        }

    }

    public void dequipArmor(int type){
        if (type==0&&equippedHat!=null){
            equippedHat.itemEquipped = false;
            armorBars -= equippedHat.armorBars;
            equippedHat = null;
            equippedHatName = "nichts";
        }else if (type==1&&equippedChest!=null){
            equippedChest.itemEquipped = false;
            armorBars -= equippedChest.armorBars;
            equippedChest = null;
            equippedChestName = "nichts";
        }else if (type==2&&equippedPants!=null){
            equippedPants.itemEquipped = false;
            armorBars -= equippedPants.armorBars;
            equippedPants = null;
            equippedPantsName = "nichts";
        }else if (type==3&&equippedShoes!=null){
            equippedShoes.itemEquipped = false;
            armorBars -= equippedShoes.armorBars;
            equippedShoes = null;
            equippedShoesName = "nichts";
        }
    }

    public void removeFromInventory(int itemNum){

        for (int i  = 0; i<maxInvSpace; i++){
            if (i == itemNum){
                inventory.set(i,0);
                break;
            }

        }
        refreshUsedInvSpace();

        for (int i  = 0; i<maxInvSpace; i++){
            if (inventory.get(i) == 0){
                for (int x  = i; x<maxInvSpace; x++){
                    if (inventory.get(x) != 0){
                        inventory.set(i,inventory.get(x));
                        inventory.set(x,0);
                        break;
                    }

                }
            }
        }

    }

    public void refreshUsedInvSpace(){
        int usedInvSpace = 0;

        for (int i  = 0; i<maxInvSpace; i++) {
            if (inventory.get(i) > 0) {
                usedInvSpace++;
            }
        }
        this.usedInvSpace = usedInvSpace;
    }

    public void updateEffects(){
        if (HP > targetHP){
            HP--;
        }else if (HP < targetHP&&targetHP<= maxHP){
            HP++;
        }
        if (PP > targetPP){
            PP--;
        }else if (PP < targetPP&&targetHP<= maxHP){
            PP++;
        }
    }

    public BufferedImage getImage(LookDirection lookDirection){
        if (usedVariant.equals(UsedVariant.Gray)||type.equals(Type.MainCharacter)) {


            if (!isMoving) {
                switch (lookDirection) {

                    case up -> {
                        return framesUp.get(0);
                    }
                    case upLeft -> {
                        return framesUpLeft.get(0);
                    }
                    case left -> {
                        return framesLeft.get(0);
                    }
                    case downLeft -> {
                        return framesDownLeft.get(0);
                    }
                    case down -> {
                        return framesDown.get(0);
                    }
                    case downRight -> {
                        return framesDownRight.get(0);
                    }
                    case right -> {
                        return framesRight.get(0);
                    }
                    case upRight -> {
                        return framesUpRight.get(0);
                    }
                    case null -> {
                        Main.getInstance().printErrOnDebug("Fehler beim auslesen der Entity Renderlogik!");
                    }

                }

            } else if (!sprinting) {

                int frame = changeSprite(framesDown.size(), sprintDownFrames.size());

                switch (lookDirection) {
                    case up -> {
                        return framesUp.get(frame);
                    }
                    case upLeft -> {
                        return framesUpLeft.get(frame);
                    }
                    case left -> {
                        return framesLeft.get(frame);
                    }
                    case downLeft -> {
                        return framesDownLeft.get(frame);
                    }
                    case down -> {
                        return framesDown.get(frame);
                    }
                    case downRight -> {
                        return framesDownRight.get(frame);
                    }
                    case right -> {
                        return framesRight.get(frame);
                    }
                    case upRight -> {
                        return framesUpRight.get(frame);
                    }
                    case null -> {
                        Main.getInstance().printErrOnDebug("Fehler beim auslesen der Entity Renderlogik!");
                    }

                }


            } else {
                int frame = changeSprite(framesDown.size(), sprintDownFrames.size());

                switch (lookDirection) {
                    case up -> {
                        return sprintUpFrames.get(frame);
                    }
                    case upLeft -> {
                        return sprintUpLeftFrames.get(frame);
                    }
                    case left -> {
                        return sprintLeftFrames.get(frame);
                    }
                    case downLeft -> {
                        return sprintDownLeftFrames.get(frame);
                    }
                    case down -> {
                        return sprintDownFrames.get(frame);
                    }
                    case downRight -> {
                        return sprintDownRightFrames.get(frame);
                    }
                    case right -> {
                        return sprintRightFrames.get(frame);
                    }
                    case upRight -> {
                        return sprintUpRightFrames.get(frame);
                    }
                    case null -> {
                        Main.getInstance().printErrOnDebug("Fehler beim auslesen der Entity Renderlogik!");
                    }

                }


            }
        }else if (usedVariant.equals(UsedVariant.Purple)){

            if (!isMoving) {
                switch (lookDirection) {

                    case up -> {
                        return specialUpFrames.get(0);
                    }

                    case left -> {
                        return specialLeftFrames.get(0);
                    }

                    case down -> {
                        return specialDownFrames.get(0);
                    }

                    case right -> {
                        return specialRightFrames.get(0);
                    }

                    case null -> {
                        Main.getInstance().printErrOnDebug("Fehler beim auslesen der Entity Renderlogik!");
                    }

                    default -> Main.getInstance().printErrOnDebug("Unerwarteter Wert für "+type+": " + lookDirection);

                }

            } else if (!sprinting) {

                int frame = changeSprite(framesDown.size(), sprintDownFrames.size());

                switch (lookDirection) {
                    case up -> {
                        return specialUpFrames.get(frame);
                    }

                    case left -> {
                        return specialLeftFrames.get(frame);
                    }

                    case down -> {
                        return specialDownFrames.get(frame);
                    }

                    case right -> {
                        return specialRightFrames.get(frame);
                    }

                    case null -> {
                        Main.getInstance().printErrOnDebug("Fehler beim auslesen der Entity Renderlogik!");
                    }
                    default -> Main.getInstance().printErrOnDebug("Unerwarteter Wert für "+type+": " + lookDirection);
                }


            }


        }
        return framesDown.get(0);
    }



    long lastSpriteChange = 0;
    int currentFrameNum = 0;

    public int changeSprite(int frameSize,int sprintFrameSize){
        if (System.currentTimeMillis()-lastSpriteChange>260&&!sprinting){
            if (currentFrameNum+1<frameSize) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }else if(System.currentTimeMillis()-lastSpriteChange>150&&sprinting){
            if (currentFrameNum+1<sprintFrameSize) {
                currentFrameNum++;
            }else {
                currentFrameNum = 0;
            }
            lastSpriteChange = System.currentTimeMillis();
        }



        return currentFrameNum;
    }


    @Override
    public void onInteract(Player player) {

    }



}
