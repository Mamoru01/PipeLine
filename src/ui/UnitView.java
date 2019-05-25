package ui;

import model.events.UnitPipeActionListner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class UnitView extends JButton implements UnitPipeActionListner {

    public UnitView() {
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.darkGray);
        addActionListener(new ButtonAction());
    }

    protected abstract ImageIcon createImage() throws IOException;

    public abstract Point getPoint();

    protected BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    protected BufferedImage createRotated90(BufferedImage image)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI/2, image.getWidth()/2, image.getHeight()/2);
        return createTransformed(image, at);
    }

    protected BufferedImage createRotated180(BufferedImage image)
    {
        return createRotated90(createRotated90(image));
    }

    protected BufferedImage createRotated270(BufferedImage image)
    {
        return createRotated90(createRotated180(image));
    }

    protected static BufferedImage createTransformed(
            BufferedImage image, AffineTransform at)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public abstract void rotated();

    protected void rotatedIcon(){
        BufferedImage combined = new BufferedImage(getIcon().getIconWidth(), getIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(((ImageIcon)getIcon()).getImage(), 0, 0, null);

        setIcon(new ImageIcon(createRotated90(combined)));
    }

    public class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            rotated();
        }
    }

    public void conductWater(){
        setBackground(Color.CYAN);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void pourWater(){
        setBackground(Color.RED);
        repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
