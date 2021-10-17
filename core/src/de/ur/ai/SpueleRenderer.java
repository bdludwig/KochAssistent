package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import moebel.Schrank;
import moebel.Spuele;
import prolog.ParameterSet;
import raum.Konfiguration;

public class SpueleRenderer extends Renderer{
    public SpueleRenderer(Spuele s, Konfiguration c) {
        super(s, c);
    }

    @Override
    public void perform() {

    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font, Camera cam) {
        font.draw(batch,
                ((Spuele)my_object).factsToProlog(new ParameterSet()).toString(),
                this.getX(),
                cam.viewportHeight - this.getY());
    }}
