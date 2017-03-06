package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * Created by Natalia on 23/02/2017.
 */

public class Fairy extends Actor{
    private Texture fairy;
    private boolean fairyExist;

    public Fairy(Texture fairy, float x, float y, boolean fairyExist) {
        this.fairy = fairy;
        this.fairyExist = true;
        setPosition(x * Constants.PIXELS_IN_METER, y * Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fairy, getX(),getY(),40,40);
    }

    public boolean isFairyExist() {
        return fairyExist;
    }

    public void setCoinExist(boolean fairyExist) {
        this.fairyExist = fairyExist;
    }
}
