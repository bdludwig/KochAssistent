package de.ur.ai;

import aktivitaet.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import plan.BTAssistent;
import status.StateDescription;
import zutat.Zutat;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameScreen implements Screen, InputProcessor {
    final Drop game;

    private OrthographicCamera camera;

    private Texture sos_image;
    private Sprite emergency;

    private double last_screen_X, last_screen_Y;
    private float width, height;

    private Renderer obj_in_hand, selected_object, over;

    private BTAssistent assi;

    public GameScreen(final Drop gam) {
        this.game = gam;
        Gdx.input.setInputProcessor(this);
        width = 640;
        height = 480;

        assi = null;

        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("sos.png"));
        Pixmap pixmap100 = new Pixmap(40, 40, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        sos_image = new Texture(pixmap100);
        emergency = new Sprite(sos_image);
        emergency.setOrigin(10, 10);

        pixmap100.dispose();
        pixmap200.dispose();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        width = camera.viewportWidth;
        height = camera.viewportHeight;

        ArrayList<Renderer> renderers = game.getRenderers();
        for (int i = 0; i < renderers.size(); i++)
            renderers.get(i).setLocation(camera);

        System.out.println("INIITIAL SITUATION: " + game.getKueche().factsToProlog());
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

        emergency.draw(game.batch);

        ArrayList<Renderer> renderers = game.getRenderers();
        ListIterator<Renderer> li = renderers.listIterator();

        if (selected_object != null) {
            selected_object.render(game, game.font, camera);
            Texture t;
            Pixmap p = new Pixmap((int) selected_object.getWidth(),
                    (int) selected_object.getHeight(),
                    Pixmap.Format.RGBA8888);
            p.setColor(0, 1, 0, 1);
            p.drawRectangle(0, 0, (int) selected_object.getWidth(), (int) selected_object.getHeight());
            game.batch.draw(t = new Texture(p), selected_object.getX(), selected_object.getY());

            game.updateRenderers();
        }

        if ((over != null) && (over != selected_object)) {
            Texture t;
            Pixmap p = new Pixmap((int)over.getWidth(),
                    (int)over.getHeight(),
                    Pixmap.Format.RGBA8888);
            p.setColor(1, 1, 0, 1);
            p.drawRectangle(0, 0, (int)over.getWidth(), (int)over.getHeight());
            game.batch.draw(t = new Texture(p), over.getX(), over.getY());
        }

        if (assi != null) {
            game.font.draw(game.batch, assi.getCurrentMessage(), 50, 25);
        }

        game.batch.end();

/*
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
        }

 */
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
        sos_image.dispose();
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
        if (selected_object != null) {
            Aktivitaet a = null;
            StateDescription current = new StateDescription(game.getKueche().factsToProlog());

            switch (character) {
                case 'ö':
                    a = new Oeffnen();
                    a.addArg(selected_object.getObject());

                    if (assi != null) assi.setLastAction(a);

                    if (a.isPossible(game.getKueche().factsToProlog())) {
                        Task.Status result_state = a.perform();

                        if (assi != null) {
                            a.setStatus(result_state);
                        }
                    }
                    else {
                        a.setStatus(Task.Status.FAILED);
                        System.out.println("cannot open");
                    }

                    if (assi != null) assi.step();

                    break;

                case 's':
                    a = new Schliessen();
                    a.addArg(selected_object.getObject());

                    if (assi != null) assi.setLastAction(a);

                    if (a.isPossible(game.getKueche().factsToProlog())) {
                        Task.Status result_state = a.perform();

                        if (assi != null) {
                            a.setStatus(result_state);
                        }
                    }
                    else {
                        a.setStatus(Task.Status.FAILED);
                        System.out.println("cannot close");
                    }

                    if (assi != null) assi.step();

                    break;

                case 'n':
                    a = new Herausnehmen();
                    a.addArg(selected_object.getObject());
                    a.addArg(((Zutat)selected_object.getObject()).getContainer());

                    if (assi != null) assi.setLastAction(a);

                    if (a.isPossible(game.getKueche().factsToProlog())) {
                        Task.Status result_state = a.perform();

                        if (assi != null) {
                            a.setStatus(result_state);
                            assi.setLastAction(a);
                        }

                        // UI Rendering für offene Schranktüren

                        if (result_state == Task.Status.SUCCEEDED) {
                            game.removeRendererCandidate(selected_object);
                            game.updateRenderers();

                            obj_in_hand = selected_object;

                            selected_object = a.getArg(1).getRenderer();
                            selected_object.setVisibility(false);

                            Pixmap pixmap200 = new Pixmap(a.getArg(0).getRenderer().getImg());
                            Pixmap pixmap100 = new Pixmap(16, 32, pixmap200.getFormat());
                            pixmap100.drawPixmap(pixmap200,
                                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
                            );

                            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap100, 0, 0));

                            pixmap100.dispose();
                            pixmap200.dispose();
                        }
                    }
                    else {
                        a.setStatus(Task.Status.FAILED);
                        System.out.println("cannot Herausnehmen");
                    }

                    if (assi != null) {
                        System.out.println("BTASSI - please next step!");
                        assi.step();
                    }

                    break;

                case 'a':
                    if (obj_in_hand == null) {
                        System.out.println("Nutzer hat nichts in der Hand.");
                    }
                    else {
                        a = new Abstellen();
                        a.addArg(obj_in_hand.getObject());
                        a.addArg(selected_object.getObject());

                        if (assi != null) assi.setLastAction(a);

                        if (a.isPossible(game.getKueche().factsToProlog())) {
                            Task.Status result_state = a.perform();

                            if (assi != null) {
                                a.setStatus(result_state);
                            }

                            // UI Rendering

                            if (result_state == Task.Status.SUCCEEDED) {
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
                        }
                        else {
                            a.setStatus(Task.Status.FAILED);
                            System.out.println("cannot Abstellen");
                        }
                    }
                    //else System.out.println("Abstellen geht nur mit Arbeitsplatte, aber nicht mit " + selected_object.getObject().bezeichner());

                    if (assi != null) {
                        System.out.println("BTASSI - please next step!");
                        assi.step();
                    }

                    break;

                case 'e':
                    if (obj_in_hand == null) {
                        System.out.println("Nutzer hat nichts in der Hand.");
                    }
                    else {
                        a = new Einstellen();
                        a.addArg(obj_in_hand.getObject());
                        a.addArg(selected_object.getObject());

                        if (assi != null) assi.setLastAction(a);

                        if (a.isPossible(game.getKueche().factsToProlog())) {
                            Task.Status result_state = a.perform();

                            if (assi != null) {
                                a.setStatus(result_state);
                            }

                            // UI Rendering

                            if (result_state == Task.Status.SUCCEEDED) {
                                obj_in_hand = null;
                                selected_object.setVisibility(false);

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
                        }
                        else {
                            a.setStatus(Task.Status.FAILED);
                            System.out.println("cannot Einstellen");
                        }
                    }

                    if (assi != null) {
                        System.out.println("BTASSI - please next step!");
                        assi.step();
                    }

                    break;

            }

            if (a != null) {
                System.out.println("LAST ACTION: " + a);
                a.effects(current);
                System.out.println("CURRENT SITUATIION: " + game.getKueche().factsToProlog());
            }
        }
        else System.out.println("no object selected");

        return true;
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

        if (emergency.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
            System.out.println("SOS");
            assi = new BTAssistent(game.getKueche());
            assi.init();
            return true;
        }

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

        if (selected_object != null) {
            System.out.println("action on: " + selected_object.label());
        }

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
            if (r.contains(touchPos.x, touchPos.y)) {
                over = r;

                Pixmap p = new Pixmap((int)r.getWidth(),
                        (int)r.getHeight(),
                        Pixmap.Format.RGBA8888);
                p.setColor(1, 1, 0, 1);
                p.drawRectangle(0, 0, (int)r.getWidth(), (int)r.getHeight());
                game.batch.begin();
                game.batch.draw(new Texture(p), r.getX(), r.getY());
                game.batch.end();
            }
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}