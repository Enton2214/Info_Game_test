import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.sound.sampled.*;

/**
 * EarthBound-style Animated DiaButton with Modern Smoothness & Sound Effects.
 * Fully customizable at runtime with standard Swing-like setters.
 * Compatible with IntelliJ IDEA and standard Java Swing projects.
 *
 * Von KI generiert und nochmal selbst angepasst
 */
public class BrikButton extends JButton {
    private double time = 0;
    private double startTime =0.0;
    private double hoverProgress = 0; // 0.0 to 1.0
    private double clickProgress = 0; // 0.0 to 1.0
    private Timer animationTimer;
    private Timer hoverTimer; // Single timer reference to prevent rapid-hover conflicts
    private boolean isHovered = false;
    private boolean isPressed = false;

    private Main main;

    // --- Audio Cache (Clip Pools für Polyphonie) ---
    private static final int HOVER_POOL_SIZE = 6; // Erlaubt bis zu 6 überlappende Hover-Sounds
    private static Clip[] cachedHoverClips = new Clip[HOVER_POOL_SIZE];
    private static int nextHoverIndex = 0;

    private static final int PRESS_POOL_SIZE = 4; // NEU: Erlaubt bis zu 4 überlappende Klick-Sounds
    private static Clip[] cachedPressClips = new Clip[PRESS_POOL_SIZE];
    private static int nextPressIndex = 0;

    private static boolean clipsInitialized = false;

    // Customizable Configuration Parameters
    private String buttonText;
    private Color primaryColor;
    private Color waveColor;
    private Color textColor;
    private int fontSize;
    private String borderStyle;
    private String waveStyle;
    private double waveSpeed;
    private double waveAmplitude;
    private boolean soundEnabled;

    private int buttonHeight;
    private int buttonWidth;

    public BrikButton(Main main) {
        this.main = main;

        initAudioClips();
        // Initialize with default values from the generator
        this.buttonText = "";
        this.primaryColor = main.themeColor[1];
        this.waveColor = main.themeColor[2];
        this.textColor = main.themeColor[3];
        this.fontSize = main.dialogFont.getSize();
        this.borderStyle = "Modern Rounded";
        this.waveStyle = "Horizontal Sine";
        this.waveSpeed = main.buttonWaveSpeed;
        this.waveAmplitude = 15;
        this.soundEnabled = true;
        this.buttonHeight = main.buttonHeight;
        this.buttonWidth = main.buttonWidth;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        // Set preferred size larger than the button itself to prevent clipping during hover lift and shadow rendering
        updatePreferredSize();

        // Mouse Interaction Listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                triggerHoverTransition(true);
                if (soundEnabled) playProceduralSound("hover");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                triggerHoverTransition(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                clickProgress = 1.0;
                if (soundEnabled) playProceduralSound("press");
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                clickProgress = 0.0;
                repaint();
            }
        });

        // Smooth 60fps Animation Timer
        animationTimer = new Timer(16, e -> {
            // Berechne die Geschwindigkeit direkt hier
            double smoothProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;

            // HIER GEÄNDERT: 1.0 bedeutet +100% (doppelt so schnell). Vorher war es 0.25.
            double speedMultiplier = 1.0 + (smoothProgress * 1.0);

            // Addiere die Geschwindigkeit direkt auf time
            time += 0.02 * waveSpeed * speedMultiplier;
            repaint();
        });
        animationTimer.start();
    }

    private void updatePreferredSize() {
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        revalidate();
    }
    // --- Customization Setters & Getters ---

    public void setWidth(int w) {
        this.buttonWidth = w;
        updatePreferredSize();
        repaint();
    }

    public void setHeight(int h) {
        this.buttonHeight = h;
        updatePreferredSize();
        repaint();
    }

    public void setButtonSize(int w, int h) {
        this.buttonWidth = w;
        this.buttonHeight = h;
        updatePreferredSize();
        repaint();
    }

    @Override
    public void setText(String text) {
        this.buttonText = text;
        super.setText(text);
        repaint();
    }

    @Override
    public String getText() {
        return this.buttonText;
    }

    public void setColors(Color primary, Color wave, Color text) {
        this.primaryColor = primary;
        this.waveColor = wave;
        this.textColor = text;
        repaint();
    }



    public void repaintButton() {
        this.primaryColor = main.themeColor[1];
        this.waveColor = main.themeColor[2];
        this.textColor = main.themeColor[3];
        this.waveSpeed = main.buttonWaveSpeed;
        if (main.vollbild){
            this.fontSize = main.extendedDialogFont.getSize();
            this.buttonWidth = (int) (main.buttonWidth*main.scaleFactor);
            this.buttonHeight = (int) (main.buttonHeight*main.scaleFactor);

        }else{
            this.fontSize = main.dialogFont.getSize();
            this.buttonWidth = main.buttonWidth;
            this.buttonHeight = main.buttonHeight;

        }
        updatePreferredSize();
        repaint();
    }

    public void repaintWaveSpeed() {

        this.waveSpeed = main.buttonWaveSpeed;
    }



    public void setPrimaryColor(Color color) {
        this.primaryColor = color;
        repaint();
    }

    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    public void setWaveColor(Color color) {
        this.waveColor = color;
        repaint();
    }

    public Color getWaveColor() {
        return this.waveColor;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        repaint();
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
        repaint();
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setBorderStyle(String style) {
        this.borderStyle = style;
        repaint();
    }

    public String getBorderStyle() {
        return this.borderStyle;
    }

    public void setWaveStyle(String style) {
        this.waveStyle = style;
        repaint();
    }

    public String getWaveStyle() {
        return this.waveStyle;
    }

    public void setWaveSpeed(double speed) {
        this.waveSpeed = speed;
    }

    public double getWaveSpeed() {
        return this.waveSpeed;
    }

    public void setWaveAmplitude(double amplitude) {
        this.waveAmplitude = amplitude;
        repaint();
    }

    public double getWaveAmplitude() {
        return this.waveAmplitude;
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    public boolean isSoundEnabled() {
        return this.soundEnabled;
    }


    public double getUIVolume() {
        return this.main.volumes[0];
    }

    // --- Internal Rendering & Logic ---

    private void triggerHoverTransition(boolean entering) {
        if (hoverTimer != null && hoverTimer.isRunning()) hoverTimer.stop();
        hoverTimer = new Timer(12, new ActionListener() {
            private double step = 0.22;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (entering) {
                    hoverProgress = Math.min(1.0, hoverProgress + step);
                    if (hoverProgress >= 1.0) hoverTimer.stop();
                } else {
                    hoverProgress = Math.max(0.0, hoverProgress - step);
                    if (hoverProgress <= 0.0) hoverTimer.stop();
                }
                repaint();
            }
        });
        hoverTimer.start();
    }

    // Lädt beide Pools einmalig in den Arbeitsspeicher
    private void initAudioClips() {
        if (clipsInitialized) return;
        try {
            // Hover-Pool befüllen
            for (int i = 0; i < HOVER_POOL_SIZE; i++) {
                cachedHoverClips[i] = generateClip("hover");
            }
            // NEU: Press-Pool befüllen
            for (int i = 0; i < PRESS_POOL_SIZE; i++) {
                cachedPressClips[i] = generateClip("press");
            }
            clipsInitialized = true;
        } catch (Exception e) {
            main.printErrOnDebug("Audio-Fehler: " + e.getMessage());
        }
    }

    // Generiert die rohen Audiodaten (unverändert, der Vollständigkeit halber dabei)
    private Clip generateClip(String type) throws Exception {
        float sampleRate = 44100f;
        int durationSamples = (int) (sampleRate * (type.equals("hover") ? 0.12 : 0.18));
        byte[] buf = new byte[durationSamples];
        for (int i = 0; i < buf.length; i++) {
            double t = i / sampleRate;
            double freq = 0, amp = 0;
            if (type.equals("hover")) {
                freq = 350.0 + (350.0 * (t / 0.15));
                amp = Math.max(0, 0.8 - (t / 0.11)) * 0.12;
                buf[i] = (byte) (Math.sin(2 * Math.PI * freq * t) * amp * 127);
            } else {
                freq = Math.max(60.0, 180.0 - (120.0 * (t / 0.18)));
                amp = Math.max(0, 1.0 - (t / 0.18)) * 0.25;
                double tf = t * freq;
                double tri = 2.0 * Math.abs(2.0 * (tf - Math.floor(tf + 0.5))) - 1.0;
                buf[i] = (byte) (tri * amp * 127);
            }
        }
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
        Clip clip = AudioSystem.getClip();
        clip.open(format, buf, 0, buf.length);
        return clip;
    }

    // Spielt den Ton ab und sucht im jeweiligen Pool nach einer freien Stimme
    private void playProceduralSound(String type) {
        Clip clip = null;

        if (type.equals("hover")) {
            // Sucht freien Hover-Clip
            for (Clip c : cachedHoverClips) {
                if (c != null && !c.isRunning()) {
                    clip = c;
                    break;
                }
            }
            if (clip == null) {
                clip = cachedHoverClips[nextHoverIndex];
                nextHoverIndex = (nextHoverIndex + 1) % HOVER_POOL_SIZE;
            }
        } else {
            // NEU: Sucht freien Press-Clip aus dem Klick-Pool
            for (Clip c : cachedPressClips) {
                if (c != null && !c.isRunning()) {
                    clip = c;
                    break;
                }
            }
            // Falls alle 4 parallel laufen, überschreiben wir den ältesten
            if (clip == null) {
                clip = cachedPressClips[nextPressIndex];
                nextPressIndex = (nextPressIndex + 1) % PRESS_POOL_SIZE;
            }
        }

        if (clip == null) return;

        // Dynamische Lautstärke anpassen
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = (float) Math.max(0.0001, main.volumes[0]);
            float dB = (float) (Math.log10(volume) * 20.0);
            gainControl.setValue(dB);
        } catch (Exception ex) {
            // Ignorieren
        }

        // Abspielen
        clip.setFramePosition(0);
        clip.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Proportional scaling values based on actual component height
        int shadowOffset = Math.max(4, (int)(h * 0.08));
        int hoverLiftMax = Math.max(3, (int)(h * 0.06));
        int radius = Math.max(4, (int)(h * 0.15));
        int innerRadius = Math.max(2, (int)(h * 0.10));
        int pixelSize = Math.max(2, (int)(h * 0.04));
        int borderThick = Math.max(2, (int)(h * 0.06));
        int borderThin = Math.max(1, (int)(h * 0.02));

        // Calculate dynamic button bounds to fit perfectly inside the component bounds
        // This prevents any clipping of borders or shadows regardless of layout manager
        int marginX = borderThick + 2;
        int marginY = borderThick + hoverLiftMax + 2;

        int btnW = w - marginX * 2;
        int btnH = h - marginY - borderThick - shadowOffset - 2;
        int btnX = marginX;
        int btnY = marginY;

        if (btnW <= 0 || btnH <= 0) {
            g2d.dispose();
            return;
        }

        double scale = 1.0 - (clickProgress * 0.06);
        g2d.translate(w / 2.0, h / 2.0);
        g2d.scale(scale, scale);
        g2d.translate(-w / 2.0, -h / 2.0);

        // Shadow
        g2d.setColor(new Color(0, 0, 0, 90));
        if (borderStyle.equals("Modern Rounded")) {
            g2d.fillRoundRect(btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset, btnH - shadowOffset, radius, radius);
        } else if (borderStyle.equals("Thick Pixel Rounded")) {
            fillPixelRoundedRect(g2d, btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset, btnH - shadowOffset, radius, pixelSize);
        } else {
            g2d.fillRect(btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset, btnH - shadowOffset);
        }

        // Hover Lift
        double smoothProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;
        double hoverYOffset = -hoverLiftMax * smoothProgress;
        g2d.translate(0, hoverYOffset);

        // Background
        drawWavyBackground(g2d, btnX, btnY, btnW - shadowOffset, btnH - shadowOffset, radius, pixelSize);

        // Borders
        g2d.setColor(textColor);
        if (borderStyle.equals("Modern Rounded")) {
            g2d.setStroke(new BasicStroke(borderThick));
            g2d.drawRoundRect(btnX + borderThin, btnY + borderThin, btnW - shadowOffset - borderThin*2, btnH - shadowOffset - borderThin*2, radius, radius);
            g2d.setStroke(new BasicStroke(borderThin));
            g2d.drawRoundRect(btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset*3, btnH - shadowOffset*3, innerRadius, innerRadius);
        } else if (borderStyle.equals("Thick Pixel Rounded")) {
            g2d.setStroke(new BasicStroke(borderThick));
            drawPixelRoundedRect(g2d, btnX + borderThin, btnY + borderThin, btnW - shadowOffset - borderThin*2, btnH - shadowOffset - borderThin*2, radius, pixelSize);
            g2d.setStroke(new BasicStroke(borderThin));
            drawPixelRoundedRect(g2d, btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset*3, btnH - shadowOffset*3, innerRadius, pixelSize);
        } else if (borderStyle.equals("Thick Pixel")) {
            g2d.setStroke(new BasicStroke(borderThick * 1.5f));
            g2d.drawRect(btnX + borderThick/2, btnY + borderThick/2, btnW - shadowOffset - borderThick, btnH - shadowOffset - borderThick);
        } else {
            g2d.setStroke(new BasicStroke(borderThick));
            g2d.drawRect(btnX + borderThin, btnY + borderThin, btnW - shadowOffset - borderThin*2, btnH - shadowOffset - borderThin*2);
            g2d.setStroke(new BasicStroke(borderThin));
            g2d.drawRect(btnX + shadowOffset, btnY + shadowOffset, btnW - shadowOffset*3, btnH - shadowOffset*3);
        }

        // Text
        if (main.vollbild){
            g2d.setFont(main.extendedDialogFont);
        }else {g2d.setFont(main.dialogFont);}
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(buttonText);
        int innerWidth = btnW - shadowOffset - borderThick * 4;

        int textOffsetX = 0;
        if (textWidth > innerWidth) {
            double now = (System.currentTimeMillis() - startTime) / 1000.0;
            double speed = 60.0;
            double maxScroll = textWidth - innerWidth;
            double pauseTime = 1.0;
            double scrollTime = maxScroll / speed;
            double cycleTime = (scrollTime + pauseTime) * 2;
            double t = now % cycleTime;

            if (t < pauseTime) {
                textOffsetX = 0;
            } else if (t < pauseTime + scrollTime) {
                textOffsetX = (int)((t - pauseTime) * speed);
            } else if (t < pauseTime * 2 + scrollTime) {
                textOffsetX = (int)maxScroll;
            } else {
                textOffsetX = (int)(maxScroll - (t - (pauseTime * 2 + scrollTime)) * speed);
            }
        }

        Shape oldClip = g2d.getClip();
        if (borderStyle.equals("Modern Rounded")) {
            g2d.clip(new RoundRectangle2D.Double(btnX + borderThick, btnY + borderThick, btnW - shadowOffset - borderThick*2, btnH - shadowOffset - borderThick*2, radius, radius));
        } else if (borderStyle.equals("Thick Pixel Rounded")) {
            g2d.clip(getPixelRoundedPath(btnX + borderThick, btnY + borderThick, btnW - shadowOffset - borderThick*2, btnH - shadowOffset - borderThick*2, radius, pixelSize));
        } else {
            g2d.clip(new Rectangle(btnX + borderThick, btnY + borderThick, btnW - shadowOffset - borderThick*2, btnH - shadowOffset - borderThick*2));
        }

        int drawX = textWidth > innerWidth ? btnX + borderThick * 2 - textOffsetX : btnX + (btnW - shadowOffset - textWidth) / 2;
        int drawY = btnY + ((btnH - shadowOffset - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(Color.BLACK);
        g2d.drawString(buttonText, drawX + 2, drawY + 2);
        g2d.setColor(textColor);
        g2d.drawString(buttonText, drawX, drawY);

        g2d.setClip(oldClip);

        g2d.dispose();
    }

    private void drawPixelRoundedRect(Graphics2D g2d, int x, int y, int w, int h, int r, int pixelSize) {
        g2d.draw(getPixelRoundedPath(x, y, w, h, r, pixelSize));
    }

    private void fillPixelRoundedRect(Graphics2D g2d, int x, int y, int w, int h, int r, int pixelSize) {
        g2d.fill(getPixelRoundedPath(x, y, w, h, r, pixelSize));
    }

    private Path2D.Double getPixelRoundedPath(int x, int y, int w, int h, int r, int pixelSize) {
        Path2D.Double path = new Path2D.Double();
        int px = Math.round((float)x / pixelSize) * pixelSize;
        int py = Math.round((float)y / pixelSize) * pixelSize;
        int pw = Math.round((float)w / pixelSize) * pixelSize;
        int ph = Math.round((float)h / pixelSize) * pixelSize;
        int pr = Math.round((float)r / pixelSize) * pixelSize;

        path.moveTo(px + pr, py);
        path.lineTo(px + pw - pr, py);
        path.lineTo(px + pw - pr + pixelSize, py + pixelSize);
        path.lineTo(px + pw - pixelSize, py + pr - pixelSize);
        path.lineTo(px + pw, py + pr);
        path.lineTo(px + pw, py + ph - pr);
        path.lineTo(px + pw - pixelSize, py + ph - pr + pixelSize);
        path.lineTo(px + pw - pr + pixelSize, py + ph - pixelSize);
        path.lineTo(px + pw - pr, py + ph);
        path.lineTo(px + pr, py + ph);
        path.lineTo(px + pr - pixelSize, py + ph - pixelSize);
        path.lineTo(px + pixelSize, py + ph - pr + pixelSize);
        path.lineTo(px, py + ph - pr);
        path.lineTo(px, py + pr);
        path.lineTo(px + pixelSize, py + pr - pixelSize);
        path.lineTo(px + pr - pixelSize, py + pixelSize);
        path.closePath();
        return path;
    }

    private void drawWavyBackground(Graphics2D g2d, int x, int y, int w, int h, int radius, int pixelSize) {
        double smoothProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;
        double currentAmplitude = waveAmplitude * (1.0 + smoothProgress * 0.15);

        // HIER WAR DER FEHLER: Das Clip-Setup wurde durch den Kommentar gelöscht und ist jetzt wieder da
        Shape clip = g2d.getClip();
        if (borderStyle.equals("Modern Rounded")) {
            g2d.setClip(new RoundRectangle2D.Double(x, y, w, h, radius, radius));
        } else if (borderStyle.equals("Thick Pixel Rounded")) {
            g2d.setClip(getPixelRoundedPath(x, y, w, h, radius, pixelSize));
        } else {
            g2d.setClip(new Rectangle(x, y, w, h));
        }

        int stripeSpacing = Math.max(10, (int)(h * 0.35));
        int stripeWidth = Math.max(5, stripeSpacing / 2);

        if (waveStyle.equals("Static")) {
            g2d.setColor(primaryColor);
            g2d.fillRect(x, y, w, h);
            g2d.setColor(waveColor);
            for (int i = -h; i < w; i += stripeSpacing) {
                int[] px = {x + i, x + i + stripeWidth, x + i + stripeWidth + h, x + i + h};
                int[] py = {y, y, y + h, y + h};
                g2d.fillPolygon(px, py, 4);
            }
        } else if (waveStyle.equals("Diagonal Scroll")) {
            g2d.setColor(primaryColor);
            g2d.fillRect(x, y, w, h);
            g2d.setColor(waveColor);
            double scroll = (time * 15) % (stripeSpacing * 2);
            for (int i = -h - (stripeSpacing*2); i < w + (stripeSpacing*2); i += stripeSpacing) {
                int[] px = {x + i + (int)scroll, x + i + stripeWidth + (int)scroll, x + i + stripeWidth + h + (int)scroll, x + i + h + (int)scroll};
                int[] py = {y, y, y + h, y + h};
                g2d.fillPolygon(px, py, 4);
            }
        } else if (waveStyle.equals("Horizontal Sine")) {
            for (int i = 0; i < h; i++) {
                double offset = Math.sin((i * 0.05) + time) * currentAmplitude;
                g2d.setColor(primaryColor);
                g2d.drawLine(x, y + i, x + w, y + i);
                g2d.setColor(waveColor);
                int stripeX = (int) ((i + offset) % stripeSpacing);
                for (int sx = stripeX - stripeSpacing; sx < w; sx += stripeSpacing) {
                    g2d.fillRect(x + sx, y + i, stripeWidth, 1);
                }
            }
        } else if (waveStyle.equals("Vertical Wave")) {
            for (int i = 0; i < w; i++) {
                double offset = Math.sin((i * 0.05) + time) * currentAmplitude;
                g2d.setColor(primaryColor);
                g2d.drawLine(x + i, y, x + i, y + h);
                g2d.setColor(waveColor);
                int stripeY = (int) ((i + offset) % stripeSpacing);
                for (int sy = stripeY - stripeSpacing; sy < h; sy += stripeSpacing) {
                    g2d.fillRect(x + i, y + sy, 1, stripeWidth);
                }
            }
        }
        g2d.setClip(clip);
    }
}