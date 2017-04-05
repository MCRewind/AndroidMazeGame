package com.gnarly.engine.components;

import com.gnarly.engine.utils.Library;

public class Animation {

	int curFrame = 0;
	int maxFrame = 0;
	int wait = 0;
	int curWait = 0;
	int dir = 1;
	boolean reverse = false;
	boolean pause = false;
	Texture[] frames;
	
	public Animation(Library library, String name, String type, int numFrames, int updatesPerFrame, boolean reverse) {
		wait = updatesPerFrame;
		this.reverse = reverse;
		maxFrame = numFrames - 1;
		frames = new Texture[numFrames];
		for (int i = 0; i < frames.length; i++)
			frames[i] = library.getTexture(name + " " + (i+1) + "." + type);
	}
	
	public Animation(Texture texture) {
		frames = new Texture[1];
		frames[0] = texture;
	}
	
	public void update() {
		if(maxFrame > 0) {
			if(curWait == wait) {
				curWait = 0;
				if(curFrame == maxFrame && !reverse)
					curFrame = -1;
				else if(curFrame == maxFrame && reverse && dir == 1)
					dir = -1;
				else if(curFrame == 0 && reverse && dir == -1)
					dir = 1;
				curFrame += dir;
			}
			else
				++curWait;
		}
	}
	
	public void pause() {
		pause = true;
	}
	
	public void unpause() {
		pause = false;
	}
	
	public void bind() {
		frames[curFrame].bind();
	}
	
	public void unbind() {
		frames[curFrame].unbind();
	}
}
