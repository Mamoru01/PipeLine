package ui;


import model.pipeline.Tap;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TapView extends UnitView{

    private Tap _tap;

    public TapView(Tap tap) {
        super();

        this._tap = tap;
        _tap.addUnitPipeActionListener(this);

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
        return _tap.get_point();
    }

    @Override
    protected ImageIcon createImage() throws IOException {
        return util.createOnePipeImage(_tap);
    }

    @Override
    public void rotated() {
        this.rotatedIcon();
        _tap.rotate();
    }

    @Override
    protected String getDescriprion() {
        return _tap.getDescriptions();
    }
}
