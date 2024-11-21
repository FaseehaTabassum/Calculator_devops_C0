package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class simple_calcy {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorWindow().createAndShowGUI());
    }
}

class CalculatorWindow {
    private JFrame frame;
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String lastOperation;

    public CalculatorWindow() {
        currentInput = new StringBuilder();
        result = 0;
        lastOperation = "=";
    }

    public void createAndShowGUI() {
        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789".contains(command)) {
                currentInput.append(command);
                display.setText(currentInput.toString());
            } else if ("/*-+".contains(command)) {
                calculate(Double.parseDouble(currentInput.toString()));
                lastOperation = command;
                currentInput.setLength(0);
            } else if ("=".equals(command)) {
                calculate(Double.parseDouble(currentInput.toString()));
                display.setText(String.valueOf(result));
                lastOperation = "=";
                currentInput.setLength(0);
            } else if ("C".equals(command)) {
                currentInput.setLength(0);
                result = 0;
                lastOperation = "=";
                display.setText("");
            }
        }

        private void calculate(double input) {
            switch (lastOperation) {
                case "+":
                    result += input;
                    break;
                case "-":
                    result -= input;
                    break;
                case "*":
                    result *= input;
                    break;
                case "/":
                    if (input != 0) {
                        result /= input;
                    } else {
                        display.setText("Error");
                        currentInput.setLength(0);
                        result = 0;
                        lastOperation = "=";
                        return;
                    }
                    break;
                case "=":
                    result = input;
                    break;
            }
            display.setText(String.valueOf(result));
        }
    }
}
