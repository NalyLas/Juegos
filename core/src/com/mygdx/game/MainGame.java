package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

	private AssetManager manager;
	public GameScreen gameScreen;
	public  GameOver gameOver;
	public  LoadScreen loadScreen;
	public MenuScreen menuScreen;


	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void create () {
		manager = new AssetManager();
		manager.load("back.png",Texture.class);
		manager.load("open.png",Texture.class);
		manager.load("floor.png",Texture.class);
		manager.load("unicorn.png",Texture.class);
		manager.load("badcrab.png",Texture.class);
		manager.load("fairy.png",Texture.class);
		manager.load("carnivorous.png",Texture.class);
		manager.load("gameover.png",Texture.class);
		manager.load("Audio/lifesound.ogg",Sound.class);
		manager.load("Audio/overSound.ogg",Sound.class);
		manager.load("Audio/bso.mp3", Music.class);


		loadScreen = new LoadScreen(this);
		setScreen(loadScreen);



	}

	public void finishLoad(){
		gameScreen = new GameScreen(this);
		gameOver = new GameOver(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}


}
