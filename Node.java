package com.game1;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Node {
	
	int x;
	int y;
	int index;
	static int nextindex = 1;
	Vector3 pos = new Vector3();
	Rectangle body;
	Node right;
	Node left;
	Node up;
	Node down;
	GameScreen gamescreen;

	public Node(int x, int y, GameScreen gamescreen) {
		pos.x = x;
		pos.y = y;
		pos.z = 0;
		//gamescreen.camera.unproject(pos);
		this.x = x;
		this.y = y;
		this.gamescreen = gamescreen;
		
		
		this.index = nextindex;
		increment();
		body = new Rectangle(x - 16, y - 16 ,32,32);
		for(Node node : gamescreen.allnodes) {
			if(node.x == this.x - 32 && node.y == this.y) {
				this.left = node;
			}
			if(node.x - 32 == this.x  && node.y == this.y) {
				this.right = node;
			}
			if(node.x == this.x && node.y == this.y - 32) {
				this.down = node;
			}
			if(node.x == this.x && node.y - 32 == this.y) {
				this.up = node;
			}	
			
			
		}
		/*for(Node node : gamescreen.allnodes) {
			if(Intersector.overlaps(node.body, new Rectangle(this.x - 32, this.y, 100,100))) {
				this.right = node;
			}*/
		
		
		
		
		
	}
	
	public void increment() {
		nextindex ++;
	}
	
	public void makeNabour() {
		for(Node node : gamescreen.allnodes) {
			if(node.x == this.x - 32 && node.y == this.y) {
				this.left = node;
			}
			if(node.x - 32 == this.x  && node.y == this.y) {
				this.right = node;
			}
			if(node.x == this.x && node.y == this.y - 32) {
				this.down = node;
			}
			if(node.x == this.x && node.y - 32 == this.y) {
				this.up = node;
			}	
			
			
		}
	}
	
	
}
