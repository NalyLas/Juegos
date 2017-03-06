package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Natalia on 23/02/2017.
 */

public class GameOver extends ScreenBase{
    private Stage stage;
    private Skin skin;
    private Image gameover;
    private TextButton retry;

    public GameOver(final MainGame game){
        super(game);
        stage = new Stage(new FitViewport(640,380));
        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
        gameover = new Image(game.getManager().get("gameover.png", Texture.class));
        retry = new TextButton("Retry", skin);

        gameover.setPosition(320 - gameover.getWidth()/2,320 - gameover.getHeight());
        retry.setSize(200,80);
        retry.setPosition(220,50);
        retry.setColor(0.9f, 0.2f, 0.8f, 1f);


        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Constants.IMPULSE_JUMP = 40;
                Constants.SPEED_Y = 4;
                game.setScreen(game.loadScreen);
            }
        });

        stage.addActor(gameover);
        stage.addActor(retry);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f,0.5f,0.9f,0.9f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
