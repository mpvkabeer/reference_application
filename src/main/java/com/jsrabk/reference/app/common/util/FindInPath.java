package com.jsrabk.reference.app.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class FindInPath {

	private String rootPath = null;
	private ArrayList<String> plainFileExtentionArray = null;
	private ArrayList<String> outputFileArray = null;
	private ArrayList<String> findTextArray = null;
	private boolean searchAllFiles = true;
	private boolean mustFindAllTexts = true;
	private boolean mustMatchCase = false;
	private boolean processCompleted = false;


	public FindInPath(){
		this.plainFileExtentionArray = new ArrayList<String>();
		this.outputFileArray = new ArrayList<String>();
		this.findTextArray = new ArrayList<String>();
		this.searchAllFiles = false;
		this.mustFindAllTexts = true;
	}

	public void start() {
		if(this.rootPath != null) {
			File filePath = new File(this.rootPath);
			processFilePath(filePath);
			this.processCompleted = true; 
		}
	}
	
	private void setSearchAllFiles(boolean searchAllFiles) {
		this.searchAllFiles = searchAllFiles;
	}
	
	private void setFindTextArray(ArrayList<String> findTextArray) {
		this.findTextArray = findTextArray;
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
			
			if(file.canRead()) {
				boolean found = false;
				if(this.searchAllFiles) {
					
					if(mustFindAllTexts) {
						found = findAllInFile(file);
					}else {
						found = findAnyInFile(file);
					}
				}else {
					if(this.plainFileExtentionArray.contains(getFileExtension(file))) {
						if(mustFindAllTexts) {
							found = findAllInFile(file);
						}else {
							found = findAnyInFile(file);
						}
					}else {
						found = false;
					}
				}
				
				if(found) {
					//System.out.println("URL: "+file.toURI());
					//outputFileArray.add(file.getAbsolutePath());
					this.outputFileArray.add(file.toURI().toString());
				}
			}
		}else {
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
		boolean found = true;
		String fileContent = readFile(file);
		
		if(!this.mustMatchCase) {
			fileContent = fileContent.toLowerCase();
		}
		
		if(fileContent!=null && fileContent.length()>0) {
			for(String findText : this.findTextArray) {
				if(!this.mustMatchCase) {
					findText = findText.toLowerCase();
				}
				found = (found && (fileContent.indexOf(findText)!=-1));
				if(!found) {
					break;
				}
			}
		}else {
			found = false;
		}
		fileContent = null;
		return found; 
	}

	private boolean findAnyInFile(File file) {
		boolean found = false;
		String fileContent = readFile(file).toLowerCase();
		if(!this.mustMatchCase) {
			fileContent = fileContent.toLowerCase();
		}

		if(fileContent!=null && fileContent.length()>0) {
			for(String findText : this.findTextArray) {
				if(!this.mustMatchCase) {
					findText = findText.toLowerCase();
				}
				found = (found || (fileContent.indexOf(findText)!=-1));
				if(found) {
					break;
				}
			}
		}else {
			found = false;
		}
		return found; 
	}

	
//	private String readUsingScanner(File file) {
//		Scanner scanner = null;
//		try {
//			scanner = new Scanner(file, StandardCharsets.UTF_8.name());
//			// we can use Delimiter regex as "\\A", "\\Z" or "\\z"
//			String data = scanner.next();
//			return data;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (scanner != null)
//				scanner.close();
//		}
//
//	}

	private String readFile(File file) {
		
		String fileContent = null;
		FileInputStream inputStream = null;

		try{
			inputStream = new FileInputStream(file.getAbsoluteFile());
			fileContent = IOUtils.toString(inputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null) {
				IOUtils.closeQuietly(inputStream);
			}
		}
        return fileContent;
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

		if(json.has("texts")){
			String texts = json.getString("texts");
			String textArray[] = texts.split("\n");
			for(String text : textArray) {
				if(findTextArray==null) {
					findTextArray = new ArrayList<String>();
				}
				findTextArray.add(text);
			}	
		}
			
		if(json.has("options")){
			options = json.getJSONObject("options");
			if(options.has("search_in_all_files")) {
				this.searchAllFiles = options.getBoolean("search_in_all_files");
			}

			if(options.has("must_find_all_texts")) {
				this.mustFindAllTexts = options.getBoolean("must_find_all_texts");
			}

			
			if(options.has("must_match_case")) {
				this.mustMatchCase = options.getBoolean("must_match_case");
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
		if(outputFileArray!=null) {
			for(String outputFile : outputFileArray) {
				outputFileJSON = new JSONObject();
				outputFileJSON.put("file_path", outputFile);
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
