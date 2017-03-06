package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Natalia on 21/02/2017.
 */

public class Box2dScreen extends ScreenBase {

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    private Body unicornBody, sueloBody, pinchoBody;
    private Fixture unicornFix, sueloFix, pinchoFix;

    private boolean salta, saltando, alive = true;

    public Box2dScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0,-10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16,9);
        camera.translate(0,1);

     /*   world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor") ||
                        fixtureB.getUserData().equals("player") && fixtureA.getUserData().equals("floor") ){
                    if(Gdx.input.isTouched()){
                        salta = true;
                    }
                    saltando = false;
                }

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("bad") ||
                        fixtureB.getUserData().equals("player") && fixtureA.getUserData().equals("bad") ){
                    alive=false;
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor") ||
                        fixtureB.getUserData().equals("player") && fixtureA.getUserData().equals("floor") ){
                    if(Gdx.input.isTouched()){
                        salta = true;
                    }
                    saltando = false;
                }

                if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("bad") ||
                        fixtureB.getUserData().equals("player") && fixtureA.getUserData().equals("bad") ){
                    alive=false;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });*/

     //   unicornBody = world.createBody(createUnicornDef());
        sueloBody = world.createBody(createBodyDef());
        pinchoBody = world.createBody(createPinchoDef(3));


        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFix = sueloBody.createFixture(sueloShape,1);
        sueloShape.dispose();

        pinchoFix = createPinchoFix(pinchoBody);

        unicornFix.setUserData("player");
        pinchoFix.setUserData("bad");
        sueloFix.setUserData("floor");

    }

    private BodyDef createPinchoDef(float x) {
        BodyDef def = new BodyDef();
        def.position.set(x,0.5f);
        return def;
    }

    private BodyDef createBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }

   /* private BodyDef createUnicornDef() {

        return def;
    }*/

    private Fixture createPinchoFix(Body pinchobody){
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0.5f,-0.5f);
        vertices[2] = new Vector2(0,0.5f);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        Fixture fix = pinchobody.createFixture(shape,1);
        shape.dispose();
        return fix;

    }

    @Override
    public void dispose() {
        unicornBody.destroyFixture(sueloFix);
        world.destroyBody(sueloBody);
        unicornBody.destroyFixture(unicornFix);
        world.destroyBody(unicornBody);
        pinchoBody.destroyFixture(pinchoFix);
        world.destroyBody(pinchoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(salta){
            salta = false;
            jump();
        }

        if(Gdx.input.justTouched() && !saltando){
            salta=true;
        }

        if(alive){
            float vely = unicornBody.getLinearVelocity().y;
            unicornBody.setLinearVelocity(2,vely);
        }



        world.step(delta,6,2);

        camera.update();
        renderer.render(world,camera.combined);
    }

    private void jump(){
        Vector2 position = unicornBody.getPosition();
        unicornBody.applyLinearImpulse(0,10,position.x,position.y,true);
    }
}
