package personagens;

import itens.*;

/**
* Classe Vilão
* Esta classe é responsavel por modelar os vilões do jogo, que podem ser Chefe ou comum
* @author Felipe e Maxwell
*/
public class Vilao extends Personagem {
	private int lifeMaximum;	

	/**
	 * Construtor da classe vilão
	 * Responsavel por adicionar os valores iniciais aos atributos da classe.
	 * @param nome. Uma String com o nome do vilão
	 * @param energia. Um inteiro com o valor de vida do vilão
	 * @param defesa. Um inteiro com o valor de defesa do vilão.
         * @param armor. Um int que contém o valor inicial da armadura do vilão.
	 * @param dano. Um inteiro com o valor de ataque do vilão.
	 * @param gold. Um inteiro com a quantidade de moedas que o vilão carrega.
	 */
	public Vilao(String nome, int energia, int defesa, int armor, int dano, int gold) {
		super(nome, energia, defesa, armor, dano, gold);
		this.lifeMaximum = energia;
	}
	
	/**
	 * Método de acesso ao maximo de vida do Heroi
	 * @return lifeMaximum. Retorna um int com o limite maximo de life do heroi
	 */
	public int getMaximumLife() {
		return this.lifeMaximum;
	}
	
	/**
	 * Método que exibe as informações sobre o Vilão no terminal
	 */
	public void printOut() {
		System.out.println("======================");
		System.out.println("Data Vilao:\n");
		super.printOut();
	}
		
}
