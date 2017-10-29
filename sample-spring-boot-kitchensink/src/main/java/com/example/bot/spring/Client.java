package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

@Slf4j
public class Client{
	private String name;
	private int age;
	private String gender;
	private int height;
	private int weight;
	public Client(String name, int age, String gender, int height, int weight) {
		this.name=name;
		this.age=age;
		this.gender=gender;
		this.height=height;
		this.weight=weight;
		try {
			Connection connection=getConnection();
			PreparedStatement stmt=connection.prepareStatement("INSERT INTO client VALUES (?,?,?,?,?);");
			stmt.setString(1,name);
			stmt.setString(3,gender);
			stmt.setInt(2,age);
			stmt.setInt(4,height);
			stmt.setInt(5,weight);
			stmt.executeQuery();
			stmt.close();
			connection.close();
		}catch (Exception e) {
			System.out.println(e);;
		}
	}
	public float calculateBMI() {
		int a=(weight*100/height)/height;
		return (float)a/100;
	}
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}
}