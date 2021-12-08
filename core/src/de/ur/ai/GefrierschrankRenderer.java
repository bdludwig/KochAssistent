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

    public void render(Drop game, BitmapFont font, Camera cam) {
        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        font.draw(game.batch,
                ((Gefrierschrank)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
