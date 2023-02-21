package com.jsrabk.reference.app.api.util.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsrabk.reference.app.common.util.CSVManipulation;
import com.jsrabk.reference.app.common.util.FindInPath;
import com.jsrabk.reference.app.common.util.FindTheSingle;
import com.jsrabk.reference.app.common.util.JSONUtil;
import com.jsrabk.reference.app.common.util.ModelGenerator;
import com.jsrabk.reference.app.common.util.MySQLUtil;
import com.jsrabk.reference.app.common.util.SiteProbe;

@RestController
@RequestMapping("/api/util")
public class UtilController {

   @PostMapping("/site_probe")
   public ResponseEntity<?> site_probe(@RequestBody String payload) {
	   JSONObject payloadJSON = new JSONObject(payload);
	   String siteURL = null;
	   if(payloadJSON.has("site_url")) {
		   siteURL = payloadJSON.getString("site_url");
	   }
	   SiteProbe siteProbe = new SiteProbe(siteURL);
	   JSONObject responseData = siteProbe.getSiteProbeResults();

	   return JSONUtil.generateJSONResponse(responseData);
   }

   @PostMapping("/model_generator")
   public ResponseEntity<?> model_generator(@RequestBody String payload) {
	   JSONArray payloadJSONArray = new JSONArray(payload);

	   ModelGenerator modelGenerator  = new ModelGenerator();
	   modelGenerator.generateJavaModel(payloadJSONArray);
	   JSONObject responseData = modelGenerator.getModelPreview();

	   return JSONUtil.generateJSONResponse(responseData);
   }

   @PostMapping("/find_in_path")
   public ResponseEntity<?> find_in_path(@RequestBody String payload) {
	   
	   FindInPath findInPath = new FindInPath();
	   findInPath.setInputData(payload);
	   findInPath.start();
	   JSONObject responseData = findInPath.getSearchResults();

	   return JSONUtil.generateJSONResponse(responseData);
   }

   @PostMapping("/find_the_single")
   public ResponseEntity<?> find_the_single(@RequestBody String payload) {
	   
	   FindTheSingle findTheSingle = new FindTheSingle();
	   findTheSingle.setInputData(payload);
	   findTheSingle.start();
	   JSONObject responseData = findTheSingle.getSearchResults();

	   return JSONUtil.generateJSONResponse(responseData);
   }
   
   @PostMapping("/csv_manipulation")
   public ResponseEntity<?> csv_manipulation(@RequestBody String payload) {
	   
	   CSVManipulation csvManipulation = new CSVManipulation();
	   csvManipulation.start("D:\\input.csv");
	   JSONObject responseData = csvManipulation.getSearchResults();

	   return JSONUtil.generateJSONResponse(responseData);
   }
   
   @PostMapping("/db_model_generator")
   public ResponseEntity<?> convertDBTablesToModel(@RequestBody String payload) {
	   JSONObject payloadJSON = new JSONObject(payload);
	   
//	   ModelGenerator modelGenerator  = new ModelGenerator();
//	   modelGenerator.generateJavaModel(payloadJSONArray);
//	   JSONObject responseData = modelGenerator.getModelPreview();
	   JSONObject responseData = new JSONObject();
	   responseData.put("data",MySQLUtil.getTableInfomation(payloadJSON));
	   responseData.put("status_code", HttpStatus.OK);
	   responseData.put("message", "Successful.");

	   return JSONUtil.generateJSONResponse(responseData);
   }
   
}