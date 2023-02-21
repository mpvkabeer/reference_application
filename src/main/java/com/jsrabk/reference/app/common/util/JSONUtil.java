package com.jsrabk.reference.app.common.util;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public final class JSONUtil {
	
	public static ResponseEntity<String> generateJSONResponse(JSONObject responseData) {
		JSONObject response = new JSONObject();
		String message = null;
		String error = null;
		HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		if(responseData.has("message")) {
			message = responseData.getString("message");
			responseData.remove("message");
		}
		if(responseData.has("error")) {
			error = responseData.getString("error");
			responseData.remove("error");
		}
		if(responseData.has("status_code")) {
			statusCode = (HttpStatus)responseData.get("status_code");
			responseData.remove("status_code");
		}
		if(statusCode == HttpStatus.OK) {
			response.put("status", "success");
			response.put("status_code", statusCode);
			response.put("message", message);
			response.put("data", responseData);
		}else {
			response.put("status", "failed");
			response.put("status_code", statusCode);
			response.put("error", error);
			response.put("data", new JSONObject());
		}

		return ResponseEntity.ok().body(response.toString());
	}

}