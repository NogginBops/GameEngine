package game.util;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 * @param <T> 
 */
public class IDHandler<T> {
	
	// JAVADOC: IDHandler

	private CopyOnWriteArrayList<ID<T>> IDs = new CopyOnWriteArrayList<ID<T>>();
	
	private int lastID = 0;
	
	/**
	 * 
	 */
	public IDHandler(){
		IDs = new CopyOnWriteArrayList<ID<T>>();
	}
	
	/**
	 * 
	 * @param object
	 */
	public void addObject(T object){
		addObject(object, "Null");
		//IDs.add(findFirstAvailableID(new ID<T>("object", 0, object)));
	}
	
	/**
	 * 
	 * @param object
	 * @param nameID
	 */
	public void addObject(T object, String nameID){
		addObject(new ID<T>(nameID, 0, object));
		//IDs.add(findFirstAvailableID(new ID<T>(nameID, 0, object)));
	}
	
	/**
	 * 
	 * @param id
	 */
	public void addObject(ID<T> id){
		IDs.add(findFirstAvailableID(id));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private ID<T> findFirstAvailableID(ID<T> id) {
		while(!isIDAvailable(id)){
			id = new ID<T>(id.name, lastID + 1, id.object);
		}
		return id;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public ID<T> getID(T object){
		for(ID<T> id : IDs){
			if(id.object == object){
				return id;
			}
		}
		return null;
	}
	
	/**
	 * @param classT 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ID[] getAllIDs(){
		return IDs.toArray(new ID[]{});
	}
	
	/**
	 * 
	 * @param nameID
	 * @return
	 */
	public T getObject(String nameID){
		for(ID<T> id : IDs){
			if(id.name == nameID){
				return id.object;
			}
		}
		return null;
	}
	
	/**
	 * @param classT
	 * @return
	 */
	public ArrayList<T> getAllObjects(){
		ArrayList<T> list = new ArrayList<>();
		for(ID<T> id : IDs){
			list.add(id.object);
		}
		return list;
	}
	
	/**
	 * 
	 * @param testID
	 * @return
	 */
	public boolean isIDAvailable(ID<T> testID) {
		for (ID<T> id : IDs) {
			if (id.id == testID.id) {
				return false;
			}
		}
		lastID = testID.id;
		return true;
	}
}
