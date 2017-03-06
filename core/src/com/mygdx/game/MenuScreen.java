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
 * Created by Natalia on 24/02/2017.
 */

public class MenuScreen extends ScreenBase {
    private Stage stage;
    private Skin skin;
    private TextButton bt1;
    private Image imv;


    public MenuScreen(final MainGame game){
        super(game);
        stage = new Stage(new FitViewport(640,400));
        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));

        imv = new Image(game.getManager().get("open.png", Texture.class));
        bt1 = new TextButton("Jugar", skin);


        bt1.setSize(200,50);
        bt1.setPosition(320 - bt1.getWidth()/2,320 - bt1.getHeight());
        bt1.setColor(0.9f, 0.2f, 0.8f, 1f);

        imv.setSize(600,254);
        imv.setPosition(320 - imv.getWidth()/2,0);



        bt1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        stage.addActor(imv);
        stage.addActor(bt1);

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
