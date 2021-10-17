package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import moebel.Schrank;
import prolog.ParameterSet;
import raum.Konfiguration;

import java.util.List;

public class SchrankRenderer extends Renderer {
    public SchrankRenderer(Schrank s, Konfiguration c) {
        super(s, c);
    }

    public void perform() {
        ((Schrank)my_object).perform();
    }

    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Schrank)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX()/this.getScaleX(),
                cam.viewportHeight - this.getY()/this.getScaleY(),
                this.getWidth(),
                0,
                true);
    }
}
