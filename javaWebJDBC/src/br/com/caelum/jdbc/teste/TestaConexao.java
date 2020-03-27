package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.SQLException;
import br.com.caelum.jdbc.ConnectionFactory;


public class TestaConexao {

	public static void main(String[] args) throws SQLException {
		
		//Cria uma instância de conexão 
		Connection con = new ConnectionFactory().getConnection();
		
		System.out.println("Conexão£o aberta!!");
		
		//Encerra a conexão
		con.close();
		
		System.out.println("Conexão£o fechada!");
	}
}
