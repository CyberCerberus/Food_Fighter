package main;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    private Thread thread;
    private boolean running;
    private int fps = 60;
    private long targetTime = 1000/fps;
    private BufferedImage image;
    private Graphics2D g;
    private StateManager sm;
    
    public GamePanel(){
	super();
	setPreferredSize(new Dimension(WIDTH *  SCALE, HEIGHT * SCALE));
	setFocusable(true);
	requestFocus();
	sm = new StateManager(WIDTH, HEIGHT);
    }
    
    @Override
    public void addNotify(){
	super.addNotify();
	if(thread == null){
	    thread = new Thread(this);
	    addKeyListener(this);
	    thread.start();
	}
    }

    @Override
    public void run() {
	init();
	
	long start;
	long elapsed;
	long wait;
	
	// game loop
	while(running) {
		
		start = System.nanoTime();		
		update();
		draw();
		drawToScreen();		
		elapsed = System.nanoTime() - start;		
		wait = targetTime - elapsed / 1000000;
		if(wait < 0) wait = 5;		
		try {
			Thread.sleep(wait);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
    
    private void drawToScreen() {
	Graphics g2 = getGraphics();
	g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	g2.dispose();
	
    }

    private void draw() {
	sm.draw(g);
	
    }

    private void update() {
	sm.update();
	Keys.update();
	
    }

    private void init(){
	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	g = (Graphics2D) image.getGraphics();
	running = true;

    }
    @Override
    public void keyPressed(KeyEvent e) {
	Keys.keySet(e.getKeyCode(), true);
	
    }

    @Override
    public void keyReleased(KeyEvent e) {
	Keys.keySet(e.getKeyCode(), false);
	
    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
    }

}
