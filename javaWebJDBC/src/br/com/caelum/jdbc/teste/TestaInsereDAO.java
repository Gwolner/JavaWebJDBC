package br.com.caelum.jdbc.teste;

import java.sql.SQLException;
import java.util.Calendar;

import br.com.caelum.jdbc.dao.ContatoDAO;
import br.com.caelum.jdbc.modelo.Contato;


public class TestaInsereDAO {

	public static void main(String[] args) throws SQLException {
		
		//Instancia do Java Bean
		Contato contato = new Contato();
		
		//Preenche seus campos
		contato.setNome("Guilherme");
		contato.setEmail("Guilherme@teste.com");
		contato.setEndereco("R. Lantejoula, 298 - Abstergo");
		contato.setDataNascimento(Calendar.getInstance());
		
		//Instancia do DAO
		ContatoDAO dao = new ContatoDAO();
		
		//Adiciona o objeto Contato
		dao.adicionar(contato);
		
		//Imprime confirmação de gravação
		System.out.println("Gravado!");
		
		
	}

}
