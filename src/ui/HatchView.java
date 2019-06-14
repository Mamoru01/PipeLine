package ui;

import model.pipeline.Hatch;
import javax.swing.*;
import java.awt.*;
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
    protected ImageIcon createImage() throws IOException {
        return util.createOnePipeImage(_hatch);
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