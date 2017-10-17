/*+**********************************************************************************
 * The contents of this file are subject to the vtiger CRM Public License Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * The Original Code is:  vtiger CRM Open Source
 * The Initial Developer of the Original Code is vtiger.
 * Portions created by vtiger are Copyright (C) vtiger.
 * All Rights Reserved.
 ************************************************************************************/
package com.vtiger.tests.vtwsclib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.vtiger.vtwsclib.WSClient;

public class CreateContactWSClient {

	static String vtigerURL = "http://localhost/vtigercrm/";
	static String vtigerUSR = "admin";
	static String vtigerUSRKEY = "n4exllIiniYC1BBs";
	
	
	public static void main(String[] args) {

		WSClient client = new WSClient(vtigerURL);
		if (Test_doLogin(client)) {
//			Test_doListTypes(client);
//			Test_doDescribe(client);
//			Test_doCreate(client);
//			Test_doRetrieve(client, "19x1");
			Test_doQuery(client);
//			Test_doInvoke(client);
		}
	}

	public static boolean Test_doLogin(WSClient client) {
		if (!client.doLogin(vtigerUSR, vtigerUSRKEY)) {
			System.out.println(client.lastError());
			return false;
		}
		return true;
	}

	public static boolean Test_doQuery(WSClient client) {
		//JSONArray result = client.doQuery("select * from Contacts where cf_854='1000'");
		JSONArray result = client.doQuery("select * from Contacts");
		if (result == null)
			return false;

		System.out.println("# Result Rows " + result.size());

		System.out.println("# " + client.getResultColumns(result));

		Iterator resultIterator = result.iterator();
		while (resultIterator.hasNext()) {
			JSONObject row = (JSONObject) resultIterator.next();
			Iterator rowIterator = row.keySet().iterator();

			System.out.println("---");
			while (rowIterator.hasNext()) {
				Object key = rowIterator.next();
				Object val = row.get(key);
				System.out.println(" " + key + " : " + val);
			}
		}

		return true;
	}

	public static void Test_doListTypes(WSClient client) {
		Map result = client.doListTypes();
		if (client.hasError(result)) {
			System.out.println(client.lastError());
		}
		System.out.println(result);
	}

	public static void Test_doDescribe(WSClient client) {
		JSONObject result = client.doDescribe("Contacts");
		if (client.hasError(result)) {
			System.out.println(client.lastError());
		}
		System.out.println(result);
	}

	public static Object Test_doCreate(WSClient client) {

		Map valueMap = new HashMap();
		valueMap.put("lastname", "Test JLead");
		valueMap.put("company", "Test JCompany");

		JSONObject result = client.doCreate("Contacts", valueMap);
		if (result == null) {
			System.out.println(client.lastError());
		}
		return result.get("id");
	}

	public static void Test_doRetrieve(WSClient client, Object record) {

		JSONObject result = client.doRetrieve(record);
		if (result == null) {
			System.out.println(client.lastError());
		}
		System.out.println(result);
	}

	public static void Test_doInvoke(WSClient client) {
		Map params = new HashMap();
		params.put("query", "SELECT * FROM Contacts;");

		Object result = client.doInvoke("query", params);
		if(client.hasError(result)) {
			System.out.println(client.lastError());
		} else {
			System.out.println(result);
		}
	}
}
