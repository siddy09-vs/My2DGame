package main;

import entity.Entity;

public class CollisionChecker {
  GamePanel gp;

  public CollisionChecker(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity entity) {
    int entityLeftWorldX = entity.worldX + entity.hitBox.x;
    int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
    int entityTopWorldY = entity.worldY + entity.hitBox.y;
    int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

    int entityLeftCol = entityLeftWorldX / gp.tileSize;
    int entityRightCol = entityRightWorldX / gp.tileSize;
    int entityTopRow = entityTopWorldY / gp.tileSize;
    int entityBottomRow = entityBottomWorldY / gp.tileSize;

    int tileNum1, tileNum2;

    switch (entity.direction) {
      case "up":
        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
        tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
        tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
        tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
        tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
    }
  }

  public int checkObject(Entity entity, boolean player) {
    int index = 999;

    for (int i = 0; i < gp.obj.length; i++) {
      if (gp.obj[i] != null) {
        // Get entity's hitbox position
        entity.hitBox.x += entity.worldX;
        entity.hitBox.y += entity.worldY;

        // Get the object's hitbox position
        gp.obj[i].hitBox.x += gp.obj[i].worldX;
        gp.obj[i].hitBox.y += gp.obj[i].worldY;

        switch (entity.direction) {
          case "up":
            entity.hitBox.y -= entity.speed;
            break;
          case "down":
            entity.hitBox.y += entity.speed;
            break;
          case "left":
            entity.hitBox.x -= entity.speed;
            break;
          case "right":
            entity.hitBox.x += entity.speed;
            break;
        }

        // Check for intersection
        if (entity.hitBox.intersects(gp.obj[i].hitBox)) {
          if (gp.obj[i].collision) {
            entity.collisionOn = true;
          }
          if (player) {
            index = i;
          }
        }

        // Reset hitbox positions
        entity.hitBox.x = entity.hitBoxDefaultX;
        entity.hitBox.y = entity.hitBoxDefaultY;
        gp.obj[i].hitBox.x = gp.obj[i].hitBoxDefaultX;
        gp.obj[i].hitBox.y = gp.obj[i].hitBoxDefaultY;

      }
    }

    return index;
  }
}
