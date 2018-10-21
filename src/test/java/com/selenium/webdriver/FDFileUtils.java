package com.selenium.webdriver;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.selenium.utils.AssertUtils;
import com.selenium.utils.FileUtils;

@SuppressWarnings("unchecked")
public class FDFileUtils {



	public static File getFileFromFolder(File directory, String fileName, boolean throwException) {
		if(directory != null && fileName != null && !fileName.isEmpty()) {
			File requestedFile = FileUtils.getAbsoluteFile(directory, fileName);
			if(throwException) {
				AssertUtils.isNull(requestedFile, "The file with the name '"  + fileName + "' does not exist in the folder '" + directory.getAbsolutePath() + "'");
			}
			return requestedFile;
		}
		return null;
	}

	public static <T> T getObjectFromJsonFile(File jsonFile, boolean isHashtable, boolean isArrayList) {
		if(jsonFile != null) {
			if(isHashtable) {
				return (T) new FDJsonConverter(jsonFile, isHashtable).getHashtable();
			} else if(isArrayList) {
				return (T) new FDJsonConverter(jsonFile).getArrayList();
			} else {
				return (T) new FDJsonConverter(jsonFile).getHashMap();
			}
		}
		return null;
	}

	public static <K, V> HashMap<K,V>  getHashMapFromJsonFile(File jsonFile) {
		return getObjectFromJsonFile(jsonFile, false, false);
	}

	public static <K, V> Hashtable<K,V> getHashtableFromJsonFile(File jsonFile) {
		return getObjectFromJsonFile(jsonFile, true, false);
	}

	public static <E> ArrayList<E> getArrayListFromJsonFile(File jsonFile) {
		return getObjectFromJsonFile(jsonFile, false, true);
	}





}