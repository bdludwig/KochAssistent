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
    public void render(Drop game, BitmapFont font, Camera cam) {
        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        font.draw(game.batch,
                ((Wasserhahn)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
