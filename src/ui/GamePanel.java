package ui;

import model.events.ViewActionListner;
import model.pipe.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JFrame implements ViewActionListner{

    private JMenuBar menu = null;
    private  String fileItems[];

    private final JPanel _fieldPanel = new JPanel();
    private final JButton _readyButton = new JButton("Готово");

    private final JProgressBar _progressBar = new JProgressBar(0, 1000);

    private PipeLine _pipeline;

    private FactoryPipeLine _lvlFactory = new FactoryPipeLine();


    public GamePanel() {

        //Окно
        JLabel logoLabel = new JLabel("PipeLine");
        getContentPane().add(logoLabel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.darkGray);
        setResizable(false);

        //Создать модель тестового трубопровода
        _pipeline = new PipeLine();

        // Меню
        createMenu();
        setJMenuBar(menu);


        //Виджеты основной части окна
        Box mainBox = Box.createVerticalBox();

        // Шапка
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

        // Игровое поле
        mainBox.add(Box.createVerticalStrut(10));
        _fieldPanel.setDoubleBuffered(true);
        createField();
        setEnabledField(false);
        mainBox.add(_fieldPanel);
        setContentPane(mainBox);

        pack();
    }

    private void createLvL(){

        fileItems = new String[_lvlFactory.get_numbersLvls().size()+1];
        int currentlvl = 0;

        for (Long number : _lvlFactory.get_numbersLvls()){
            fileItems[currentlvl] = ("уровень №" + number);
            currentlvl++;
        }

        fileItems[currentlvl] = "Выход";
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

    private void repaintField() {

        _fieldPanel.removeAll();
        UnitView unitView;

        for (int row = 1; row <= _pipeline.get_dimension().height; row++)
        {
            for (int col = 1; col <= _pipeline.get_dimension().width; col++)
            {
                try {
                    if (_pipeline.get_Segment(new Point(row,col)) instanceof PipeFitting){
                        unitView = new PipeFittingView((PipeFitting)_pipeline.get_Segment(new Point(row,col)));
                    }else if (_pipeline.get_Segment(new Point(row,col)) instanceof Tap){
                        unitView = new TapView((Tap)_pipeline.get_Segment(new Point(row,col)));
                    }else/* if (_pipeline.getSegment(new Point(row,col)) instanceof Hatch)*/{
                        unitView = new HatchView((Hatch)_pipeline.get_Segment(new Point(row,col)));
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

        createLvL();

        menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Игра");

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

            if ("выход".equals(command)) {
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

    private void  stopGame(){
        _timer.stop();
        setEnabledField(false);
        String str;
        str = (_pipeline.testing())?"Выигрыш":"Проигрыш";
        JOptionPane.showMessageDialog(this,
                "<html><h2>"+ str +"</h2><i>"+ str +"</i>");
    }

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
