package de.ur.ai;

import aktivitaet.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import main.KochAssistentObject;
import moebel.Arbeitsplatte;
import zutat.Zutat;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameScreen implements Screen, InputProcessor {
    final Drop game;

    Music rainMusic;
    OrthographicCamera camera;

    private double last_screen_X, last_screen_Y;
    private float width, height;

    private Renderer obj_in_hand, selected_object, over;

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

        ArrayList<Renderer> renderers = game.getRenderers();
        for (int i = 0; i < renderers.size(); i++)
            renderers.get(i).setLocation(camera);
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

        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        if (selected_object != null) {
            while (li.hasNext()) {
                Renderer r = li.next();

                if (r.label().equals(selected_object.label())) {
                    r.render(game, game.font, camera);
                }

            }
            game.batch.end();

            game.updateRenderers();
        }
        else if (over != null) {
            game.batch.end();

            while (li.hasNext()) {
                Renderer r = li.next();

                if (r.label().equals(over.label())) {
                    ShapeRenderer shapeRenderer = new ShapeRenderer();
                    camera.update();
                    shapeRenderer.setProjectionMatrix(camera.combined);

                    shapeRenderer.setColor(1, 1, 0, 1);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                    shapeRenderer.end();
                }
            }
        }
        else game.batch.end();


        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
        }
    }

    @Override
    public void resize(int new_width, int new_height) {
        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        while (li.hasNext()) {
            Renderer r = li.next();
            r.setBounds(r.getConfig().getX()/r.getScaleX(),
                    camera.viewportHeight - (r.getConfig().getH() + r.getConfig().getY())/r.getScaleY(),
                    r.getConfig().getW()/r.getScaleX(),
                    r.getConfig().getH()/r.getScaleY());
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
            ArrayList<Renderer> args = new ArrayList<Renderer>();
            Aktivitaet a;

            switch (character) {
                case 'ö':
                    a = new Oeffnen();
                    args.add(selected_object);

                    if (a.isPossible(args, game.getKueche().factsToProlog())) a.perform(args);
                    else System.out.println("cannot open");

                    break;

                case 's':
                    a = new Schliessen();
                    args.add(selected_object);

                    if (a.isPossible(args, game.getKueche().factsToProlog())) a.perform(args);
                    else System.out.println("cannot close");

                    break;

                case 'n':
                    System.out.println(selected_object.getObject().id() + " nehmen");
/*
                    if (selected_object.getObject() instanceof Zutat) {

 */
                        a = new Herausnehmen();

                        args.add(selected_object);
                        args.add(((Zutat)selected_object.getObject()).getContainer().getRenderer());

                        if (a.isPossible(args, game.getKueche().factsToProlog())) {
                            a.perform(args);

                            game.removeRendererCandidate(selected_object);
                            game.updateRenderers();
                            obj_in_hand = selected_object;

                            selected_object = args.get(1);

                            Pixmap pixmap200 = new Pixmap(args.get(0).getImg());
                            Pixmap pixmap100 = new Pixmap(16, 32, pixmap200.getFormat());
                            pixmap100.drawPixmap(pixmap200,
                                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
                            );

                            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap100, 0, 0));

                            pixmap100.dispose();
                            pixmap200.dispose();
                        }
                        else System.out.println("cannot Herausnehmen");
                        /*
                    }
                    else System.out.println("Herausnehmen geht nur mit Zutaten, aber nicht mit " + selected_object.getObject().bezeichner());
*/
                    break;

                case 'a':
                    System.out.println(selected_object.getObject().id() + " abstellen");

                    if (obj_in_hand == null) {
                        System.out.println("Nutzer hat nichts in der Hand.");
                    }
                    else {
                        a = new Abstellen();

                        args.add(obj_in_hand);
                        args.add(selected_object);

                        if (a.isPossible(args, game.getKueche().factsToProlog())) {
                            a.perform(args);

                            game.removeRendererCandidate(obj_in_hand);
                            game.updateRenderers();

                            obj_in_hand = null;

                            Pixmap pixmap200 = new Pixmap(Gdx.files.internal("hand.png"));
                            Pixmap pixmap100 = new Pixmap(16, 32, pixmap200.getFormat());
                            pixmap100.drawPixmap(pixmap200,
                                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
                            );

                            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap100, 0, 0));

                            pixmap100.dispose();
                            pixmap200.dispose();
                        }
                        else System.out.println("cannot Abstellen");
                    }
                    //else System.out.println("Abstellen geht nur mit Arbeitsplatte, aber nicht mit " + selected_object.getObject().bezeichner());

                    break;
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
        over = null;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();
        Vector3 touchPos = new Vector3();

        System.out.println("mouse released on: " + screenX + "," + screenY);

        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        while (li.hasNext()) {
            Renderer r = li.next();
            if (r.contains(touchPos.x, touchPos.y)) {
                if (selected_object == null) selected_object = r;
                else if (selected_object.getBoundingRectangle().overlaps(r.getBoundingRectangle()))
                    selected_object = r;
                else {
                    System.out.println("selected: " + selected_object.getBoundingRectangle() + " r: " + r.getBoundingRectangle());
                }
            }
        }

        if (selected_object != null) System.out.println("action on: " + selected_object.label());

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 touchPos = new Vector3();

        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        if (selected_object != null) game.updateRenderers();

        while (li.hasNext()) {
            Renderer r = li.next();
//            System.out.println(r.label() + " with " + r.getBoundingRectangle() + " at " + screenX + "," + screenY);
            if (r.contains(touchPos.x, touchPos.y)) {
                System.out.println("mouse over: " + r.label() + " at " + touchPos.x + "," + touchPos.y);

                over = r;
            }
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}