package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  GamePanel gp;
  KeyHandler keyH;

  public final int screenX;
  public final int screenY;
  public int hasKey = 0;

  private final int spriteUpdateFrames = 12;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
    screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

    hitBox = new Rectangle(8, 16, 32, 32);
    hitBoxDefaultX = hitBox.x;
    hitBoxDefaultY = hitBox.y;

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {
    worldX = gp.tileSize * 6; // Starting position
    worldY = gp.tileSize * 39;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {
    try {
      up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_up_1.png"));
      up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_up_2.png"));
      down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_down_1.png"));
      down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_down_2.png"));
      left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_left_1.png"));
      left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_left_2.png"));
      right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_right_1.png"));
      right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/main_brown_man_right_2.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

      if (keyH.upPressed) {
        direction = "up";
      } else if (keyH.downPressed) {
        direction = "down";
      } else if (keyH.leftPressed) {
        direction = "left";
      } else if (keyH.rightPressed == true) {
        direction = "right";
      }

      // Check for tile collisions
      collisionOn = false;
      gp.cChecker.checkTile(this);

      // Check for object collision
      int objIndex = gp.cChecker.checkObject(this, true);
      pickUpObject(objIndex);

      // If there are no collisions (collisionOn = false), the player can move
      if (collisionOn == false) {
        switch (direction) {
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
          case "left":
            worldX -= speed;
            break;
          case "right":
            worldX += speed;
            break;
        }
      }

      spriteCounter++;
      if (spriteCounter > spriteUpdateFrames) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }

        spriteCounter = 0;
      }
    }
  }

  public void pickUpObject(int i) {
    // If object is detected
    if (i != 999) {
      String objectName = gp.obj[i].name;

      switch (objectName) {
        case "Key":
          gp.playSE(1);
          hasKey++;
          gp.obj[i] = null;
          gp.ui.showMessage("Picked up a key");
          break;
        case "Door":
          if (hasKey > 0) {
            gp.playSE(3);
            gp.obj[i] = null;
            hasKey--;
            gp.ui.showMessage("Door opened");
          } else {
            gp.ui.showMessage("Key required");
          }
          break;
        case "Chest":
          gp.ui.gameFinished = true;
          gp.stopMusic();
          gp.playSE(4);
          break;
        case "Boots":
          gp.playSE(2);
          speed += 1;
          gp.obj[i] = null;
          gp.ui.showMessage("Speed Up!");
          break;
      }
    }
  }

  public void draw(Graphics2D g2) {
    /*
     * g2.setColor(Color.white);
     * g2.fillRect(x, y, gp.tileSize, gp.tileSize);
     */

    BufferedImage image = null;

    switch (direction) {
      case "up":
        if (spriteNum == 1) {
          image = up1;
        }
        if (spriteNum == 2) {
          image = up2;
        }
        break;

      case "down":
        if (spriteNum == 1) {
          image = down1;
        }
        if (spriteNum == 2) {
          image = down2;
        }
        break;

      case "left":
        if (spriteNum == 1) {
          image = left1;
        }
        if (spriteNum == 2) {
          image = left2;
        }
        break;

      case "right":
        if (spriteNum == 1) {
          image = right1;
        }
        if (spriteNum == 2) {
          image = right2;
        }
        break;
    }

    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
  }
}
