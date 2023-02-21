package com.jsrabk.reference.app.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class FindTheSingle {

	private String rootPath = null;
	private ArrayList<String> plainFileExtentionArray = null;
	private ArrayList<String> outputFileArray = null;
	private ArrayList<String> outputTotalOpeningTextCountArray = null;
	private ArrayList<String> outputTotalClosingTextCountArray = null;
	private boolean searchAllFiles = true;
	private boolean processCompleted = false;
	private String openingText;
	private String closingText;
	private long totalOpeningTexts = 0;
	private long totalClosingTexts = 0;	

	public FindTheSingle(){
		this.plainFileExtentionArray = new ArrayList<String>();
		this.outputFileArray = new ArrayList<String>();
		this.outputTotalOpeningTextCountArray = new ArrayList<String>();
		this.outputTotalClosingTextCountArray = new ArrayList<String>();
		this.searchAllFiles = false;
	}

	public void start() {
		if(this.rootPath != null) {
			File filePath = new File(this.rootPath);
			processFilePath(filePath);
			this.processCompleted = true; 
		}
	}

	private void loadPlainFileExtentions() {
		this.plainFileExtentionArray.add("txt");
		this.plainFileExtentionArray.add("bat");
		this.plainFileExtentionArray.add("html");
		this.plainFileExtentionArray.add("js");
		this.plainFileExtentionArray.add("css");
		this.plainFileExtentionArray.add("csv");
		this.plainFileExtentionArray.add("java");
		this.plainFileExtentionArray.add("php");
		this.plainFileExtentionArray.add("json");
		this.plainFileExtentionArray.add("py");
		this.plainFileExtentionArray.add("sh");
		this.plainFileExtentionArray.add("scala");
	}
	
	private void processFilePath(File file) {
	
		if(file.isFile()) {
			//System.out.println("\t\t"+file.getAbsolutePath());
			this.totalOpeningTexts = 0;
			this.totalClosingTexts = 0;

			if(file.canRead()) {
				boolean found = false;
				if(this.searchAllFiles) {
					found = findAllInFile(file);
				}else {
					if(this.plainFileExtentionArray.contains(getFileExtension(file))) {
						found = findAllInFile(file);
					}else {
						found = false;
					}
				}
				
				if(found) {
					//this.outputFileArray.add(file.getAbsolutePath());
					this.outputFileArray.add(file.toURI().toString());
					this.outputTotalOpeningTextCountArray.add(""+this.totalOpeningTexts);
					this.outputTotalClosingTextCountArray.add(""+this.totalClosingTexts);
				}
			}
		} else {
			//System.out.println("\t"+file.getAbsolutePath());
			File fileArray[] = file.listFiles();
			if(fileArray.length>0) {
				for(File childFile: fileArray) {
					processFilePath(childFile);
				}
			}
		}
	}
	
	private boolean findAllInFile(File file) {
		boolean foundTheSingle = false;

		String fileContent = null;
		FileInputStream inputStream = null;

		try{
			inputStream = new FileInputStream(file.getAbsoluteFile());
			fileContent = IOUtils.toString(inputStream).toLowerCase();
			
			this.totalOpeningTexts = StringUtils.countOccurrencesOf(fileContent, this.openingText.toLowerCase());
			this.totalClosingTexts = StringUtils.countOccurrencesOf(fileContent, this.closingText.toLowerCase());

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null) {
				IOUtils.closeQuietly(inputStream);
			}
			fileContent = null;
		}
		
			
		
	    if(totalOpeningTexts != totalClosingTexts) {
	    	foundTheSingle = true;
	    }
			
		return foundTheSingle; 
	}
	
	private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
	public void setInputData(String payload) {
		JSONObject json = new JSONObject(payload);
		JSONObject options = null;

		if(json.has("path")){
			this.rootPath = json.getString("path");  			
		}

		if(json.has("opening_text") && json.getString("opening_text").length()>0){
			this.openingText = json.getString("opening_text");
		}
		
		if(json.has("closing_text") && json.getString("closing_text").length()>0){
			this.closingText = json.getString("closing_text");
		}
			
		if(json.has("options")){
			options = json.getJSONObject("options");
			if(options.has("search_in_all_files")) {
				this.searchAllFiles = options.getBoolean("search_in_all_files");
			}

			String fileExtentions = null;
			if(options.has("file_extentions")) {
				fileExtentions = options.getString("file_extentions");
			}

			if(!searchAllFiles) {
				if(fileExtentions!=null && fileExtentions.trim().length()!=0) {
					String fileExtentionArray[] = fileExtentions.split(",");
					for(String fileExtention : fileExtentionArray) {
						this.plainFileExtentionArray.add(fileExtention);
					}
				}else {
					loadPlainFileExtentions();
				}
			}
		}
			
	}
		
	public JSONObject getSearchResults() {
		JSONObject response = new JSONObject();
		JSONArray outputFileJSONArray = new JSONArray(); 
		JSONObject outputFileJSON = null;
		if(this.outputFileArray!=null && this.outputTotalOpeningTextCountArray!=null && this.outputTotalClosingTextCountArray!=null) {
			for(int i=0; i<this.outputFileArray.size();i++) {
				outputFileJSON = new JSONObject();
				outputFileJSON.put("file_path", this.outputFileArray.get(i));
				outputFileJSON.put("total_opening_texts", this.outputTotalOpeningTextCountArray.get(i));
				outputFileJSON.put("total_closing_texts", this.outputTotalClosingTextCountArray.get(i));
				outputFileJSONArray.put(outputFileJSON);
			}
		}
		
		if(this.processCompleted) {
			response.put("status_code", HttpStatus.OK);
			response.put("input_data", "");
			response.put("output", outputFileJSONArray);
		}else {
			response.put("status_code", HttpStatus.BAD_GATEWAY);
			response.put("input_data", "");
			response.putOpt("output",  null);
			response.put("message",  "Please provide valid inputs.");
		}
		return response;
	}

}
