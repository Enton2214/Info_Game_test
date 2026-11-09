import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

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


    }
    public void repaintGame(Graphics2D g2, JPanel panel){
        scaleX = (double) panel.getWidth() / 160;
        scaleY= (double) panel.getHeight() / 90;
        g2.setColor(Color.RED);
        g2.fillRect(0,0,panel.getWidth()/2,panel.getHeight()/2);

    }
    //wird ausgeführt, wenn mausButton gedrückt wird
    public void mousePressed(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        main.printOnDebug("mousePressed");
    }
    //wird ausgeführt, wenn die maus bewegt wird
    public void mouseMoved(MouseEvent e){
        int  mouseX = e.getX();
        int mouseY = e.getY();
        main.printOnDebug("mouseMoved");

    }





}
