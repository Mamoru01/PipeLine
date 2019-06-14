package ui;

import model.ConfigurationGame;
import model.pipeline.Pipe;
import model.pipeline.PipeFitting;

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
        return util.createTwoPipeImage(_pipe);
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
