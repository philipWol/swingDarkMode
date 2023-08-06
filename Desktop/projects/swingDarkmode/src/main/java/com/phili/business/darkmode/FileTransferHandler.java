package com.phili.business.darkmode;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileTransferHandler extends TransferHandler {
    private JTextArea targetTextArea;
    private boolean isDarkMode;

    public void setTargetTextArea(JTextArea targetTextArea) {
        this.targetTextArea = targetTextArea;
    }

    public FileTransferHandler(JTextArea blueTextArea,boolean isDarkMode) {
        setTargetTextArea(targetTextArea);
        this.isDarkMode = isDarkMode;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            try {
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                StringBuilder content = new StringBuilder();
                for (File file : files) {
/*                if (file.isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {*/
                    content.append(readFileContent(file)).append("\n");
                }
                targetTextArea.setText(content.toString());
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
    return false;
}

    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}