package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
  GamePanel gp;
  public Tile[] tile;
  public int mapTileNum[][];

  public TileManager(GamePanel gp) {
    this.gp = gp;
    tile = new Tile[10];
    mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

    getTileImage();
    loadMap("/res/maps/world01.txt");
  }

  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/stone.png"));
      tile[1].collision = true;

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
      tile[2].collision = true;

      tile[3] = new Tile();
      tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
      tile[4].collision = true;

      tile[5] = new Tile();
      tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadMap(String filePath) {
    try {
      InputStream is = getClass().getResourceAsStream(filePath);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      for (int row = 0; row < gp.maxWorldRow; row++) {
        String line = br.readLine();
        String numbers[] = line.split(" ");

        for (int col = 0; col < gp.maxWorldCol; col++) {
          int num = Integer.parseInt(numbers[col]);
          mapTileNum[row][col] = num;
        }
      }

      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2) {
    for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
      for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
        int tileNum = mapTileNum[worldRow][worldCol];

        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
          g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
      }
    }
  }
}
