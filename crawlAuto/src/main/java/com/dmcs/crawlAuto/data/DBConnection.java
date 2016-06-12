package com.dmcs.crawlAuto.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBConnection {

	final static Logger logger = Logger.getLogger(DBConnection.class);

	private Connection conn = null;

	public DBConnection(String databaseUrl, String user, String password) {
		logger.debug("Setup DB connetion using MySQL JDBC");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Can't load MySQL JDBC Driver: " + e.getMessage());
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");

		try {
			conn = DriverManager.getConnection(databaseUrl, user, password);

		} catch (SQLException e) {
			logger.error("Connection Failed! Check output console: " + e.getMessage());
		}
	}

	public Connection getConn() {
		logger.debug("Start using getConn");

		return conn;
	}

	public void returnConn() {
		logger.debug("Start using returnConn");

		try {
			if (conn != null) {
				conn.close();
			}

		} catch (Exception e) {
			logger.error("Error when returnConn: " + e.getMessage());
		}
	}
}
