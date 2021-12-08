package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.KochAssistentObject;
import moebel.Arbeitsplatte;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;
import zutat.Nudeln;
import zutat.Salz;

import java.util.ArrayList;

public class ArbeitsplatteRenderer extends Renderer{
    public ArbeitsplatteRenderer(Arbeitsplatte a, Konfiguration c) {
        super(a, c);
    }

    @Override
    public void perform() {

    }

    @Override
    public void render(Drop game, BitmapFont font, Camera cam) {
        Arbeitsplatte s = (Arbeitsplatte) my_object;

        setBounds(my_config.getX() / this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY()) / this.getScaleY(),
                my_config.getW() / this.getScaleX(),
                my_config.getH() / this.getScaleY());

        ArrayList<KochAssistentObject> items = s.getContainedObjects();

        for (int i = 0; i < items.size(); i++) {
            KochAssistentObject o = items.get(i);
            Renderer r;
            float w = 16;
            float h = 11;
            Konfiguration conf = new Konfiguration((int) (my_config.getX() + (w + 1) * i),
                    (int) my_config.getY(),
                    (int) w,
                    (int) h);

            game.getKueche().addItem(o);

            if (o instanceof Salz) r = new SalzRenderer((Salz) o, conf);
            else if (o instanceof Nudeln) r = new NudelRenderer((Nudeln) o, conf);
            else break;

            o.setRenderer(r);

            r.render(game, font, cam);
            game.addRendererCandidate(r);
        }
    }
}
