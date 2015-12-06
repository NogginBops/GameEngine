package game.sound;

import java.awt.geom.Point2D;

public final class AudioUtil {
	
	//JAVADOC: AudioUtil
	
	public static double distance(Point2D source, Point2D listener){
		return Math.sqrt(distanceSqrd(source, listener));
	}
	
	public static double distanceSqrd(Point2D source, Point2D listener){
		return (source.getX() - listener.getX()) * (source.getX() - listener.getX()) + (source.getY() - listener.getY()) * (source.getY() - listener.getY());
	}
	
	public static Point2D getVector(Point2D source, Point2D listener){
		return new Point2D.Double(source.getX() - listener.getX(), source.getY() - listener.getY());
	}
}
