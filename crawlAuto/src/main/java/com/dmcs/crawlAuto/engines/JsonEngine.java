package com.dmcs.crawlAuto.engines;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dmcs.crawlAuto.contract.ConfigMapping;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class JsonEngine {
	
	public ConfigMapping readMapping(String filePath) {
		ConfigMapping configMapping = null;
		Gson gson = new Gson();
		
		try {
			configMapping = gson.fromJson(new FileReader(filePath), ConfigMapping.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return configMapping;
	}
	
	public void saveMapping(ConfigMapping config, String filePath) {
        Gson gson = new Gson();
        
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
