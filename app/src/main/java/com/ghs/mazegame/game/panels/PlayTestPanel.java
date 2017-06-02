package com.ghs.mazegame.game.panels;

import android.util.Log;

import static com.ghs.mazegame.game.Main.*;

import com.ghs.mazegame.R;
import com.ghs.mazegame.engine.components.Shader;
import com.ghs.mazegame.engine.components.Texture;
import com.ghs.mazegame.engine.display.Camera;
import com.ghs.mazegame.engine.math.Vector3f;
import com.ghs.mazegame.game.Main;
import com.ghs.mazegame.game.interfaces.Panel;
import com.ghs.mazegame.game.objects.DPad;
import com.ghs.mazegame.game.map.Map;
import com.ghs.mazegame.game.objects.Image;
import com.ghs.mazegame.game.objects.Player;
import com.ghs.mazegame.game.objects.Selector;

import java.util.ArrayList;

public class PlayTestPanel implements Panel {

    private int state = -1;

    private Map map;
    private Player player;
    private DPad dpad;

    private int パイロシニコル = 0;

    private Selector sel;

    private Camera camera;

    private ArrayList<Integer> items = new ArrayList<>();

    public Long startTime;

    public PlayTestPanel(Camera camera) {
        this.camera = camera;
        dpad = new DPad(camera, SCALE * 0.25f, cameraHeight - SCALE * 3.25f, SCALE * 3, SCALE * 3);
        sel = new Selector(camera, SCALE * 5.25f, cameraHeight - SCALE * 5.25f, 0.1f, SCALE * 3, SCALE * 3, new int[] {1, 1, 1, 1}, new int[] {R.drawable.stone_key_wall_left, R.drawable.stone_key_wall_right, R.drawable.stone_key_wall_up, R.drawable.stone_key_wall_down}, false);
        player = new Player(camera, new Texture(R.drawable.samby), new Shader(R.raw.defaultvs, R.raw.defaultfs), 0, 0, SCALE, SCALE);
    }

    public void update() {
        dpad.update();
        sel.update();
        updatePlayer();
        updateCamera();
        checkTile();
    }

    public void render() {
        map.render();
        player.render();
        //dpad.render();
        sel.render();
    }

    private void updatePlayer() {
        float speed = SCALE * 3;
        player.translate(dpad.getDir().mul(speed * time, new Vector3f()));
        player.update();
        map.checkPlayerCollision(player);
    }

    private void updateCamera() {
        int width = map.getWidth();
        int height = map.getHeight();
        //Sets the camera position putting the player in the center
        camera.setPosition(player.getX() + (player.getWidth() - camera.getWidth()) / 2, player.getY() + (player.getHeight() - camera.getHeight()) / 2, 0);
        //Checks that the camera is not going past the edge of the map and adjusting the camera position if neccessary
        if(camera.getX() < 0)
            camera.setPosition(0, camera.getY(), 0);
        else if(camera.getX() > width * SCALE - camera.getWidth())
            camera.setPosition(width * SCALE - camera.getWidth(), camera.getY(), 0);
        if(camera.getY() < 0)
            camera.setPosition(camera.getX(), 0, 0);
        else if(camera.getY() > height * SCALE - camera.getHeight())
            camera.setPosition(camera.getX(), height * SCALE - camera.getHeight(), 0);
    }

    private void checkTile() {
        Vector3f center = player.getCenter();
        int type = map.getTile((int) (center.x / SCALE), (int) (center.y / SCALE), true);
        center.sub((int) (center.x / SCALE) * SCALE + SCALE / 2, (int) (center.y / SCALE) * SCALE + SCALE / 2, 0);
        if(type == Map.TYPE_END && center.lengthSquared() <= SCALE) {
            // Map ends
            state = Main.STATE_EDIT;
            float totalTime = (System.nanoTime() - startTime) / 1000000f;

            //doSomething(totalTime);
        }
        if(type == Map.TYPE_GOLD_KEY && center.lengthSquared() <= SCALE) {
            map.setTileRaw(true, Map.TYPE_EMPTY, (int) (player.getCenter().x / SCALE), (int) (player.getCenter().y / SCALE));
            items.add(Map.TYPE_GOLD_KEY);
            Log.d("key", "Position: " + (int) (player.getCenter().x / SCALE) + (int) (player.getCenter().y / SCALE));
        }
    }

    public int checkState() {
        int temp = state;
        state = -1;
        return temp;
    }

    public void setActive(Map map) {
        // Map starts
        this.map = map;
        map.setState(Map.STATE_PLAY);
        player.setPosition(map.getStart().mul(SCALE, new Vector3f()));
        player.setBounds(map.getBounds());

        startTime = System.nanoTime();



    }

    public Map getMap() {
        return map;
    }
}
