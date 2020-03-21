package br.com.caelum.jdbc.teste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.caelum.jdbc.ConnectionFactory;

public class TestaInsere {

	/* Para inserir dados em uma tabela de um banco de dados 
	 * entidade-relacional basta usar a cláusula INSERT.
	 * Precisamos especificar quais os campos que desejamos 
	 * atualizar e os valores.
	 * 
	 * String sql = "insert into contatos " +
"(nome,email,endereco, dataNascimento)" +
" values ('" + nome + "', '" + email + "', '" +
endereco + "', '"+ dataNascimento +"')";
	 * 
	 * Outro problema é o clássico “preconceito contra Joana D’arc”, 
	 * formalmente chamado de SQL Injection. O que acontece quando o 
	 * contato a ser adicionado possui no nome uma aspas simples? O 
	 * código SQL se quebra todo e para de funcionar ou, pior ainda, 
	 * o usuário nal é capaz de alterar seu código sql para executar 
	 * aquilo que ele deseja (SQL injection)... tudo isso porque 
	 * escolhemos aquela linha de código e não zemos o escape de 
	 * caracteres especiais.
	 * 
	 * Mais um problema que enxergamos aí é na data. Ela precisa ser 
	 * passada no formato que o banco de dados entenda e como uma 
	 * String, portanto, se você possui um objeto java.util.Calendar 
	 * que é o nosso caso, você precisará fazer a conversão desse 
	 * objeto para a String.
	 * 
	 * 
	 * Por esses três motivos não usaremos código SQL como mostrado 
	 * anteriormente. Vamos imaginar algo mais genérico e um pouco 
	 * mais interessante:
	 * 
	 * String sql = "insert into contatos"+
						 "(nome,email,endereco,dataNascimento)"+
						 "value (?,?,?,?)";
	 * 
	 * 
	 * Perceba que não colocamos os pontos de interrogação de brincadeira,
	 * mas sim porque realmente não sabemos o que desejamos inserir. 
	 * Estamos interessados em executar aquele códigomas não sabemos ainda
	 * quais são os parâmetros que utilizaremos nesse código SQL que será 
	 * executado, chamado de statement.
	 * 
	 * As cláusulas são executadas em um banco de dados através da interface 
	 * PreparedStatement. Para receber um PreparedStatement relativo à conexão,
	 * basta chamar o método prepareStatement, passando como argumento o comando
	 * SQL com os valores vindos de variáveis preenchidos com uma interrogação.
	 * 
	 * Logo em seguida, chamamos o método setString do PreparedStatement para 
	 * preencher os valores que são do tipo String, passando a posição (começando
	 * em Õ) da interrogação no SQL e o valor que deve ser colocado:
	 * 
	 * Precisamos definir também a data de nascimento do nosso contato, para isso,
	 * precisaremos de um objeto do tipo java.sql.Date para passarmos para o nosso
	 * PreparedStatement. Nesse exemplo, vamos passar a data atual. Para isso, 
	 * vamos passar um long que representa os milissegundos da data atual para 
	 * dentro de um java.sql.Date que é o tipo suportado pela API JDBC. Vamos 
	 * utilizar a classe Calendar para conseguirmos esses milissegundos:
	 * 
	 * java.sql.Date dataParaGravar = new java.sql.Date(
Calendar.getInstance().getTimeInMillis());
stmt.setDate(4, dataParaGravar);
	 * 
	 * Fechando conexão apropriadamente
	 * 
	 * Não é comumutilizar JDBCdiretamente hoje em dia. O mais praticado é o uso 
	 * de alguma API deORMcomo a JPA ou o Hibernate. Tanto na JDBC quanto em 
	 * bibliotecas ORM deve-se prestar atenção no momento de fechar a conexão.
	 * O comum é fechar a conexão em um bloco finally:
	 * 
	 * 
	 * Dessa forma,mesmo que o código dentro do try lance exception, o con.close()
	 * será executado. Garantimos que não deixaremos uma conexão pendurada sem uso. 
	 * Esse código pode car muito maior se quisermos ir além. Pois o que acontece 
	 * no caso de con.close lançar uma exception? Quem a tratará?
	 * 
	 * Trabalhar com recursos caros, como conexões, sessões, threads e arquivos, 
	 * sempre deve ser muito bem pensado. Deve-se tomar cuidado para não deixar 
	 * nenhum desses recursos abertos, pois poderá vazar algo precioso da nossa 
	 * aplicação. Como veremos durante o curso, é importante centralizar esse tipo 
	 * de código em algum lugar, para não repetir uma tarefa complicada como essa.
	 * 
	 * Além disso, há a estrutura do Java ß conhecida como try-with-resources. 
	 * Ela permite declarar e inicializar,dentro do try, objetos que implementam 
	 * AutoCloseable. Dessa forma, ao término do try, o próprio compilador inserirá 
	 * instruções para invocar o close desses recursos, além de se precaver em 
	 * relação a exceções que podem surgir por causa dessa invocação. Nosso código 
	 * ficaria mais reduzido e organizado, além do escopo de con só valer dentro 
	 * do try.
	 * 
	 * A má pratica Statement
	 * 
	 * Em vez de usar o PreparedStatement, você pode usar uma interface mais simples 
	 * chamada Statement, que simplesmente executa uma cláusula SQL no método 
	 * execute:
	 * 
	 * Statement stmt = con.createStatement();
stmt.execute("INSERT INTO ...");
stmt.close();
	 * 
	 * Mas prefira a classe PreparedStatement que é mais rápida que Statement e deixa 
	 * seu código muito mais limpo. Geralmente, seus comandos SQL conterão valores 
	 * vindos de variáveis do programa Java; usando Statements,você terá que fazer 
	 * muitas concatenações, mas usando PreparedStatements, isso ca mais limpo e 
	 * fácil.
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static void main(String[] args) throws SQLException {
		
//		Connection con = null;
		
		//O try com declaração e iniciação se chama try-with-resources
		try(Connection con = new ConnectionFactory().getConnection()){
//		try {
			//Conectando
//			con = new ConnectionFactory().getConnection();
			
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
