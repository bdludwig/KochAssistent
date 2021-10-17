package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import moebel.Arbeitsplatte;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class ArbeitsplatteRenderer extends Renderer{
    public ArbeitsplatteRenderer(Arbeitsplatte a, Konfiguration c) {
        super(a, c);
    }

    @Override
    public void perform() {

    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Arbeitsplatte)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }
}
