package de.ur.ai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import elektrogeraet.*;
import kuechengeraet.Wasserhahn;
import main.KochAssistentObject;
import moebel.*;
import raum.Konfiguration;
import raum.Kueche;
import raum.Kuechenplan;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class Drop extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Texture backgroundImage, handImage;
    public Sprite hand;

    private Kueche meineKueche;
    private Kuechenplan meinPlan;
    private ArrayList<Renderer> renderer;

    public void create() {
        Rectangle r;
        KochAssistentObject k;
        // environment

        meineKueche = new Kueche();
        meinPlan = new Kuechenplan();

        k = new Ofen();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 391, 228, 460-393, 306-228);

        k = new Kuehlschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 462, 136, 535-462, 306-136);

        k = new Gefrierschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 462, 308, 535-462, 403-308);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 391, 136, 460-393, 226-136);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 84, 145, 151-84, 189-145);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 84, 191, 151-84, 236-191);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 153, 145, 218-153, 189-145);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 153, 191, 218-153, 236-191);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 220, 145, 286-220, 189-145);

        k = new Oberschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 220, 191, 286-220, 236-191);

        k = new Unterschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 75, 308, 143-75, 403-308);

        k = new Unterschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 145, 308, 213-145, 403-308);

        k = new Unterschrank();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 215, 308, 283-215, 403-308);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 285, 308, 389-285, 326-308);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 393, 308, 462-393, 326-308);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 285, 328, 389-285, 362-328);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 393, 328, 462-393, 362-328);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 285, 364, 389-285, 403-362);

        k = new Schublade();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 393, 364, 462-393, 403-362);

        k = new Wasserhahn();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 146, 261, 30, 15);

        k = new Dunstabzug();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 288, 193, 391-288, 210-193);

        k = new Spuele();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 94, 291, 100, 10);

        k = new Arbeitsplatte();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 195, 291, 100, 10);

        k = new Kochfeld();
        meineKueche.addItem(k);
        meinPlan.neuerGegenstand(k, 295, 291, 381-295, 10);

        // visualization for environment

        renderer = new ArrayList<Renderer>();

        ListIterator<KochAssistentObject> li = meineKueche.getItems().listIterator();

        while (li.hasNext()) {
            k = li.next();
            Konfiguration c = meinPlan.konfigurationVon(k.id());

            if (k instanceof Ofen) renderer.add(new OvenRenderer((Ofen)k, c));
            else if (k instanceof Schrank) renderer.add(new SchrankRenderer((Schrank) k, c));
            else if (k instanceof Kuehlschrank) renderer.add(new KuehlschrankRenderer((Kuehlschrank) k, c));
            else if (k instanceof Gefrierschrank) renderer.add(new GefrierschrankRenderer((Gefrierschrank) k, c));
            else if (k instanceof Schublade) renderer.add(new SchubladeRenderer((Schublade) k, c));
            else if (k instanceof Wasserhahn) renderer.add(new WasserhahnRenderer((Wasserhahn) k, c));
            else if (k instanceof Dunstabzug) renderer.add(new DunstabzugRenderer((Dunstabzug) k, c));
            else if (k instanceof Arbeitsplatte) renderer.add(new ArbeitsplatteRenderer((Arbeitsplatte) k, c));
            else if (k instanceof Spuele) renderer.add(new SpueleRenderer((Spuele) k, c));
            else if (k instanceof Kochfeld) renderer.add(new KochfeldRenderer((Kochfeld) k, c));
            else System.err.println("ERROR: unknown type: " + k.getClass().getSimpleName());
        }

        // visualization background and user

        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        font.getData().setScale(0.8f);
        backgroundImage = new Texture(Gdx.files.internal("kitchen.jpeg"));

        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("hand.jpeg"));
        Pixmap pixmap100 = new Pixmap(50, 50, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        handImage = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();
        hand = new Sprite(handImage, 50, 50);
        this.setScreen(new GameScreen(this));
    }

    public ArrayList<Renderer> getRenderers() {
        return renderer;
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundImage.dispose();
        handImage.dispose();
    }
}