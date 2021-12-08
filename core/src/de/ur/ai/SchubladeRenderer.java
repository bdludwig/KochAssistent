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

    public void render(Drop game, BitmapFont font, Camera cam) {
        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        font.draw(game.batch,
                ((Schublade)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}