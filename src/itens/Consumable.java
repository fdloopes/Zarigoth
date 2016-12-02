package src.itens;

/**
 * Classe que extende Item e que é responsavel por modelar itens consumiveis que aumentam a energia do Heroi
 * @author Felipe e Maxwell
 */
public class Consumable extends Item{
	/**
	 * Construtor da classe Consumable com 3 parametros
	 * @param nome recebe uma string que define o nome do Consumable
	 * @param descricao recebe uma string com uma descrição mais detalhada do consumable
	 * @param peso recebe um inteiro que define o peso do Consumable
	 */
	public Consumable (String nome, String descricao, int peso) {
		super(nome,descricao,peso);
	}
	
	/**
	 * Construtor da classe Consumable com 4 parametros (sobrecarga)
	 * @param nome recebe uma string que define o nome do Consumable
	 * @param descricao recebe uma string com uma descrição mais detalhada do consumable
	 * @param peso recebe um inteiro que define o peso do Consumable
	 * @param preco recebe um inteiro que define o preco do Consumable
	 */
	public Consumable (String nome, String descricao, int peso, int preco) {
		super(nome,descricao,peso,preco);
	}


}
