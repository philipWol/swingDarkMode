package com.phili.business;

import com.phili.business.darkmode.DataCompareFrame;

import javax.swing.*;

import java.awt.*;

import static javax.swing.UIManager.setLookAndFeel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

           // DataCompareFrame dataCompareFrame = new DataCompareFrame();
        });
        EventQueue.invokeLater(() -> {
            DataCompareFrame frame = new DataCompareFrame();
            frame.setVisible(true);
        });
    }
}
