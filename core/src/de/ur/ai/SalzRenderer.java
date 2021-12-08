package de.ur.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import raum.Konfiguration;
import zutat.Salz;

public class SalzRenderer extends Renderer {
    private final FileHandle my_img = Gdx.files.internal("salz.png");

    public SalzRenderer(Salz s, Konfiguration c) {
        super(new Texture(Gdx.files.internal("salz.jpeg")), s, c);
    }

    @Override
    public FileHandle getImg() {
        return my_img;
    }

    @Override
    public void perform() {
    }

    public void render(Drop game, BitmapFont font, Camera cam) {
        setBounds(my_config.getX()/this.getScaleX(),
                cam.viewportHeight - (my_config.getH() + my_config.getY())/this.getScaleY(),
                my_config.getW()/this.getScaleX(),
                my_config.getH()/this.getScaleY());

        this.draw(game.batch);
    }
}