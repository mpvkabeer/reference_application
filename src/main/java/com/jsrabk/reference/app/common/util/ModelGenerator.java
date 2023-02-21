package com.jsrabk.reference.app.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class ModelGenerator {

	JSONArray modelFileDataArray = null; 
	String rootFolderPath = null;

	public ModelGenerator() {
		this.modelFileDataArray = new JSONArray();
		long timestamp = new Date().getTime();
		this.rootFolderPath = ProjectConstant.defaultRootFolderPath + "/model_files/"+timestamp;		
	}

	public void generateJavaModel(JSONArray modelJSONArray) {		
		JSONObject modelJSON = null;
		String packageName = null;
		String className = null;
		String variableData = null;
		String variablePairArray[] = null;
		String variableArray[] = null;
		String variableType = null;
		String variableName = null;
		String modelFileData = null;
		
		StringBuilder headerSection = null;
		StringBuilder variablesSection = null;
		StringBuilder functionsSection = null;
		StringBuilder footerSection = null;
		
		for(int i=0; i<modelJSONArray.length(); i++) {
			headerSection = new StringBuilder();
			variablesSection = new StringBuilder();
			functionsSection = new StringBuilder();
			footerSection = new StringBuilder("}");			
			
			modelJSON = modelJSONArray.getJSONObject(i);
			packageName = modelJSON.getString("package");
			className = modelJSON.getString("class");
			variableData = modelJSON.getString("variables");
			variablePairArray = variableData.trim().split(",");
			
			headerSection.append("package "+packageName+";\r\n" + 
					"\r\n" + 
					"import javax.persistence.Entity;\r\n" + 
					"import javax.persistence.GeneratedValue;\r\n" + 
					"import javax.persistence.GenerationType;\r\n" + 
					"import javax.persistence.Id;\r\n" + 
					"import javax.persistence.Table;\r\n" + 
					"\r\n" + 
					"@Entity\r\n" + 
					"@Table(name=\""+className+"\")\r\n" + 
					"public class "+className+" {\r\n" + 
					"\t\r\n" + 
					"\t@Id\r\n" + 
					"\t@GeneratedValue(strategy = GenerationType.IDENTITY)");
			
			
			for (String variablePair : variablePairArray) {
				variableArray = variablePair.trim().split(" ");				
				variableType = formatVariableType(variableArray[0].trim());
				variableName = toVariableCase(variableArray[1].trim());
			
				variablesSection.append("\tprivate ");				
				variablesSection.append(variableType);
				variablesSection.append(" ");
				variablesSection.append(variableName);
				variablesSection.append(";\n");
				
				//Getter function
				functionsSection.append("\n");
				functionsSection.append("\tpublic ");
				functionsSection.append(variableType);
				functionsSection.append(" ");
				functionsSection.append("get"+toClassCase(variableName)); 
				functionsSection.append("(){\n");
				functionsSection.append("\t\treturn this.");
				functionsSection.append(variableName);
				functionsSection.append(";\n\t}\n");
				
				//Setter function
				functionsSection.append("\n");
				functionsSection.append("\tpublic void "); 
				functionsSection.append("set"+toClassCase(variableName)); 
				functionsSection.append("(");
				functionsSection.append(variableType);
				functionsSection.append(" ");
				functionsSection.append(variableName);
				functionsSection.append("){\n");
				functionsSection.append("\t\tthis.");
				functionsSection.append(variableName);
				functionsSection.append(" = ");
				functionsSection.append(variableName);
				functionsSection.append(";\n\t}\n");
			}
			
			modelFileData = headerSection.toString() 
							+ "\n" 
							+ variablesSection.toString()
							+ "\n" 
							+ functionsSection.toString()
							+ "\n" 
							+ footerSection.toString();
			
			//System.out.println("modelFileData: \n"+modelFileData);
			createModelFile(packageName, className, modelFileData);
			this.modelFileDataArray.put(new JSONObject().put(className+".java", modelFileData));
		}
		
		try {
			zipFolder(Paths.get(this.rootFolderPath), Paths.get(this.rootFolderPath+".zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public JSONObject getModelPreview() {

		JSONObject response = new JSONObject(); 
		response.put("status_code", HttpStatus.OK);
		response.put("message", "model files are created successfully.");
		response.put("model_class_file_path",this.rootFolderPath);
		return response;
	}

	private String formatVariableType(String variableType) {
				
//		return switch(variableType.toLowerCase()) {
//			case "boolean"-> "boolean";
//			case "char"-> "char";
//			case "byte"->  "byte";
//			case "short"-> "short";
//			case "int" -> "int";
//			case "long" -> "Long";
//			case "float" -> "float";
//			case "double" -> "Double";
//			case "string" -> "String";
//			default -> "Unknown";
		
		String actualVariableType = variableType.toLowerCase();
		if(actualVariableType.equals("boolean")) return "boolean";
		else if(actualVariableType.equals("char")) return "char";
		else if(actualVariableType.equals("byte")) return "byte";
		else if(actualVariableType.equals("short")) return "short";
		else if(actualVariableType.equals("int")) return "int";
		else if(actualVariableType.equals("long")) return "Long";
		else if(actualVariableType.equals("float")) return "float";
		else if(actualVariableType.equals("double")) return "Double";
		else if(actualVariableType.equals("string")) return "String";
		else return "Unknown";
	}

	private String toClassCase(String className) {
		if(className.length()>0) {
			if(className.length()>1) {
				className = className.substring(0,1).toUpperCase() + className.substring(1); 
			}else {
				className = className.substring(0,1).toUpperCase();
			}
		}
		
		return className;
	}

	private String toVariableCase(String variableName) {
		if(variableName.length()>0) {
			if(variableName.length()>1) {
				variableName = variableName.substring(0,1).toLowerCase() + variableName.substring(1); 
			}else {
				variableName = variableName.substring(0,1).toLowerCase();
			}
		}
		
		return variableName;
	}
	
	private void createModelFile(String packageName, String className, String modelFileData) {

		String folderPath = this.rootFolderPath + "/" + packageName.replaceAll("[.]","/");
		
		Path directoryPath = Paths.get(folderPath);
		try {
			Files.createDirectories(directoryPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		File file = new File(directoryPath + "/" + className+".java");
		try{
		    FileWriter fw = new FileWriter(file.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(modelFileData);
		    bw.close();
		    fw.close();
		}
		catch (IOException e){
		    e.printStackTrace();
		}
		
	}

    private void zipFolder(final Path sourceFolderPath, Path zipPath) throws Exception {
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();
    }
	
}