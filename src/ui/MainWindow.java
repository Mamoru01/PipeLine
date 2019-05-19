package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow  extends JFrame{

    private JPanel fieldPanel = new JPanel();

    private final int CELL_SIZE = 100;

    int x = 5;

    public MainWindow() {


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);

        JLabel logoLabel = new JLabel();

        getContentPane().add(logoLabel, BorderLayout.CENTER);
        pack();

        Box mainBox = Box.createVerticalBox();
        mainBox.setBackground(Color.black);
        // Игровое поле
        mainBox.add(Box.createVerticalStrut(10));
        fieldPanel.setDoubleBuffered(true);
        createField();
        setEnabledField(true);
        mainBox.add(fieldPanel);

        setContentPane(mainBox);
        pack();
        setResizable(false);

    }

    private void createField(){

        fieldPanel.setDoubleBuffered(true);
        fieldPanel.setLayout(new GridLayout(x, x));

        Dimension fieldDimension = new Dimension(CELL_SIZE*x, CELL_SIZE*x);

        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);

        repaintField();
    }

    public void repaintField() {

        fieldPanel.removeAll();

        for (int row = 1; row <= x; row++)
        {
            for (int col = 1; col <= x; col++)
            {
                JButton button = new JButton("");
                button.setFocusable(false);
                button.setBackground(Color.WHITE);
                button.setEnabled(true);
                try {
                    button.setIcon(createPipe());
                }catch (Exception e) {
                   e.printStackTrace();
                }
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setBackground(Color.black);
                //button.setContentAreaFilled(false);
                fieldPanel.add(button);
                button.addActionListener(new ClickListener());
            }
        }

        fieldPanel.validate();
    }


    private void setEnabledField(boolean on){

        Component comp[] = fieldPanel.getComponents();
        for(Component c : comp)
        {    c.setEnabled(on);   }
    }



    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
        }
    }

    private ImageIcon createPipe() throws IOException {
        String path = "D:/VSTU/ООП/pipeline/1x";

        // load source images
        BufferedImage image = ImageIO.read(new File(path, "stal100.png"));
        BufferedImage overlay = ImageIO.read(new File(path, "med100.png"));
        BufferedImage inter = ImageIO.read(new File(path, "inter.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(createRotated(createFlipped(overlay)), 0, 0, null);
        g.drawImage(createRotated(createFlipped(inter)), 0, 0, null);
        g.drawImage(inter, 0, 0, null);
        // Save as new image
        ImageIO.write(combined, "PNG", new File(path, "combined.png"));

        return new ImageIcon(combined);
    }

    private static BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    private static BufferedImage createRotated(BufferedImage image)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI/2, image.getWidth()/2, image.getHeight()/2);
        return createTransformed(image, at);
    }

    private static BufferedImage createTransformed(
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
}
