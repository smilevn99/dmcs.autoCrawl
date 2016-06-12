package com.dmcs.crawlAuto.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.dmcs.crawlAuto.contract.Category;

public class CategoryDAO {
	final static Logger logger = Logger.getLogger(CategoryDAO.class);

	private DBConnection dbConn;
	private String databaseUrl;
	private String user;
	private String password;

	public CategoryDAO(String databaseUrl, String user, String password) {
		this.databaseUrl = databaseUrl;
		this.user = user;
		this.password = password;
	}

	public Category addCategory(Category category) {
		PreparedStatement preparedStmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			dbConn = new DBConnection(databaseUrl, user, password);
			conn = dbConn.getConn();

			String query = " insert into category (catename, rootlink, catelink, slug, status, active)"
					+ " values (?, ?, ?, ?, ?, ?) on duplicate key update slug = slug";

			preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, category.getCategoryName());
			preparedStmt.setString(2, category.getRootLink());
			preparedStmt.setString(3, category.getCateLink());
			preparedStmt.setString(4, category.getCateSlug());
			preparedStmt.setInt(5, category.getStatus());
			preparedStmt.setInt(6, category.getActive());

			preparedStmt.execute();

			rs = preparedStmt.getGeneratedKeys();
			if (rs.next()) {
				int catId = rs.getInt(1);
				logger.debug("Added new category with id: " + catId);
				category.setCategoryId(catId);
			}
		} catch (Exception e) {
			logger.error("Error when add category: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (preparedStmt != null)
					preparedStmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				logger.error("Error when release DB connetion: " + e.getMessage());
			}
		}

		return category;
	}
}
