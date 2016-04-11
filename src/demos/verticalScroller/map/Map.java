package demos.verticalScroller.map;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;

import org.xml.sax.XMLReader;

/**
 * @author Julius Häger
 *
 */
public class Map {
	
	//JAVADOC: Map
	private String title;
	
	private int width, height;
	
	private int speed;
	
	private CopyOnWriteArrayList<MapTile> tiles;
	
	/**
	 * @param width
	 * @param height
	 * @param title
	 * @param speed
	 * @param mapTiles
	 */
	public Map(String title, int width, int height, int speed, MapTile ... mapTiles) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.speed = speed;
		
		tiles = new CopyOnWriteArrayList<MapTile>(mapTiles);
	}
	
	/**
	 * @return
	 */
	public CopyOnWriteArrayList<MapTile> getAllTiles(){
		return tiles;
	}
	
	/**
	 * @return
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * @return
	 */
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * @param MapXml
	 * @return
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 */
	public static Map parseMap(File mapXml) throws FileNotFoundException, XMLStreamException{
		String title;
		int width, height;
		int speed;
		CopyOnWriteArrayList<MapTile> tiles;
		
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		XMLEventReader reader = xmlif.createXMLEventReader(mapXml.getName(), new FileInputStream(mapXml));
		
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			System.out.println(event.toString());
			
		}
		
		return null;
	}
	
	
}
