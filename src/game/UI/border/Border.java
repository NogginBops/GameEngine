package game.UI.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Border {

	protected int top = 5, bottom = 5, left = 5, right = 5;

	protected Color color = Color.BLACK;

	public Border() {
		top = bottom = left = right = 5;
	}

	public Border(int width) {
		top = bottom = left = right = width;
	}

	public Border(int width, Color color) {
		top = bottom = left = right = width;
		this.color = color;
	}

	public abstract void paint(Graphics2D g2d, Rectangle area);

	public Rectangle getInnerArea(Rectangle rect) {
		return new Rectangle(rect.x + left, rect.y + top, rect.width - right - left, rect.height - bottom - top);
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setWidth(int width) {
		top = bottom = left = right = width;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
