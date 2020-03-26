# Java Web - JDBC

Estudo do JDBC tendo como base a apostila Java para Desenvolvimento Web da Caelum, usando o design patterns DAO (Data Access Object), uma fábrica	de	conexões ao BD MySQL e o Connector/J (mysql connector java).

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

`Clique direito sobre o projeto > Build Path > Add	to Build Path`
 
 ## Banco MySQL
 
O banco MySQL que usaremos se chamará <b>bd_jdbc</b> e para criá-lo usamos:

```mysql
CREATE DATABASE bd_jdbc;
```

## Fábrica de conexões

A classe ConnectionFactory é usada para implementar a fábrica, onde o objeto Connection tem sua construção encapsulada. Desta forma, além de evitar repetição de código para cada nova conexão, concentra em uma única classe os parametos que precisam ser alterados.

```java
public Connection getConnection(){
  try{
    return DriverManager.getConnection("jdbc:mysql://localhost/bd_jdbc","root","root");
  }catch(SQLException e){
    throw new RuntimeException(e);
  }
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
````
As interrogações representam os dados que desejamos inserir. Este código SQL é chamado de <b>statement</b>. As cláusulas são executadas em um BD através da interface <b>PreparedStatement</b>. Basta chamar o método <b>prepareStatement</b>, passando como argumento o comando SQL com os valores vindos de variáveis

  * As clÃ¡usulas sÃ£o executadas em um banco de dados atravÃ©s da interface 
	 * PreparedStatement. Para receber um PreparedStatement relativo Ã  conexÃ£o,
	 * basta chamar o mÃ©todo prepareStatement, passando como argumento o comando
	 * SQL com os valores vindos de variÃ¡veis preenchidos com uma interrogaÃ§Ã£o.
	 * 
	 * Logo em seguida, chamamos o mÃ©todo setString do PreparedStatement para 
	 * preencher os valores que sÃ£o do tipo String, passando a posiÃ§Ã£o (comeÃ§ando
	 * em Ã•) da interrogaÃ§Ã£o no SQL e o valor que deve ser colocado:
	 * 
	 * Precisamos definir tambÃ©m a data de nascimento do nosso contato, para isso,
	 * precisaremos de um objeto do tipo java.sql.Date para passarmos para o nosso
	 * PreparedStatement. Nesse exemplo, vamos passar a data atual. Para isso, 
	 * vamos passar um long que representa os milissegundos da data atual para 
	 * dentro de um java.sql.Date que Ã© o tipo suportado pela API JDBC. Vamos 
	 * utilizar a classe Calendar para conseguirmos esses milissegundos:
	 * 
	 * java.sql.Date dataParaGravar = new java.sql.Date(
Calendar.getInstance().getTimeInMillis());
stmt.setDate(4, dataParaGravar);
	 * 
	 * Fechando conexÃ£o apropriadamente
	 * 
	 * NÃ£o Ã© comumutilizar JDBCdiretamente hoje em dia. O mais praticado Ã© o uso 
	 * de alguma API deORMcomo a JPA ou o Hibernate. Tanto na JDBC quanto em 
	 * bibliotecas ORM deve-se prestar atenÃ§Ã£o no momento de fechar a conexÃ£o.
	 * O comum Ã© fechar a conexÃ£o em um bloco finally:
	 * 
	 * 
	 * Dessa forma,mesmo que o cÃ³digo dentro do try lance exception, o con.close()
	 * serÃ¡ executado. Garantimos que nÃ£o deixaremos uma conexÃ£o pendurada sem uso. 
	 * Esse cÃ³digo pode Â�car muito maior se quisermos ir alÃ©m. Pois o que acontece 
	 * no caso de con.close lanÃ§ar uma exception? Quem a tratarÃ¡?
	 * 
	 * Trabalhar com recursos caros, como conexÃµes, sessÃµes, threads e arquivos, 
	 * sempre deve ser muito bem pensado. Deve-se tomar cuidado para nÃ£o deixar 
	 * nenhum desses recursos abertos, pois poderÃ¡ vazar algo precioso da nossa 
	 * aplicaÃ§Ã£o. Como veremos durante o curso, Ã© importante centralizar esse tipo 
	 * de cÃ³digo em algum lugar, para nÃ£o repetir uma tarefa complicada como essa.
	 * 
	 * AlÃ©m disso, hÃ¡ a estrutura do Java ÃŸ conhecida como try-with-resources. 
	 * Ela permite declarar e inicializar,dentro do try, objetos que implementam 
	 * AutoCloseable. Dessa forma, ao tÃ©rmino do try, o prÃ³prio compilador inserirÃ¡ 
	 * instruÃ§Ãµes para invocar o close desses recursos, alÃ©m de se precaver em 
	 * relaÃ§Ã£o a exceÃ§Ãµes que podem surgir por causa dessa invocaÃ§Ã£o. Nosso cÃ³digo 
	 * ficaria mais reduzido e organizado, alÃ©m do escopo de con sÃ³ valer dentro 
	 * do try.
	 * 
	 * A mÃ¡ pratica Statement
	 * 
	 * Em vez de usar o PreparedStatement, vocÃª pode usar uma interface mais simples 
	 * chamada Statement, que simplesmente executa uma clÃ¡usula SQL no mÃ©todo 
	 * execute:
	 * 
	 * Statement stmt = con.createStatement();
stmt.execute("INSERT INTO ...");
stmt.close();
	 * 
	 * Mas prefira a classe PreparedStatement que Ã© mais rÃ¡pida que Statement e deixa 
	 * seu cÃ³digo muito mais limpo. Geralmente, seus comandos SQL conterÃ£o valores 
	 * vindos de variÃ¡veis do programa Java; usando Statements,vocÃª terÃ¡ que fazer 
	 * muitas concatenaÃ§Ãµes, mas usando PreparedStatements, isso Â�ca mais limpo e 
	 * fÃ¡cil.
	 * 
	 * 
	 * 
	 * 
	 */










