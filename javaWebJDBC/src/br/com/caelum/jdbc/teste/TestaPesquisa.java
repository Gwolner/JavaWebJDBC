package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.caelum.jdbc.ConnectionFactory;

public class TestaPesquisa {
	
	public static void main(String[] args) throws SQLException {
		
		//Pega a conexão e o Statement
		Connection con = new ConnectionFactory().getConnection();
		String sql = "SELECT * FROM contatos";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		//Executa um select
		ResultSet rs = stmt.executeQuery();
		
		//ExibiÃ§Ã£o opcional do nome das colunas
		System.out.println("Nome :: E-mail :: EndereÃ§o :: Data de Nascimento");
		
		//Itera no ResultSet
		while(rs.next()){
			String nome = rs.getString("nome");
			String email = rs.getString("email");
			String end = rs.getString("endereco");
			Date data = rs.getDate("dataNascimento");
			
			System.out.println(nome+" :: "+email+" :: "+end+" :: "+data);
			
		}
	}
}
