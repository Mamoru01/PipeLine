package ui;

import model.pipe.Hatch;
import model.pipe.PipeFitting;
import model.pipe.PipeLine;
import model.pipe.Tap;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {

    private JPanel _fieldPanel = new JPanel();
    private JButton _readyButton = new JButton("Готово");
    private JProgressBar _progressBar = new JProgressBar(0, 1000);
    private PipeLine _pipeline;
    private final int CELL_SIZE = 100;

    public GamePanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.darkGray);

        JLabel logoLabel = new JLabel("PipeLine");

        getContentPane().add(logoLabel, BorderLayout.CENTER);
        pack();

        Box mainBox = Box.createVerticalBox();

        // Шапка
        mainBox.add(Box.createVerticalStrut(10));
        _readyButton.setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        panel.add(_progressBar);
        panel.add(_readyButton);
        panel.setBackground(Color.WHITE);

        mainBox.add(panel, BorderLayout.PAGE_START);

        _progressBar.setSize(panel.getSize().width - _readyButton.getSize().width,_readyButton.getSize().height);

        //Создать трубопровод
        _pipeline = new PipeLine();

        // Игровое поле
        mainBox.add(Box.createVerticalStrut(10));
        _fieldPanel.setDoubleBuffered(true);
        createField();
        setEnabledField(true);
        mainBox.add(_fieldPanel);

        setContentPane(mainBox);
        pack();
        setResizable(false);
    }


    private void createField(){
        _fieldPanel.setDoubleBuffered(true);
        _fieldPanel.setLayout(new GridLayout(_pipeline.get_dimension().height, _pipeline.get_dimension().width));

        Dimension fieldDimension = new Dimension(CELL_SIZE*_pipeline.get_dimension().width,
                CELL_SIZE*_pipeline.get_dimension().height);

        _fieldPanel.setPreferredSize(fieldDimension);
        _fieldPanel.setMinimumSize(fieldDimension);
        _fieldPanel.setMaximumSize(fieldDimension);

        repaintField();
    }

    public void repaintField() {

        _fieldPanel.removeAll();
        UnitView unitView;

        for (int row = 1; row <= _pipeline.get_dimension().height; row++)
        {
            for (int col = 1; col <= _pipeline.get_dimension().width; col++)
            {
                try {
                    if (_pipeline.getSegment(new Point(row,col)) instanceof PipeFitting){
                        unitView = new PipeFittingView((PipeFitting)_pipeline.getSegment(new Point(row,col)));
                    }else if (_pipeline.getSegment(new Point(row,col)) instanceof Tap){
                        unitView = new TapView((Tap)_pipeline.getSegment(new Point(row,col)));
                    }else/* if (_pipeline.getSegment(new Point(row,col)) instanceof Hatch)*/{
                        unitView = new HatchView((Hatch)_pipeline.getSegment(new Point(row,col)));
                    }

                    _fieldPanel.add(unitView);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        _fieldPanel.validate();
    }


    private void setEnabledField(boolean on){
        _progressBar.setEnabled(on);
        _readyButton.setEnabled(on);
        Component comp[] = _fieldPanel.getComponents();
        for(Component c : comp) {    c.setEnabled(on);   }
    }

}
