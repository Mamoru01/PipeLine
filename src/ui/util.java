package ui;

import model.ConfigurationGame;
import model.pipeline.ElementPipeline;
import model.pipeline.Pipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class util {

    static ImageIcon mergeImages(List<BufferedImage> imageList){
        BufferedImage empty = new BufferedImage(imageList.get(0).getWidth(), imageList.get(0).getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = empty.getGraphics();
        for (BufferedImage image : imageList){
            g.drawImage(image, 0, 0, null);
        }
        try {
            ImageIO.write(empty, "PNG", new File(ConfigurationGame.path, "simple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(empty);
    }

    public static ImageIcon createOnePipeImage(ElementPipeline pl) throws IOException {

        String path = ConfigurationGame.path;

        BufferedImage combined = new BufferedImage(
                pl.get_pipes().get(0).getImageDeametr().getWidth(),
                pl.get_pipes().get(0).getImageDeametr().getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Pipe pipe1 =  pl.get_pipes().get(0);
        BufferedImage m1 = pipe1.get_material().get_image();
        BufferedImage d1 = pipe1.getImageDeametr();
        BufferedImage t1 = pl.get_additionalImage();

        if (pipe1.get_direction() == Pipe.Direction.Left){
            m1 = createRotated90(m1);
            d1 = createRotated90(d1);
        }else if (pipe1.get_direction() == Pipe.Direction.Up){
            m1 = createRotated180(m1);
            d1 = createRotated180(d1);
        }else if (pipe1.get_direction() == Pipe.Direction.Right){
            m1 = createRotated270(m1);
            d1 = createRotated270(d1);
        }

        Graphics g = combined.getGraphics();
        g.drawImage(m1, 0, 0, null);
        g.drawImage(d1, 0, 0, null);
        g.drawImage(t1, 0, 0, null);


        ImageIO.write(combined, "PNG", new File(path, "combined1.png"));

        return new ImageIcon(combined);
    }

    public static ImageIcon createTwoPipeImage(ElementPipeline _pipe) throws IOException {

        BufferedImage combined = new BufferedImage(
                _pipe.get_pipes().get(0).getImageDeametr().getWidth(),
                _pipe.get_pipes().get(0).getImageDeametr().getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Pipe pipe1 =  _pipe.get_pipes().get(0);
        BufferedImage m1 = pipe1.get_material().get_image();
        BufferedImage d1 = pipe1.getImageDeametr();

        Pipe pipe2 =  _pipe.get_pipes().get(1);
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
        }else if(pipe1.get_direction() == Pipe.Direction.Right && pipe2.get_direction() == Pipe.Direction.Up){
            m1 = createRotated270(m1);
            d1 = createRotated270(d1);
            m2 = createFlipped(m2);
            d2 = createFlipped(d2);
        }


        Graphics g = combined.getGraphics();
        g.drawImage(m1, 0, 0, null);
        g.drawImage(d1, 0, 0, null);
        g.drawImage(m2, 0, 0, null);
        g.drawImage(d2, 0, 0, null);

        ImageIO.write(combined, "PNG", new File(ConfigurationGame.path, "pipe.png"));

        return new ImageIcon(combined);
    }

    //--------------------------------- Функции для работы с изображением --------------------------
    public static BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    public static BufferedImage createRotated90(BufferedImage image)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI/2, image.getWidth()/2, image.getHeight()/2);
        return createTransformed(image, at);
    }

    public static BufferedImage createRotated180(BufferedImage image)
    {
        return createRotated90(createRotated90(image));
    }

    public static BufferedImage createRotated270(BufferedImage image)
    {
        return createRotated90(createRotated180(image));
    }

    public static BufferedImage createTransformed(
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
