package com.phili.business.darkmode;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.awt.Color.DARK_GRAY;

public class TextArea {
    private final DataCompareFrame dataCompareFrame;
    private final FileTransferHandler fileTransferHandler; // Add this line


    public TextArea(DataCompareFrame dataCompareFrame) {
        this.dataCompareFrame = dataCompareFrame;
        this.fileTransferHandler = new FileTransferHandler(dataCompareFrame.blueTextArea, dataCompareFrame.isDarkMode);

    }

    public void addFileToTextAreas() {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(dataCompareFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                // Ask the user to select the text area to add the file content
                String[] options = {"Blue Area", "Red Area", "Green Area", "Pink Area"};
                int selectedOption = JOptionPane.showOptionDialog(dataCompareFrame,
                        "Select the area to add the file content:",
                        "Select Area",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                JTextArea selectedTextArea = switch (selectedOption) {
                    case 0 -> dataCompareFrame.blueTextArea;
                    case 1 -> dataCompareFrame.redTextArea;
                    case 2 -> dataCompareFrame.greenTextArea;
                    case 3 -> dataCompareFrame.pinkTextArea;
                    default -> null;
                };
                if (selectedTextArea != null) {
                    selectedTextArea.setText(content.toString());
                    selectedTextArea.setVisible(true);
                    // Show the text area
                    // Now, set the targetTextArea for the transferHandler
                    dataCompareFrame.transferHandler.setTargetTextArea(selectedTextArea);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(dataCompareFrame, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void compareTextAreas() {
        String blueText = dataCompareFrame.blueTextArea.getText();
        String redText = dataCompareFrame.redTextArea.getText();
        String greenText = dataCompareFrame.greenTextArea.getText();
        String pinkText = dataCompareFrame.pinkTextArea.getText();

        if (blueText.equals(redText) && redText.equals(greenText) && greenText.equals(pinkText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in all text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (blueText.equals(redText) && redText.equals(greenText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in blue, red, and green text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (redText.equals(greenText) && greenText.equals(pinkText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in red, green, and pink text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (greenText.equals(blueText) && blueText.equals(pinkText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in blue, green, and pink text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (blueText.equals(redText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in blue and red text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (redText.equals(greenText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in red and green text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (greenText.equals(pinkText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in green and pink text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (pinkText.equals(blueText)) {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in blue and pink text areas is identical.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dataCompareFrame, "The information in all text areas is different.", "Comparison Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateTextAreas() {
        Color textAreaColor = dataCompareFrame.isDarkMode ? Color.WHITE : DARK_GRAY;
        Color foregroundColor = dataCompareFrame.isDarkMode ? DARK_GRAY : Color.WHITE;

        dataCompareFrame.blueTextArea.setBackground(textAreaColor);
        dataCompareFrame.blueTextArea.setForeground(foregroundColor);
        dataCompareFrame.redTextArea.setBackground(textAreaColor);
        dataCompareFrame.redTextArea.setForeground(foregroundColor);
        dataCompareFrame.greenTextArea.setBackground(textAreaColor);
        dataCompareFrame.greenTextArea.setForeground(foregroundColor);
        dataCompareFrame.pinkTextArea.setBackground(textAreaColor);
        dataCompareFrame.pinkTextArea.setForeground(foregroundColor);
    }

    public void clearTextAreas() {
        dataCompareFrame.greenTextArea.setText("");
        dataCompareFrame.blueTextArea.setText("");
        dataCompareFrame.redTextArea.setText("");
        dataCompareFrame.pinkTextArea.setText(""); // Clear the pinkTextArea
    }

    public JTextArea createTextAreaWithMenuBar(String title) {
        JTextArea textArea = new JTextArea();
        JTextField textField = new JTextField();
        JButton button = new JButton("copy");
        JButton pasteButton = new JButton("paste");
        textArea.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.copy();
            }
        });
        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.paste();
            }
        });

        // Enable right-click menu and copy/paste functionality
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.add(button);
        copyItem.addActionListener(e -> textField.copy());
        popupMenu.add(copyItem);
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> textField.cut());
        popupMenu.add(cutItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.add(pasteButton);
        pasteItem.addActionListener(e -> textField.paste());
        popupMenu.add(pasteItem);
        textArea.setComponentPopupMenu(popupMenu);
//        popupMenu.add(button);

        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        textAreaPanel.setBorder(BorderFactory.createTitledBorder(title));

        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setTransferHandler(fileTransferHandler);
        fileTransferHandler.setTargetTextArea(textArea);
        return textArea;
    }
//
//    public JTextField createTextFieldWithPopupMenu(String title) {
//        JTextField textField = new JTextField();
//        textField.setColumns(10);
//
//        JPopupMenu popupMenu = new JPopupMenu();
//        Action cut = new DefaultEditorKit.CutAction();
//        cut.putValue(Action.NAME, "Cut");
//        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(cut);
//
//        Action copy = new DefaultEditorKit.CopyAction();
//        copy.putValue(Action.NAME, "Copy");
//        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(copy);
//
//        Action paste = new DefaultEditorKit.PasteAction();
//        paste.putValue(Action.NAME, "Paste");
//        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(paste);
//
//        Action selectAll = new SelectAll();
//        popupMenu.add(selectAll);
//
//        textField.setComponentPopupMenu(popupMenu);
////
////        JPanel textFieldPanel = new JPanel(new BorderLayout());
////        textFieldPanel.add(textField, BorderLayout.CENTER);
////        textFieldPanel.setBorder(BorderFactory.createTitledBorder(title));
//
//        return textField;
//    }

//    public JTextField copy() {
//        JTextField textField = new JTextField();
//        textField.setColumns(10);
//
//        JPopupMenu popupMenu = new JPopupMenu();
////        Action cut = new DefaultEditorKit.CutAction();
////        cut.putValue(Action.NAME, "Cut");
////        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
////        popupMenu.add(cut);
//
//        Action copy = new DefaultEditorKit.CopyAction();
//        copy.putValue(Action.NAME, "Copy");
//        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(copy);
////
////        Action paste = new DefaultEditorKit.PasteAction();
////        paste.putValue(Action.NAME, "Paste");
////        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
////        popupMenu.add(paste);
////
////        Action selectAll = new SelectAll();
////        popupMenu.add(selectAll);
//
//        textField.setComponentPopupMenu(popupMenu);
////
////        JPanel textFieldPanel = new JPanel(new BorderLayout());
////        textFieldPanel.add(textField, BorderLayout.CENTER);
////        textFieldPanel.setBorder(BorderFactory.createTitledBorder(title));
//
//        return textField;
//    }
//    public JTextField paste(String title) {
//        JTextField textField = new JTextField();
//        textField.setColumns(10);
//
//        JPopupMenu popupMenu = new JPopupMenu();
//        Action cut = new DefaultEditorKit.CutAction();
//        cut.putValue(Action.NAME, "Cut");
//        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(cut);
//
//        Action copy = new DefaultEditorKit.CopyAction();
//        copy.putValue(Action.NAME, "Copy");
//        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(copy);
//
//        Action paste = new DefaultEditorKit.PasteAction();
//        paste.putValue(Action.NAME, "Paste");
//        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
//        popupMenu.add(paste);
//
//        Action selectAll = new SelectAll();
//        popupMenu.add(selectAll);
//
//        textField.setComponentPopupMenu(popupMenu);
////
////        JPanel textFieldPanel = new JPanel(new BorderLayout());
////        textFieldPanel.add(textField, BorderLayout.CENTER);
////        textFieldPanel.setBorder(BorderFactory.createTitledBorder(title));
//
//        return textField;
//    }
    static class SelectAll extends TextAction {
        public SelectAll() {
            super("Select All");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent component = getFocusedComponent();
            if (component != null) {
                component.selectAll();
                component.requestFocusInWindow();
            }
        }
    }
}


