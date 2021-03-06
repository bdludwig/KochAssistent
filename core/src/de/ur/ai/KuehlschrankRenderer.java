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

    public void render(Drop game, BitmapFont font, Camera cam) {
        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        font.draw(game.batch,
                ((Kuehlschrank)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
