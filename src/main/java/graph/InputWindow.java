package graph;

import linal.SuperMegaSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class InputWindow extends JFrame implements ActionListener, FocusListener {
    static int amountDots = 2;
    static JTextField[] textX;
    static JTextField[] textY;

    static JTextField numberOfDots;
    static JButton setNumberOfDots;
    static JTextField target;
    static JFrame frame;

    static JButton submit;

    static JButton fromFile;

    static JButton fromFunction;
    static JPanel gridDots = null;
    static InputWindow inputWindow;
    static JScrollPane sp = null;
    static JPanel firstGrid = new JPanel(new GridLayout(5, 1));
    static JPanel lastGrid = new JPanel(new GridLayout(1, 3));
    static JPanel p;
    static JPanel tableGrid;

    // default constructor
    InputWindow() {}

    public static void init()
    {
        inputWindow = new InputWindow();

        frame = new JFrame("Input dots");
        submit = new JButton("submit");
        fromFile = new JButton("fromFile");
        fromFunction = new JButton("fromFunction");
        setNumberOfDots = new JButton("set amount");



        submit.addActionListener(inputWindow);
        fromFile.addActionListener(inputWindow);
        fromFunction.addActionListener(inputWindow);
        setNumberOfDots.addActionListener(inputWindow);

        numberOfDots = new JTextField(16);
        target = new JTextField(16);
        numberOfDots.addFocusListener(inputWindow);
        target.addFocusListener(inputWindow);

        p = new JPanel();

        firstGrid.add(new JLabel("Amount of dots", SwingConstants.CENTER));
        firstGrid.add(numberOfDots);
        firstGrid.add(setNumberOfDots);
        firstGrid.add(new JLabel("target", SwingConstants.CENTER));
        firstGrid.add(target);
        makeTable();

        lastGrid.add(submit);
        lastGrid.add(fromFile);
        lastGrid.add(fromFunction);
        // add panel to frame
        p.add(firstGrid);
        p.add(lastGrid);
        makeTable();
        frame.add(p);
        // set the size of frame
        frame.setSize(350, 600);

        frame.setVisible(true);
    }
    private static void panelSomething() {

    }
    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        clearColors();
        String s = e.getActionCommand();
        if (s.equals(fromFunction.getText())) {
            FunctionsWindow.init();
        }
        if (s.equals(submit.getText())) {
            Double[][] dots = new Double[amountDots][2];
            boolean flag = true;
            Double tar = checkTextFieldIsNumber(target);
            if (tar == null)
                flag = false;
            for (int i = 0; i < amountDots; ++i) {
                Double parsed = checkTextFieldIsNumber(textX[i]);
                if (parsed == null)
                    flag = false;
                if (flag)
                    dots[i][0] = parsed;

                parsed = checkTextFieldIsNumber(textY[i]);
                if (parsed == null)
                    flag = false;
                if (flag)
                    dots[i][1] = parsed;
            }
            for (int i = 0; i <amountDots; i++)
                for (int j = i + 1; j < amountDots; ++j) {
                    if (checkTextFieldIsNumber(textX[i]) != null && checkTextFieldIsNumber(textX[j])!=null &&
                            Math.abs(checkTextFieldIsNumber(textX[i]) - checkTextFieldIsNumber(textX[j])) < 0.00000001) {
                        textX[i].setBackground(Color.red);
                        textX[j].setBackground(Color.red);
                        flag = false;
                    }
                }
            if (flag) {
                if (SuperMegaSolver.thread != null)
                    SuperMegaSolver.thread.stop();
                String result = SuperMegaSolver.solveAllProblems(dots, tar);
                try {OutputWindow.frame.setVisible(false);} catch (Exception ignored) {}
                OutputWindow.init(result);
            }
        }
        if (s.equals(fromFile.getText())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                clearText();
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(selectedFile);
                    Scanner scanner = new Scanner(inputStream);
                    int i = 0;
                    while (scanner.hasNext()) {
                        textX[i].setText(scanner.next());
                        textY[i].setText(scanner.next());
                        i++;
                    }
                    amountDots = i;
                    numberOfDots.setText(Integer.toString(i));
                }
                catch (Exception ignored) {}
                finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ignored) {}
                    }
                }
            }
        }
        if (s.equals(setNumberOfDots.getText())) {
            Integer parsed = checkTextFieldIsInt(numberOfDots);
            if (parsed != null && parsed > 1) {
                amountDots = (int) Math.floor(parsed);
                makeTable();
            } else {
                numberOfDots.setBackground(Color.RED);
            }
        }
    }
    private static void clearColors() {
        numberOfDots.setBackground(Color.WHITE);
        target.setBackground(Color.white);
        for (int i = 0; i < amountDots; ++i) {
            textX[i].setBackground(Color.WHITE);
            textY[i].setBackground(Color.WHITE);
        }
    }
    private static void clearText() {
        for (int i = 0; i < amountDots; ++i) {
            textX[i].setText("");
            textY[i].setText("");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        clearColors();
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
    private Double checkTextFieldIsNumber(JTextField field) {
        String x = field.getText();
        double parsed = 0d;
        try {
            parsed = Double.parseDouble(x.replace(",", "."));
        } catch (Exception ex) {
            field.setBackground(Color.RED);
            return null;
        }
        return parsed;
    }
    private Integer checkTextFieldIsInt(JTextField field) {
        String x = field.getText();
        int parsed = 0;
        try {
            parsed = Integer.parseInt(x);
        } catch (Exception ex) {
            field.setBackground(Color.RED);
            return null;
        }
        return parsed;
    }
    static void makeTable() {
        //input dots
        textX = new JTextField[amountDots];
        textY = new JTextField[amountDots];
        for (int i = 0; i < amountDots; ++i) {
            textX[i] = new JTextField(8);
            textX[i].addFocusListener(inputWindow);
            textY[i] = new JTextField(8);
            textY[i].addFocusListener(inputWindow);

        }
        gridDots = new JPanel(new GridLayout(amountDots + 1, 3));
        gridDots.setPreferredSize(new Dimension(250, amountDots*30));
        if (sp != null)
            p.remove(tableGrid);
        sp = new JScrollPane(gridDots);
        tableGrid = new JPanel(new BorderLayout());
//        tableGrid.setSize(200, 100);
//        tableGrid.setMaximumSize(new Dimension(200, 100));
        tableGrid.setPreferredSize(new Dimension(300, 400));
        tableGrid.add(sp);
        p.add(tableGrid);
        gridDots.add(new JLabel("", SwingConstants.CENTER));
        gridDots.add(new JLabel("X", SwingConstants.CENTER));
        gridDots.add(new JLabel("Y", SwingConstants.CENTER));
        for (int i = 0; i < amountDots; ++i) {
            gridDots.add(new JLabel(Integer.toString(i+1), SwingConstants.CENTER));
            gridDots.add(textX[i]);
            gridDots.add(textY[i]);
        }
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
}