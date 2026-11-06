import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EarthBound-style Animated Slider with Modern Smoothness & Sound Effects.
 * Fully customizable at runtime with standard Swing-like setters.
 * Compatible with IntelliJ IDEA and standard Java Swing projects.
 *
 * Von KI generiert und nochmal selbst angepasst
 */
public class BrikSlider extends JComponent {
    private double time = 0;
    private double hoverProgress = 0.0;
    private double thumbHoverProgress = 0.0;
    private double clickProgress = 0.0;
    private Timer animationTimer;
    private Timer hoverTimer;
    private Timer thumbHoverTimer;
    private boolean isHovered = false;
    private boolean isThumbHovered = false;
    private boolean isDragging = false;

    private long lastSoundTime = 0;
    private int lastSoundValue = -1;

    private int value;
    private int min;
    private int max;
    private int step;
    private final List<ChangeListener> changeListeners = new ArrayList<>();

    private String labelText;
    private int fontSize;
    private Color trackColor;
    private Color waveColor;
    private Color thumbColor;
    private Color textColor;
    private String borderStyle;
    private String waveStyle;
    private double waveSpeed;
    private double waveAmplitude;
    private boolean soundEnabled;
    private int trackHeight;
    private int trackWidth;

    private Main main;

    public BrikSlider(Main main) {
        this.main = main;

        this.labelText = "VOLUME";
        this.min = 0;
        this.max = 100;
        this.step = 1;
        this.value = 50;
        this.trackColor = main.themeColor[1];
        this.waveColor = main.themeColor[2];
        this.thumbColor = main.themeColor[4];
        this.textColor = main.themeColor[3];
        this.borderStyle = "Modern Rounded";
        this.waveStyle = "Horizontal Sine";
        this.waveSpeed = main.buttonWaveSpeed;
        this.waveAmplitude = 10;
        this.soundEnabled = true;
        this.trackHeight = main.sliderHeight;
        this.trackWidth = main.sliderWidth;
        this.fontSize = main.dialogFont.getSize();

        setPreferredSize(new Dimension(460, 144));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isDragging) {
                    if (isHovered) {
                        isHovered = false;
                        triggerHoverTransition(false);
                    }
                    if (isThumbHovered) {
                        isThumbHovered = false;
                        triggerThumbHoverTransition(false);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Rectangle track = getTrackBounds();
                Rectangle thumb = getThumbBounds();

                double smoothHoverProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;
                int trackYOffset = (int)(-6.0 * smoothHoverProgress);
                track.translate(0, trackYOffset);

                double smoothThumbProgress = (1.0 - Math.cos(thumbHoverProgress * Math.PI)) / 2.0;
                int thumbYOffset = (int)(-4.0 * smoothThumbProgress) + trackYOffset;
                thumb.translate(0, thumbYOffset);

                if (thumb.contains(e.getPoint()) || track.contains(e.getPoint())) {
                    isDragging = true;
                    clickProgress = 1.0;

                    isHovered = true;
                    isThumbHovered = true;
                    triggerHoverTransition(true);
                    triggerThumbHoverTransition(true);

                    if (!thumb.contains(e.getPoint())) {
                        updateValueFromMouse(e.getX());
                    }
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging) {
                    isDragging = false;
                    clickProgress = 0.0;
                    if (soundEnabled) {
                        playProceduralSound("release", value);
                    }
                    checkHoverStates(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    updateValueFromMouse(e.getX());
                }
                checkHoverStates(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                checkHoverStates(e.getPoint());
            }

            private void checkHoverStates(Point p) {
                if (isDragging) {
                    if (!isHovered) {
                        isHovered = true;
                        triggerHoverTransition(true);
                    }
                    if (!isThumbHovered) {
                        isThumbHovered = true;
                        triggerThumbHoverTransition(true);
                    }
                    return;
                }

                Rectangle track = getTrackBounds();
                Rectangle thumb = getThumbBounds();

                double smoothHoverProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;
                int trackYOffset = (int)(-6.0 * smoothHoverProgress);
                track.translate(0, trackYOffset);

                double smoothThumbProgress = (1.0 - Math.cos(thumbHoverProgress * Math.PI)) / 2.0;
                int thumbYOffset = (int)(-4.0 * smoothThumbProgress) + trackYOffset;
                thumb.translate(0, thumbYOffset);

                boolean overTrack = track.contains(p);
                boolean overThumb = thumb.contains(p);

                if (overTrack && !isHovered) {
                    isHovered = true;
                    triggerHoverTransition(true);
                    if (soundEnabled) {
                        playProceduralSound("hover", value);
                    }
                } else if (!overTrack && isHovered) {
                    isHovered = false;
                    triggerHoverTransition(false);
                }

                if (overThumb && !isThumbHovered) {
                    isThumbHovered = true;
                    triggerThumbHoverTransition(true);
                } else if (!overThumb && isThumbHovered) {
                    isThumbHovered = false;
                    triggerThumbHoverTransition(false);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        animationTimer = new Timer(16, e -> {
            double speedMultiplier = 1.0 + (hoverProgress * 0.5);
            time += 0.02 * waveSpeed * speedMultiplier;
            repaint();
        });
        animationTimer.start();
    }

    public void setValue(int val) {
        int oldVal = this.value;
        int steppedVal = min + Math.round((float)(val - min) / step) * step;
        this.value = Math.max(min, Math.min(max, steppedVal));

        if (oldVal != this.value) {
            fireStateChanged();
            if (soundEnabled && isDragging) {
                long now = System.currentTimeMillis();
                if (now - lastSoundTime > 50 && this.value != lastSoundValue) {
                    playProceduralSound("drag", this.value);
                    lastSoundTime = now;
                    lastSoundValue = this.value;
                }
            }
            repaint();
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setStep(int step) {
        this.step = Math.max(1, step);
        setValue(this.value);
    }

    public int getStep() {
        return this.step;
    }

    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
        setValue(this.value);
    }

    public void setColors(Color track, Color thumb, Color wave, Color text) {
        this.trackColor = track;
        this.thumbColor = thumb;
        this.waveColor = wave;
        this.textColor = text;
        repaint();
    }

    public void setLabelText(String text) {
        this.labelText = text;
        repaint();
    }

    public void setTrackHeight(int height) {
        this.trackHeight = Math.max(4, height);
        repaint();
    }

    public int getTrackHeight() {
        return this.trackHeight;
    }

    public void setTrackWidth(int width) {
        this.trackWidth = Math.max(12, width);
        repaint();
    }

    public void setWidth(int width) {
        setTrackWidth(width);
    }

    public void setTrackSize(int width, int height) {
        this.trackHeight = Math.max(4, height);
        this.trackWidth = Math.max(16, width);
        repaint();
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public void repaintSlider() {
        this.trackColor = main.themeColor[1];
        this.waveColor = main.themeColor[2];
        this.textColor = main.themeColor[3];
        this.thumbColor = main.themeColor[4];
        this.waveSpeed = main.buttonWaveSpeed;

        if (main.vollbild){
            this.fontSize = main.extendedDialogFont.getSize();
            this.trackWidth =  (main.sliderWidth*main.scaleFactor);
            this.trackHeight = (int) (main.sliderHeight*main.scaleFactor*0.8);
        }else{
            this.fontSize = main.dialogFont.getSize();
            this.trackWidth = main.sliderWidth;
            this.trackHeight = main.sliderHeight;
        }

        revalidate();
        repaint();

    }

    public void updatePreferredSize() {
        setPreferredSize(new Dimension(trackWidth, trackHeight));
        setMaximumSize(getPreferredSize());

        revalidate();
    }
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }

    private void fireStateChanged() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener l : changeListeners) {
            l.stateChanged(event);
        }
    }

    private Rectangle getTrackBounds() {
        int w = getWidth();
        int h = getHeight();
        int trackH = this.trackHeight;
        int thumbW = Math.max(16, (int)(trackH * 1.33));
        int thumbH = Math.max(24, trackH * 2);
        int fontSize = Math.max(14, (int)(trackH * 0.85));

        int maxTrackW = w - thumbW - 24;
        int trackW = Math.min(this.trackWidth, maxTrackW);

        if (trackW < 16) trackW = 16;
        int trackX = (w - trackW) / 2;

        int idealTrackY = (h - trackH) / 2 + (15 + fontSize) / 2;

        int minY = thumbH / 2 - trackH / 2 + fontSize + 26;
        int maxY = h - trackH / 2 - thumbH / 2 - 10;

        int trackY = idealTrackY;
        if (maxY >= minY) {
            trackY = Math.max(minY, Math.min(maxY, idealTrackY));
        } else {
            trackY = minY;
        }

        return new Rectangle(trackX, trackY, trackW, trackH);
    }

    private Rectangle getThumbBounds() {
        Rectangle track = getTrackBounds();
        double pct = (double)(value - min) / (max - min);
        int thumbH = Math.max(24, track.height * 2);
        int thumbW = Math.max(16, (int)(track.height * 1.33));
        int thumbX = (int)(track.x + pct * track.width - thumbW / 2);
        int thumbY = track.y + track.height / 2 - thumbH / 2;
        return new Rectangle(thumbX, thumbY, thumbW, thumbH);
    }

    private void updateValueFromMouse(int mouseX) {
        Rectangle track = getTrackBounds();
        double pct = (double)(mouseX - track.x) / track.width;
        pct = Math.max(0.0, Math.min(1.0, pct));
        setValue((int)(min + pct * (max - min)));
    }

    private void triggerHoverTransition(boolean entering) {
        if (hoverTimer != null && hoverTimer.isRunning()) hoverTimer.stop();
        hoverTimer = new Timer(12, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (entering) {
                    hoverProgress += 0.12;
                    if (hoverProgress >= 1.0) { hoverProgress = 1.0; hoverTimer.stop(); }
                } else {
                    hoverProgress -= 0.08;
                    if (hoverProgress <= 0.0) { hoverProgress = 0.0; hoverTimer.stop(); }
                }
                repaint();
            }
        });
        hoverTimer.start();
    }

    private void triggerThumbHoverTransition(boolean entering) {
        if (thumbHoverTimer != null && thumbHoverTimer.isRunning()) thumbHoverTimer.stop();
        thumbHoverTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (entering) {
                    thumbHoverProgress += 0.12;
                    if (thumbHoverProgress >= 1.0) { thumbHoverProgress = 1.0; thumbHoverTimer.stop(); }
                } else {
                    thumbHoverProgress -= 0.12;
                    if (thumbHoverProgress <= 0.0) { thumbHoverProgress = 0.0; thumbHoverTimer.stop(); }
                }
                repaint();
            }
        });
        thumbHoverTimer.start();
    }

    private void playProceduralSound(String type, int val) {
        new Thread(() -> {
            try {
                float sampleRate = 22050f;
                double duration = type.equals("drag") ? 0.05 : (type.equals("hover") ? 0.10 : 0.12);
                int durationSamples = (int) (sampleRate * duration);
                byte[] buf = new byte[durationSamples];

                for (int i = 0; i < buf.length; i++) {
                    double t = i / sampleRate;
                    double freq = 0;
                    double amp = 0;

                    if (type.equals("hover")) {
                        freq = 350.0 + (350.0 * (t / 0.10));
                        amp = Math.max(0, 1.0 - (t / 0.10)) * 0.12 * main.volumes[0];
                        buf[i] = (byte) (Math.sin(2 * Math.PI * freq * t) * amp * 127);
                    } else if (type.equals("drag")) {
                        double pct = (double)(val - min) / (max - min);
                        freq = 220.0 + pct * 440.0;
                        amp = Math.max(0, 1.0 - (t / 0.05)) * 0.15 * main.volumes[0];
                        buf[i] = (byte) (Math.sin(2 * Math.PI * freq * t) * amp * 127);
                    } else {
                        freq = Math.max(90.0, 180.0 - (90.0 * (t / 0.12)));
                        amp = Math.max(0, 1.0 - (t / 0.12)) * 0.2 * main.volumes[0];
                        double tf = t * freq;
                        double tri = 2.0 * Math.abs(2.0 * (tf - Math.floor(tf + 0.5))) - 1.0;
                        buf[i] = (byte) (tri * amp * 127);
                    }
                }

                AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(format);
                sdl.open(format, buf.length);
                sdl.start();
                sdl.write(buf, 0, buf.length);
                sdl.drain();
                sdl.close();
            } catch (Exception ex) {
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Rectangle track = getTrackBounds();
        Rectangle thumb = getThumbBounds();

        if (main.vollbild){
            g2d.setFont(main.extendettextfont);
        }else {g2d.setFont(main.textfont);}

        int textY = track.y + track.height / 2 - thumb.height / 2 - 22;
        String valStr = String.valueOf(value);
        FontMetrics fm = g2d.getFontMetrics();
        int shadowOffset = Math.max(2, Math.round(fontSize * 0.08f));

        g2d.setColor(Color.BLACK);
        g2d.drawString(labelText, track.x + shadowOffset, textY + shadowOffset);
        g2d.drawString(valStr, track.x + track.width - fm.stringWidth(valStr) + shadowOffset, textY + shadowOffset);

        g2d.setColor(textColor);
        g2d.drawString(labelText, track.x, textY);
        g2d.drawString(valStr, track.x + track.width - fm.stringWidth(valStr), textY);

        double scale = 1.0 - (clickProgress * 0.04);
        g2d.translate(w / 2.0, h / 2.0);
        g2d.scale(scale, scale);
        g2d.translate(-w / 2.0, -h / 2.0);

        g2d.setColor(new Color(0, 0, 0, 90));
        if (borderStyle.equals("Modern Rounded")) {
            g2d.fillRoundRect(track.x + 6, track.y + 6, track.width, track.height, 8, 8);
        } else {
            g2d.fillRect(track.x + 6, track.y + 6, track.width, track.height);
        }

        double smoothHoverProgress = (1.0 - Math.cos(hoverProgress * Math.PI)) / 2.0;
        double trackYOffset = -6.0 * smoothHoverProgress;
        g2d.translate(0, trackYOffset);

        drawWavyBackground(g2d, track.x, track.y, track.width, track.height);

        g2d.setColor(textColor);
        g2d.setStroke(new BasicStroke(3f));
        if (borderStyle.equals("Modern Rounded")) {
            g2d.drawRoundRect(track.x + 1, track.y + 1, track.width - 2, track.height - 2, 8, 8);
        } else {
            g2d.drawRect(track.x + 1, track.y + 1, track.width - 2, track.height - 2);
        }

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRect(thumb.x + 4, thumb.y + 4 - (int)trackYOffset, thumb.width, thumb.height);

        double smoothThumbProgress = (1.0 - Math.cos(thumbHoverProgress * Math.PI)) / 2.0;
        double thumbYOffset = -4.0 * smoothThumbProgress;
        g2d.translate(0, thumbYOffset);

        g2d.setColor(thumbColor);
        g2d.fillRect(thumb.x, thumb.y, thumb.width, thumb.height);

        g2d.setColor(textColor);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRect(thumb.x + 1, thumb.y + 1, thumb.width - 2, thumb.height - 2);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawRect(thumb.x + 4, thumb.y + 4, thumb.width - 8, thumb.height - 8);

        g2d.setColor(textColor);
        g2d.fillRect(thumb.x + thumb.width / 2 - 2, thumb.y + 6, 4, thumb.height - 12);

        g2d.dispose();
    }

    private void drawWavyBackground(Graphics2D g2d, int x, int y, int w, int h) {
        Shape clip = g2d.getClip();
        if (borderStyle.equals("Modern Rounded")) {
            g2d.setClip(new RoundRectangle2D.Double(x, y, w, h, 8, 8));
        } else {
            g2d.setClip(new Rectangle(x, y, w, h));
        }

        double currentAmplitude = waveAmplitude * (1.0 + hoverProgress * 0.2);

        for (int i = 0; i < h; i++) {
            double offset = 0;
            if (waveStyle.equals("Horizontal Sine")) {
                offset = Math.sin((i * 0.1) + time) * currentAmplitude;
            }

            g2d.setColor(trackColor);
            g2d.drawLine(x, y + i, x + w, y + i);

            g2d.setColor(waveColor);
            int stripeX = (int) ((i + offset) % 32);
            for (int sx = stripeX - 32; sx < w; sx += 32) {
                g2d.fillRect(x + sx, y + i, 16, 1);
            }
        }
        g2d.setClip(clip);
    }
}