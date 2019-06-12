package ui;

import model.events.ViewActionListner;
import model.pipeline.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Игровое окно
 */
public class GamePanel extends JFrame implements ViewActionListner{

    private JMenuBar menu = null;
    private  String fileItems[];

    private final JPanel _fieldPanel = new JPanel();
    private final JButton _readyButton = new JButton("Ready");

    private final JProgressBar _progressBar = new JProgressBar(0, 1000);

    private PipeLineField _pipeline;

    private FactoryPipeLine _lvlFactory = new FactoryPipeLine();


    public GamePanel() {

        JLabel logoLabel = new JLabel("PipeLineField");
        getContentPane().add(logoLabel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.darkGray);
        setResizable(false);

        createOneLvL();

        createMenu();
        setJMenuBar(menu);

        Box mainBox = Box.createVerticalBox();


        mainBox.add(Box.createVerticalStrut(10));
        _readyButton.setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        _progressBar.setForeground(Color.darkGray);
        ActionListener clickReadyButton = actionEvent -> {
            stopGame();
        };
        _readyButton.addActionListener(clickReadyButton);
        panel.add(_progressBar);
        panel.add(_readyButton);
        panel.setBackground(Color.WHITE);
        mainBox.add(panel, BorderLayout.PAGE_START);

        // Game field
        mainBox.add(Box.createVerticalStrut(10));
        _fieldPanel.setDoubleBuffered(true);
        createField();
        setEnabledField(false);
        mainBox.add(_fieldPanel);
        setContentPane(mainBox);

        pack();
    }

    private void createOneLvL(){
       _pipeline = _lvlFactory.createLvl(_lvlFactory.get_numbersLvls().get(0));
    }

    private void createLvLMenu(){

        fileItems = new String[_lvlFactory.get_numbersLvls().size()+1];
        int currentlvl = 0;

        for (Long number : _lvlFactory.get_numbersLvls()){
            fileItems[currentlvl] = ("lvl №" + number);
            currentlvl++;
        }

        fileItems[currentlvl] = "Exit";
    }

    private void createField(){

        _fieldPanel.setDoubleBuffered(true);
        _fieldPanel.setLayout(new GridLayout(_pipeline.get_dimension().height, _pipeline.get_dimension().width));

        int CELL_SIZE = 100;
        Dimension fieldDimension = new Dimension(CELL_SIZE *_pipeline.get_dimension().width,
                CELL_SIZE *_pipeline.get_dimension().height);

        _fieldPanel.setPreferredSize(fieldDimension);
        _fieldPanel.setMinimumSize(fieldDimension);
        _fieldPanel.setMaximumSize(fieldDimension);

        repaintField();
    }

    /**
     * Перерисовать поле
     */
    private void repaintField() {

        _fieldPanel.removeAll();
        UnitView unitView;

        for (int row = 1; row <= _pipeline.get_dimension().height; row++)
        {
            for (int col = 1; col <= _pipeline.get_dimension().width; col++)
            {
                try {
                    if (_pipeline.get_ElementsPipeline(row,col) instanceof PipeFitting){
                        unitView = new PipeFittingView((PipeFitting)_pipeline.get_ElementsPipeline(row,col));
                    }else if (_pipeline.get_ElementsPipeline(row,col) instanceof Tap){
                        unitView = new TapView((Tap)_pipeline.get_ElementsPipeline(row,col));
                    }else/* if (_pipeline.getSegment(row,col) instanceof Hatch)*/{
                        unitView = new HatchView((Hatch)_pipeline.get_ElementsPipeline(row,col));
                    }
                    unitView.addViewActionListener(this);
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

        Component[] comp = _fieldPanel.getComponents();

        for(Component c : comp) {
            c.setEnabled(on);
        }
    }

    private void createMenu() {

        createLvLMenu();

        menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Game");

        for (String fileItem : fileItems) {

            JMenuItem item = new JMenuItem(fileItem);
            item.setActionCommand(fileItem.toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);

        menu.add(fileMenu);
    }

    public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            if ("exit".equals(command)) {
                System.exit(0);
            }

            for (String com : fileItems){
                if (com.equals(command)) {
                    _pipeline = _lvlFactory.createLvl(Long.parseLong(com.split("№")[1]));
                    startGame();
                }
            }
        }
    }

    /**
     * Закончить игру и тестировать трубопровод
     */
    private void  stopGame(){
        _timer.stop();
        setEnabledField(false);
        String str;
        str = (_pipeline.testingPipeline())?"Win":"Losing";
        JOptionPane.showMessageDialog(this,
                "<html><h2>"+ str +"</h2><i>"+ str +"</i>");
    }

    /**
     * Начать игру. Запустить таймер.
     */
    private  void startGame(){
        setEnabledField(true);
        createField();
        _progressBar.setMaximum(_pipeline.get_Time());
        _progressBar.setValue(0);
        _timer.start();
        pack();
    }

    @Override
    public void updateView() {
        pack();
        this.update(this.getGraphics());
    }

    // -------------- Таймер ----------------------------------
    private ActionListener updateProBar = actionEvent -> {
        int val = _progressBar.getValue();
        if (val >= _progressBar.getMaximum()) {
            stopGame();
            return;
        }
        _progressBar.setValue(++val);
    };

    private final Timer _timer = new Timer(10, updateProBar);

}
