package com.game1;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor{

	Game1 game;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	SpriteBatch batch;
	TiledMap map = new TmxMapLoader().load("ew3.tmx");
	Tiled tiled = new Tiled();
	TiledMapTileLayer tilelayer = (TiledMapTileLayer) map.getLayers().get(0);
	int tileSize = (int) tilelayer.getTileWidth();
	int mapWidth = tilelayer.getWidth() * tileSize;  
	int mapHeight = tilelayer.getHeight() * tileSize;
	
	ArrayList<Node> allnodes = new ArrayList<Node>();
	
	ShapeRenderer sr;
	
	float up;
	float down;
	float right;
	float left;
	float viewX;
	float viewY;
	Player player2;
	
	//Player player = new Player(this, game);

	
	public GameScreen(Game1 game) {
		this.game = game;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		MapObjects objects = map.getLayers().get(1).getObjects();
		sr = new ShapeRenderer();
		for (int i = 16; i < mapWidth; i += 32) {
			for (int j = 16; j < mapHeight; j += 32) {
				for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
					Rectangle rectangle = rectangleObject.getRectangle();
				    if (!Intersector.overlaps(rectangle, new Rectangle(i,j,1,1))) {
				    	allnodes.add(new Node(i,j, this));
				    }
				}
			}
		}
		for(Node node : allnodes) {
			node.makeNabour();
		}
		
		
		
	
		
		player2 = new Player(this, game, 752, 560);
		
		
		
		
		
		renderer = new OrthogonalTiledMapRenderer(map);
		Gdx.input.setInputProcessor(this);
		
		
	}
	
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
       
        //player.collision(player.direction);
        //player.playerMove();
        //player.render(delta);
        
        //player2.collision(player2.direction);
        //player2.playerMove();
        player2.render(delta);
               
        
        viewX = right + left;
        viewY = up + down;
        game.batch.setProjectionMatrix(camera.combined);
        camera.translate(viewX, viewY);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        
        
        game.batch.begin();
        
        //player.batch(game.batch);
        player2.batch(game.batch);
        for (int i = 0; i < allnodes.size(); i++) {
        	game.batch.draw(player2.playersprite, allnodes.get(i).x, allnodes.get(i).y, 2, 2);
		}
		game.batch.end();
        
        
        
        
        
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.D) {
			right = 600 * Gdx.graphics.getDeltaTime();
		}
		if(keycode == Input.Keys.A) {
			left = -600 * Gdx.graphics.getDeltaTime();
		}
		if(keycode == Input.Keys.W) {
			up = 600 * Gdx.graphics.getDeltaTime();
		}
		if(keycode == Input.Keys.S) {
			down = -600 * Gdx.graphics.getDeltaTime();
		}
		//player.keyDown(keycode);
		player2.keyDown(keycode);
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.D) {
			right = 0;
		}
		if(keycode == Input.Keys.A) {
			left = 0;
		}
			
		if(keycode == Input.Keys.W) {
			up = 0;
			
		}
		if(keycode == Input.Keys.S) {
			down = 0;
			
		}
		//player.keyUp(keycode);
		player2.keyUp(keycode);
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		
		//player.touchDown(screenX, screenY, pointer, button);
		player2.touchDown(screenX, screenY, pointer, button);
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		player2.touchUp(screenX, screenY, pointer, button);
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
