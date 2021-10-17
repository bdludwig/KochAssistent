package de.ur.ai;

import aktivitaet.Aktivitaet;
import aktivitaet.Oeffnen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.ListIterator;


public class GameScreen implements Screen, InputProcessor {
    final Drop game;

    Music rainMusic;
    OrthographicCamera camera;

    private double last_screen_X, last_screen_Y;
    private float width, height;

    private boolean hand_moving;
    private Renderer selected_object, over;

    public GameScreen(final Drop gam) {
        this.game = gam;
        Gdx.input.setInputProcessor(this);
        width = 640;
        height = 480;

        // load the drop sound effect and the rain background "music"

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        // rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        width = camera.viewportWidth;
        height = camera.viewportHeight;

        game.hand.setX(camera.viewportWidth/2.0f-25);
        game.hand.setY(0);
        game.hand.setRegionHeight(50);
        game.hand.setRegionWidth(50);
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 1, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.

        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.batch.draw(game.backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.hand.draw(game.batch);

        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        if (selected_object != null) {
            while (li.hasNext()) {
                Renderer r = li.next();

                if (r.label().equals(selected_object.label())) {
                    r.render(game.batch, game.font, camera);
                }

            }
        }
        else if (over != null) {
            while (li.hasNext()) {
                Renderer r = li.next();

                if (r.label().equals(over.label())) {
                    ShapeRenderer shapeRenderer = new ShapeRenderer();
                    camera.update();
                    shapeRenderer.setProjectionMatrix(camera.combined);

                    shapeRenderer.setColor(1, 1, 0, 1);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.rect(r.getX() / r.getScaleX(),
                            camera.viewportHeight - r.getHeight() - r.getY() / r.getScaleY(),
                            r.getWidth(),
                            r.getHeight());
                    shapeRenderer.end();
                }
            }
        }

        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
        }
    }

    @Override
    public void resize(int new_width, int new_height) {
        float scale_x, scale_y;

        scale_x = ((float)new_width)/width;
        scale_y = ((float)new_height)/height;

        System.out.println("new size: " + width + "," + height + ","  + new_width + "," + new_height + ","+ scale_x + "," + scale_y + "," + camera.viewportWidth + "," + camera.viewportHeight);

        width = new_width;
        height = new_height;


        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        while (li.hasNext()) {
            li.next().scaleBounds(scale_x, scale_y);
        }

        camera.update();

    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        // rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        rainMusic.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("key: "  + character);
        if (selected_object != null) {
            if (character == 'ö') {
                Aktivitaet a = new Oeffnen();
                ArrayList<Renderer> args = new ArrayList<Renderer>();
                args.add(selected_object);
                if (a.isPossible(args)) a.perform(args);
                else System.out.println("cannot open");
            }
        }
        else System.out.println("no object selected");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        System.out.println("mouse pressed on: " + screenX + "," + screenY);

        selected_object = null;

        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        if (game.hand.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
            hand_moving = true;
            last_screen_X = touchPos.x - game.hand.getWidth()/2.0;
            last_screen_Y = touchPos.y - game.hand.getHeight()/2.0;

            game.hand.setX((float) last_screen_X);
            game.hand.setY((float) last_screen_Y);
        }
        else hand_moving = false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        System.out.println("mouse released on: " + screenX + "," + screenY);
        hand_moving = false;

        while (li.hasNext()) {
            Renderer r = li.next();
            if (r.contains(screenX, screenY)) {
                System.out.println("action on: " + r.label() + " - " + r.getBoundingRectangle() + ", " + r.getX());
                selected_object = r;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPos = new Vector3();


        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);
        System.out.println("dragged on: " + touchPos.x + "," + touchPos.y);

        if (hand_moving) {
            last_screen_X = touchPos.x - game.hand.getWidth()/2.0;
            last_screen_Y = touchPos.y - game.hand.getHeight()/2.0;

            game.hand.setX((float) last_screen_X);
            game.hand.setY((float) last_screen_Y);
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        while (li.hasNext()) {
            Renderer r = li.next();
            if (r.contains(screenX, screenY)) {
                System.out.println("mouse over: " + r.label() + " at " + screenX + "," + screenY);

                over = r;
            }
        }

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}