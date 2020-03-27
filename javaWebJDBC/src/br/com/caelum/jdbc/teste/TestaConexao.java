package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.SQLException;
import br.com.caelum.jdbc.ConnectionFactory;


public class TestaConexao {

	public static void main(String[] args) throws SQLException {
		
		//Cria uma inst�ncia de conex�o 
		Connection con = new ConnectionFactory().getConnection();
		
		System.out.println("Conex�o�o aberta!!");
		
		//Encerra a conex�o
		con.close();
		
		System.out.println("Conex�o�o fechada!");
	}
}
