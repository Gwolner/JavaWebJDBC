package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.caelum.jdbc.ConnectionFactory;

public class TestaPesquisa {
	/*
	 * Para pesquisar também utilizamos a interface PreparedStatement para montar nosso comando SQL.
Mas como uma pesquisa possui um retorno (diferente de uma simples inserção), usaremos o método
executeQuery que retorna todos os registros de uma determinada query.
 *
 *O objeto retornado é do tipo ResultSet do JDBC, o que nos permite navegar por seus registros através do
método next. Esse método retornará false quando chegar ao m da pesquisa, portanto ele é normalmente
utilizado para fazer um laço nos registros:
 *
 *
 *Recursos avançados: o cursor
Assim como o cursor do banco de dados, só é possível mover para o próximo registro. Para
permitir um processo de leitura para trás é necessário especicar na abertura do ResultSet que
tal cursor deve ser utilizado.
 *
 *
 *
 *
 */
	
	public static void main(String[] args) throws SQLException {
		
		//Pega a conexão e o Statement
		Connection con = new ConnectionFactory().getConnection();
		String sql = "SELECT * FROM contatos";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		//Executa um select
		ResultSet rs = stmt.executeQuery();
		
		//Exibição opcional do nome das colunas
		System.out.println("Nome :: E-mail :: Endereço :: Data de Nascimento");
		
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
