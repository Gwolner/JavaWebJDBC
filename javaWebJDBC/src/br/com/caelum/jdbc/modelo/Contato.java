package br.com.caelum.jdbc.modelo;

import java.util.Calendar;

public class Contato {
	
	/* JavaBean para representar a entidade do banco
	 * 
	 * O que são Javabeans? A pergunta que não quer se calar pode ser
	 *  respondida muito facilmente uma vez que a uma das maiores 
	 *  confusões feitas aí fora é entre Javabeans e Enterprise Java Beans
	 *  (EJB). 
	 *  
	 *  Javabeans são classes que possuem o construtor sem argumentos e com 
	 *  métodos de acesso do tipo get e set! Mais nada! Simples, não? 
	 *  Já os EJBs costumam ser javabeans com características mais avançadas
	 *  
	 *  Podemos usar beans por diversos motivos, normalmente as classes que 
	 *  representam nosso modelo de dados são usados dessa forma.
	 *  
	 *  Usaremos:
	 *  
	 *  uma classe com métodos do tipo get e set para cada um de seus 
	 *  parâmetros, que representa algum objeto;
	 *  
	 *  uma classe com construtor sem argumentos que representa uma coleção 
	 *  de objetos.  
	 *  
	 */
	private Long id;
	private String nome;
	private String email;
	private String endereco;
	private Calendar dataNascimento;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
}
