import java.awt.image.BufferedImage;
import java.util.List;

public class Tile {

    public BufferedImage image;
    int ID;
    boolean collision=false;
    boolean[][] collisionMap;

    boolean animated=false;
    public List<BufferedImage> animationFrames;
    int animatedPicNumbers;
    int animationGap;

    public String name="";



}
