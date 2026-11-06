import javax.swing.*;
import java.awt.*;

public class FightDisplay {
    private final Main main;

    JPanel fightPanel;

    public FightDisplay(Main main){
        this.main = main;


    }

    public void loadFightPanel(){

        JPanel fightPanel = new JPanel();
        fightPanel.setBackground(Color.RED);



        this.fightPanel = fightPanel;


    }
}
