package Anuna2D.model;

import Anuna2D.controller.KeyHandler;
import Anuna2D.entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int maxScreenColumns = 20;
    private final int maxScreenRows = 12;

    // Window mode
    private final int screenWidth = tileSize * maxScreenColumns; // 960 px
    private final int screenHeight = tileSize * maxScreenRows; // 576 px

    private final int FPS = 60;

    // WORLD SETTINGS
    private final int maxWorldColumns = 50;
    private final int maxWorldRows = 50;
    private final int worldWidth = tileSize * maxWorldColumns;
    private final int worldHeight = tileSize * maxWorldRows;

    // GAME THREAD
    private Thread gameThread;

    //KEY HANDLER
    KeyHandler keyH = new KeyHandler();

    //PLAYER instantiation
    Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   //improves the game rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
    }

}
