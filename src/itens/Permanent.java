package src.itens;

/**
 * Classe que extende Item e que é responsavel por modelar itens permanentes que aumentam atributos do Heroi
 * @author felipe e Maxwell
 */
public class Permanent extends Item {
	private int atkBonus;
	private int armBonus;
	private int defBonus;
	private int lifeBonus;
	
	/**
	 * Construtor da classe Permanent com 7 parametros
	 * @param nome recebe uma string com o nome do item
	 * @param descricao recebe uma string com um descrição do item
	 * @param peso recebe um inteiro com o peso do item
	 * @param atkBonus recebe um inteiro com o adicional de ataque que o item gera
	 * @param defBonus recebe um inteiro com o adicional de defesa que o item gera
	 * @param armBonus recebe um inteiro com o adicional de armadura que o item gera
	 * @param lifeBonus recebe um inteiro com o adicional de vida Maxima que o item gera
	 */
	public Permanent (String nome, String descricao, int peso, int atkBonus, int defBonus,int armBonus, int lifeBonus) {
		super(nome,descricao,peso);
		this.atkBonus=atkBonus;
		this.defBonus=defBonus;
		this.lifeBonus=lifeBonus;
		this.armBonus= armBonus;
	}
	
	/**
	 * Construtor da classe Permanent com 8 parametros(sobrecarga)
	 * @param nome recebe uma string com o nome do item
	 * @param descricao recebe uma string com um descrição do item
	 * @param peso recebe um inteiro com o peso do item
	 * @param atkBonus recebe um inteiro com o adicional de ataque que o item gera
	 * @param defBonus recebe um inteiro com o adicional de defesa que o item gera
	 * @param armBonus recebe um inteiro com o adicional de armadura que o item gera
	 * @param lifeBonus recebe um inteiro com o adicional de vida Maxima que o item gera
	 * @param preco recebe um inteito com o preço do item
	 */
	public Permanent (String nome, String descricao, int peso, int atkBonus, int defBonus,int armBonus, int lifeBonus,int preco) {
		super(nome,descricao,peso,preco);
		this.atkBonus=atkBonus;
		this.defBonus=defBonus;
		this.lifeBonus=lifeBonus;
		this.armBonus= armBonus;
		
	}
	
	/**
	 * Método de acesso a Defesa Bonus do item
	 * @return this.defBonus retorna um inteiro com adicional de defesa do item
	 */
	public int getDefBonus(){
		return this.defBonus;
	}
	
	/**
	 * Método de acesso ao Ataque Bonus do item
	 * @return this.atkBonus retorna um inteiro com adicional de ataque do item
	 */
	public int getAtkBonus(){
		return this.atkBonus;
	}
	
	/**
	 * Método de acesso ao Bonus de Vida do item
	 * @return this.lifeBonus retorna um inteiro com adicional de vida maxima do item
	 */
	public int getLifeBonus(){
		return this.lifeBonus;
	}
	
	/**
	 * Método de acesso ao Bonus de Armadura do item
	 * @return this.armBonus retorna um inteiro com o adicional de armadura do item
	 */
	public int getArmBonus() {
		return this.armBonus;
	}
	
}
