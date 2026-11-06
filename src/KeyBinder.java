import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBinder extends JPanel {

    private int keylocation;
    private int currentKeyCode;
    private boolean isListening = false;

    private Main main;

    JButton button;
    BrikText textField;

    public KeyBinder(String actionName, int defaultKeyCode, Main main) {
        this.main = main;
        button = new JButton();
        button.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("SPACE"), "none");
        button.setFont(main.dialogFont);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        textField = new BrikText(main);
        textField.setText(actionName);
        textField.setFont(main.dialogFont);
        button.setBackground(main.themeColor[0]);
        button.setForeground(main.themeColor[3]);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        setBindSize(main.frameWidth, main.buttonHeight);

        this.keylocation = defaultKeyCode;
        this.currentKeyCode = main.keyBinds[keylocation];
        this.setOpaque(false);

        setLayout(new GridLayout(1,2));
        add(textField);
        add(button);

        updateButtonText();

        setAlignmentX(CENTER_ALIGNMENT);
        setVisible(true);

        button.addActionListener(e -> {
            if (!isListening) {
                isListening = true;
                button.setText("Drücke eine Taste...");
                button.setBackground(main.themeColor[1]);
                button.requestFocusInWindow();
            }
        });

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isListening) {

                    //Auswahl Abbrechen
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        isListening = false;
                        button.setBackground(main.themeColor[0]);
                        updateButtonText();
                        return;
                    }

                    if (!button.isFocusPainted()){
                        isListening = false;
                        button.setBackground(main.themeColor[0]);
                        updateButtonText();
                        return;
                    }

                    for (int i=0; i<main.keyBinds.length; i++) {
                        if(main.keyBinds[i] == e.getKeyCode()) {
                            isListening = false;
                            button.setBackground(main.themeColor[0]);
                            updateButtonText();
                            if (i != keylocation) {
                                button.setText("Taste bereits Vergeben!");
                                button.setBackground(Color.RED);
                            }

                            return;
                        }

                    }

                    currentKeyCode = e.getKeyCode();
                    main.keyBinds[keylocation] = currentKeyCode;
                    main.updateKeyBinds();
                    isListening = false;

                    button.setBackground(main.themeColor[0]);
                    updateButtonText();
                }
            }
        });
    }

    private void updateButtonText() {
        button.setText(KeyEvent.getKeyText(currentKeyCode));

    }

    public void repaintKeyBinder(){
        button.setBackground(main.themeColor[0]);
        button.repaint();
        button.setForeground(main.themeColor[3]);

        if(main.vollbild){
            textField.SetFont(main.extendedDialogFont);
            button.setFont(main.extendettextfont);

            setBindSize(main.extendedFrameWidth, main.buttonHeight*main.scaleFactor);

        }else{
            textField.SetFont(main.dialogFont);
            button.setFont(main.dialogFont);
            setBindSize(main.frameWidth, main.buttonHeight);


        }
        textField.repaintText();
        updateButtonText();
    }

    public void setBindSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new  Dimension(width, height));
    }

}
