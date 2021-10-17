package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import moebel.Schrank;
import moebel.Schublade;
import prolog.ParameterSet;
import raum.Konfiguration;

public class SchubladeRenderer extends Renderer {
    public SchubladeRenderer(Schublade s, Konfiguration c) {
        super(s, c);
    }

    public void perform() {
        ((Schublade) my_object).perform();
    }

    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Schublade)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}