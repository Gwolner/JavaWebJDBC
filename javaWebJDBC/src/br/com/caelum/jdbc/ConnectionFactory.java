package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection(){
		try{
			/* DriverManager é a classe responsável por se comunicar com todos os 
			 * drives que foram disponibilizados e o método getConnection indica
			 * qual banco desejamos nos conectar.
			 */
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/bd_jdbc","root","root");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}	
	}
}
