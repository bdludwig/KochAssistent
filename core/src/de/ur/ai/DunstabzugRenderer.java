package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import elektrogeraet.Dunstabzug;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

public class DunstabzugRenderer extends Renderer {
    public DunstabzugRenderer(Dunstabzug d, Konfiguration c) {
        super(d, c);
    }

    @Override
    public void perform() {

    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Dunstabzug)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
