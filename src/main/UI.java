package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
  GamePanel gp;
  Font arial_25, arial_40, arial_80B;
  BufferedImage keyImage;
  public boolean messageOn = false;
  public String message = "";
  int messageCounter = 0;
  public boolean gameFinished = false;

  double playTime;
  DecimalFormat dFormat = new DecimalFormat("#0.0"); // Display up to 1 decimal point

  public UI(GamePanel gp) {
    this.gp = gp;

    arial_25 = new Font("Arial", Font.PLAIN, 25);
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
    OBJ_Key key = new OBJ_Key();
    keyImage = key.image;
  }

  public void showMessage(String text) {
    messageCounter = 0;
    message = text;
    messageOn = true;
  }

  public void draw(Graphics2D g2) {
    // If the treasure is found, display messages accordingly
    if (gameFinished) {
      g2.setFont(arial_40);
      g2.setColor(Color.white);

      String text;
      int textLength;
      int x;
      int y;

      // Display treasure message
      text = "You found the treasure!";
      textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

      x = (gp.screenWidth / 2) - textLength / 2;
      y = (gp.screenWidth / 2) - 225;
      g2.drawString(text, x, y);

      // Display total play time
      text = "Your Time is " + dFormat.format(playTime) + "!";
      textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

      x = (gp.screenWidth / 2) - textLength / 2;
      y = (gp.screenWidth / 2) + 110;
      g2.drawString(text, x, y);

      // Display congrats message
      g2.setFont(arial_80B);
      g2.setColor(Color.yellow);

      text = "Congratulations";
      textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

      x = (gp.screenWidth / 2) - textLength / 2;
      y = (gp.screenWidth / 2) + 50;
      g2.drawString(text, x, y);

      // Stop the game
      gp.stopGame();
    } else {
      g2.setFont(arial_25);
      g2.setColor(Color.white);
      g2.drawImage(keyImage, 15, 12, 30, 30, null);
      g2.drawString("x " + gp.player.hasKey, 55, 35);

      // Play time
      playTime += (double) 1 / gp.FPS;
      g2.drawString("Play Time: " + dFormat.format(playTime), gp.screenWidth - 200, 30);

      // Display message
      if (messageOn == true) {
        g2.drawString(message, 10, gp.screenHeight - 10);

        messageCounter++;

        if (messageCounter > gp.FPS * 2) {
          messageOn = false;
          messageCounter = 0;
        }
      }
    }
  }
}
