package src.itens;

/**
 * Classe Item
 * Esta classe é responsavel por modelar itens do jogo, como consumiveis e permanentes
 * @author felipe e Maxwell
 */
public class Item {
	private String name;
	private String description;
	private int weight;
	private int price;
	
	/**
	 * Construtor da classe item com 3 parametros
	 * @param nome String com o nome do item
	 * @param descricao Uma string com uma breve descrição do objeto
	 * @param peso Recebe o valor do peso do item
	 */
	public Item(String nome, String descricao, int peso) {
		this.name = nome;
		this.description = descricao;
		this.weight = peso;
	}
	
	/**
	 * Construtor da classe item com 4 parametros(sobrecarga)
	 * @param nome string com o nome do item
	 * @param descricao uma string com uma breve descrição do objeto
	 * @param peso recebe o valor do peso do item
	 * @param preco recebe um inteiro com o preco do item
	 */
	public Item(String nome, String descricao, int peso, int preco){
		this.name = nome;
		this.description = descricao;
		this.weight = peso;
		this.price = preco;
	}
	
	/**
	 * Método de acesso ao nome do item
	 * @return this.name Retorna uma String com o nome do item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Método de acesso da descrição do item
	 * @return this.description Retorna uma String com a descrição do item 
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Método de acesso para o peso do item
	 * @return this.weight Retorna um inteiro com o peso do item
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Método de acesso ao preco do item
	 * @return this.price retorna um inteiro com o preco do item
	 */
	public int getPrice(){
		return this.price;
	}
}
