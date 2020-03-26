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
 
O driver utilizado neste projeto é o <i>mysql-connector-java-8.0.17</i>.
Para fazer o download desta versão ou de uma mais recente, pode-se visitar o site do [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

O driver deve ser adicionado ao projeto por exportação de arquivos externos do tipo `.jar`. No Eclipse, que foi a IDE utilizada neste projeto, o caminho utilizado foi 
 
 ## O projeto
 
 Este projeto Java foi desenvolvido usando o Eclipse
 
 
 
 
