package com.dmcs.crawlAuto.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import com.dmcs.crawlAuto.contract.Content;

public class ContentDAO {
	final static Logger logger = Logger.getLogger(ContentDAO.class);

	private DBConnection dbConn;
	private String databaseUrl;
	private String user;
	private String password;

	public ContentDAO(String databaseUrl, String user, String password) {
		this.databaseUrl = databaseUrl;
		this.user = user;
		this.password = password;
	}

	public void addContents(List<Content> contents) {
		PreparedStatement preparedStmt = null;
		Connection conn = null;

		try {
			dbConn = new DBConnection(databaseUrl, user, password);
			conn = dbConn.getConn();

			String query = " insert into content (cateid, title, image_thumb, image_link, des, content, status, slug, create_date, active)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) on duplicate key update slug = slug";

			preparedStmt = conn.prepareStatement(query);
			int i = 0;

			for (Content content : contents) {
				preparedStmt.setInt(1, content.getCategoryId());
				preparedStmt.setString(2, content.getTitle());
				preparedStmt.setString(3, content.getImageThumb());
				preparedStmt.setString(4, content.getImageLink());
				preparedStmt.setString(5, content.getDescription());
				preparedStmt.setString(6, content.getContent());
				preparedStmt.setInt(7, content.getStatus());
				preparedStmt.setString(8, content.getSlug());
				preparedStmt.setString(9, content.getCreateDate());
				preparedStmt.setInt(10, content.getActive());
				
				logger.debug("Title: " + content.getTitle());
				logger.debug("Content: " + content.getContent());

				preparedStmt.addBatch();
				i++;

				if (i % 100 == 0 || i == contents.size()) {
					preparedStmt.executeBatch();
				}
			}
		} catch (Exception e) {
			logger.error("Error when add content: " + e.getMessage());
		} finally {
			try {
				if (preparedStmt != null)
					preparedStmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				logger.error("Error when release DB connetion: " + e.getMessage());
			}
		}
	}
}
