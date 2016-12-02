package src.wozuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import personagens.Personagem;
import itens.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;       // stores exits of this room.
    private HashMap<String, Personagem> personagens;
    private HashMap<String, Item> itens;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.personagens = new HashMap<String, Personagem>();
        this.itens = new HashMap<String,Item>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
    	
        return "====================================================\n"
        	   +"You are " + description + ".\n"
               + getExitString() + "\n"
               + getItensString() + "\n"
               + getPersonagensString() + "\n"
               + "====================================================\n";
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = this.exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Returns a string with the bad guys in the room, for example
     * "Villains: Skeleton1 Skeleton2".
     * @return Room villains.
     */
    private String getPersonagensString(){
        String returnString = "Villains:";
        Set<String> keys = this.personagens.keySet();
        for(String nome : keys) {
            returnString += "\n\t" + nome;
        }
        return returnString;
    }

    /**
     * Return items in the room, for example
     * "Itens: Potion".
     * @return Room itens.
     */
    private String getItensString(){
    	String returnString = "Itens:";
    	Set<String> keys = this.itens.keySet();
    	for(String nome : keys){
    		returnString += "\n\t"+nome;
    	}
    	return returnString;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return this.exits.get(direction);
    }
    
    /**
     * Método que retorna um personagem através da sua busca pelo nome passado por parametro
     * @param nome utilizado para buscar o personagem do nome correspondente
     * @return Personagem. Retorna Personagem com nome correspondente passado por parametro
     */
    public Personagem getPersonagem(String nome) {
    	return this.personagens.get(nome);
    }
    
    /**
     * Método que retorna um item através de sua busca pelo nome passado por parametro
     * @param nome. Nome do item buscado
     * @return Item. Retorna o item correspondente com o nome passado por parametro
     */
    public Item getItem(String nome){
    	return this.itens.get(nome);
    }
    
    /**
     * Método para inserir personagens na sala
     * @param personagem. Personagem passado por parametro é adicionado na sala
     */
    public void insertPersonagem(Personagem personagem) {
    	this.personagens.put(personagem.getName(), personagem);
    }
    
    /**
     * Método que remove o personagem passado por parametro da sala
     * @param personagem. Personagem passado por parametro é removido da sala
     */
    public void removePersonagem(Personagem personagem){
    	this.personagens.remove(personagem.getName(),personagem);
    }
    
    /**
     * Método que adiciona o item passado por parametro na sala
     * @param item. Item passado por parametro é adicionado a sala.
     */
    public void insertItem(Item item){
    	this.itens.put(item.getName(), item);
    }
    
    /**
     * Método que remove o item passado por parametro da sala
     * @param item. Item passado por paramtro é removido da sala.
     */
    public void removeItem(Item item){
    	this.itens.remove(item.getName(), item);
    }
    
    /**
     * Método que verifica se existe algum personagem na sala
     * @return true se a sala estiver vazia e false caso contrario.
     */
    public boolean emptyRoom(){
    	if (this.personagens.isEmpty())
    		return true;
    	else
    		return false;
    }
}

