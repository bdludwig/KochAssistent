package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import elektrogeraet.Ofen;
import kuechengeraet.Wasserhahn;
import main.KochAssistentObject;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class WasserhahnRenderer extends Renderer {
    public WasserhahnRenderer(KochAssistentObject k, Konfiguration c) {
        super(k, c);
    }

    @Override
    public void perform() {

    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Wasserhahn)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
