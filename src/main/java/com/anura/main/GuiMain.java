package com.anura.main;

import com.anura.view.ExtendedGamePanel;
import com.anura.view.GamePanel;

import javax.swing.*;

public class GuiMain {
    public static void main(String[] args) {

        // Load up the new extended game panel
        SwingUtilities.invokeLater(() -> new ExtendedGamePanel());
    }
}
