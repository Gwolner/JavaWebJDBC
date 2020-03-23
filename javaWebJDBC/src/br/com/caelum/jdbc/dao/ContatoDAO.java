package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;


public class ContatoDAO{
	/* DAO - Data Access Object
	 * 
	 * 
	 * 
	 */
	private Connection conex;
	
	public ContatoDAO() {
		this.conex = new ConnectionFactory().getConnection();
	}
	
	public void adicionar(Contato contato) throws SQLException {
		
		String sql = "INSERT INTO contatos"+
					 "(nome, email,endereco,dataNascimento)"+
					 "VALUE(?,?,?,?)";
		try{
			//Preparando Statment para inserção
			PreparedStatement stmt = conex.prepareStatement(sql); 
			
			//Setando valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(
					contato.getDataNascimento().getTimeInMillis()));
			
			//Executa
			stmt.execute();
			stmt.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			conex.close();
		}
	}
}
