package personagens;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import itens.*;

// Necessário traduzir os textos dessa classe

/**
 * Classe Heroi
 * Esta classe é responsavel por modelar o Heroi do jogo, que pode ser Barbaro, Guerreiro ou Mago
 * @author Felipe e Maxwell
 */
public class Heroi extends Personagem {
	private int lifeMaximum;
	private int weightLimit;
	private int continues = 3;
	private int potions;	// Controle da quantidade de Potion
	private int foods;	 // Controle quantidade food
	private Map<String, Item> mochila;
	private Permanent equippedWeapon;
	private Permanent equippedArmor;
	private Permanent equippedShield;
	private Permanent equippedJewelry1;
	private Permanent equippedJewelry2;
	
	/**
	 * Construtor da classe Heroi, onde é invocado o método setAttributes da classe mãe(Personagem)
	 * de acordo com o heroi escolhida na tela de seleção.
	 * @param name. String com o nome do Heroi informado pelo usuário.
	 * @param optionPersonagem. O tipo do Heroi que o usuario escolheu.
	 */
	public Heroi(String name,int optionPersonagem) {
		super(name, 0, 0, 0, 0, 0);
		this.mochila = new HashMap();
		switch (optionPersonagem) {
		case 1:
			super.setAttributes(15, 13, 0, 15);
			this.weightLimit = 100;
			this.lifeMaximum = 15;
			//==INSERE ITEM INICIAL NO INVENTARIO DO PERSONAGEM E EQUIPA==
			Permanent initialAxe = new Permanent("Small-Axe","Small-Axe(Damage:6)", 10, 6, 0, 0, 0); // A descrição aparece no inventario
			this.setEquippedWeapon(initialAxe);
			break;
			
		case 2:
			super.setAttributes(13, 18, 0, 18);
			this.weightLimit = 75;
			this.lifeMaximum = 18;
			//==INSERE ITEM INICIAL NO INVENTARIO DO PERSONAGEM E EQUIPA==
			Permanent initialSword = new Permanent("Short-Sword","Short-Sword(Damage:4)", 10, 4, 0, 0, 0); // A descrição aparece no inventario
			this.setEquippedWeapon(initialSword);
			break;
			
		case 3:
			super.setAttributes(20, 9, 0, 10);
			this.weightLimit = 50;
			this.lifeMaximum = 9;
			//==INSERE ITEM INICIAL NO INVENTARIO DO PERSONAGEM E EQUIPA==
			Permanent initialStaff = new Permanent("Firebolt-Staff","Firebolt-Staff(Damage:8)", 10, 8, 0, 0, 0); // A descrição aparece no inventario
			this.setEquippedWeapon(initialStaff);
			break;
		}
		
	}

	/**
	 * Método para obter o peso total dos itens no inventário
	 * @return pesoTotal. Retorna um inteiro com o peso total dos itens no inventario do heroi
	 */
	private int calculateWeight() {
		int pesoTotal = 0;
		for(Item item : this.mochila.values()) {
			pesoTotal += item.getWeight();
		}
		if(this.getGold()>1000){
			pesoTotal+= (this.getGold()/1000)+1; //Calcula o peso do ouro acima de 1000 moedas
		}
		else{
			pesoTotal+=1; // Adiciona uma unidade de peso pois não passou de 1000 moedas ainda
		}
		for(int i=0; i<foods-1; i++){
			pesoTotal+=this.getItem("Food").getWeight();
		}
		for(int i=0; i<potions-1; i++){
			pesoTotal+=this.getItem("Potion").getWeight();
		}
		return pesoTotal;
	}
	
	/**
	 * Método que adiciona um item ao inventario do Heroi
	 * @param item. Um objeto do tipo item é passado por parametro para ser adicionado ao inventario
         * @return true. Se o item foi adicionado a mochila do Heroi
         * @return false. Se o item não foi adicionado a mochila
	 */
	public boolean insertItem(Item item) { // Alterei este método para retornar um booleano caso possa inserir o item ou não
		if (this.calculateWeight() + item.getWeight() <= this.weightLimit) {
			this.mochila.put(item.getName(), item);
			if(item instanceof Consumable){
				if(item.getName()=="Potion"){
					this.potions++;
				}else{
					this.foods++;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para remover um item do inventario do heroi através do nome do item.
	 * @param nome. O nome do item deve ser passado por parametro.
	 * @return item. Retorna o item removido do inventario.
	 */
	public Item removeItem(String nome) {
		Item item = this.mochila.get(nome);
		if (item != null){
			if(item instanceof Consumable){
				if(item.getName()=="Potion"){
					if(this.potions>1)
						this.potions--;
					else{
						this.potions--;
						this.mochila.remove(nome);
					}
				}else{
					if(this.foods>1)
						this.foods--;
					else{
						this.foods--;
						this.mochila.remove(nome);
					}
				}
			}else
				this.mochila.remove(nome);
		}
		else
			System.out.println("\n# O item '" + nome + "' nao esta na mochila do heroi!\n");
		return item;
	}
	
	/**
	 * Método que verifica se um item está no inventario do heroi através do nome
	 * do item passado por parametro.
	 * @param nome. O nome do item deve ser passado por parametro
	 * @return boolean. Retorna um boolean com true se o item estiver no inventario ou false caso contrario
	 */
	public boolean testItem(String nome){
		if(this.mochila.get(nome) != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Método que alimenta o heroi aumentando 2 pontos de life
	 */
	public void feed() {
		increase();
		increase();
	}
	
	/**
	 * Método que exibe as informações sobre o Heroi no terminal
	 */
	public void printOut() {
		System.out.println("======================");
		System.out.println("Data Hero:\n");
		super.printOut();
	}
	
	/**
	 * Método de acesso ao maximo de vida do Heroi
	 * @return this.lifeMaximum. Retorna um int com o limite maximo de life do heroi
	 */
	public int getMaximumLife() {
		if(getEquippedJewelry1()!=null){
			if(getEquippedJewelry2()!=null)
				return this.lifeMaximum + getEquippedJewelry1().getLifeBonus()+getEquippedJewelry2().getLifeBonus();
			else
				return this.lifeMaximum + getEquippedJewelry1().getLifeBonus();
		}else
			return this.lifeMaximum;
	}
	
	/**
	 * Método de acesso a um item do inventario através do seu nome
	 * @param nome. String que deve conter o nome do item buscado
	 * @return item. Retorna o item correspondente ao nome passado por parametro
	 */
	public Item getItem(String nome){
    	return this.mochila.get(nome);
    }
	
	/**
	 * Método que exibe os itens contidos no inventario do Heroi
	 * @return returnString. Retorna uma String contendo por exemplo: "Inventory: item1 item2"
	 */
	public String getInventoryString(){ // Criei esse metodo que exibe a mochila do personagem		
		// OBS: Modifiquei esse método para ter funcionamento igual aos outros comandos, exemplo: look
		String returnString = "\n====================================================\n"+"Inventory:";
		Set<String> keys = this.mochila.keySet();
		for(String nome : keys) {
			if(this.getItem(nome) instanceof Consumable){
				if(this.getItem(nome).getName()=="Potion"){
					returnString += "\n\t"+this.getItem(nome).getDescription()+" ("+this.potions+")";
				}else{
					returnString += "\n\t"+this.getItem(nome).getDescription()+" ("+this.foods+")";
				}
			}else
				returnString += "\n\t" + this.getItem(nome).getDescription();
		}
		returnString += "\nGold: \n\t"+getGold()+"\n";
		returnString += "\nWeight: \n\t"+calculateWeight()+" / "+this.weightLimit+"\n";
		returnString += this.getEquippedItens()+"\n";
		return returnString+"====================================================\n";
		
	}
	
	/**
	 * Método de acesso aos itens equipados no Heroi
	 * @return returnString retorna uma string contendo todos itens equipados no Heroi
	 * ex: Equipped Itens:
	 * 					Weapon: None
	 * 					Armor: Tallum(Armor: 20)	
	 */
	public String getEquippedItens(){//INFORMA OS ITENS QUE ESTAO EQUIPADOS COM O HEROI
		// modifiquei este método para ter funcionamento semelhante aos demais gets
		String returnString = "\nEquipped Itens:";
		if (this.equippedWeapon==null){
			returnString += "\n\tWeapon: None";
		}else{
			returnString += "\n\tWeapon: "+this.equippedWeapon.getDescription();
		}
		if (this.equippedArmor==null){
			returnString += "\n\tArmor: None";
		}else{
			returnString += "\n\tArmor: "+this.equippedArmor.getDescription();
		}
		if (this.equippedShield==null){
			returnString += "\n\tShield: None";
		}else{
			returnString += "\n\tShield: "+this.equippedShield.getDescription();
		}
		if (this.equippedJewelry1==null){
			returnString += "\n\tJewelry1: None";
		}else{
			returnString += "\n\tJewelry1: "+this.equippedJewelry1.getDescription();
		}
		if (this.equippedJewelry2==null){
			returnString += "\n\tJewelry2: None";
		}else{
			returnString += "\n\tJewelry2: "+this.equippedJewelry2.getDescription();
		}
		return returnString;
	}
	
	/**
	 * Método de acesso aos Continues disponiveis do Heroi
	 * @return this.continues retorna um inteiro com a quantidade de continues que o Heroi possui
	 */
	public int getContinues(){
		return this.continues;
	}
	
	/**
	 * Método que decrementa a quantidade de continues do heroi
	 */
	public void decreaseContinues(){
		this.continues--;
	}
	
	/**
	 * Método de acesso aos atributos do heroi
	 * @return returnString retorna uma string com os atributos do Heroi
	 * EX: Character:
	 * 				Name: Teste
	 * 				Life: 8/20
	 * 				Damage: 8
	 */
	public String getCharacter(){
		String returnString = "\n====================================================\n"+"Character:";
		returnString += "\n\tName: "+this.getName();
		returnString += "\n\tLife: "+this.getLife()+" / "+this.getMaximumLife();
		returnString += "\n\tDamage: "+this.getDamage();
		returnString += "\n\tDefense: "+this.getDefense();
		returnString += "\n\tArmor: "+this.getArmor();
		returnString += "\n\tContinues: "+this.getContinues();
		returnString += "\n"+this.getEquippedItens()+"\n";
		return returnString+"====================================================\n";
	}
	
	
	/**
	 * Método de acesso ao Damage(Dano) de ataque da arma utilizada pelo Heroi
	 * @return 1 se não possuir arma equipada
	 * @return int retorna um inteiro com o ataque da arma equipada
	 */
	public int getDamage(){
		if (this.equippedWeapon==null){
			return 1; //ISSO FAZ COM QUE O HEROI DE APENAS 1 DE DANO SE NAO HOUVER ARMA EQUIPADA
		} else{
			return this.equippedWeapon.getAtkBonus();
		}
	}
	
	/**
	 * Método de acesso a Armor(Armadura) utilizada pelo Heroi
	 * @return int se não tiver armadura equipada retorna um inteiro com valor da armadura default
	 * @return int se armadura estiver equipada retorna um inteiro com valor
	 * da armadura equipada + armadura default da classe do personagem
	 */
	public int getArmor(){
		if (this.equippedArmor==null){
			return super.getArmor();
		} else{
			return (super.getArmor()+this.equippedArmor.getArmBonus());
		}
	}
	
	/**
	 * Método de acesso a Defesa do Heroi, leva em consideração Defesa do heroi + o escudo equipado(caso tenha algum). 
	 * @return int se não tiver escudo equipado retorna none
	 * @return int retorna um inteiro equivalente a defesa se tiver um escudo equipado
	 */
	public int getDefense(){
		if (this.equippedShield==null){
			return super.getDefense();
		} else{
			return (super.getDefense()+this.equippedShield.getDefBonus());
		}
	}
	
	/**
	 * Método de acesso a arma equipada
	 * @return Permanent retorna um item permanente que é a arma equipada
	 */
	public Permanent getEquippedWeapon() {
		return this.equippedWeapon;
	}

	/**
	 * Método modificador da arma equipada
	 * @param equippedSword recebe um item permanente que deve ser a arma para equipar
	 */
	public void setEquippedWeapon(Permanent equippedSword) {
		if(this.equippedWeapon != null){ // Se já tiver um item equipado então coloca ele no inventario
			this.insertItem(this.getEquippedWeapon());
		}
		this.equippedWeapon = equippedSword;
	}

	/**
	 * Método de acesso a armadura equipada
	 * @return equippedArmor retorna a armadura equipada
	 */
	public Permanent getEquippedArmor() {
		return this.equippedArmor;
	}

	/**
	 * Método modificador da armadura equipada
	 * @param equippedArmor recebe um item Permanent com a armadura a ser equipada
	 */
	public void setEquippedArmor(Permanent equippedArmor) {
		if(this.equippedArmor!= null){ // Se já tiver um item equipado então coloca ele no inventario
			this.insertItem(this.getEquippedArmor());
		}
		this.equippedArmor = equippedArmor;
	}

	/**
	 * Método de acesso ao escudo equipado
	 * @return equippedShield retorna um item permanente com o escudo equipado
	 */
	public Permanent getEquippedShield() {
		return this.equippedShield;
	}

	/**
	 * Método modificador do escudo equipado
	 * @param equippedShield recebe um item Permanent com o escudo a ser equipado
	 */
	public void setEquippedShield(Permanent equippedShield) {
		if(this.equippedShield != null){ // Se já tiver um item equipado então coloca ele no inventario
			this.insertItem(this.getEquippedShield());
		}
		this.equippedShield = equippedShield;
	}
	
	/**
	 * Método de acesso a joia equipada no slot 1
	 * @return equippedJewelry1 retorna um item Permanent com a joia equipada no slot1
	 */
	public Permanent getEquippedJewelry1() {
		return this.equippedJewelry1;
	}
	
	/**
	 * Método de acesso a joia equipada no slot2
	 * @return equippedJewelry2 retorna um item Permanent com a joia equipada no slot2
	 */
	public Permanent getEquippedJewelry2() {
		return this.equippedJewelry2;
	}

	/**
	 * Método modificador de joias equipadas
	 * @param equippedJewelry recebe um item Permanent com a Joia a ser equipada
	 */
	public void setEquippedJewelry(Permanent equippedJewelry) {
		if(this.equippedJewelry1 != null){
			if(this.equippedJewelry2 == null){
				this.equippedJewelry2 = equippedJewelry;
			}
			else{
				if(this.equippedJewelry1.getLifeBonus() > this.equippedJewelry2.getLifeBonus()){
					this.insertItem(this.equippedJewelry2); // Se já tiver um item equipado então coloca ele no inventario
					this.equippedJewelry2 = equippedJewelry;
				}
				else{
					this.insertItem(this.equippedJewelry1); // Se já tiver um item equipado então coloca ele no inventario
					this.equippedJewelry1 = equippedJewelry;
				}
			}
		}else
			this.equippedJewelry1= equippedJewelry;
	}
	
	/**
	 * Método para incrementar em 1 continues do Heroi
	 */
	public void increaseContinue(){
		this.continues++;
	}

	
}
