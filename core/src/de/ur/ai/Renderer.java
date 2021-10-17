package de.ur.ai;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.KochAssistentObject;
import moebel.Schublade;
import raum.Konfiguration;

abstract public class Renderer extends Sprite {
    protected KochAssistentObject my_object;

    public Renderer(KochAssistentObject k, Konfiguration c) {
        super();

        my_object = k;

        this.setBounds(c.getX(), c.getY(), c.getW(), c.getH());
    }

    public boolean contains(float x, float y) {
        return this.getBoundingRectangle().contains(x, y);
    }

    public String label() {
        return my_object.toString();
    }

    public abstract void perform();

    public abstract void render(SpriteBatch batch, BitmapFont font, Camera cam);

    public void scaleBounds(float scale_x, float scale_y) {
        this.setScale(this.getScaleX()*scale_x, this.getScaleY()*scale_y);
        this.setX(this.getX()*scale_x);
        this.setY(this.getY()*scale_y);
        System.out.println(label() + ": " + this.getBoundingRectangle());
    }

    public KochAssistentObject getObject() {
        return my_object;
    }
}
