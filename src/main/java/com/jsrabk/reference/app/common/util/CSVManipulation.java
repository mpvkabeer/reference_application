package com.jsrabk.reference.app.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.jsrabk.reference.app.api.model.MyBook;

public class CSVManipulation {

	private String rootPath = null;

	public CSVManipulation(){
	}

	public void start(String rootPath) {
		this.rootPath = rootPath;
		if(this.rootPath != null) {
			ArrayList<MyBook> myBooks = readMyBooksFromCSV(rootPath);
			process(myBooks);			
		}
	}
	
	private ArrayList<MyBook> readMyBooksFromCSV(String fileName) 
	{ 
		ArrayList<MyBook> myBooks = new ArrayList<MyBook>();
		try {
			Path pathToFile = Paths.get(fileName);
			BufferedReader br = Files.newBufferedReader(pathToFile);
			// read the first line from the text file 
			String line = br.readLine();
			// loop until all lines are read
			while (line != null) {
				// use string.split to load a string array with the values from 
				// each line of 
				// the file, using a comma as the delimiter
				String[] attributes = line.split(",");
				MyBook myBook = createMyBook(attributes); 
				// adding MyBook into ArrayList 
				myBooks.add(myBook); 
				// read next line before looping 
				// if end of file reached, line would be null 
				line = br.readLine(); 
			}
		} catch (IOException ioe) 
			{ 
				ioe.printStackTrace(); 
			}
		return myBooks; 
		}

	private MyBook createMyBook(String[] attributes) {
		MyBook myBook = new MyBook();
		myBook.setName(attributes[0]);
		myBook.setType(attributes[1]);
		myBook.setScore(attributes[2]);
		myBook.setSegment(attributes[3]);
		myBook.setUrl(attributes[4]);
		return myBook;
	}

	private void process(ArrayList<MyBook> myBooks) {
		ArrayList<String> segmentList = new ArrayList<String>();
		for(MyBook myBook : myBooks) {
			if(!segmentList.contains(myBook.getSegment())){
				segmentList.add(myBook.getSegment());
			}
		}
		
		JSONArray outputURLArray = new JSONArray();
		JSONObject segmentJSON = new JSONObject(); 
		String urls = "";
		for(String currentSegment : segmentList) {
			urls = "";
			for(MyBook myBook : myBooks) {
				if(myBook.getSegment().equals("")) {
					if(urls.length()==0) {
						urls = myBook.getUrl();
					}else {
						urls = urls +  "," + myBook.getUrl();
					}
				}
			}
			
			segmentJSON = new JSONObject();
			segmentJSON.put(currentSegment, "urls");
			outputURLArray.put(segmentJSON);
		}
		
		File file = new File("D:\\output.json");
		try{
		    FileWriter fw = new FileWriter(file.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(outputURLArray.toString());
		    bw.close();
		    fw.close();
		}
		catch (IOException e){
		    e.printStackTrace();
		}
		
		
		
	}

	

		
	public JSONObject getSearchResults() {
		JSONObject response = new JSONObject();
			response.put("status_code", HttpStatus.OK);
			response.put("input_data", "");
			response.put("output", "done");
		return response;
	}
	
//	public static void main(String[] args) {
//		String jsonString = ""+
//		"{"+
//		"	path: \"F:\\SoftwareServices\\Projects\\SpringWorkspace\\KabeerTest\","+
//		"	texts: \"a\","+
//		"	options:{"+
//		"		search_in_all_files: true,"+
//		"		must_find_all_texts: true,"+
//		"		file_extentions: \"\","+
//		"		must_match_case: false"+
//		"	}"+
//		"}";
//		
//		FindInPath listFile = new FindInPath();
//		listFile.setInputData(jsonString);
//		listFile.start();
//		JSONObject response = listFile.getSearchResults();
//		System.out.println("Output: "+response.toString());
//	}

}
