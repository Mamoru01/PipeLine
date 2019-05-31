package ui;

import model.events.UnitPipeActionListner;
import model.events.ViewActionListner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class UnitView extends JButton implements UnitPipeActionListner {

    public UnitView() {
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.darkGray);
        addActionListener(new ButtonAction());
    }

    protected void setDescriprion(){
        setToolTipText(getDescriprion());
    }

    protected abstract String getDescriprion();

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
        fireUpdateView();
    }
    public void pourWater(){
        setBackground(Color.RED);
        fireUpdateView();
    }

    // ---------------------- Порождает события -----------------------------

    ArrayList<ViewActionListner> PlayerListeners = new ArrayList();

    // Присоединяет слушателя
    public void addViewActionListener(ViewActionListner l) {
        PlayerListeners.add(l);
    }

    // Отсоединяет слушателя
    public void removeViewActionListener(ViewActionListner l) {
        PlayerListeners.remove(l);
    }

    // Оповещает слушателей о событии
    protected void fireUpdateView() {

        for (ViewActionListner p:PlayerListeners){
            p.updateView();
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
