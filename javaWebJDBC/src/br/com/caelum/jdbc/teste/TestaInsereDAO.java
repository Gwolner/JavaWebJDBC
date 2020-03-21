package br.com.caelum.jdbc.teste;

import java.sql.SQLException;
import java.util.Calendar;

import br.com.caelum.jdbc.dao.ContatoDAO;
import br.com.caelum.jdbc.modelo.Contato;


public class TestaInsereDAO {

	public static void main(String[] args) throws SQLException {
		
		Contato contato = new Contato();
		
		contato.setNome("Juninho");
		contato.setEmail("juninho@teste.com");
		contato.setEndereco("R. Lantejoula, 298 - Abstergo");
		contato.setDataNascimento(Calendar.getInstance());
		
		ContatoDAO dao = new ContatoDAO();
		
		dao.adicionar(contato);
		
		System.out.println("Gravado!");
		
		
	}

}
