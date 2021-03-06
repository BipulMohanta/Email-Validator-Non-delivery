package com.mav.email.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.mav.email.bo.EmailMessage;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.exception.ValidationException;

/**
 * 
 * @author bipul.mohanta
 *
 */
public class GenericUtil {

	private GenericUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject validateJSON(String jsonString) throws JSONException {
		return new JSONObject(jsonString);
	}

	/**
	 * 
	 * @param emailMessage
	 * @return
	 * @throws JSONException
	 * @throws ValidationException
	 */
	public static void validateRequestEmailObject(EmailMessage emailMessage) throws JSONException, ValidationException {

		if (CollectionUtils.isEmpty(emailMessage.getToEmail()) || StringUtils.isBlank(emailMessage.getSubject())
				|| StringUtils.isBlank(emailMessage.getFromUser())) {

			throw new ValidationException(HttpStatus.BAD_REQUEST, "Invalid Request Input");
		}

	}

	/**
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static Map<String, Object> convertJSONObjectToMap(JSONObject jsonObject) {

		Map<String, Object> returnMap = new HashMap<String, Object>();

		Set<String> jsonKeySet = jsonObject.keySet();

		jsonKeySet.stream().forEach(key -> {

			Object value = jsonObject.get(key);
			if (value instanceof JSONArray) {
				returnMap.put(key, convertJSONArrayToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				returnMap.put(key, convertJSONObjectToMap((JSONObject) value));
			} else {
				returnMap.put(key, value);
			}
		});

		return returnMap;

	}

	/**
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static List<Object> convertJSONArrayToList(JSONArray jsonArray) {

		List<Object> returnList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Object value = jsonArray.get(i);
			if (value instanceof JSONArray) {
				returnList.add(convertJSONArrayToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				returnList.add(convertJSONObjectToMap((JSONObject) value));
			} else {
				returnList.add(value);
			}

		}

		return returnList;

	}

	private boolean validateFileExtension(String extension) {
		return false;

	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static String commaSeperatedEmailAddress(List<String> emailAddress) {
		if (emailAddress != null)
			return emailAddress.stream().collect(Collectors.joining(","));
		else
			return null;
	}

	public static void validateRequestAttachmentFileIds(EmailMessage emailMessage) throws CustomServiceException {
		
		if(CollectionUtils.isEmpty(emailMessage.getServerFileIds())) {
			throw new CustomServiceException("", HttpStatus.BAD_REQUEST);
		}
		
	}

}
