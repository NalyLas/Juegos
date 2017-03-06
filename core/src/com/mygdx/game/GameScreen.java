package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.Background;
import com.mygdx.game.Actors.Carnivorous;
import com.mygdx.game.Actors.Crab;
import com.mygdx.game.Actors.Fairy;
import com.mygdx.game.Actors.Floor;
import com.mygdx.game.Actors.Unicorn;

import java.util.ArrayList;

/**
 * Created by Natalia on 21/02/2017.
 */

public class GameScreen extends ScreenBase{

    private Stage stage;
    private World world;
    private Unicorn unicorn;

    private ArrayList<Floor> listFloor = new ArrayList<Floor>();
    private ArrayList<Carnivorous> listCarn = new ArrayList<Carnivorous>();
    private ArrayList<Crab> listCrabs = new ArrayList<Crab>();
    private ArrayList<Fairy> listFairy = new ArrayList<Fairy>();

    int c1=0,c2=20;

    private Sound life,dead;
    private Music bso;

    public GameScreen(MainGame game) {
        super(game);
        life = game.getManager().get("Audio/lifesound.ogg");
        dead = game.getManager().get("Audio/overSound.ogg");

        bso = game.getManager().get("Audio/bso.mp3");

        stage = new Stage(new FitViewport(640,380));
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new GameContactListener());



    }


    @Override
    public void show() {

        Texture unicorntext = game.getManager().get("unicorn.png");
        Texture fairytext = game.getManager().get("fairy.png");
        Texture crabtext = game.getManager().get("badcrab.png");
        Texture floortext = game.getManager().get("floor.png");
        Texture carntext = game.getManager().get("carnivorous.png");

        unicorn = new Unicorn(world,unicorntext,new Vector2(2,1));
        listFloor.add(new Floor(world,floortext,0,1000,0.9f));
        for(int i=0;i<150;i++){
            c1+=12;
            listCarn.add(new Carnivorous(world, carntext, c1, 0.9f));
        }

        listFairy.add(new Fairy(fairytext,10,2.1f,true));
        for(int i=0;i<150;i++){
            c2+=30;
            listFairy.add(new Fairy(fairytext,c2,2.1f,true));
        }

        listCrabs.add(new Crab(world, crabtext, new Vector2(5,10)));
        listCrabs.add(new Crab(world, crabtext, new Vector2(40,10)));
        listCrabs.add(new Crab(world, crabtext, new Vector2(80,10)));
        listCrabs.add(new Crab(world, crabtext, new Vector2(120,10)));
        listCrabs.add(new Crab(world, crabtext, new Vector2(130,10)));



        for(Floor floor: listFloor){
            stage.addActor(floor);
        }
        for (Carnivorous plants : listCarn) {
            stage.addActor(plants);
        }
        for (Crab crabs : listCrabs) {
            stage.addActor(crabs);
        }
        for (Fairy fairy : listFairy) {
            stage.addActor(fairy);
        }


        stage.addActor(unicorn);


        bso.setVolume(0.2f);
        bso.play();

    }

    @Override
    public void hide() {
        unicorn.detach();
        unicorn.remove();

        for(Floor floor: listFloor){
            floor.detach();
            floor.remove();
        }
        listFloor.clear();
        for(Carnivorous plants : listCarn){
            plants.detach();
            plants.remove();
        }

        listCarn.clear();
        for(Crab crabs : listCrabs){
            crabs.detach();
            crabs.remove();
        }
        listCrabs.clear();
        for (Fairy fairy : listFairy) {
            fairy.remove();
        }
        listFairy.clear();
        dead.stop();

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f,0.5f,0.9f,0.9f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(unicorn.getX() > 200 && unicorn.isAlive()){
            stage.getCamera().translate( (Constants.SPEED_Y-0.05f) * delta * Constants.PIXELS_IN_METER,0,0);
        }

        if(Gdx.input.justTouched()){
            life.play();
            unicorn.jump();
        }

        stage.act();

        world.step(delta,6,2);

        stage.draw();
        checkCollisions();

    }

    private void checkCollisions() {
        for (Fairy fairy : listFairy) {
            if (fairy.isFairyExist()) {
                if (unicorn.getX() + unicorn.getWidth() > fairy.getX() && unicorn.getX() < fairy.getX() + fairy.getWidth()&& unicorn.getY() + unicorn.getHeight() > fairy.getY() && unicorn.getY() < fairy.getY() + fairy.getHeight()) {
                    fairy.setCoinExist(false);
                    fairy.remove();
                    Constants.SPEED_Y++;
                    Constants.IMPULSE_JUMP++;
                }

            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }


    private class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object userA, Object userB) {

            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null) {
                return false;
            }
            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {

            if (areCollided(contact, "unicorn", "floor")) {
                unicorn.setJumping(false);
                if(Gdx.input.isTouched()){
                    life.play();
                    unicorn.setMustJump(true);
                }

            }

            if (areCollided(contact, "unicorn", "carnivorous") || areCollided(contact, "unicorn","crab")) {
                if (unicorn.isAlive()) {
                    bso.stop();
                    unicorn.setAlive(false);
                    dead.play();


                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {

                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOver);
                                        }
                                    })
                            )
                    );
                }
            }
        }

        @Override
        public void endContact(final Contact contact) {
        }

        @Override public void preSolve(Contact contact, Manifold oldManifold) { }
        @Override public void postSolve(Contact contact, ContactImpulse impulse) { }
    }


}
