package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

/* ConnectionFactory implementa o design pattern Factory que 
 * prega o encapsulamento da construção (fabricação) de objetos 
 * complicados. Assim, além de evitarmos repetição de código 
 * sempre que precisarmos de uma conexão, se tivermos de alterar 
 * alguma parametro basta recorrer a uma única classe.
 * 
 * 
 * Neste caso, ela é responsável por criar uma conexão (Connection)
 * com o BD MySQL. Esta clsse não é static não se deve usar a mesma
 * conexão apra várias finalidades por qeustão de segurnaça.
 * 
 * Na prática,um sistema que usa JDBC deve ter um Poll de conexões 
 * para disponibilizar e limitar o numero de conexões ao BD.
 */
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/bd_jdbc","root","root");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}		
	}
}
