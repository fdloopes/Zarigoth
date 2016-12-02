package personagens;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import itens.Item;

/**
 * Classe Chefe
 * Esta classe é responsavel por modelar vilões chefes 
 * @author felipe e Maxwell
 */
public class Chefe extends Vilao {
	private Map<String, Item> loot;
	
	/**
	 * Construtor da classe Chefe
	 * @param nome recebe uma string com o nome do Chefe
	 * @param energia recebe um inteiro com o valor da vida do Chefe
	 * @param defesa recebe um inteiro com o valor da defesa do Chefe
	 * @param armor recebe um inteiro com o valor bonus de armadura do Chefe
	 * @param dano recebe um inteiro com o valor de ataque do Chefe
	 * @param gold recebe um inteiro com a quantia de dinheiro que o chefe carrega
	 */
	public Chefe(String nome, int energia, int defesa,int armor, int dano, int gold) {
		super(nome, energia, defesa, armor, dano,gold);
		this.loot = new HashMap();
	}
	
	/**
	 * Método que adiciona um item ao inventario do Heroi
	 * @param item. Um objeto do tipo item é passado por parametro para ser adicionado ao inventario
	 */
	public void insertItem(Item item) { // Alterei este método para retornar um booleano caso possa inserir o item ou não
		this.loot.put(item.getName(), item);
	}
	
	/**
	 * Método para remover um item do inventario do heroi através do nome do item.
	 * @param nome. O nome do item deve ser passado por parametro.
	 * @return item. Retorna o item removido do inventario.
	 */
	public Item removeItem(String nome) {
		Item item = this.loot.get(nome);
		if (item != null)
			this.loot.remove(nome);
		else
			System.out.println("\n# O item '" + nome + "' nao esta na mochila do heroi!\n");
		return item;
	}
	
	/**
	 * Método de acesso a um item do inventario através do seu nome
	 * @param nome. String que deve conter o nome do item buscado
	 * @return item. Retorna o item correspondente ao nome passado por parametro
	 */
	public Item getItem(String nome){
    	return this.loot.get(nome);
    }
	
	/**
	 * Método de acesso a um item dropado aleatoriamente do inventario do Vilão Chefe
	 * @return item retorna o item a ser dropado do inventario do Vilão Chefe
	 */
	public Item getDrop(){
		int dado = getLuck(loot.size());
		int contador = 1;
		Set<String> keys = this.loot.keySet();
		for(String nome : keys) {
			if(contador == dado){
				return this.loot.get(nome);
			}
			contador++;
		}
		return getItem("War-Axe");
	}
	
}
