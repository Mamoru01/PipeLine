package ui;

import model.ConfigurationGame;
import model.pipeline.Hatch;
import model.pipeline.Pipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HatchView extends UnitView {

    private Hatch _hatch;

    public HatchView(Hatch hatch) {
        super();
        this._hatch = hatch;
        _hatch.addUnitPipeActionListener(this);

        try {
            this.setIcon(createImage());
        } catch (IOException e) {
            e.printStackTrace();
            this.setText("Error");
            this.setBackground(Color.WHITE);
        }

        setDescriprion();
    }

    @Override
    public Point getPoint() {
        return _hatch.get_point();
    }


    @Override
    protected  ImageIcon createImage() throws IOException {

        String path = ConfigurationGame.path;
        int w;
        int h;

        w = _hatch.get_pipes().get(0).getImageDeametr().getWidth();
        h = _hatch.get_pipes().get(0).getImageDeametr().getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Pipe pipe1 = _hatch.get_pipes().get(0);
        BufferedImage m1 = pipe1.get_material().get_image();
        BufferedImage d1 = pipe1.getImageDeametr();
        BufferedImage t1 = _hatch.get_additionalImage();

        if (pipe1.get_direction() == Pipe.Direction.Left) {
            m1 = createRotated90(m1);
            d1 = createRotated90(d1);
        } else if (pipe1.get_direction() == Pipe.Direction.Up) {
            m1 = createRotated180(m1);
            d1 = createRotated180(d1);
        } else if (pipe1.get_direction() == Pipe.Direction.Right) {
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
        _hatch.rotate();
    }

    @Override
    protected String getDescriprion() {
        return _hatch.getDescriptions();
    }
}