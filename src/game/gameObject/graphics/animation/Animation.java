package game.gameObject.graphics.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.util.Copyable;

/**
 * @author Julius Häger
 *
 */
public class Animation implements Copyable<Animation> {
	
	//NOTE: Should a animation have a name to be invoked easily or is that handled by the animation manager?
	
	//NOTE: Thread safe?
	private List<Frame> frames;
	
	private boolean loop = true;
	
	private boolean running = false;
	
	private boolean paused = false;
	
	private float time = 0;
	
	private float totalRunningTime = 0;
	
	/**
	 * @param frames
	 */
	public Animation(Frame[] frames) {
		if(frames == null || frames.length <= 0){
			throw new IllegalArgumentException("A animation cannot contain zero frames!");
		}
		
		this.frames = Arrays.asList(frames);
		
		for (int i = 0; i < frames.length; i++) {
			totalRunningTime += frames[i].time;
		}
	}
	
	/**
	 * @param delay
	 * @param bufferedImages
	 */
	public Animation(float delay, BufferedImage...bufferedImages) {
		if(bufferedImages == null || delay == 0 || bufferedImages.length <= 0){
			throw new IllegalArgumentException("A animation cannot contain zero frames!");
		}
		
		frames = new ArrayList<>();
		
		for (int i = 0; i < bufferedImages.length; i++) {
			frames.add(new Frame(bufferedImages[i], delay));
			totalRunningTime += delay;
		}
	}
	
	private Animation(List<Frame> frames, boolean loop, boolean running, boolean paused, float time, float totalRunningTime){
		this.frames = frames;
		this.loop = loop;
		this.running = running;
		this.paused = paused;
		this.time = time;
		this.totalRunningTime = totalRunningTime;
	}
	
	/**
	 * @param deltaTime
	 */
	public void update(float deltaTime){
		if(running){
			if(paused){
				return;
			}
			
			time += deltaTime;
			
			//Stop running if the animation has ended.
			if(time > totalRunningTime){
				running = false;
				
				//If the animation should loop start the animation and reset the time.
				if(loop == true){
					running = true;
					time = 0;
				}else{
					
				}
			}	
		}
	}
	
	/**
	 * @return
	 */
	public BufferedImage getCurrentImage(){
		//NOTE: Is there a smarter/better way to do this? (There probably is)
		//Should the image be stored in a image and set in the update() method?
		
		//NOTE: Should a animation return a image even though 
		float counter = 0;
		for (int i = 0; i < frames.size(); i++) {
			if((counter += frames.get(i).time) >= time){
				return frames.get(i).image;
			}
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public int getCurrentFrameNumber(){
		float counter = 0;
		for (int i = 0; i < frames.size(); i++) {
			if((counter += frames.get(i).time) >= time){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 */
	public void start(){
		running = true;
	}
	
	/**
	 * 
	 */
	public void pause(){
		paused = true;
	}
	
	/**
	 * 
	 */
	public void resume(){
		paused = false;
	}
	
	/**
	 * 
	 */
	public void stop(){
		running = false;
	}
	
	/**
	 * @return
	 */
	public BufferedImage[] getImages(){
		BufferedImage[] images = new BufferedImage[frames.size()];
		
		for (int i = 0; i < images.length; i++) {
			images[i] = frames.get(i).image;
		}
		
		return images;
	}
	
	/**
	 * @param frame
	 */
	public void addFrame(Frame frame){
		frames.add(frame);
		totalRunningTime += frame.time;
	}
	
	/**
	 * @param frame
	 */
	public void removeFrame(Frame frame){
		if(frames.remove(frame)){
			totalRunningTime -= frame.time;
		}
	}
	
	/**
	 * @return
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @param paused
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public Animation copy() {
		return new Animation(new ArrayList<>(frames), loop, running, paused, time, totalRunningTime);
	}
}
