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

public class Crab extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fix;

    public Crab(World world, Texture texture, Vector2 position){
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,0.5f);
        fix = body.createFixture(box,3);
        fix.setUserData("crab");
        box.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,(body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public void detach(){
        body.destroyFixture(fix);
        world.destroyBody(body);
    }
}
