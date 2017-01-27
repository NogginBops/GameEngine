package game.settings;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.GameSettings;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;

/**
 * @author Julius Häger
 *
 */
public class SettingsUtil {
	
	//JAVADOC: SettingsUtil
	
	private static Pattern primitiveTypePattern = Pattern.compile("([a-zA-Z0-9]*):\\s*\\(([a-zA-Z0-9.$]*)\\)\\s*\\\"([a-zA-Z0-9.#_\\s]*)\\\"", Pattern.DOTALL);
	
	private static Pattern referenceTypePattern = Pattern.compile("([a-zA-Z0-9]*):\\s*\\(([a-zA-Z0-9.$]*)\\)\\s*\\{", Pattern.DOTALL);
	
	private static Pattern primitiveCompositeElementPattern = Pattern.compile("\\(([a-zA-Z0-9.$]*)\\)\\s*\\\"(.*?)\\\"", Pattern.DOTALL);
	
	private static Pattern compositeCompositeElementPattern = Pattern.compile("\\(([a-zA-Z0-9.$]*)\\)\\s*\\{", Pattern.DOTALL);
	
	//TODO: Make it so that you can pass arguments to the method call?
	
	/**
	 * @param fileName
	 * @return
	 */
	public static GameSettings load(String fileName){
		String text = "";
		
		try {
			text = IOHandler.load(new LoadRequest<String>("Settings", new File(fileName), String.class)).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameSettings settings = new GameSettings();
		
		Matcher primitiveMatcher = primitiveTypePattern.matcher(text);
		
		while(primitiveMatcher.find()){
			settings.putSetting(primitiveMatcher.group(1), parsePrimitive(primitiveMatcher.group(2), primitiveMatcher.group(3)));
			
			//System.out.println("Added primitive: " + primitiveMatcher.group(1) + " of type: " + primitiveMatcher.group(2));
		}
		
		Matcher compositeMatcher = referenceTypePattern.matcher(text);
		
		while (compositeMatcher.find()) {
			//System.out.println(compositeMatcher.group(1) + " - " + compositeMatcher.group(2) + " - " + compositeMatcher.group(3));
			
			//Find the content
			String content = substringToNextMachingBracket(text, compositeMatcher.end());
			
			settings.putSetting(compositeMatcher.group(1), parseComposite(compositeMatcher.group(2), content));
		}
		
		return settings;
	}
	
	//TODO: Fix object inheritance!
	//You can't pass a BoxTransform to a new Transform(Transform) because that will not be recognized as the same type
	
	private static Object parseComposite(String type, String valueText){
		
		//System.out.println("Parsing " + type + ", with input: " + valueText);
		
		ArrayList<Object> arguments = new ArrayList<>();
		
		Matcher primitiveArgumentMatcher = primitiveCompositeElementPattern.matcher(valueText);
		
		Matcher compositeArgumentMatcher = compositeCompositeElementPattern.matcher(valueText);
		
		int lastPrimitive = Integer.MAX_VALUE;
		
		int lastComposite = Integer.MAX_VALUE;
		
		boolean hasPrimitive = primitiveArgumentMatcher.find();
		
		boolean hasComposite = compositeArgumentMatcher.find();
		
		while (hasPrimitive || hasComposite) {
			try{
				lastPrimitive = primitiveArgumentMatcher.start();
			}catch (IllegalStateException e) {
				lastPrimitive = Integer.MAX_VALUE;
			}
			
			try{
				lastComposite = compositeArgumentMatcher.start();
			}catch (IllegalStateException e) {
				lastComposite = Integer.MAX_VALUE;
			}
			
			if(lastPrimitive == Integer.MAX_VALUE && lastComposite == Integer.MAX_VALUE){
				break;
			}
			
			if(lastPrimitive != Integer.MAX_VALUE && lastPrimitive < lastComposite){
				
				//System.out.println("Parsing primitive " + primitiveArgumentMatcher.group(1) + " with input: " + primitiveArgumentMatcher.group(2) + ", lastPrimitive: " + lastPrimitive + ", lastComposite: " + lastComposite);
				
				//Parse the primitive
				arguments.add(parsePrimitive(primitiveArgumentMatcher.group(1), primitiveArgumentMatcher.group(2)));
				
				hasPrimitive = primitiveArgumentMatcher.find();
				
			}else if(lastComposite != Integer.MAX_VALUE){
				
				
				//Find content
				String content = substringToNextMachingBracket(valueText, compositeArgumentMatcher.end());
				
				//System.out.println("Parsing composite " + compositeArgumentMatcher.group(1) + " with input: " + content + ", lastPrimitive: " + lastPrimitive + ", lastComposite: " + lastComposite);
				
				//Parse the composite
				arguments.add(parseComposite(compositeArgumentMatcher.group(1), content));
				
				//Match primitives until we are past the composite
				while (lastPrimitive < (compositeArgumentMatcher.end() + content.length()) && primitiveArgumentMatcher.find()) {
					lastPrimitive = primitiveArgumentMatcher.start();
				}
				
				//Do we have any more primitives to parse?
				//TODO: Make sure the last primitive gets parsed correctly
				
				int end = compositeArgumentMatcher.end();
				
				do{
					hasComposite = compositeArgumentMatcher.find();
					
					if(hasComposite){
						lastComposite = compositeArgumentMatcher.start();
					}
					
				}while(lastComposite < (end + content.length()) && hasComposite);
				
			}
		}
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(type);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Object value = null;
		try {
			Class<?>[] parameters = new Class<?>[arguments.size()];
			int i = 0;
			for (Object object : arguments) {
				parameters[i] = getPrimitiveType(object.getClass());
				
				i++;
			}
			value = clazz.getConstructor(parameters).newInstance(arguments.toArray());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	private static Object parsePrimitive(String type, String valueText){
		
		//System.out.println(type + " - " + valueText);
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(type);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Object value = null;
		try {
			//System.out.println((char)valueText.charAt(0) + "!!!");
			//Special case for enums and static method calls
			if(valueText.charAt(0) == '#'){
				//Enum special case
				
				String name = valueText.substring(1);
				
				//System.out.println("Parsing special value enum: " + name);
				
				for (Object e : clazz.getEnumConstants()) {
					if(e.toString().equals(name)){
						value = e;
					}
				}
			}else if(valueText.charAt(0) == '.'){
				//static method call case
				Method method = clazz.getMethod(valueText.substring(1));
				
				//System.out.println("Parsing special value method: " + method.getName());
				
				value = method.invoke(null);
			}else{
				//This should be a class with a T T(String) constructor
				
				//Special case for character because it does not have a constructor that takes a string
				if(clazz.getName() == "java.lang.Character"){
					if(valueText.length() > 1){
						throw new Error("Cannot cast a String with a length greater than one to Character!");
					}
					
					value = valueText.charAt(0);
				}else{
					value = clazz.getConstructor(String.class).newInstance(valueText);
				}
			}
			
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	//TODO: Move to some util file
	
	private static String substringToNextMachingBracket(String text, int index){
		
		int i = 0;
		
		StringReader reader = new StringReader(text.substring(index));
		
		int open = 1;
		
		char c;
		try {
			while((c = (char)reader.read()) != (char) -1 && open >= 1){
				
				i++;
				
				if(c == '{'){
					open++;
				}else if(c == '}'){
					open--;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text.substring(index, index + i);
	}
	
	/**
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	public static Class<?> getPrimitiveType(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, SecurityException{
		try {
			Class<?> primitive = (Class<?>) clazz.getField("TYPE").get(null);
			if(primitive != null){
				return primitive;
			}
		} catch (NoSuchFieldException e) {
			return clazz;
		}
		return clazz;
	}
	
	/**
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	public static boolean hasPrimitiveType(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, SecurityException{
		try {
			Class<?> primitive = (Class<?>) clazz.getField("TYPE").get(null);
			if(primitive != null){
				return true;
			}
		} catch (NoSuchFieldException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public static void save(){
		
	}
}
