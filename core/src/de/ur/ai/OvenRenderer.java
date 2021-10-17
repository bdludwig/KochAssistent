package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import elektrogeraet.Ofen;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class OvenRenderer extends Renderer {

    public OvenRenderer(Ofen o, Konfiguration c) {
        super(o, c);
    }

    public void perform() {
        ((Ofen)my_object).processClick();
    }

    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Ofen)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }
}
