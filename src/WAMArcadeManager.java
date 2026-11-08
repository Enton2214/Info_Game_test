import javax.swing.*;
import java.awt.*;

public class WAMArcadeManager{
    //WAM = Wack a mole
    private final Main main;

    //scaleFaktor für den Monitor von einem Pixel für ein 160x90p großes Bild
    double scaleX = 0;
    double scaleY = 0;




    public WAMArcadeManager(Main main) {
        this.main = main;


    }


    //wird einmal ausgeführt, wenn der Spieler mit der Arcade Maschine interagiert
    public void startGame(){
        main.gameStatus = Main.GameState.WackAMole;
        main.printOnDebug("starte Wack A Mole");


    }

    public void updateGame(){
        Main.gameDisplay.scaleX = scaleX;
        Main.gameDisplay.scaleY = scaleY;

    }
    public void repaintGame(Graphics2D g2, JPanel panel){
        g2.setColor(Color.RED);
        g2.fillRect(0,0,panel.getWidth()/2,panel.getHeight()/2);

    }





}
