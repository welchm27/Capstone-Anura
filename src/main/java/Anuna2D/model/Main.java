package Anuna2D.model;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Anuna 2D game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // enable the window settings to take on the subclass attributes for example width and height

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
