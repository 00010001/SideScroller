package platformer;

import assets.Bullet;
import assets.Element;
import assets.Camera;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import utils.FileUtil;
import utils.Renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Platformer extends BasicGameState {

    private final static int TILESIZE = 48;

    private Camera camera = Camera.getInstance();

    public static List<Element> solidElementList;
    public static List<Element> decorativeElementList;

    public static List<Bullet> bulletList = new ArrayList<>();

    private SpriteSheet solidTilesSheet = null;
    private SpriteSheet decorativeTilesSheet = null;

    private StaticLevel level = StaticLevel.getInstance();
    private Player player = Player.getInstance();

    private boolean renderShapes = false;

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

        Image solidTileset = new Image("res/a_solid2.png");
        solidTilesSheet = new SpriteSheet(solidTileset, TILESIZE, TILESIZE);

        Image decorativeTileset = new Image("res/a_decorative.png");
        decorativeTilesSheet = new SpriteSheet(decorativeTileset, TILESIZE, TILESIZE);

        solidElementList = new ArrayList<>();
        decorativeElementList = new ArrayList<>();

        try {
            FileUtil.readElementSetFromFileTo(solidElementList, "solid.txt");
            FileUtil.readElementSetFromFileTo(decorativeElementList, "decorative.txt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        level.init(solidElementList);
        player.init(gc);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        Renderer.drawBackground(gc, g);
        Renderer.renderElementsFromList(decorativeElementList, decorativeTilesSheet, camera);
        Renderer.renderElementsFromList(solidElementList, solidTilesSheet, camera);
        //Renderer.renderCameraInfo(g);
        if (renderShapes) {
            Renderer.renderStaticLevel(g);
        }
        Renderer.renderPlayer(g);
        Renderer.renderBullets(g);

    }

    private void updateOptions(GameContainer gc) {
        if (gc.getInput().isKeyPressed(Input.KEY_U)) {
            if (renderShapes)
                renderShapes = false;
            else
                renderShapes = true;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        camera.followPlayer(gc);

        level.update(gc, delta);
        player.update(gc, delta);
        updateOptions(gc);


        for (Iterator<Bullet> iterator = bulletList.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            bullet.update();
            for (Shape shape : StaticLevel.getInstance().getShapeSet()) {
                if (bullet.getShape().intersects(shape)) {
                    iterator.remove();
                }
            }

        }
    }


}

