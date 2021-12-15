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
import zutat.Nudeln;
import zutat.Salz;
import zutat.Zutat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class Drop extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Texture backgroundImage;
    public Sprite hand;

    private Kueche meineKueche;
    private Kuechenplan meinPlan;

    private ArrayList<Renderer> renderer;
    private ArrayList<Renderer> renderers_to_add;
    private ArrayList<Renderer> renderers_to_remove;

    public void create() {
        Rectangle r;
        KochAssistentObject k, z;
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

        z = new Salz();
        meineKueche.addItem(z);
        ((Moebel)k).addContainedObject(z);
        ((Zutat)z).setContainer(k);

        z = new Nudeln();
        meineKueche.addItem(z);
        ((Moebel)k).addContainedObject(z);
        ((Zutat)z).setContainer(k);

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
        renderers_to_add = new ArrayList<Renderer>();
        renderers_to_remove = new ArrayList<Renderer>();

        ListIterator<KochAssistentObject> li = meineKueche.getItems().listIterator();
        Renderer nr;

        while (li.hasNext()) {
            k = li.next();
            Konfiguration c = meinPlan.konfigurationVon(k.id());

            if (k instanceof Ofen) {
                nr = new OvenRenderer((Ofen)k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Schrank) {
                nr = new SchrankRenderer((Schrank) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Kuehlschrank) {
                nr = new KuehlschrankRenderer((Kuehlschrank) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Gefrierschrank) {
                nr = new GefrierschrankRenderer((Gefrierschrank) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Schublade) {
                nr = new SchubladeRenderer((Schublade) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Wasserhahn) {
                nr = new WasserhahnRenderer((Wasserhahn) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Dunstabzug) {
                nr = new DunstabzugRenderer((Dunstabzug) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Arbeitsplatte) {
                nr = new ArbeitsplatteRenderer((Arbeitsplatte) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Spuele) {
                nr = new SpueleRenderer((Spuele) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Kochfeld) {
                nr = new KochfeldRenderer((Kochfeld) k, c);
                renderer.add(nr);
                k.setRenderer(nr);
            }
            else if (k instanceof Zutat) {}
//                renderer.add(new SalzRenderer((Salz) k,
//                             meinPlan.konfigurationVon(((Salz)k).getContainer().id())));
            else System.err.println("ERROR: unknown type: " + k.getClass().getSimpleName());
        }

        // visualization background and user

        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        font.setColor(0, 0, 1, 1);
        backgroundImage = new Texture(Gdx.files.internal("kitchen.jpeg"));

        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("hand.png"));
        Pixmap pixmap100 = new Pixmap(16, 32, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap100, 0, 0));

        pixmap100.dispose();
        pixmap200.dispose();

        this.setScreen(new GameScreen(this));
    }

    public Kueche getKueche() {
        return meineKueche;
    }

    public ArrayList<Renderer> getRenderers() {
        return renderer;
    }

    public void addRenderer(Renderer r) {
        renderer.add(r);
    }

    public void removeRenderer(Renderer r) {
        int i = 0;
        KochAssistentObject k;

        r.getTexture().dispose();

        while (i < renderer.size()) {
            k = renderer.get(i).getObject();

            if ((r.getObject() != null) && (k != null)) {
                if (r.getObject().id().equals(k.id())) break;
            }

            i++;
        }

        if (i < renderer.size()) renderer.remove(i);
    }

    public void addRendererCandidate(Renderer r) {
        renderers_to_add.add(r);
    }

    public void removeRendererCandidate(Renderer r) {
        renderers_to_remove.add(r);
    }

    public void updateRenderers() {
        for (Renderer r : renderers_to_remove) {
            removeRenderer(r);
        }

        for (Renderer r : renderers_to_add) {
            addRenderer(r);
        }

        renderers_to_add.clear();
        renderers_to_remove.clear();
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundImage.dispose();
    }
}