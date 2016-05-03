package game.IO.save;

import java.io.File;
import java.io.IOException;

/**
 * @author Julius Häger
 * @version 1.0
 */
public final class SaverUtil {
	
	//TODO: Inspect if this is should be done better
	
	//JAVADOC: SaverUtil

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static boolean makeFileUsable(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				if (file.canWrite()) {
					return true;
				} else {
					System.err.println("Can't write");
					return false;
				}
			} else {
				System.err.println("Is't a file");
				return false;
			}
		} else {
			if (file.getParentFile().exists()) {
				try {
					if (file.createNewFile()) {
						if (file.isFile()) {
							if (file.canWrite()) {
								return true;
							} else {
								System.err.println("Can't write");
								return false;
							}
						} else {
							System.err.println("Is't a file");
							return false;
						}
					} else {
						System.err.println("Failed to create file");
						return false;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				if (file.getParentFile().mkdirs()) {
					try {
						if (file.createNewFile()) {
							if (file.isFile()) {
								if (file.canWrite()) {
									return true;
								} else {
									System.err.println("Can't write");
									return false;
								}
							} else {
								System.err.println("Is't a file");
								return false;
							}
						} else {
							System.err.println("Failed to create file");
							return false;
						}
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				} else {
					System.err.println("Failed mkdir");
					return false;
				}
			}
		}
	}
}
