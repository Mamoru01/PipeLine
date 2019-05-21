package ui;

import model.ConfigurationGame;
import model.pipe.Pipe;
import model.pipe.PipeFitting;
import model.pipe.PipeLine;
import model.pipe.Segment;

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
    private PipeLine _pipeline;
    private final int CELL_SIZE = 100;

    public MainWindow() {

        _pipeline = new PipeLine();

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
        fieldPanel.setLayout(new GridLayout(_pipeline.get_dimension().height, _pipeline.get_dimension().width));

        Dimension fieldDimension = new Dimension(CELL_SIZE*_pipeline.get_dimension().width,
                CELL_SIZE*_pipeline.get_dimension().height);

        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);

        repaintField();
    }

    public void repaintField() {

        fieldPanel.removeAll();

        for (int row = 1; row <= _pipeline.get_dimension().height; row++)
        {
            for (int col = 1; col <= _pipeline.get_dimension().width; col++)
            {
                JButton button = new JButton("");
                button.setFocusable(false);
                button.setBackground(Color.WHITE);
                button.setEnabled(true);
                try {
                    button.setIcon(createPipeFitting((PipeFitting)_pipeline.getSegment(new Point(row,col))));
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
            BufferedImage combined = new BufferedImage(button.getIcon().getIconWidth(), button.getIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g = combined.getGraphics();
            g.drawImage(((ImageIcon)button.getIcon()).getImage(), 0, 0, null);

            button.setIcon(new ImageIcon(createRotated90(combined)));
        }
    }

    private ImageIcon createPipe() throws IOException {
        String path = "D:/VSTU/ООП/pipeline/1x";

        // load source images
        BufferedImage image = ImageIO.read(new File(path, "BlackMatter.png"));
        BufferedImage overlay = ImageIO.read(new File(path, "Steel.png"));
        BufferedImage inter = ImageIO.read(new File(path, "inter.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(createRotated90(createFlipped(overlay)), 0, 0, null);
        g.drawImage(createRotated90(createFlipped(inter)), 0, 0, null);
        g.drawImage(inter, 0, 0, null);
        // Save as new image
        ImageIO.write(combined, "PNG", new File(path, "combined.png"));

        return new ImageIcon(combined);
    }


    private ImageIcon createPipeFitting(PipeFitting s) throws IOException {

        String path = ConfigurationGame.path;
        int w;
        int h;

        w = s.get_pipes().get(0).getImageDeametr().getWidth();
        h = s.get_pipes().get(0).getImageDeametr().getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Pipe pipe1 =  s.get_pipes().get(0);
        BufferedImage m1 = pipe1.get_material().get_image();
        BufferedImage d1 = pipe1.getImageDeametr();

        Pipe pipe2 =  s.get_pipes().get(1);
        BufferedImage m2 = pipe2.get_material().get_image();
        BufferedImage d2 = pipe2.getImageDeametr();

        if (pipe1.get_direction() == Pipe.Direction.Down && pipe2.get_direction() == Pipe.Direction.Right){
            m2 = createRotated90(createFlipped(m2));
            d2 = createRotated90(createFlipped(d2));
        }else if (pipe1.get_direction() == Pipe.Direction.Down && pipe2.get_direction() == Pipe.Direction.Up){
            m2 = createRotated180(m2);
            d2 = createRotated180(d2);
        }else if(pipe1.get_direction() == Pipe.Direction.Down && pipe2.get_direction() == Pipe.Direction.Left){
            m1 = createRotated180(createFlipped(m1));
            d1 = createRotated180(createFlipped(d1));
            m2 = createRotated90(m2);
            d2 = createRotated90(d2);
        }else if(pipe1.get_direction() == Pipe.Direction.Left && pipe2.get_direction() == Pipe.Direction.Up){
            m1 = createRotated270(createFlipped(m1));
            d1 = createRotated270(createFlipped(d1));
            m2 = createRotated180(m2);
            d2 = createRotated180(d2);
        }else if(pipe1.get_direction() == Pipe.Direction.Left && pipe2.get_direction() == Pipe.Direction.Right){
            m1 = createRotated90(m1);
            d1 = createRotated90(d1);
            m2 = createRotated270(m2);
            d2 = createRotated270(d2);
        }else if(pipe1.get_direction() == Pipe.Direction.Left && pipe2.get_direction() == Pipe.Direction.Down){
            m1 = createRotated90(m1);
            d1 = createRotated90(d1);
            m2 = createRotated180(createFlipped(m2));
            d2 = createRotated180(createFlipped(d2));
        }else if(pipe1.get_direction() == Pipe.Direction.Up && pipe2.get_direction() == Pipe.Direction.Right){
            m1 = createFlipped(m1);
            d1 = createFlipped(d1);
            m2 = createRotated270(m2);
            d2 = createRotated270(d2);
        }else if(pipe1.get_direction() == Pipe.Direction.Up && pipe2.get_direction() == Pipe.Direction.Down){
            m1 = createRotated180(m1);
            d1 = createRotated180(d1);
        }else if(pipe1.get_direction() == Pipe.Direction.Up && pipe2.get_direction() == Pipe.Direction.Left){
            m1 = createRotated180(m1);
            d1 = createRotated180(d1);
            m2 = createRotated270(createFlipped(m2));
            d2 = createRotated270(createFlipped(d2));
        }else if(pipe1.get_direction() == Pipe.Direction.Right && pipe2.get_direction() == Pipe.Direction.Down){
            m1 = createRotated90(createFlipped(m1));
            d1 = createRotated90(createFlipped(d1));
        }else if(pipe1.get_direction() == Pipe.Direction.Right && pipe2.get_direction() == Pipe.Direction.Left){
            m1 = createRotated270(m1);
            d1 = createRotated270(d1);
            m2 = createRotated90(m2);
            d2 = createRotated90(d2);
        }


        Graphics g = combined.getGraphics();
        g.drawImage(m1, 0, 0, null);
        g.drawImage(d1, 0, 0, null);
        g.drawImage(m2, 0, 0, null);
        g.drawImage(d2, 0, 0, null);

        ImageIO.write(combined, "PNG", new File(path, "combined1.png"));

        return new ImageIcon(combined);
    }

    private static BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    private static BufferedImage createRotated90(BufferedImage image)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI/2, image.getWidth()/2, image.getHeight()/2);
        return createTransformed(image, at);
    }

    private static BufferedImage createRotated180(BufferedImage image)
    {
        return createRotated90(createRotated90(image));
    }

    private static BufferedImage createRotated270(BufferedImage image)
    {
        return createRotated90(createRotated180(image));
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
