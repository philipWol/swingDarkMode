package com.phili.business.darkmode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;



public class MyTreePanel extends JPanel {
    private JTree tree;
    private JTextArea targetTextArea;
    private boolean isDarkMode;

    private JTextArea blueTextArea;
    private JTextArea redTextArea;
    private JTextArea greenTextArea;
    private JTextArea pinkTextArea;

    public void setTargetTextArea(JTextArea targetTextArea) {
        this.targetTextArea = targetTextArea;
    }

    public MyTreePanel() {
        this.targetTextArea = targetTextArea;
        this.isDarkMode = isDarkMode;
        initializeTree();
    }

    private void initializeTree() {
        DefaultMutableTreeNode  root = new DefaultMutableTreeNode("C:");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        // Build your tree hierarchy here (You can modify this part based on your requirements)
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("TYKON");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Be");
        root.add(node1);
        root.add(node2);

        tree = new JTree(treeModel);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath selectedPath = tree.getPathForLocation(e.getX(), e.getY());
                    if (selectedPath != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                        if (selectedNode != null) {
                            Object userObject = selectedNode.getUserObject();
                            if (userObject instanceof File) {
                                File selectedFile = (File) userObject;
                                handleFileSelection(selectedFile);
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void handleFileSelection(File selectedFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            // Ask the user to select the text area to add the file content
            String[] options = {"Blue Area", "Red Area", "Green Area", "Pink Area"};
            int selectedOption = JOptionPane.showOptionDialog(this,
                    "Select the area to add the file content:",
                    "Select Area",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            JTextArea selectedTextArea = switch (selectedOption) {
                case 0 -> blueTextArea;
                case 1 -> redTextArea;
                case 2 -> greenTextArea;
                case 3 -> pinkTextArea;
                default -> null;
            };
            if (selectedTextArea != null) {
                selectedTextArea.setText(content.toString());
                selectedTextArea.setVisible(true); // Show the text area
            }
            if (targetTextArea != null) {
                targetTextArea.setText(content.toString());
                targetTextArea.setVisible(true); // Show the text area
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add nodes from under "dir" into curTop. Highly recursive.
    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(dir);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector<String> ol = new Vector<>();
        String[] tmp = dir.list();
        for (int i = 0; tmp != null && i < tmp.length; i++)
            ol.addElement(tmp[i]);
        ol.sort(String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector<String> files = new Vector<>();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
                addNodes(curDir, f);
            else
                files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }

    public Dimension getMinimumSize() {
        return new Dimension(200, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }

    public void setBlueTextArea(JTextArea blueTextArea) {
        this.blueTextArea = blueTextArea;
    }

    public void setRedTextArea(JTextArea redTextArea) {
        this.redTextArea = redTextArea;
    }

    public void setGreenTextArea(JTextArea greenTextArea) {
        this.greenTextArea = greenTextArea;
    }

    public void setPinkTextArea(JTextArea pinkTextArea) {
        this.pinkTextArea = pinkTextArea;
    }
    public JFrame getTree(){
        return null;
    }
    public void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            handleFileSelection(selectedFile);
        }
    }
}