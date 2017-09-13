import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import platformer.Platformer;
import utils.FileUtil;

import java.io.IOException;

public class Application extends StateBasedGame {

    // Game state identifiers
    public static final int EDITOR = 0;
    public static final int FARMER     = 1;
    public static final int PLATFORMER         = 2;

    // Application Properties
    public static final int WIDTH   = 1024;
    public static final int HEIGHT  = 768;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;

    // Class Constructor
    public Application(String appName) {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
      // this.addState(new Editor());
        this.addState(new Platformer());

       /// this.addState(new platformer.Platformer());
    }

    // Main Method
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Application("My Game v" + VERSION));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean closeRequested() {


        if(Editor.solidElementList != null){
            try {
                FileUtil.writeElementSetToFile(Editor.solidElementList, "solid.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(Editor.decorativeElementList != null){
            try {
                FileUtil.writeElementSetToFile(Editor.decorativeElementList, "decorative.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}