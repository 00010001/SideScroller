import assets.Element;
import assets.Camera;
import com.sun.org.apache.regexp.internal.RE;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import platformer.ShapeType;
import utils.FileUtil;
import utils.Renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by X on 9/8/2017.
 */
public class Editor extends BasicGameState {

    private Camera camera = null;

    private SpriteSheet solidTilesSheet = null;
    private SpriteSheet decorativeTilesSheet = null;
    private SpriteSheet currentSpriteSheet = null;

    private Image currentTile = null;
    private int currentTileset = 0;
    private int tilesCursor = 0;
    private int currentTileSubImageX;
    private int currentTileSubImageY;

    public static List<Element> solidElementList;
    public static List<Element> decorativeElementList;
    private List<Element> currentElementList;


    private Input input = null;

    private final static int TILESIZE = 48;

    private int snapToGridX;
    private int snapToGridY;

    int angle = 0;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

        camera = Camera.getInstance();


        Image solidTileset = new Image("res/a_solid2.png");
        solidTilesSheet = new SpriteSheet(solidTileset, TILESIZE, TILESIZE);

        Image decorativeTileset = new Image("res/a_decorative.png");
        decorativeTilesSheet = new SpriteSheet(decorativeTileset, TILESIZE, TILESIZE);

        currentTile = solidTilesSheet.getSubImage(0, 0);
        currentSpriteSheet = solidTilesSheet;

        solidElementList = new ArrayList<>();
        decorativeElementList = new ArrayList<>();

        try {
            FileUtil.readElementSetFromFileTo(solidElementList, "solid.txt");
            FileUtil.readElementSetFromFileTo(decorativeElementList, "decorative.txt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        currentElementList = solidElementList;
        input = gc.getInput();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        Renderer.drawBackground(gc, g);

        Renderer.renderElementsFromList(decorativeElementList, decorativeTilesSheet, camera);

        Renderer.renderElementsFromList(solidElementList, solidTilesSheet, camera);

        Input input = gc.getInput();

        Renderer.renderCameraInfo(g);
        // tiles.startUse();

        currentTile.draw(input.getMouseX(), input.getMouseY());

        Image turret = new Image("res/turret.png");

        turret.rotate(angle);
        turret.draw(400,400);

        g.draw(new Rectangle(snapToGridX - camera.getPos().x, snapToGridY - camera.getPos().y, TILESIZE, TILESIZE));

    }



    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gc.getInput().isKeyDown(Input.KEY_M)){
            angle++;
        }
        if(gc.getInput().isKeyDown(Input.KEY_N)){
            angle--;
        }


        camera.update(gc);
        updateRelativeMousePos();
        onLeftClickAddTile();
        onRightClickRemoveTile();
        onMiddleClickChangeTileSet();
    }

    private void onMiddleClickChangeTileSet() {
        if (input.isMousePressed(2)) {
            if (currentSpriteSheet == solidTilesSheet) {
                currentSpriteSheet = decorativeTilesSheet;
                currentElementList = decorativeElementList;
            } else {
                currentSpriteSheet = solidTilesSheet;
                currentElementList = solidElementList;
            }
        }
        currentTile = currentSpriteSheet.getSubImage(currentTileSubImageX, currentTileSubImageY);
    }

    private void updateRelativeMousePos() {
        snapToGridX = (input.getMouseX() + (int) camera.getPos().x) / TILESIZE * TILESIZE;
        snapToGridY = (input.getMouseY() + (int) camera.getPos().y) / TILESIZE * TILESIZE;
    }

    private void onRightClickRemoveTile() {

        if (input.isMousePressed(1)) {

            Element elementToRemove = null;
            for (Element element : currentElementList) {
                if (element.getPositionX() == snapToGridX && element.getPositionY() == snapToGridY) {
                    elementToRemove = element;
                }
            }

            currentElementList.remove(elementToRemove);
        }
    }

    private void onLeftClickAddTile() {
        System.out.println(currentTileSubImageX + " " + currentTileSubImageY);
        if (input.isMousePressed(0)) {
            if(currentTileSubImageX == 5 && currentTileSubImageY == 2 && currentElementList == solidElementList){
                currentElementList.add(new Element(snapToGridX, snapToGridY, currentTileSubImageX, currentTileSubImageY, ShapeType.StairsRightDown));
            }
            else if(currentTileSubImageX >= 0 && currentTileSubImageX < 2 && currentTileSubImageY == 3 && currentElementList == solidElementList){
                currentElementList.add(new Element(snapToGridX, snapToGridY, currentTileSubImageX, currentTileSubImageY, ShapeType.HighCelling));
            }
            else {
                currentElementList.add(new Element(snapToGridX, snapToGridY, currentTileSubImageX, currentTileSubImageY));
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change) {
        changeTilesCursor(change);
    }

    private void changeTilesCursor(int change) {
        if (change < 0) {
            if (tilesCursor < (currentSpriteSheet.getHorizontalCount() * currentSpriteSheet.getVerticalCount() - 1)) {
                tilesCursor++;
            }
        } else {
            if (tilesCursor > 0) {
                tilesCursor--;
            }
        }
        currentTileSubImageX = tilesCursor % currentSpriteSheet.getHorizontalCount();
        currentTileSubImageY = tilesCursor / currentSpriteSheet.getHorizontalCount();

        currentTile = currentSpriteSheet.getSubImage(currentTileSubImageX, currentTileSubImageY);
    }


}
