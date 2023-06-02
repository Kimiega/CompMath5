package graph;

import linal.Equation;
import linal.SuperMegaSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Function;

public class FunctionsWindow extends JFrame implements ActionListener, FocusListener {
    static FunctionsWindow functionsWindow;
    static JPanel p;
    static JButton buttonSin;
    static JButton buttonXXX;
    static JFrame frame;
    static JTextField left;
    static JTextField right;
    static JTextField amount;
    static JLabel labelAmount;
    static JLabel labelLeft;
    static JLabel labelRight;
    static JPanel grid = new JPanel(new GridLayout(3, 3));
    public static void init() {
        functionsWindow = new FunctionsWindow();
        buttonSin = new JButton("sin(x)");
        buttonXXX = new JButton("x^3 +5x - 5");
        labelAmount = new JLabel("amount of dots");
        labelLeft = new JLabel("left border");
        labelRight = new JLabel("right border");
        frame = new JFrame();
        p = new JPanel();
        left = new JTextField();
        right = new JTextField();
        amount = new JTextField();
        left.addFocusListener(functionsWindow);
        right.addFocusListener(functionsWindow);
        amount.addFocusListener(functionsWindow);
        buttonSin.addActionListener(functionsWindow);
        buttonXXX.addActionListener(functionsWindow);
        grid.add(labelLeft);
        grid.add(labelRight);
        grid.add(labelAmount);
        grid.add(left);
        grid.add(right);
        grid.add(amount);
        grid.add(buttonSin);
        grid.add(buttonXXX);
        p.add(grid);
        frame.add(p);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals(buttonSin.getText()) || s.equals(buttonXXX.getText())) {
            Double l = checkTextFieldIsNumber(left);
            Double r = checkTextFieldIsNumber(right);
            Integer am = checkTextFieldIsInt(amount);
            if (am == null || am < 2 || l == null || r == null) {
                amount.setBackground(Color.red);
                return;
            }
            if (Math.abs(l - r) < 0.0000001) {
                left.setBackground(Color.red);
                right.setBackground(Color.red);
                return;
            }
            InputWindow.amountDots = am;
            InputWindow.makeTable();
            if (s.equals(buttonSin.getText())) {
                double it = l;
                double h = (r - l)/(am - 1);
                for (int i = 0; i < am; ++i) {
                    InputWindow.textX[i].setText(Double.toString(it));
                    InputWindow.textY[i].setText(Double.toString(sinx().apply(it)));
                    it+=h;
                }
                SuperMegaSolver.fromWindowEquation = new Equation() {
                    @Override
                    public Function<Double, Double> equation() {
                        return sinx();
                    }

                    @Override
                    public String equationToString() {
                        return "sin(x)";
                    }
                };
            }
            if (s.equals(buttonXXX.getText())) {
                double it = l;
                double h = (r - l)/(am - 1);
                for (int i = 0; i < am; ++i) {
                    InputWindow.textX[i].setText(Double.toString(it));
                    InputWindow.textY[i].setText(Double.toString(fxxx().apply(it)));
                    it+=h;
                }
                SuperMegaSolver.fromWindowEquation = new Equation() {
                    @Override
                    public Function<Double, Double> equation() {
                        return fxxx();
                    }

                    @Override
                    public String equationToString() {
                        return "x*x*x +5*x - 5";
                    }
                };
            }
            frame.setVisible(false);
            frame.removeAll();
            p.removeAll();
            grid.removeAll();
        }

    }
    Function<Double, Double> sinx() {
        return Math::sin;
    }
    Function<Double, Double> fxxx() {
        return (Double x) -> x*x*x +5*x - 5;
    }

    @Override
    public void focusGained(FocusEvent e) {
        left.setBackground(Color.white);
        right.setBackground(Color.white);
        amount.setBackground(Color.white);
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
}
