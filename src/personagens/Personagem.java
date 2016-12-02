package personagens;

import java.util.Random;

/**
 * Classe Personagem
 * Esta classe é responsavel por modelar os personagens do jogo, que podem ser Heroi ou Vilão
 * @author Felipe e Maxwell
 */
public abstract class Personagem {
	private String name;  // armazena o nome do personagem
	private int life;  // armazena a vida do personagem
	private int defense; // armazena a defesa do personagem
	private int armor; //armazena a armadura do personagem
	private int damage; // armazena o dano de ataque
	private int gold; //armazena a quantidade de dinheiro do personagem
	private static Random dado = new Random();
	
	/**
	 * Construtor da classe Personagem
	 * Responsavel por adicionar os valores iniciais aos atributos da classe.
	 * @param nome. Uma String com o nome do personagem informado pelo usuario.
	 * @param energia. Um int que contém a energia(life) inicial do personagem.
	 * @param defesa. Um int que contém o valor inicial de defesa do personagem.
         * @param armor. Um int que contém o valor inicial da armadura do personagem.
	 * @param dano. Um int que contém o valor inicial de poder de ataque do personagem.
	 * @param gold. Um inteiro com a quantidade de moedas que o personagem carrega.
	 */
	public Personagem(String nome, int energia, int defesa, int armor, int dano, int gold) {
		this.name = nome;
		this.life = energia;
		this.defense = defesa;
		this.armor = armor;
		this.damage = dano;
		this.gold = gold;
	}
	
	/**
	 * Método de acesso ao nome do personagem
	 * @return name. Retorna um String contendo o nome do personagem
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Método de acesso a vida(life) do personagem
	 * @return life. Retorna um inteiro contendo a life do personagem
	 */
	public int getLife() {
		return this.life;
	}
	
	/**
	 * Método de acesso a defesa do personagem
	 * @return defense. Retorna um inteiro contendo a defesa do personagem
	 */
	public int getDefense() {
		return this.defense;
	}
	
	/**
	 * Método de acesso a armadura do personagem
	 * @return armor. Retorna um inteiro contendo a armadura do personagem
	 */
	public int getArmor() {
		return this.armor;
	}
	
	/**
	 * Método de acesso ao dano de ataque do personagem
	 * @return damage. Retorna um inteiro contendo o ataque do personagem
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * Método modificador dos atributos da classe personagem
	 * @param dano. Um inteiro passado por parametro contendo o dano de ataque do personagem.
	 * @param defesa. Um inteiro passado por parametro contendo a defesa do personagem.
	 * @param armor. Um inteiro passado por parametro contendo a armadura default do personagem.
	 * @param energia. Um inteiro passado por parametro contendo a energia(life) do personagem.
	 */
	public void setAttributes(int dano, int defesa, int armor, int energia){
		this.life = energia;
		this.defense = defesa;
		this.armor = armor;
		this.damage = dano;
	}
		
	/**
	 * Método que realiza uma luta entre dois personagens
	 * @param oponente. Um objeto do tipo Personagem é passado por parametro
	 * Temos polimorfismo de método aqui, pois horas podemos receber o heroi por parametro
	 * e horas podemos receber um vilão por parametro.
	 */
	public void fight(Personagem oponente) {
		int dado;
		
		if(this instanceof Chefe){
			dado = getLuck(25);
		}else
			dado = getLuck(20);
		
		if (dado > oponente.getDefense()) {
			System.out.println("Successfully Attack!!\n");
			oponente.decrease(this.getDamage()-oponente.getArmor());
		}else  {
			System.out.println("Attack Missed!!\n");
		}
	}
	
	/**
	 * Método para verificar se o personagem está morto
	 * @return boolean. Se o personagem estiver morto retorna true e se estiver vivo retorna false.
	 */
	public boolean isDead() {
		if (this.life <= 0)
			return true;
		else
		    return false;
	}
	
	/**
	 * Método abstrato que deve ser implementa nas classes que extenderem esta classe 
	 * @return int. Retorna um inteiro contendo o valor maximo de vida do personagem
	 */
	public abstract int getMaximumLife();
	
	/**
	 * Método que adiciona um ponto de vida(life) ao personagem sempre que invocado
	 * e se o personagem já não atingiu o limite maximo de vida.
	 */
	public void increase() {
		if (this.life < getMaximumLife())
			this.life++;
	}
	
	/**
	 * Método que decrementa os pontos de vida(life) do personagem 
	 * com base no inteiro passado por parametro
	 * @param dano. Inteiro passado por parametro que possui o valor 
	 * que deve ser retirado da vida do personagem.
	 */
	public void decrease(int dano) {
		if(this.getArmor()>=dano){
			System.out.println("The Attack Stopped on enemy armor!!");
		} else {
			dano-=this.getArmor();//realiza o "desconto" da armadura no dano do personagem
			if (this.life-dano > 0){
				this.life-=dano;
			}else{
				System.out.println("\n# " + this.name + " is dead!\n");
				this.life=0;
			}
			
		}
		
		
		
	}
	
	/**
	 * Método de acesso ao valor tirado no dado. Utilizado para as lutas.
	 * @param valorMaximo. Um inteiro passado por parametro que define o valor maximo do range de valores
	 * @return int. Retorna um inteiro contendo o valor tirado no dado.
	 */
	public int getLuck(int valorMaximo) {
		return dado.nextInt(valorMaximo) + 1;
	}
	
	/**
	 * Método de acesso as moedas que o personagem carrega
	 * @return gold. Retorna a quantidade de moedas que o personagem carrega
	 */
	public int getGold(){
		return this.gold;
	}
	
	/**
	 * Método para adicionar dinheiro para o personagem
	 * @param quantia. Recebe a quantidade de dinheiro
	 */
	public void addGold(int quantia){
		if(quantia > 0)
			this.gold+=quantia;
	}
	
	/**
	 * Método para retirar uma quantia de dinheiro do personagem
	 * @param quantia. Recebe a quantidade de dinheiro
	 * @return true se quantia foi retirada com sucesso.
	 */
	public boolean removeGold(int quantia){
		if(quantia > 0){
			if(this.gold >= quantia){
				this.gold-=quantia;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método para exibir na tela os dados do personagem
	 */
 	public void printOut() {
		System.out.println("\tName: " + this.name);
		System.out.println("\tLife: " + this.life+" / "+this.getMaximumLife());
		System.out.println("======================");
	}

	
}
