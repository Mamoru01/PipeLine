package ui;

import model.ConfigurationGame;
import model.pipe.Pipe;
import model.pipe.PipeFitting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PipeFittingView extends UnitView {

    private PipeFitting _pipe;

    public PipeFittingView(PipeFitting pipeFitting) {
        super();
        this._pipe = pipeFitting;
        _pipe.addUnitPipeActionListener(this);

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
        return _pipe.get_point();
    }

    @Override
    protected ImageIcon createImage() throws IOException {

        String path = ConfigurationGame.path;
        int w;
        int h;

        w = _pipe.get_pipes().get(0).getImageDeametr().getWidth();
        h = _pipe.get_pipes().get(0).getImageDeametr().getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

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

        ImageIO.write(combined, "PNG", new File(path, "combined1.png"));

        return new ImageIcon(combined);
    }

    @Override
    public void rotated() {
        this.rotatedIcon();
        _pipe.rotate();
    }

    @Override
    protected String getDescriprion() {
        return _pipe.getDescriptions();
    }

}
