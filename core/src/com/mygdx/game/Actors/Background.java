package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
 * Created by Natalia on 24/02/2017.
 */
public class Background extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fix;

    public Background(World world){
        this.world = world;
        texture = new Texture("back.png");

        BodyDef def = new BodyDef();
        def.position.set(0,0);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(9,5);
        fix = body.createFixture(box,4);
        box.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(0,0);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }



    @Override
    public void act(float delta) {

    }

    public void detach(){
        body.destroyFixture(fix);
        world.destroyBody(body);
    }


}
