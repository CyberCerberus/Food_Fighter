package engine.content;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BackGround {
    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private int width;
    private int height;

    private final int panelWidth, panelHeight;


    public BackGround(BufferedImage image, int panelWidth, int panelHeight) {

	this.image = image;
	width = image.getWidth();
	height = image.getHeight();
	this.panelWidth = panelWidth;
	this.panelHeight = panelHeight;

    }



    public void setPosition(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public void setVector(double dx, double dy) {
	this.dx = dx;
	this.dy = dy;
    }

    public void setDimensions(int i1, int i2) {
	width = i1;
	height = i2;
    }

    public double getx() { return x; }
    public double gety() { return y; }

    public void update() {
	x += dx;
	while(x <= -width) x += width;
	while(x >= width) x -= width;
	y += dy;
	while(y <= -height) y += height;
	while(y >= height) y -= height;
    }

    public void draw(Graphics2D g) {

	g.drawImage(image, (int)x, (int)y, null);

	if(x < 0) {
	    g.drawImage(image, (int)x + panelWidth, (int)y, null);
	}
	if(x > 0) {
	    g.drawImage(image, (int)x - panelWidth, (int)y, null);
	}
	if(y < 0) {
	    g.drawImage(image, (int)x, (int)y + panelHeight, null);
	}
	if(y > 0) {
	    g.drawImage(image, (int)x, (int)y - panelHeight, null);
	}
    }
}
