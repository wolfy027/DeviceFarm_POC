package com.selenium.webdriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.selenium.utils.AssertUtils;

public class FDJsonConverter {

	private JsonElement jsonElement;
	private boolean isHashtable;

	public FDJsonConverter(File jsonFile,boolean isHashtable) {
		this(jsonFile);
		this.isHashtable = isHashtable;
	}

	public FDJsonConverter(File jsonFile) {
		AssertUtils.isNull(jsonFile,"The jsonFile cannot be null");
		try {
			this.jsonElement = new JsonParser().parse(new FileReader(jsonFile));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public FDJsonConverter(InputStream instream,boolean isHashtable) {
		this(instream);
		this.isHashtable = isHashtable;
	}

	public FDJsonConverter(InputStream instream) {
		AssertUtils.isNull(instream,"The instream cannot be null");
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new InputStreamReader(instream));
			this.jsonElement = (new JsonParser().parse(buff));
		} catch (JsonIOException | JsonSyntaxException e) {	e.printStackTrace();}
		finally {
			if(buff!=null)	try {buff.close();} catch(Exception ignored) {};
		}
	}

	public FDJsonConverter(String jsonString) {
		AssertUtils.isNull(jsonString,"The jsonString cannot be null");
		AssertUtils.isEmptyString(jsonString,"The jsonString cannot be empty");
		try {
			this.jsonElement = (new JsonParser().parse(jsonString));
		} catch (JsonIOException | JsonSyntaxException e) {	e.printStackTrace();}
	}

	public FDJsonConverter(String jsonString,boolean isHashtable) {
		this(jsonString);
		this.isHashtable = isHashtable;
	}

	public FDJsonConverter(JsonElement jsonObject) {
		AssertUtils.isNull(jsonObject,"The jsonObject cannot be null");
		this.jsonElement = jsonObject;
	}

	public HashMap<String,Object> getHashMap() {
		if(!this.jsonElement.isJsonObject()) {
			throw new IllegalStateException("Illegal object type '" + this.jsonElement.getClass());
		}
		return !this.isHashtable?this.toHashMap(this.jsonElement.getAsJsonObject()):null;
	}

	public Hashtable<String,Object> getHashtable() {
		if(!this.jsonElement.isJsonObject()) {
			throw new IllegalStateException("Illegal object type '" + this.jsonElement.getClass());
		}
		return this.isHashtable ? this.toHashtable(this.jsonElement.getAsJsonObject()):null;
	}

	public ArrayList<Object> getArrayList() {
		if(!this.jsonElement.isJsonArray()) {
			throw new IllegalStateException("Illegal object type '" + this.jsonElement.getClass());
		}
		return this.toArray(this.jsonElement.getAsJsonArray());
	}

	private HashMap<String,Object> toHashMap(JsonObject json) {
		if(json == null) {
			return null;
		}
		HashMap<String,Object> jsonMap = new HashMap<String, Object>();
		Iterator<Entry<String,JsonElement>> iterator = json.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String,JsonElement> entry = iterator.next();
			JsonElement jsonElement = entry.getValue();
			jsonMap.put(entry.getKey(),this.toJavaObject(jsonElement));
		}
		return jsonMap;
	}

	private Hashtable<String,Object> toHashtable(JsonObject json) {
		if(json == null)
			return null;
		Hashtable<String,Object> jsonDic = new Hashtable<String, Object>();
		Iterator<Entry<String,JsonElement>> iterator = json.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String,JsonElement> entry = iterator.next();
			JsonElement jsonElement = entry.getValue();
			jsonDic.put(entry.getKey(),this.toJavaObject(jsonElement));
		}
		return jsonDic;
	}

	private ArrayList<Object> toArray(JsonArray jsonArray){
		if(jsonArray == null)
			return null;
		ArrayList<Object> list = new ArrayList<Object>();
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while(iterator.hasNext()) {
			JsonElement jsonElement = iterator.next();
			list.add(this.toJavaObject(jsonElement));
		}
		return list;
	}

	private Object toJavaObject(JsonElement jsonElement) {
		if(jsonElement == null || jsonElement.isJsonNull()) {
			return this.isHashtable?"null":null;
		}
		else if(jsonElement.isJsonObject()) {
			return this.isHashtable ? this.toHashtable(jsonElement.getAsJsonObject()) : this.toHashMap(jsonElement.getAsJsonObject());
		}
		else if(jsonElement.isJsonArray()) {
			return toArray(jsonElement.getAsJsonArray());
		}
		return this.convertJsonPrimitiveToJavaLiteral(jsonElement.getAsJsonPrimitive());
	}

	private Object convertJsonPrimitiveToJavaLiteral(JsonPrimitive jsonPrim) {
		if(jsonPrim == null || jsonPrim.isJsonNull()) {
			return this.isHashtable?"null":null;
		}
		else if(jsonPrim.isString()) {
			return jsonPrim.getAsString();
		}
		else if(jsonPrim.isBoolean()) { 
			return jsonPrim.getAsBoolean();
		}
		return jsonPrim.getAsNumber().doubleValue();
	}

	public void setIsHashtable(boolean isHashtable) {
		this.isHashtable = isHashtable;
	}

	public boolean isHashtable() {
		return this.isHashtable;
	}

	public JsonElement getJsonObject(){
		return this.jsonElement;
	}
}