import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;

/**Einfacher Textschatten Generator
 * NICHT von KI generiert
 */

public class BrikText extends JTextPane {
    private Main main;
    private int shadowOffset = Math.max(1, main.textfont.getSize() / 10);
    private Color shadowColor = new Color(0, 0, 0, 142);
    private Color fontColor = main.themeColor[3];

    private boolean editable;
    private Font font;

    private Timer typewriterTimer;
    public boolean keyTypeIsFinished = true;
    private int delayMs;
    private int targetDelay = 1200;

    public BrikText(Main main) {
        this.main = main;
        this.editable = false;
        this.font = main.textfont;

        super();
        setFont(font);
        setEditable(editable);
        setOpaque(false);
        setForeground(fontColor);
    }

    public void startTypewrtiting(String text) {
        keyTypeIsFinished = false;

        if(typewriterTimer != null && typewriterTimer.isRunning()){
            typewriterTimer.stop();
        }

        final int[] currentCharIndex = {0};
        delayMs = (targetDelay/3)/text.length();
        setText("");

        typewriterTimer = new Timer(delayMs, e -> {
            if (currentCharIndex[0] <= text.length()) {

                String partText = text.substring(0,currentCharIndex[0]);
                setText(partText);

                repaintText();

                currentCharIndex[0]++;

            }else {
                typewriterTimer.stop();
                keyTypeIsFinished = true;
            }
        });

        typewriterTimer.start();

    }

    public void repaintText(){
        setContentType("text/plain");
        fontColor = main.themeColor[3];

        setForeground(fontColor);

        StyledDocument doc = getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, fontColor);

        doc.setCharacterAttributes(0, doc.getLength(), attrs, true);

        repaint();


    }

    public void setTextColor(Color color){

        this.fontColor = color;
        repaintInternText();
    }

    public void repaintInternText(){
        setContentType("text/plain");
        setForeground(fontColor);

        StyledDocument doc = getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, fontColor);

        doc.setCharacterAttributes(0, doc.getLength(), attrs, true);

        repaint();

    }

    public void repaintHTMLText(){


    }
    public void SetFont(Font font) {
        this.font = font;
        shadowOffset = Math.max(1, font.getSize() / 10);
        setFont(font);
        repaint();

    }

    public void setShadow(Color color, int Offset){
        this.shadowColor = color;
        this.shadowOffset = Offset;
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {


        if (getWidth() <= 0 || getHeight() <= 0) {

            super.paintComponent(g);
            return;
        }

        if(isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        BufferedImage textImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg = textImage.createGraphics();
        gImg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gImg.setClip(0,0,getWidth(),getHeight());

        boolean originalOpaque = isOpaque();
        setOpaque(false);
        super.paintComponent(gImg);
        gImg.dispose();
        setOpaque(originalOpaque);

        BufferedImage shadowImage = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gshadow = shadowImage.createGraphics();
        gshadow.drawImage(textImage, 0, 0, null);
        gshadow.setComposite(AlphaComposite.SrcIn);
        gshadow.setColor(shadowColor);
        gshadow.fillRect(0, 0, getWidth(), getHeight());
        gshadow.dispose();

        g2d.drawImage(shadowImage, shadowOffset, shadowOffset, null);

        g2d.drawImage(textImage, 0, 0, null);


    }



}
