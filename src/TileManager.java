import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TileManager {

    private final Main main;
    public Tile[] tile;
    final int collMapSize = 24;

    ArrayList<ArrayList<Integer>> mapBG;
    ArrayList<ArrayList<Integer>> mapMG;
    ArrayList<ArrayList<Integer>> mapFG;


    public TileManager(Main main) {
        this.main = main;

        tile = new Tile[main.maxTiles];
        loadTiles();

        mapBG = new ArrayList<>();
        mapMG = new ArrayList<>();
        mapFG = new ArrayList<>();

        loadMap(mapBG,"Miscellaneous/WorldSettings/Maps/map_bg.txt");
        loadMap(mapMG,"Miscellaneous/WorldSettings/Maps/map_mg.txt");
        loadMap(mapFG,"Miscellaneous/WorldSettings/Maps/map_fg.txt");






    }

    public void loadTiles() {




        try {


            InputStream is = main.loadExternalText("Miscellaneous/WorldSettings/Tile_info_sheet/tiles_info.txt");

            if (is == null) {
                main.printErrOnDebug("tiles_Info.txt wurde nicht gefunden!");
                System.exit(404);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            List<String> allLines = br.lines().toList();
            br.close();

            int infoListLength = allLines.size();

            int tileNum = 1;
            int tileLine = 1;

            tile[tileNum] = new Tile();
            for (int i = 0; i < infoListLength; i++) {

                String line = allLines.get(i);


                if (line.contains("_animated")) {
                    tile[tileNum].animationFrames = loadAnimationTile(line,tileNum);
                    tile[tileNum].name =getTileName(line);

                    tileLine = 2;

                } else if (line.contains(".png")) {
                    tile[tileNum].image = main.loadExternalImage("Grafik/Tiles/" + line);
                    tile[tileNum].name =getTileName(line);

                    tileLine = 2;
                } else if (tileLine == 2) {
                    tile[tileNum].ID = tileNum;
                    tileLine = 3;
                } else if (tileLine == 3) {
                    if (line.contains("true")) {
                        tile[tileNum].collision = true;

                    } else if (line.contains("false")) {
                        tile[tileNum].collision = false;
                    }
                    tileLine = 4;

                } else if (tileLine == 4) {
                    if (tile[tileNum].collision && !line.contains("true") && !line.contains("false")) {
                        loadCollisionMap(allLines, i, tileNum);
                        i = i + (collMapSize-1);

                    } else if (line.contains("true")) {
                        tile[tileNum].animated = true;
                        tileLine = 5;
                    } else if (line.contains("false")) {
                        tile[tileNum].animated = false;
                        tileLine = 1;
                    }


                }else if (tileLine == 5 && tile[tileNum].animated) {
                    tile[tileNum].animatedPicNumbers = Integer.parseInt(line.trim());

                    tileLine =6;

                } else if (tileLine == 6 && tile[tileNum].animated) {
                    tile[tileNum].animationGap = Integer.parseInt(line.trim());

                    tileLine = 1;
                }


                if (line.isBlank()) {
                    tileNum++;
                    tile[tileNum] = new Tile();
                    tileLine = 1;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public List<BufferedImage> loadAnimationTile(String folderName,int tileNum) {
        List<BufferedImage> frames = new ArrayList<>();
        int frameIndex = 0;

        while (true) {

            String path = "Grafik/Tiles/" + folderName + "/" + frameIndex + ".png";


            if (!main.checkExternalPath(path)){
                break;
            }


            frames.add(main.loadExternalImage(path));

            frameIndex++;
        }

        main.printOnDebug("Animation von tile " +tileNum + " geladen! ("+frameIndex+" frames)");
        return frames;
    }

    public void loadCollisionMap(List<String> allLines, int lineNum, int tileNum) {
        tile[tileNum].collisionMap = new boolean[collMapSize][collMapSize];

        for (int zeile = 0; zeile < collMapSize; zeile++) {
            if (lineNum + zeile >= allLines.size()) {
                main.printErrOnDebug("FEHLER: Textdatei ist zu kurz! Zeile " + (lineNum + zeile) + " existiert nicht.");
                break;
            }

            String line = allLines.get(lineNum + zeile);
            String[] numbers = line.trim().split(" ");

            for (int spalte = 0; spalte < collMapSize; spalte++) {

                int num = Integer.parseInt(numbers[spalte]);
                tile[tileNum].collisionMap[zeile][spalte] = (num == 1);


            }
        }

        main.printOnDebug("Collision Map von tile "+tileNum+" geladen!");
    }

    public void loadMap(ArrayList<ArrayList<Integer>> mapType,String path){
        try {
            InputStream is = main.loadExternalText(path);

            if (is == null) {
                main.printErrOnDebug("Map Datei "+path+" wurde nicht gefunden!");
                System.exit(404);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<String> allLines = br.lines().toList();
            br.close();

            for (int zeile = 0; zeile < allLines.size(); zeile++) {
                String line = allLines.get(zeile);
                String[] numbers = line.trim().split(" ");
                mapType.add(zeile, new ArrayList<>());

                for (int spalte = 0; spalte < numbers.length; spalte++) {
                    int num = Integer.parseInt(numbers[spalte]);
                    mapType.get(zeile).add(spalte, num);
                }
            }

            main.printOnDebug("Map "+path+" geladen!");
        }catch (IOException e) {
            e.printStackTrace();
        }




    }




    public void repaint(Graphics2D g2, ArrayList<ArrayList<Integer>> MapType){

        int worldZeile = 0;
        int worldSpalte = 0;

        int tileSize = main.originalTileSize * main.tileScaleFaktor;

        while (worldZeile < MapType.size() && worldSpalte < MapType.get(worldZeile).size()) {

            double worldY = worldZeile * main.originalTileSize;
            double worldX = worldSpalte * main.originalTileSize;

            double deltaX = worldX - main.playerWorldX;
            double deltaY = worldY - main.playerWorldY;

            int screenX = (int) (deltaX * main.tileScaleFaktor) + (int) main.playerScreenX;
            int screenY = (int) (deltaY * main.tileScaleFaktor) + (int) main.playerScreenY;




            if (    worldX + tileSize > main.playerWorldX -main.playerScreenX &&
                    worldX - tileSize < main.playerWorldX + main.playerScreenX &&
                    worldY + tileSize > main.playerWorldY - (main.playerScreenY*1.2) &&
                    worldY - tileSize < main.playerWorldY +(main.playerScreenY*1.2)) {

                if (MapType.get(worldZeile).get(worldSpalte) != 0) {
                BufferedImage tile = getTile(MapType.get(worldZeile).get(worldSpalte));
                g2.drawImage(tile, screenX, screenY, tileSize, tileSize, null);
                }
            }



            worldSpalte++;

            if (worldSpalte == MapType.get(worldZeile).size()) {
                worldSpalte = 0;
                worldZeile++;
            }


        }





    }

    public BufferedImage getTile(int tileNum) {



        if (tile[tileNum].animated) {


            int frameIndex = (int) ((System.currentTimeMillis() / (2000/(tile[tileNum].animationGap/60)))%tile[tileNum].animatedPicNumbers);
            return tile[tileNum].animationFrames.get(frameIndex);

        }else{
            return tile[tileNum].image;
        }



    }

    public String getTileName(String line) {
        int endX = 0;

        for (int x = 0; x < line.length(); x++) {
            if (line.charAt(x) == '.'||line.charAt(x)=='_') {
                endX = x;
                break;
            }

        }
        return line.substring(0,endX);
    }
}


