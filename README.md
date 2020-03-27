# Java Web - JDBC

Estudo do JDBC usando uma fábrica de conexões ao BD MySQL, o driver Connector/J e o design patterns DAO (Data Access Object).

## Persistência

O	 processo	 de	 armazenamento	 de	 dados	 é	 também	 chamado	 de	 persistência.	 A	 biblioteca	 de persistência	 em	 banco	 de	 dados	 relacionais	 do	 Java	 é	 chamada	 JDBC	 (Java	 DataBase	 Connectivity).

* <b>Persistindo através de socket?</b>

Para	conectar-se	a	um	banco	de	dados,	poderíamos	abrir	sockets	diretamente	com	o	servidor	que	o hospeda (Oracle, MySQL ou outros) e	nos comunicarmos	 com	 ele	 através	 de	 seu	 protocolo proprietário. A questão é saber qual o protocolo proprietário de cada BD e isto é extremamente complexo por questões de proteção dos direitos de acesso a cada bancos. 

* <b>Usando uma API?</b>

Uma alternativa seria utilizar uma API específica para cada tipo de banco. Estaabordagem facilita muito o trabalho por não ser preciso entender o protocolo de cada banco. Porém teriamos de alterar o código sempre que mudarmos de banco, para usar sua API específica.

* <b>A solução é usar JDBC!</b>

Especificamente em Java, a conexão pode ser feita de forma mais alegante. Para evitar que cada banco tenha sua própria API, além de um conjunto de classes e métodos, podemos ter um único conjunto de interfaces bem definidas que devem ser implementadas. Esse conjunto de interfaces fica dentro do pacote `java.sql` e nos referimos a ele como JDBC.

<imagem interface JDBC>

Caso precise trabalhar com MySQL, é preciso que as classes concretas implementem essa interfaces do pacote `java.sql`. 

Esse conjunto de classes concretas é quem faz a ponte entre o código que usa a API JDBC e o banco de dados, pois são elas quem sabem se comunicar através do protocolo proprietário do banco. Esse conjunto de classe recebe o nome de `driver`. Todos os principais bancos de dados que existem possuem drivers JDBC para que você possa utilizá-los com Java.

<imagem interface JDBC>
 
 ## Driver 
 
O driver utilizado neste projeto é o <i>mysql-connector-java-8.0.17</i>.</br>
Para fazer o download desta versão ou de uma mais recente, pode-se visitar o site do [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

O driver deve ser adicionado ao projeto por exportação de arquivos externos do tipo `.jar`.</br>
No Eclipse, IDE utilizada neste projeto, o caminho utilizado foi: 

<b><i>Clique direito sobre o projeto > Build Path > Add	to Build Path</i></b>
 
 ## Banco MySQL
 
O banco MySQL que usaremos se chamará <b>bd_jdbc</b> e para criá-lo usamos:
```mysql
CREATE DATABASE bd_jdbc;
```

## Fábrica de conexões

A classe ConnectionFactory é usada para implementar a fábrica, onde o objeto Connection tem sua construção encapsulada. Desta forma, além de evitar repetição de código para cada nova conexão, concentra em uma única classe os parametos que precisam ser alterados.
```java
try{
	return DriverManager.getConnection("jdbc:mysql://localhost/bd_jdbc","root","root");
}catch(SQLException e){
	throw new RuntimeException(e);
}
```
<b>DriverManager</b> é a classe responsável por se comunicar com todos os drives que foram disponibilizados e o método <b>getConnection</b> indica qual banco desejamos nos conectar.

É importante saber que esta classe não é static pois não se deve compartilhar a mesma conexão para vários rcursos.</br>
Na prática, um sistema que usa JSBC deve usar um Pool de conexões para disponibilizar e limitar o número de conexões ao BD.

## Testando a conexão

A classe <b>TestaConexao</b> serve para confirmar que a versão do driver utilizado é compatível com a versão do BD, que a fábrica está funcnionando corretamente e que banco criado está acessível para conexões externas provenientes do projeto.
```java
//Cria uma instância de conexão
Connection con = new ConnectionFactory().getConnection();

//Encerra a conexão
con.close();
```

## Inserindo dados

Para inserir dados em uma tabela de um banco entidade-relacionamento basta usar a clausura INSERT e, claro, especificando quais campos desejamos inserir valores.
```java
String sql = "insert into contatos " +
             "(nome,email,endereco, dataNascimento)" +
             "values ('" + nome + "', '" + email + "', '" + endereco + "', '"+ dataNascimento +"')";
```
Porém aqui tem-se o clássico problema de "Joana D'arc", tambem chamado de <b>SQL Injection</b>, onde uma aspas adicional, sem fechamento, é introduzida no sistema, quebrandpo todo o código que se apresenta conforma a disposição acima. O que resolveria isso seria introduzir sempre o caractere de escape após as aspas, porém nem todo o usuario conhece e, muito menos, fará isto.

Outro problema acima é na data. Ela precisa ser passada como String para o banco, sendo assim, deve-se sempre fazer a conversão do objeto `java.util.Calendar`para String. Por esses motivos não usaremos o código SQL como mostrado e sim de uma forma mais interessante.
```java
String sql = "insert into contatos"+
             "(nome,email,endereco,dataNascimento)"+
             "value (?,?,?,?)";

PreparedStatement stmt = con.prepareStatement(sql);
````

As interrogações representam os dados que desejamos inserir. Este código SQL é chamado de <b>statement</b>. As cláusulas são executadas em um BD através da interface <b>PreparedStatement</b>. Basta chamar o método <b>prepareStatement</b>, passando como argumento o comando SQL contendo as interrogações.

Usamos o método <b>setString</b> do PreparedStatement para preencher os valores, passando a posição (começando em 1) e o valor (como String).
```java
stmt.setString(1, "Guilherme");
stmt.setString(2, "Guilherme@teste.com");
stmt.setString(3, "R. SQL 295 Workbench");
```

Para representar a data foi usado o objeto `java.sql.Date`. Neste exemplo, está sendo passado a ata atual, passando um long que representa os milissegundos.
```java
java.sql.Date dataParaGravar = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
stmt.setDate(4, dataParaGravar);
```

Por fim, o método <b>execute()</b> do objeto PreparedStatement executa o comando SQL.
```java
stmt.execute();
```

## Fechando apropriadamente a conexão

É comum que fechar uma conexão em um bloco <i>finally</i>, desta forma, mesmo que o código dentro do <i>try</i> lance uma <i>exception</i>, não ficará com este recurso aberto, pois poderá vazar algo precioso da aplicação
```java
Connection con = null;

try {
	con = new ConnectionFactory().getConnection();
	
	//Resto do código...
	
}catch(SQLException e) {
	System.out.println(e);
}finally {
	con.close();
}
```

Além disto, há a estrutura de Java conhecida como <b>try-with-resources</b> que permite declarar e inicializar objetos dentro do <i>ry</i> que possual a caracteristica de <b>AutoCloseable</b>. Desta forma, ao terminar a execução do bloco <i>try</i>, o compilador invoca o método <b>close()</b> deste objeto. Isso garante que o código fique mais reduzido e organizado.


## A má prática Statement

Pode-se usar no lugar de PreparedStatement a classe <i>Statement</i> que executa a cláusula SQL diretamente no método execute().
```java
Statement stmt = con.createStatement();
stmt.execute("INSERT INTO ...");
stmt.close();
```
Mas a classe PreparedStatement é mais indicada, pois deixa o código mais limpo e será preciso fazer muitas concatenações para os valores do comando SQL.

## Javabeans

É a representação da entidade no banco através de classes que possuem o contrutor sem argumentos e possuem métodos do tipo <b>get</b> e <b>set</b> para cada um de seus parâmetros. Usa-se Javabeans em qualquer situação que precisa-se representar o modelo de dados.

Neste projeto o Javabean utilizado é a classe <b>Contato</b>
```java
public class Contato {
	
	private Long id;
	private String nome;
	private String email;
	private String endereco;
	private Calendar dataNascimento;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEndereco(){
		return endereco;
	}
	
	public void setEndereco(String endereco){
		this.endereco = endereco;
	}
	
	public Calendar getDataNascimento(){
		return dataNascimento;
	}
	
	public void setDataNascimento(Calendar dataNascimento){
		this.dataNascimento = dataNascimento;
	}
}
```

## DAO - Data Access Object

A ideia de usar o padrão de projeto DAO é remover o código de acesso ao banco de dados de suas classes de lógica e colocá-lo em uma classe responsável pelo acesso aos dados. Desta forma a manutenção do código fica mais fácil.

Além do mais, pode-se utilizar um único método que será responsável pela inclusão dos dados no banco.
```java
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
```
Através de uma única classe será possível acessar ao banco de dados e somente através dessa classe será possível acessar os dados. Este padrão permite a implementação de uma camada adicional de segurança aos dados do BD.

Conforme visto no snippet acima, precisa-se usar uma classe adicional chamada <b>ContatoDAO</b>que contenha o método adicionar().
```java
public class ContatoDAO{
	private Connection conex;
	
	public ContatoDAO(){
		//Conecta-se ao banco ao se construir sua instância
		this.conex = new ConnectionFactory().getConnection();
	}
	
	public void adicionar(Contato contato) throws SQLException{
		String sql = "INSERT INTO contatos"+
			     "(nome, email,endereco,dataNascimento)"+
			     "VALUE(?,?,?,?)";
		try{
			//Preparando Statment para insersão.
			PreparedStatement stmt = conex.prepareStatement(sql);

			//Setando valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));

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
```

## INSERT via DAO

Podemos realizar uma inserção de dados através da classe <b>TestaInsereDAO</b>.
```java
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
```

## Consultas ao banco

Para consultas ao banco utilizaremos a interface PreparedStatement, onde adicionamos nosso comando SQL.

Diferente de um INSERT, na consulta temos um retorno. Usamos o método <b>executeQuery</b> para retornar todos os registros de uma determianda query.
```java
//Pega a conexão e o Statement
Connection con = new ConnectionFactory().getConnection();
String sql = "SELECT * FROM contatos";
PreparedStatement stmt = con.prepareStatement(sql);

//Executa um select
ResultSet rs = stmt.executeQuery();
```

O objeto retornado é do tipo <b>ResultSet</b>, onde podemos navegar por seus registros através do método next().
```java
while(rs.next()){
	String nome = rs.getString("nome");
	String email = rs.getString("email");
	String end = rs.getString("endereco");
	Date data = rs.getDate("dataNascimento");
	
	System.out.println(nome+" :: "+email+" :: "+end+" :: "+data);

}
```
Esse método retorna false quando chega ao fim dos registros, sendo ideal para fazer laços nos registros.

## Recurso avançado: o cursor

Assim como no cursor de um banco de dados, só é possível mover para o próximo registro. Para fazer uma leitura para trás, é preciso especificar na abertura do ResultSet que o cursor será utilizado.

## Material de apoio

Este projeto para estudo do JDBC foi realizado com base na prática e teoria abordado nas apostila [Java para Desenvolvimento Web](https://www.caelum.com.br/apostila-java-web/), da Caelum. Esta apostila gratúita é distribuido pela Caelum e faz parte do curso FJ-21.
