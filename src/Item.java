import java.awt.image.BufferedImage;

public class Item {

    String name = "leer";
    BufferedImage itemIcon = null;
    String describe = "kein Beschreibung";
    double price = 0;
    double worth =0;

    int HPEffect =0;
    int PPEffect =0;
    int speedEffect =0;
    long speedDuration = 0;// in sekunden
    boolean curesPoison = false;
    public ArmorType armorType = null;
    public ItemType itemType = null;

    int armorBars =0;
    boolean itemEquipped = false;
    public enum ItemType {
        Essen,
        Trinken,
        Rüstung,
        Waffe,
        Kram

    }

    public enum ArmorType{
        Kopf,
        Brust,
        Beine,
        Füße,
    }

    int weaponDamage = 0;
    int precision =0; // Trefferquote der Waffe(0-100%)




    public Item(BufferedImage itemIcon, String name,String describe,double price) {
        this.itemIcon = itemIcon;
        this.name = name;
        this.describe = describe;
        this.price = price;
        this.worth = Math.round(price*0.7);
    }

    public void setFoodEffect(int HPEffect, int PPEffect, int speedEffect, int speedDuration, boolean curesPoison) {
        this.HPEffect = HPEffect;
        this.PPEffect = PPEffect;
        this.speedEffect = speedEffect;
        this.speedDuration = speedDuration;
        itemType = ItemType.Essen;
        this.curesPoison = curesPoison;
    }

    public void setDrinkEffect(int HPEffect, int PPEffect, int speedEffect, int speedDuration, boolean curesPoison) {
        this.HPEffect = HPEffect;
        this.PPEffect = PPEffect;
        this.speedEffect = speedEffect;
        this.speedDuration = speedDuration;
        itemType = ItemType.Trinken;
        this.curesPoison = curesPoison;
    }

    public void setArmorEffect(int armorBars, ArmorType armorType) {
        this.armorBars = armorBars;
        this.armorType = armorType;
        itemType = ItemType.Rüstung;
    }

    public void setWeaponEffect(int weaponDamage,int precision){
        this.weaponDamage = weaponDamage;
        this.precision = precision;
        itemType = ItemType.Waffe;
    }



    public void setKramEffect(){
        itemType = ItemType.Kram;
    }

}
