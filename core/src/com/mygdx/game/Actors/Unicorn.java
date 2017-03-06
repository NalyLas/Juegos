package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
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
 * Created by Natalia on 21/02/2017.
 */

public class Unicorn extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fix;
    private boolean alive=true, jumping = false, mustJump = false;

    public Unicorn(World world, Texture texture, Vector2 position){
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,0.5f);
        fix = body.createFixture(box,3);
        fix.setUserData("unicorn");
        box.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,(body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }


    public boolean isAlive() {
        return alive;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;

    }

    public boolean isMustJump() {
        return mustJump;
    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }

   @Override
    public void act(float delta) {
        if(mustJump){
            mustJump = false;
            jump();
        }

        if (alive){
            float vely = body.getLinearVelocity().y;
            body.setLinearVelocity(Constants.SPEED_Y,vely);

        }

       if(jumping){
           body.applyForceToCenter(0,-Constants.IMPULSE_JUMP * 1.2f, true);
       }

    }

    public void jump(){
        if(!jumping && alive){
            jumping=true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constants.IMPULSE_JUMP,position.x,position.y,true);
        }

    }

    public void detach(){
        body.destroyFixture(fix);
        world.destroyBody(body);
    }


}
