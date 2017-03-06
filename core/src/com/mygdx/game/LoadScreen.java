package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Natalia on 24/02/2017.
 */

public class LoadScreen extends ScreenBase {
    private Stage stage;
    private Skin skin;
    private Label load;

    public LoadScreen(final MainGame game){
        super(game);
        stage = new Stage(new FitViewport(640,380));
        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
        load = new Label("Loading...",skin);
        load.setPosition(320 - load.getWidth()/2,320 - load.getHeight());

        stage.addActor(load);

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getManager().update()){
            game.finishLoad();
        }else {
            int progres = (int)(game.getManager().getProgress()*100);
            load.setText("Loading..." + progres + "%");
        }

        stage.act();
        stage.draw();
    }

}
