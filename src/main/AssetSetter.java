package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
  GamePanel gp;

  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObjects() {
    // Secret pond key
    gp.obj[0] = new OBJ_Key();
    gp.obj[0].worldX = gp.tileSize * 7;
    gp.obj[0].worldY = gp.tileSize * 9;

    gp.obj[1] = new OBJ_Chest();
    gp.obj[1].worldX = gp.tileSize * 44;
    gp.obj[1].worldY = gp.tileSize * 43;

    // Chest door
    gp.obj[2] = new OBJ_Door();
    gp.obj[2].worldX = gp.tileSize * 44;
    gp.obj[2].worldY = gp.tileSize * 46;

    // Starting door
    gp.obj[3] = new OBJ_Door();
    gp.obj[3].worldX = gp.tileSize * 6;
    gp.obj[3].worldY = gp.tileSize * 31;

    gp.obj[4] = new OBJ_Boots();
    gp.obj[4].worldX = gp.tileSize * 45;
    gp.obj[4].worldY = gp.tileSize * 16;

    gp.obj[5] = new OBJ_Key();
    gp.obj[5].worldX = gp.tileSize * 6;
    gp.obj[5].worldY = gp.tileSize * 35;
  }
}
