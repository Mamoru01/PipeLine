package ui;

import model.ConfigurationGame;
import model.pipe.Pipe;
import model.pipe.Tap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TapView extends UnitView{

    Tap _tap;

    public TapView(Tap tap) {
        super();
        this._tap = tap;

        try {
            this.setIcon(createImage());
        } catch (IOException e) {
            e.printStackTrace();
            this.setText("Error");
            this.setBackground(Color.WHITE);
        }

    }

    @Override
    public Point getPoint() {
        return _tap.get_point();
    }

    @Override
    protected ImageIcon createImage() throws IOException {

        String path = ConfigurationGame.path;
        int w;
        int h;

        w = _tap.get_pipes().get(0).getImageDeametr().getWidth();
        h = _tap.get_pipes().get(0).getImageDeametr().getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Pipe pipe1 =  _tap.get_pipes().get(0);
        BufferedImage m1 = pipe1.get_material().get_image();
        BufferedImage d1 = pipe1.getImageDeametr();
        BufferedImage t1 = _tap.get_additionalImage();

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

    @Override
    public void rotated() {
        this.rotatedIcon();
        _tap.rotate();
    }

}
