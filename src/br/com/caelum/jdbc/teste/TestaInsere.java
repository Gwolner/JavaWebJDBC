package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.caelum.jdbc.ConnectionFactory;

public class TestaInsere {

	public static void main(String[] args) throws SQLException {
		
		//Connection con = null;
		
		//O try com declaração e iniciação se chama try-with-resources
		try(Connection con = new ConnectionFactory().getConnection()){
			//Conectando
			//con = new ConnectionFactory().getConnection();
			
			//Criando uma PreparedStatement
			String sql = "insert into contatos"+
						 "(nome,email,endereco,dataNascimento)"+
						 "value (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			
			//Preenchendo os valores
			stmt.setString(1, "Eliane");
			stmt.setString(2, "eli@dias.com");
			stmt.setString(3, "R. SQL 295 Workbench");
			stmt.setDate(4, new java.sql.Date(
					Calendar.getInstance().getTimeInMillis()));
			
			//Executa
			stmt.execute();
			
			//Fecha
			stmt.close();
			
			System.out.println("Gravado!!");
		}catch(SQLException e) {
			System.out.println(e);
		}finally {
			//Fecha conexão havendo ou não erro na inserção dos dados.
			//con.close();
			System.out.println("Conexão encerrada!");
		}
	}
}
