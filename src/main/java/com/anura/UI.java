package com.anura;

import com.anura.view.GamePanel;
import object.OBJ_Backpack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    BufferedImage background;
    Font arial_40;
    BufferedImage backpackImage;
    public Boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue;
    public int menuNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Backpack backpack = new OBJ_Backpack();
        backpackImage = backpack.image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            // do playState logic
            g2.drawString("Inventory: ", 10, 20);
             g2.drawImage(backpackImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen(){

        try{
            background = ImageIO.read(getClass().getResourceAsStream("/GuiTitleScreen.png"));
            g2.drawImage(background, 0, 0, null);
        }catch(IOException e){
            e.printStackTrace();
        }
        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        String text = "NEW GAME";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2 + (gp.tileSize * 2);
        g2.drawString(text, x, y);
        if(menuNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME(under development)";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(menuNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(menuNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
        // Menu Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        String text1 = "Use W/S to change Selection";
        String text2 = "then Enter to make your choice";
        x = 10;
        y += gp.tileSize;
        g2.drawString(text1, x, y);
        g2.drawString(text2, x, y + gp.tileSize/2);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    // helper method to get center of screen x to display text centered
    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    private void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26));

        for (String line : currentDialogue.split("\n")){
        g2.drawString(currentDialogue, x, y);
        y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.white);
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 35, 35);
    }
}


