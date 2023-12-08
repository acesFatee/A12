import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CellularAutomatonApp {

    private static final String ENGLISH = "English";
    private static final String FRENCH = "French";
    private static final String HELP_MESSAGE_EN = "Welcome to Cellular Automaton App!";
    private static final String HELP_MESSAGE_FR = "Bienvenue dans l'application d'automates cellulaires !";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cellular Automaton App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 800);
            frame.setResizable(false);

            // Create a panel for the grid
            JPanel gridPanel = createGridPanel();

            // Create a panel for input and update button
            JPanel inputPanel = createInputPanel(frame, gridPanel);

            // Create a toolbar
            JToolBar toolBar = createToolBar(frame);

            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon("./media/ca.png");
            int fixedWidth = 600;
            Image image = imageIcon.getImage().getScaledInstance(fixedWidth, 200, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);

            // Use BorderLayout to position the toolbar at the top, grid at the center, and input at the bottom
            frame.setLayout(new BorderLayout());
            frame.add(toolBar, BorderLayout.NORTH);
            frame.add(imageLabel, BorderLayout.NORTH);
            frame.add(gridPanel, BorderLayout.CENTER);
            frame.add(inputPanel, BorderLayout.SOUTH);

            // Set the frame visibility
            frame.setVisible(true);
        });
    }

    private static JToolBar createToolBar(JFrame frame) {
        JToolBar toolBar = new JToolBar();

        // Language Selection Button Group
        ButtonGroup languageGroup = new ButtonGroup();

        JToggleButton englishButton = new JToggleButton(ENGLISH);
        englishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the application's language to English
                setLanguage(ENGLISH);
            }
        });

        JToggleButton frenchButton = new JToggleButton(FRENCH);
        frenchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the application's language to French
                setLanguage(FRENCH);
            }
        });

        // Help Button
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display help message based on the current language
                String helpMessage;
                if (englishButton.isSelected()) {
                    helpMessage = HELP_MESSAGE_EN;
                } else {
                    helpMessage = HELP_MESSAGE_FR;
                }
                JOptionPane.showMessageDialog(frame, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add buttons to the toolbar
        languageGroup.add(englishButton);
        languageGroup.add(frenchButton);

        toolBar.add(englishButton);
        toolBar.add(frenchButton);
        toolBar.addSeparator();
        toolBar.add(helpButton);

        return toolBar;
    }

    private static JPanel createGridPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(50, 50, 2, 2));

        // Create buttons for each cell in the grid
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(10, 10));

                panel.add(button);
            }
        }

        return panel;
    }

    private static JPanel createInputPanel(JFrame frame, JPanel gridPanel) {
        JPanel panel = new JPanel();

        // Create a text field for input
        JTextField inputField = new JTextField(20); // 20 columns
        panel.add(inputField);

        // Create an update button
        JButton updateButton = new JButton("Run");
        panel.add(updateButton);

        // Create a label for displaying the decimal representation
        JLabel decimalLabel = new JLabel("Model: ");
        panel.add(decimalLabel);

        // Add ActionListener to the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int binaryValue = Integer.parseInt(inputField.getText());

                    // Ensure that the binary input has the correct number of bits
                    if (Integer.toString(binaryValue).length() != 8) {
                        JOptionPane.showMessageDialog(frame, "Enter a binary number with " + 8 + " bits");
                        return;
                    }

                    CALogic calogic = new CALogic(50, 50, binaryValue);
                    // Update calogic with the new binary input
                    calogic.setRule(binaryValue);
                    calogic.run();
                    // Update the grid panel with the new matrix values
                    updateGridPanel(gridPanel, calogic);

                    // Update the decimal label
                    decimalLabel.setText("Model: " + calogic.getDecimal(binaryValue));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid binary input");
                }
            }
        });

        return panel;
    }

    private static void updateGridPanel(JPanel gridPanel, CALogic calogic) {
        Component[] components = gridPanel.getComponents();
        int index = 0;

        // Update the color of each button in the grid based on the new matrix values
        for (int i = 0; i < calogic.rows; i++) {
            for (int j = 0; j < calogic.cols; j++) {
                JButton button = (JButton) components[index++];
                button.setBackground(calogic.matrix[i][j] == 1 ? Color.BLACK : Color.WHITE);
            }
        }
    }

    private static void setLanguage(String language) {
        System.out.println("Language selected: " + language);
    }
}
