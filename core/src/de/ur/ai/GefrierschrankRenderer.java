package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import elektrogeraet.Elektrogeraet;
import elektrogeraet.Gefrierschrank;
import elektrogeraet.Kuehlschrank;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class GefrierschrankRenderer extends Renderer {
    public GefrierschrankRenderer(Gefrierschrank s, Konfiguration c) {
        super(s, c);
    }

    public void perform() {
        ((Gefrierschrank) my_object).processClick();
    }

    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Gefrierschrank)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
