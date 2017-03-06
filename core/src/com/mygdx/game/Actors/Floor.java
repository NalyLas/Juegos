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
 * Created by Natalia on 22/02/2017.
 */

public class Floor extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fix;

    public Floor(World world, Texture texture, float x , float width, float y){
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(x + width / 2,y - 0.5f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2,0.5f);
        fix = body.createFixture(box,1);
        fix.setUserData("floor");
        box.dispose();

        setSize(width * Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
        setPosition(x * Constants.PIXELS_IN_METER, (y - 1) * Constants.PIXELS_IN_METER);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fix);
        world.destroyBody(body);
    }
}
