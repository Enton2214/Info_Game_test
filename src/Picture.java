import java.awt.image.BufferedImage;

public class Picture {

    public BufferedImage image;
    public BufferedImage secondImage;
    public int x = 0;
    public int y = 0;

    public int width = 0;
    public int height = 0;

    public int Alpha = 0;

    public boolean triggered = false;
    public boolean triggeredSize = false;
    public boolean triggeredAlpha = false;

    public boolean active = false;
    public boolean activeSize = false;
    public long lastFrameTime = 0;
    public long lastFrameSizeTime = 0;

    public boolean activeAlpha = false;
    public long lastAlphaTime = 0;

    public void resetTriggerPos(){
        triggered = false;
    }
    public void resetTriggerSize(){
        triggeredSize = false;
    }
    public void resetTriggerAlpha(){
        triggeredAlpha = false;
    }




}
