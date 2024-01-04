package com.nhncloud.edu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.nhncloud.edu.dto.DbMovieDto;

//import com.nhncloud.edu.dto.DbMovieDto;

public class DbMovieInfoDao {

	private JdbcTemplate jdbcTemplate;

//	private String driver = "com.mysql.cj.jdbc.Driver";
//	private String url = "jdbc:mysql://125.6.36.92:3306/nhn_gallery?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
//	private String user = "admin";
//	private String password = "Test123!";
	private String dbip;

	public DbMovieInfoDao(String dbip) {
		this.dbip = dbip;
		
	}

	private DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("admin");
		dataSource.setPassword("Test123!");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(
				"jdbc:mysql://"+dbip+":3306/nhn_gallery?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8&useSSL=false");
//		dataSource.setUrl(
//				"jdbc:mysql://"+"125.6.36.92"+":3306/nhn_gallery?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8&useSSL=false");
		
		System.out.println("Test DataSource getUrl : " + dataSource.getUrl());
		System.out.println("Test DataSource : " + dataSource);
		return dataSource;
	}

	public List<DbMovieDto> getList(String container_name) {
		jdbcTemplate = new JdbcTemplate(dataSource());
		List<DbMovieDto> list = jdbcTemplate.query(
				"SELECT container_name, title, contents_code, contents_type\r\n"
				+ "FROM gal_mov_info\r\n"
				+ "WHERE container_name = ?", new RowMapper<DbMovieDto>() {
					@Override
					public DbMovieDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						DbMovieDto dmd = new DbMovieDto();
						dmd.setTitle(rs.getString("title"));
						dmd.setType(rs.getString("contents_type"));
						dmd.setYoutube(rs.getString("contents_code"));
						return dmd;
					}
				},container_name);
		return list;
	}
	
	public int addList(String container_name, String y_code, String c_title) {
		jdbcTemplate = new JdbcTemplate(dataSource());
		int cnt = jdbcTemplate.update("INSERT INTO gal_mov_info(container_name,title,contents_code)\r\n"
				+ "VALUES (?,?,?)", container_name, c_title, y_code);
		return cnt;
	}

}
