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
	 * Já foi possível sentir que colocar código SQL dentro 
	 * de suas classes de lógica é algo nem um pouco elegante
	 * e muito menos viável quando você precisa manter o seu 
	 * código.
	 * 
	 * 
	 * A ideia a seguir é remover o código de acesso ao banco de 
	 * dados de suas classes de lógica e colocá-lo em uma classe 
	 * responsável pelo acesso aos dados. Assim o código de acesso 
	 * ao banco de dados ca em um lugar só, tornando mais fácil 
	 * a manutenção.
	 * 
	 * Que tal se pudéssemos chamar um método adiciona que adiciona 
	 * um Contato ao banco? Em outras palavras quero que o código 
	 * a seguir funcione:
	 * 
	 * Misterio bd = new Misterio();
	 * bd.adiciona("meu nome", "meu email", "meu endereço", meuCalendar);
	 * 
	 * Se contato tivesse 20 atributos, passaríamos 20 parâmetros?
	 * 
	 * seria muito melhor e mais elegante poder chamar um único método 
	 * responsável pela inclusão. 
	 * 
	 * 	// pronto para gravar
Contato contato = new Contato();
contato.setNome("Caelum");
contato.setEmail("contato@caelum.com.br");
contato.setEndereco("R. Vergueiro 3185 cj87");
contato.setDataNascimento(Calendar.getInstance());
// grave nessa conexão!!!
Misterio bd = new Misterio();
// método elegante
bd.adiciona(contato);
System.out.println("Gravado!");
	 * 
	 * O código anterior já mostra o poder que alcançaremos: através de uma única classe seremos capazes de
acessar o banco de dados e, mais ainda, somente através dessa classe será possível acessar os dados.
Esta ideia, inocente à primeira vista, é capaz de isolar todo o acesso a banco em classes bem simples, cuja
instância é um objeto responsável por acessar os dados. Da responsabilidade deste objeto surgiu o nome de
Data Access Object ou simplesmente DAO, um dos mais famosos padrões de projeto (design pattern).
O que falta para o código acima funcionar é uma classe chamada ContatoDao com um método chamado
adiciona. Vamos criar uma que se conecta ao banco ao construirmos uma instância dela:
	 * 
	 * Agora que todo ContatoDao possui uma conexão com o banco, podemos focar no método adiciona, que
recebe um Contato como argumento e é responsável por adicioná-lo através de código SQL
	 * 
	 * Agora que todo ContatoDao possui uma conexão com o banco, podemos focar no método adiciona, que
recebe um Contato como argumento e é responsável por adicioná-lo através de código SQL
	 * 
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
			
			//Encerra
			stmt.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			conex.close();
		}
	}
}
