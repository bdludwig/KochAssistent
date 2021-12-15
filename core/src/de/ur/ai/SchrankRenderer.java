package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import main.KochAssistentObject;
import moebel.Schrank;
import raum.Konfiguration;
import zutat.Nudeln;
import zutat.Salz;

import java.util.ArrayList;

public class SchrankRenderer extends Renderer {
    public SchrankRenderer(Schrank s, Konfiguration c) {
        super(s, c);
        contained_elements_visible = false;
    }

    public void perform() {
        ((Schrank)my_object).perform();
    }

    public void render(Drop game, BitmapFont font, Camera cam) {
        Schrank s = (Schrank)my_object;

        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        if (s.isOpen()) {
            ArrayList<KochAssistentObject> items = s.getContainedObjects();

            if (contained_elements_visible == false) {
                for (int i = 0; i < items.size(); i++) {
                    KochAssistentObject o = items.get(i);
                    Renderer r;
                    float w = my_config.getW()/4;
                    float h = my_config.getH()/4;
                    Konfiguration conf = new Konfiguration((int)(my_config.getX() + (w+1)*(i % 4)),
                            (int)(my_config.getY() + (h+1)*(i /4)),
                            (int)w,
                            (int)h);

                    if (o instanceof Salz) r = new SalzRenderer((Salz) o, conf);
                    else if (o instanceof Nudeln) r = new NudelRenderer((Nudeln) o, conf);
                    else break;

                    o.setRenderer(r);

                    r.render(game, font, cam);
                    game.addRendererCandidate(r);
                }

                contained_elements_visible = true;
            }
            else {
                Renderer r;

                for (KochAssistentObject o : items) {
                    r = o.getRenderer();

                    r.render(game, font, cam);
                }
            }
        }
        else {
            if (contained_elements_visible) {
                makeInvisible(game);
                contained_elements_visible = false;
            }
            /*
            font.draw(game.batch,
                    ((Schrank) my_object).factsToProlog(new ParameterSet()).toString(),
                    this.getX() / this.getScaleX(),
                    cam.viewportHeight - this.getY() / this.getScaleY(),
                    this.getWidth(),
                    0,
                    true);
             */
        }
    }

    @Override
    public void makeInvisible(Drop game) {
        Schrank s = (Schrank)my_object;
        ArrayList<KochAssistentObject> items = s.getContainedObjects();

        for (KochAssistentObject o : items) {
            if (o.getRenderer() != null) {
                o.getRenderer().setPosition(0, 0);
                game.removeRendererCandidate(o.getRenderer());
            }
        }
    }
}
