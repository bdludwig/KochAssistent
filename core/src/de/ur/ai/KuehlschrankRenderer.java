package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import elektrogeraet.Kuehlschrank;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class KuehlschrankRenderer extends Renderer {
    public KuehlschrankRenderer(Kuehlschrank s, Konfiguration c) {
        super(s, c);
    }

    public void perform() {
        ((Kuehlschrank)my_object).processClick();
    }

    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Kuehlschrank)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
