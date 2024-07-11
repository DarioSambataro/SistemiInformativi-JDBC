package com.ibm.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Database {
	private Connection connection;
	
	String server, user, password, databaseName;
	int port;
	
	public Database(String databaseName) {
		server = "localhost";
		user = "root";
		password = "admin";
		port = 3306;
		this.databaseName = databaseName;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}
	
	private Connection startConnection() {
		if (connection == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName(server);
			dataSource.setPortNumber(port);
			dataSource.setUser(user);
			dataSource.setPassword(password);
			//dataSource.setDatabaseName(databaseCreation(databaseName));
			
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	public String databaseCreation(String databaseName) {
		String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName + ";";
		
		try {
			PreparedStatement ps = startConnection().prepareStatement(sql);
			ps.executeUpdate(sql);
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return databaseName;
	}
	
	public void NoResultSetQuery(String query) {
		try {
			
			PreparedStatement ps = startConnection().prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void firstQuery(String query) {
		PreparedStatement ps;
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void secondQuery() {
		PreparedStatement ps;
		
		
		String query = "SELECT cognome, COUNT(prestito.id) AS Num_libri FROM utenti INNER JOIN prestito ON utenti.id = prestito.id_utente "
				+ "GROUP BY cognome ORDER BY Num_libri DESC LIMIT 3; ";
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println("NOME: " + rs.getString(1) + " NUMERO LIBRI: + " + rs.getInt(2));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void thirdQuery() {
		PreparedStatement ps;
		
		String query = "SELECT nome, cognome, titolo FROM utenti INNER JOIN prestito ON utenti.id = prestito.id_utente"
				+ " INNER JOIN libri ON prestito.id_libro = libri.id"
				+ " WHERE fine > NOW()";
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println("NOME: " + rs.getString(1) + " " + rs.getString(2) +  " TITOLO:  " + rs.getString(3));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fourthQuery(String utente) {
		PreparedStatement ps;
		String regex = " ";
		String [] splitted = utente.split(regex);
		
		String query = "SELECT titolo, inizio, fine FROM utenti INNER JOIN prestito ON utenti.id = prestito.id_utente"
				+ " INNER JOIN libri ON prestito.id_libro = libri.id"
				+ " WHERE nome = '"+splitted[0]+"' AND cognome = '"+splitted[1]+"';";
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println("TITOLO: " + rs.getString(1) + " INIZIO: " + rs.getString(2) +  " FINE:  " + rs.getString(3));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fifthQuery() {
		PreparedStatement ps;

		
		String query = "SELECT titolo, COUNT(*) AS numero_prestiti FROM libri INNER JOIN prestito ON libri.id = prestito.id_libro"
				+ " GROUP BY titolo ORDER BY numero_prestiti DESC;";
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println("TITOLO: " + rs.getString(1) + " NUMERO PRESTITI: " + rs.getInt(2));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sixthQuery() {
		PreparedStatement ps;

		
		String query = "SELECT nome, cognome, titolo, datediff(fine, inizio) as Durata"
				+ " FROM utenti INNER JOIN prestito ON utenti.id = prestito.id_utente INNER JOIN libri ON prestito.id_libro = libri.id"
				+ " WHERE datediff(fine,inizio) > 15;";
		try {
			ps = startConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println("NOME: " + rs.getString(1) + " COGNOME: " + rs.getString(2) + " TITOLO: " + rs.getString(3) + " DURATA IN GIORNI: " + rs.getInt(4));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
