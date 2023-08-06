package com.phili.business.darkmode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.UIManager;
import static java.awt.Color.DARK_GRAY;
import static javax.swing.UIManager.setLookAndFeel;

public class DataCompareFrame extends JFrame implements ActionListener {
    JButton darkModeButton;
    JButton compareButton;
    JPanel topPanel;
    JPanel buttonPanel;
    JPanel westPanel;
    JPanel bluepanel;
    JPanel redpanel;
    JPanel greenpanel;
    JPanel pinkpanel;
    JPanel southPanel;
    JTextArea blueTextArea;
    JTextArea redTextArea;
    JTextArea greenTextArea;
    JTextArea pinkTextArea;
    MyTreePanel myTreePanel= new MyTreePanel();
    FileTransferHandler transferHandler; // Add this line
    TextArea textArea = new TextArea(this);
    boolean isDarkMode = false;

    public DataCompareFrame() {
        this.setTitle("Dark Compare Tool ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(920, 620));

        topPanel = new JPanel(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(buttonPanel);
        darkModeButton = new JButton("Dark Mode");
        darkModeButton.addActionListener(e -> {
            updateDarkMode();
        });
        buttonPanel.add(darkModeButton);

        compareButton = new JButton("Compare");
        compareButton.addActionListener(e -> textArea.compareTextAreas());
        buttonPanel.add(compareButton);

        JButton importButton = new JButton("Import");
        importButton.addActionListener(e -> textArea.addFileToTextAreas());
        buttonPanel.add(importButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("deleteButton");

        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(deleteButton);
        this.add(southPanel, BorderLayout.SOUTH);

/*        // Initialize MyTreePanel without any arguments
        MyTreePanel = new MyTreePanel(blueTextArea, isDarkMode);
        transferHandler = new FileTransferHandler(blueTextArea,isDarkMode);
        transferHandler.setTargetTextArea(blueTextArea);
        MyTreePanel.getTree().setTransferHandler(transferHandler);*/

        // Add MyTreePanel to the westPanel
        westPanel = new JPanel(new BorderLayout());
        westPanel.add(myTreePanel, BorderLayout.CENTER);
        this.add(westPanel, BorderLayout.WEST);

        // Create TextArea and pass the instance of DataCompareFrame
        textArea = new TextArea(this);

        // Create JTextAreas and add them to the panels
        blueTextArea = textArea.createTextAreaWithMenuBar("Blue Text Area");
        redTextArea = textArea.createTextAreaWithMenuBar("Red Text Area");
        greenTextArea = textArea.createTextAreaWithMenuBar("Green Text Area");
        pinkTextArea = textArea.createTextAreaWithMenuBar("Pink Text Area");

        bluepanel = new JPanel(new BorderLayout());
        bluepanel.add(new JScrollPane(blueTextArea), BorderLayout.CENTER);

        redpanel = new JPanel(new BorderLayout());
        redpanel.add(new JScrollPane(redTextArea), BorderLayout.CENTER);

        greenpanel = new JPanel(new BorderLayout());
        greenpanel.add(new JScrollPane(greenTextArea), BorderLayout.CENTER);

        pinkpanel = new JPanel(new BorderLayout());
        pinkpanel.add(new JScrollPane(pinkTextArea), BorderLayout.CENTER);

        // Set the target text area for each FileTransferHandler
        FileTransferHandler blueTransferHandler = new FileTransferHandler(blueTextArea, isDarkMode);
        FileTransferHandler redTransferHandler = new FileTransferHandler(redTextArea, isDarkMode);
        FileTransferHandler greenTransferHandler = new FileTransferHandler(greenTextArea, isDarkMode);
        FileTransferHandler pinkTransferHandler = new FileTransferHandler(pinkTextArea, isDarkMode);

        blueTextArea.setTransferHandler(blueTransferHandler);
        redTextArea.setTransferHandler(redTransferHandler);
        greenTextArea.setTransferHandler(greenTransferHandler);
        pinkTextArea.setTransferHandler(pinkTransferHandler);

        // Add all panels to the main frame
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bluepanel, redpanel);
        splitPane1.setResizeWeight(0.5);
        splitPane1.setOneTouchExpandable(true);
        splitPane1.setContinuousLayout(true);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, greenpanel);
        splitPane2.setResizeWeight(0.7);
        splitPane2.setOneTouchExpandable(true);
        splitPane2.setContinuousLayout(true);

        JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane2, pinkpanel);
        splitPane3.setResizeWeight(0.7);
        splitPane3.setOneTouchExpandable(true);
        splitPane3.setContinuousLayout(true);

        this.add(splitPane3);

        // Set divider size to 0 to make the dividers invisible by default
        splitPane1.setDividerSize(0);
        splitPane2.setDividerSize(0);
        splitPane3.setDividerSize(0);

        // Add mouse motion listeners to set resizable divider size on hover
        splitPane1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                splitPane1.setDividerSize(5); // Set the divider size on hover
            }
        });

        splitPane2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                splitPane2.setDividerSize(5); // Set the divider size on hover
            }
        });

        splitPane3.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                splitPane3.setDividerSize(5); // Set the divider size on hover
            }
        });

        // Add horizontal and vertical scroll bars to the main frame
        JScrollPane mainScrollPane = new JScrollPane(splitPane3);
        this.add(mainScrollPane, BorderLayout.CENTER);

        textArea.updateTextAreas();

        this.setVisible(true);
    }

    private void updateDarkMode() {
        isDarkMode = !isDarkMode;
        Color backgroundColor = isDarkMode ? Color.WHITE : DARK_GRAY;
        Color buttonPanelColor = isDarkMode ? Color.WHITE : DARK_GRAY;

        // Set background colors for all panels
        this.getContentPane().setBackground(backgroundColor);
        buttonPanel.setBackground(buttonPanelColor);
        topPanel.setBackground(backgroundColor);
        topPanel.setBackground(backgroundColor);

        bluepanel.setBackground(backgroundColor);
        redpanel.setBackground(backgroundColor);
        greenpanel.setBackground(backgroundColor);
        pinkpanel.setBackground(backgroundColor);
        southPanel.setBackground(backgroundColor);
        westPanel.setBackground(backgroundColor);

        blueTextArea.getParent().setBackground(backgroundColor);
        redTextArea.getParent().setBackground(backgroundColor);
        greenTextArea.getParent().setBackground(backgroundColor);
        pinkTextArea.getParent().setBackground(backgroundColor);
        westPanel.getParent().setBackground(backgroundColor);

        // Update the title bar color
        try {
            if (isDarkMode) {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        textArea.updateTextAreas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("deleteButton".equals(command)) {
            textArea.clearTextAreas();
        }
    }
}