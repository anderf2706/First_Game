		package com.game1;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player implements Screen, InputProcessor{
	
	GameScreen gamescreen;
	Game1 game;
	
	Rectangle the_player;
	Rectangle the_mouse;
	Rectangle future_the_player;	
	Rectangle future_the_player2;
	
	int rightnum;
	int leftnum ;
	int upnum;
	int downnum;
	
	Vector2 nodeFin;
	
	Texture playersprite;
	Texture green;
	
	boolean executed = false;
	
	MapLayer obstacles;
	int objectLayerId = 2;
	
	Vector3 playerlocation;
	
	Vector2 mouseInWorld2D;
    Vector3 mouseInWorld3D;
    
    ArrayList<Node> path = new ArrayList<Node>();
	
	float speed = 200;
	Vector2 direction;
	Vector2 new_direction;
	//Vector2 new_direction2;
	Vector2 start;
	Vector2 end;
	Vector3 tp;	
	Vector3 tp2;
	float distance;
	
	boolean colliding = false;
	boolean playerChosen = false;
	boolean moving = false;
	boolean backdraw = false;
	Node chosenNode;
	Node playerNode;
	
	float touchX;
	float touchY;
	MapObjects objects;
	
	float oldX;
	float oldY;
	
	public Player(GameScreen gamescreen, Game1 game, int x, int y) {
		this.gamescreen = gamescreen;
		this.game = game;
		the_mouse = new Rectangle();
		the_mouse.height = 5;
		the_mouse.width = 5;
		the_player = new Rectangle();
		
		objects = gamescreen.map.getLayers().get(1).getObjects();
		future_the_player = new Rectangle();
		
		the_player.x = x;
		the_player.y = y;
		the_player.height = 5;
		the_player.width = 5;
		future_the_player2 = new Rectangle();
		
		playerlocation = new Vector3(0,0,0);
		Vector2 mouseInWorld2D = new Vector2();
	    Vector3 mouseInWorld3D = new Vector3();
	    this.mouseInWorld2D = mouseInWorld2D;
	    this.mouseInWorld3D = mouseInWorld3D;
	    
	    
		
		

		
		
	
		
		playersprite = new Texture(Gdx.files.internal("bucket.png"));
		green = new Texture(Gdx.files.internal("green.jpg"));
		
		for(Node node : gamescreen.allnodes) {
			if(Intersector.overlaps(node.body, the_player)) {
				this.playerNode = node;
				System.out.println(playerNode.x +  " " + playerNode.y + "");
				System.out.println("" + playerNode.down + playerNode.left + playerNode.right + playerNode.up);
				//System.out.println(playerNode.down.x + " " + playerNode.down.y);
				System.out.println(playerNode.index);
				System.out.println("cool");
				break;
			}
		}
		
	}
	
	public void make_trail() {
		path.add(playerNode);
			nodeFin = new Vector2(chosenNode.x, chosenNode.y);
			System.out.println("nice");
			for (int j = 0; j < 20; j++) { //velger den neste noden i pathen
				
				System.out.println("inne j");
				ArrayList<Integer> nextnodes = new ArrayList<Integer>();
				System.out.println(path.size());
				if(path.get(j).right == chosenNode) {
					path.add(path.get(j).right);
					break;
				}
				if(path.get(j).left == chosenNode) {
					path.add(path.get(j).left);
					break;
				}
				if(path.get(j).up == chosenNode) {
					path.add(path.get(j).up);
					break;
				}
				if(path.get(j).down == chosenNode) {
					path.add(path.get(j).down);
					break;
				}
				
				if(path.get(j).right != null) {
				rightnum = ((int) nodeFin.dst(path.get(j).right.x, path.get(j).right.y));
				}
				else {rightnum = 100000;}
				if(path.get(j).left != null) {
				leftnum = ((int) nodeFin.dst(path.get(j).left.x, path.get(j).left.y));
				}
				else {leftnum = 100000;}
				if(path.get(j).up != null) {
				upnum = ((int) nodeFin.dst(path.get(j).up.x, path.get(j).up.y));
				}
				else {upnum = 100000;}
				if(path.get(j).down != null) {
				downnum = ((int) nodeFin.dst(path.get(j).down.x, path.get(j).down.y));
				}
				else {downnum = 100000;}
				nextnodes.add(rightnum);
				nextnodes.add(leftnum);
				nextnodes.add(upnum);
				nextnodes.add(downnum);
				Collections.sort(nextnodes);
				System.out.println(nextnodes);

				if(nextnodes.get(0) == rightnum) {
					path.add(path.get(j).right);
					continue;
				}
				if(nextnodes.get(0) == leftnum) {
					path.add(path.get(j).left);
					continue;
				}
				if(nextnodes.get(0) == upnum) {
					path.add(path.get(j).up);
					continue;
				}
				if(nextnodes.get(0) == downnum) {
					path.add(path.get(j).down);
					continue;
				}
				
		

			}
			System.out.println("ferdig");
			System.out.println(path);
		
	}




	/*public boolean collision(Vector2 direction) {
		if(moving) {
			this.future_the_player.x = playerlocation.x + (direction.x * speed*Gdx.graphics.getDeltaTime()); 
			this.future_the_player.y = playerlocation.y + (direction.y * speed*Gdx.graphics.getDeltaTime());
			//System.out.println("moving");
			MapObjects objects = gamescreen.map.getLayers().get(1).getObjects();
			for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
				Rectangle rectangle = rectangleObject.getRectangle();
			    if (Intersector.overlaps(rectangle, future_the_player)) {
			    	//System.out.println("colliding");
			    	return true;
			    	
			    	//lag en ny vector her som implementeres i move.
			    }
			    return false;
			}
		}
		return false;
		
	}*/
	
	
				
				
				/*for(Node node : gamescreen.allnodes) {
					System.out.println("4");
					if(path.get(j).index == node.index + 1) {
					nextnodes.add(node);
					}
					if(path.get(j).index == node.index - 1) {
						nextnodes.add(node);
						}
					if(path.get(j).index == node.index + gamescreen.mapWidth/100) {
						nextnodes.add(node);
						}
					if(path.get(j).index == node.index - gamescreen.mapWidth/100) {
						nextnodes.add(node);
						}
					System.out.println("5");
				}*/
				

	




	/*public void playerMove() {
		
		if(direction != null) {
			//float actual_distance = start.dst(playerlocation.x, playerlocation.y);
			if(moving) {	
				if(!collision(direction)) {
					//if(!(actual_distance >= distance)) {
						this.playerlocation.x = playerlocation.x + (direction.x * speed*Gdx.graphics.getDeltaTime()); 
						this.playerlocation.y = playerlocation.y+ (direction.y * speed*Gdx.graphics.getDeltaTime());
						
						this.the_player.x = playerlocation.x - 50;
						this.the_player.y = playerlocation.y - 50;
					}
					else {	
							this.playerlocation.x = this.touchX;
							this.playerlocation.y = this.touchY;
							
							System.out.println(actual_distance);
							
							this.the_player.x = playerlocation.x - 50;
							this.the_player.y = playerlocation.y - 50;
							moving = false;
							return;
				
					}
				}
				
					
				else {
					Vector2 new_direction = new Vector2();
					Vector2 new_direction2 = new Vector2();
					Vector2 new_pos = new Vector2(this.playerlocation.x, this.playerlocation.y);
					//new_direction = (this.end.sub(new_pos).nor());
					//new_direction2 = (this.end.sub(new_pos).nor());
					boolean clear = false;
					Rectangle future_the_player1 = new Rectangle();
					future_the_player1.height = 1;
					future_the_player1.width = 1;
					
					while(!clear) {
						
						this.new_direction = new_direction.setToRandomDirection();
						future_the_player1.x = playerlocation.x + (this.new_direction.x * speed*Gdx.graphics.getDeltaTime()); 
						future_the_player1.y = playerlocation.y + (this.new_direction.y * speed*Gdx.graphics.getDeltaTime());
						System.out.println("iinn");
						
						if((!collision(this.new_direction) && ((this.end.dst(future_the_player1.x, future_the_player1.y)) <= 
								this.end.dst(new_pos)))) {
							this.the_player.x = future_the_player.x - 50;
							this.the_player.y = future_the_player.y - 50;
							this.direction = new_direction;
							System.out.println("klart");
							clear = true;
						}
					}
					
					/*float delta2 = Math.abs(new_direction2.angle()) - Math.abs(direction.angle());
					float delta1 = Math.abs(new_direction.angle()) - Math.abs(direction.angle());
					if(delta2 > delta1)	{
						/*this.playerlocation.x = playerlocation.x + (new_direction.x * speed*Gdx.graphics.getDeltaTime()); 
						this.playerlocation.y = playerlocation.y + (new_direction.y * speed*Gdx.graphics.getDeltaTime());
						System.out.println("2");
						this.the_player.x = playerlocation.x - 50;
						this.the_player.y = playerlocation.y - 50;
					}
					else {
						this.playerlocation.x = playerlocation.x + (new_direction2.x * speed*Gdx.graphics.getDeltaTime()); 
						this.playerlocation.y = playerlocation.y + (new_direction2.y * speed*Gdx.graphics.getDeltaTime());
						System.out.println("3");
						this.the_player.x = playerlocation.x - 50;
						this.the_player.y = playerlocation.y - 50;
					
				
				}	
			}
		}
	}*/	
	
	
	public void makevector() {
		//if() {
		gamescreen.map.getTileSets().getTile(1).getProperties();
		//}
	}
	
	public void rotate() {
		gamescreen.camera.rotate(4 * Gdx.graphics.getDeltaTime());
	}
	
	


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.P) {
			
			playerChosen = !playerChosen;
		}
		
		return false;
	}




	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}




	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void overlap(int button) {
		if((Intersector.overlaps(the_mouse, new Rectangle(this.the_player.x - 16, this.the_player.y - 16, 32, 32)))){
			playerChosen = true;
			System.out.println("player_chosen");
		
		}
		if(!(Intersector.overlaps(the_mouse, new Rectangle(this.the_player.x - 16, this.the_player.y - 16, 32, 32))) && (button == Input.Buttons.LEFT)) {
			playerChosen = false;
			System.out.println("unchosen");
		}
	}




	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			overlap(button);
			
			if(playerChosen) {
				if(button == Input.Buttons.RIGHT) {
					System.out.println("klar");
					if(!executed) {
						System.out.println("klar2");
						
					
						float touchX = mouseInWorld2D.x;
						float touchY = mouseInWorld2D.y;
						this.touchX = touchX;
						this.touchY = touchY;
						
						moving = true;
						Vector2 start = new Vector2(playerlocation.x , playerlocation.y);
						this.start = start;
						
						Vector2 end = new Vector2(mouseInWorld2D.x, mouseInWorld2D.y);
						Rectangle mouserec = new Rectangle(the_mouse.x, the_mouse.y, 1,1);
						this.end = end;
						for (Node node : gamescreen.allnodes) {
							
						    if (Intersector.overlaps(node.body, mouserec)) {
						    	this.chosenNode = node;
						    
						    }
						}
						
						
						float distance = start.dst(mouseInWorld2D.x,  mouseInWorld2D.y);
						Vector2 direction = (end.sub(start).nor());
						
						this.distance = distance;
						this.direction = direction;
						make_trail();
						System.out.println("inne");
						//make_trail();
						/*System.out.println(end);
						System.out.println(distance);
						System.out.println(direction.angle());
						System.out.println(start);*/
						executed = true;
					}
				}
			}
		return false;
		
	}




	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		executed = false;
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




	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void batch(SpriteBatch batch) {
		/*if(the_player != null) {
			batch.draw(playersprite, the_player.x - 16, the_player.y - 16, 36,36);
	        }*/
		if(playerChosen) {
			batch.draw(green, the_player.x + 25, the_player.y + 125, 50, 10);
		}
		
		if(chosenNode != null) {
			batch.draw(green, chosenNode.x, chosenNode.y, 5, 5);
		
		}
		if(playerNode != null) {
			batch.draw(green, playerNode.x, playerNode.y, 5, 5);
			
			
			batch.draw(green, playerNode.right.x, playerNode.right.y, 5, 5);
			batch.draw(green, playerNode.left.x, playerNode.left.y, 5, 5);
			batch.draw(green, playerNode.up.x, playerNode.up.y, 5, 5);
			batch.draw(green, playerNode.down.x, playerNode.down.y, 5, 5);
			
		}
		
		
		
	}




	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		  mouseInWorld3D.x = Gdx.input.getX();
		  mouseInWorld3D.y = Gdx.input.getY();
		  mouseInWorld3D.z = 0;
		  gamescreen.camera.unproject(mouseInWorld3D);
		  mouseInWorld2D.x = mouseInWorld3D.x;
		  mouseInWorld2D.y = mouseInWorld3D.y;
		  
		  the_mouse.x = mouseInWorld2D.x;
		  the_mouse.y = mouseInWorld2D.y;
		  //if()
		  //check_player();
				  
		  
		  //System.out.println( mouseInWorld2D.x + " " + mouseInWorld2D.y);
	}
	
	public void check_player() {
		 if(!Intersector.overlaps(the_player, playerNode.body)) {
			  for (Node node : gamescreen.allnodes) {
					
		  			if (Intersector.overlaps(node.body, the_player)) {
		  				this.playerNode = node;
		  			}
		  		}
		  }
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

	
}

