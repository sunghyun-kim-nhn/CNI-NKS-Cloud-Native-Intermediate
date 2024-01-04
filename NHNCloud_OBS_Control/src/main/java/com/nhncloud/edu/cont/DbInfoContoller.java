package com.nhncloud.edu.cont;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncloud.edu.dao.DbMovieInfoDao;

public class DbInfoContoller {
	private String dbip;

	public DbInfoContoller(String dbUrl) {
		dbip = dbUrl;		
	}

	public String getMovieInfo(String c_name) {
		DbMovieInfoDao dmid = new DbMovieInfoDao(dbip);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		JSONArray jarr = new JSONArray();
		try {
			jsonString = mapper.writeValueAsString(dmid.getList(c_name));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jarr.put(jsonString);
		System.out.println("JSON List Test : " + jsonString);

		return jsonString;

	}
	
	public int addMovieInfo(String c_name, String c_title, String y_code) {
		DbMovieInfoDao dmid = new DbMovieInfoDao(dbip);
		//String c_name, String c_title, String y_code
		int cnt = dmid.addList(c_name, y_code, c_title);

		return cnt;
	}


}
