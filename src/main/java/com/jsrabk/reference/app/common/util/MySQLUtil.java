package com.jsrabk.reference.app.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public final class MySQLUtil {
	
	public static JSONArray getTableInfomation(JSONObject payloadJSON) {		   

		   String dbHost = payloadJSON.getString("host");
		   String dbUser = payloadJSON.getString("username");
		   String dbPassword = payloadJSON.getString("password");
		   String dbPort = payloadJSON.getString("port");
		   String database = payloadJSON.getString("database");
		   
		   JSONArray tableJSONArray = new JSONArray();
		   Connection con = null;   
		   Statement stmt = null;
		   Statement childStmt = null;
		   Statement descStmt = null;
		   ResultSet rs = null;

		   try{  			   
			   Class.forName("com.mysql.cj.jdbc.Driver");  
			   con = DriverManager.getConnection(  
			   "jdbc:mysql://"+dbHost+":"+dbPort+"/"+database+"?useSSL=false",dbUser,dbPassword);   
			   stmt = con.createStatement();  
			   rs = stmt.executeQuery("SHOW TABLES");
			   while(rs.next()) {
				   String tableName = rs.getString(1);
				   descStmt=con.createStatement();
				   ResultSet rsFields=descStmt.executeQuery("DESC "+tableName);
				   JSONObject tableJSON = new JSONObject();
				   JSONArray fieldJSONArray = new JSONArray();
				   JSONObject fieldJSON = null;
				   //boolean childTable = false;
				   while(rsFields.next()) {
					   fieldJSON = new JSONObject();
					   fieldJSON.put("field_name", rsFields.getString("Field"));
					   fieldJSON.put("field_type", rsFields.getString("Type"));
					   fieldJSON.put("field_null_type", rsFields.getString("Null"));
					   fieldJSON.put("field_key_type", rsFields.getString("Key"));
					   fieldJSON.put("field_default_data", rsFields.getString("Default"));
					   fieldJSON.put("field_extra_data", rsFields.getString("Extra"));
					   
//					   if(fieldJSON.get("field_key_type").equals("MUL")){
//						   childTable = true;
//					   }
					   fieldJSONArray.put(fieldJSON);
				   }
				   tableJSON.put("table_name", tableName);
				   //tableJSON.put("is_child_table", childTable);
				   tableJSON.put("field_array", fieldJSONArray);
				   
				   tableJSONArray.put(tableJSON);
				   
				   if(rsFields!=null) {
					   rsFields.close();
				   };
			   }
			   
			   // Start setting Parent tables
			   JSONObject tableJSON = null;
			   String childTableName = null;
			   String childColumnName = null;
			   //String referenceTableName = null;
			   for (int i=0; i<tableJSONArray.length();i++) {
				   tableJSON = tableJSONArray.getJSONObject(i);
				   childStmt = con.createStatement();
				   ResultSet connectedRs = childStmt.executeQuery("SELECT TABLE_NAME, COLUMN_NAME FROM information_schema.KEY_COLUMN_USAGE WHERE table_schema = '"+database+"' AND referenced_table_name = '"+tableJSON.get("table_name")+"'");
				   while(connectedRs.next()) {
					   childTableName = connectedRs.getString("TABLE_NAME");
					   childColumnName = connectedRs.getString("COLUMN_NAME");
					   //referenceTableName = connectedRs.getString("REFERENCED_TABLE_NAME");
					   JSONArray tempTableArray = new JSONArray();
					   for (int ch=0; ch<tableJSONArray.length();ch++) {
						   JSONObject childTableJSON = tableJSONArray.getJSONObject(ch);
						   if(childTableJSON.getString("table_name").equals(childTableName)) {
							   JSONArray childTableFieldJSONArray = childTableJSON.getJSONArray("field_array");
							   JSONArray tempTableFieldArray = new JSONArray();
							   for (int fi=0; fi<childTableFieldJSONArray.length();fi++) {
								   JSONObject childTableFieldJSON = childTableFieldJSONArray.getJSONObject(fi);
								   if(childTableFieldJSON.getString("field_name").equals(childColumnName)) {
									   childTableFieldJSON.put("field_type", tableJSON.get("table_name"));
								   }
								   tempTableFieldArray.put(childTableFieldJSON);
							   }
							   JSONObject tempTableJSON = new JSONObject();
							   tempTableJSON.put("table_name", childTableName);
							   //tempTableJSON.put("is_child_table", true);
							   tempTableJSON.put("field_array", tempTableFieldArray);

							   tempTableArray.put(tempTableJSON);
						   } else {
							   tempTableArray.put(childTableJSON);
						   }
					   }
					   tableJSONArray = tempTableArray;
				   }
				   if(connectedRs!=null) {
					   connectedRs.close();   
				   }
				   if(childStmt!=null) {
					   childStmt.close();
				   }
				   
			   }
			   			   			   
			   //End setting Parent tables
			   if(rs!=null) {
				   rs.close();
			   }
			   if(stmt!=null) {
				   stmt.close();
			   }
			   if(descStmt!=null) {
				   descStmt.close();
			   }			   
			   if(con!=null) {
				   con.close();  
			   }
		   }catch(Exception e){
			   System.out.println(e);
			   if(rs!=null) {
				   try{rs.close();} catch(Exception rse) {}
			   }
			   if(stmt!=null) {
				   try{stmt.close();} catch(Exception stmte) {}
			   }
			   if(con!=null) {
				   try{con.close();} catch(Exception cone) {}  
			   }
		   }  

		return tableJSONArray; 
	}

}