package src.wozuul;

import personagens.*;
import itens.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Heroi heroi;
    private Scanner reader = new Scanner(System.in);         // leitor do terminal
    private static Random dado = new Random();				 // Gerar valores aleatorios
    private Map<String, Item> shop;						     // Armazena itens para o shop

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {

        parser = new Parser();
        heroi = new Heroi(selectName(),selectPersonagem());        	
        shop = new HashMap();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada,p1,p2,p3,p4,p5,p6,p7,c1,c2,c3,c4,m1,m2,m3,b1;

        // create the rooms
        // Entrance
        entrada = new Room("Entrace of Dungeon & Shop");

        //Tusk Map
        p1 = new Room("Demon Tusk Room - 1");
        p2 = new Room("Demon Tusk Room - 2");
        p3 = new Room("Demon Tusk Room - 3");
        p4 = new Room("Demon Tusk Room - 4");
        p5 = new Room("Demon Tusk Room - 5");
        p6 = new Room("Demon Tusk Room - 6");
        p7 = new Room("Demon Tusk Room - 7 - Treasure Room");

        //Horn Map
        c1 = new Room("Demon Horn Room - 1");
        c2 = new Room("Demon Horn Room - 2");
        c3 = new Room("Demon Horn Room - 3");
        c4 = new Room("Demon Horn Room - 4 - Treasure Room");

        //Portal map
        m1 = new Room("Hall to Morahghul Room - 1");
        m2 = new Room("Hall to Morahghul Room - 2");
        m3 = new Room("Morahghul Room - Ritual's Portal");

        //Final Room
        b1 = new Room("Ritual's Room - Final Boss");

        // initialise room exits

        //========ENTRANCE========
        entrada.setExit("west", p1);
        entrada.setExit("south", m1);
        entrada.setExit("east", c1);
        //=======================

        //====TUSK MAP=========
        p1.setExit("north", p3);
        p1.setExit("south", p2);
        p1.setExit("east", entrada);

        p2.setExit("north", p1);
        p2.setExit("west", p5);

        p3.setExit("south", p1);
        p3.setExit("west", p4);

        p4.setExit("east", p3);
        p4.setExit("south", p6);

        p5.setExit("north", p6);
        p5.setExit("east", p2);

        p6.setExit("north", p4);
        p6.setExit("south", p5);
        p6.setExit("west", p7);

        p7.setExit("east", p6);
        p7.setExit("west", p1);
        //========================

        //=== HORN MAP ====
        c1.setExit("east", c2);
        c1.setExit("west", entrada);

        c2.setExit("east", c3);
        c2.setExit("west", c1);

        c3.setExit("west", c2);
        c3.setExit("east", c4);

        c4.setExit("west", c3);
        c4.setExit("south",c1);
        //====================

        //===== PORTAL'S MAPL====
        m1.setExit("north", entrada);
        m1.setExit("south", m2);

        m2.setExit("north", m1);
        m2.setExit("south", m3);

        m3.setExit("north", m2);
        m3.setExit("south", b1);
        //===========================

        //===== FINAL ROOM ==========
        b1.setExit("north", m2);
        //===========================

        // =========== Adicionando itens ao shop ===========
        Permanent weaponShop = new Permanent("Katana-Sword","Katana-Sword(Damage:15)", 20, 15, 0, 0, 0, 450);
        Permanent armorShop = new Permanent("Tallum-Armor","Tallum-Armor(Armor: 3)", 20, 0, 0, 3, 0, 300);
        Permanent jewelryShop = new Permanent("Baium-Ring", "Baium-Ring(+1 Life)", 10, 0, 0, 0, 1, 175);
        Permanent shieldShop = new Permanent("Bone-Shield", "Bone-Shield(Defense: 1)", 30, 0, 1, 0, 0, 200);
        Consumable potionShop = new Consumable("Potion", "Potion", 2, 20);
        Consumable foodShop = new Consumable("Food", "Food", 1, 10);
        Consumable continueShop = new Consumable("Continue-Scroll","Continue-Scroll(Continue +1)",0 ,1000);

        this.shop.put(weaponShop.getName(), weaponShop);
        this.shop.put(armorShop.getName(), armorShop);
        this.shop.put(jewelryShop.getName(),jewelryShop);
        this.shop.put(shieldShop.getName(),shieldShop);
        this.shop.put(potionShop.getName(),potionShop);
        this.shop.put(foodShop.getName(),foodShop);
        this.shop.put(continueShop.getName(),continueShop);

        //============ADICIONANDO ITENS  INICIAIS AO HEROI============

        Consumable item3 = new Consumable("Potion", "Potion", 2);
        Consumable item4 = new Consumable("Food", "Food", 1);

        heroi.insertItem(item3);
        heroi.insertItem(item4);
        //============ ADICIONANDO VILAO AS SALAS ==========
        // GetLuck(ValorMaximo) é utilizado para gerar atributos aleatorios para os vilões
        // Vilões e Chefe para salas da Presa de Zarigoth
        //SALA 1
        // Nome,        Energia,    Defesa, Armadura, Dano,        Gold
        Vilao skeleton1 = new Vilao("skeleton1", 5, 9, 0, 2, getLuck(30)); 
        Vilao skeleton2 = new Vilao("skeleton2", 5, 9, 0, 2, getLuck(30)); 
        p1.insertPersonagem(skeleton1);
        p1.insertPersonagem(skeleton2);

        //SALA2
        Vilao skeleton3 = new Vilao("skeleton1", 5, 9, 0, 2, getLuck(30)); 
        Vilao skeleton4 = new Vilao("skeleton2", 5, 9, 0, 2, getLuck(30)); ;
        p2.insertPersonagem(skeleton3);
        p2.insertPersonagem(skeleton4);
        Consumable item5 = new Consumable("Food", "Food", 1);
        p2.insertItem(item5);

        //SALA 3
        Vilao skeleton5 = new Vilao("skeleton1", 5, 9, 0, 2, getLuck(30)); 
        Vilao skeleton6 = new Vilao("skeleton2", 5, 9, 0, 2, getLuck(30)); 
        p3.insertPersonagem(skeleton5);
        p3.insertPersonagem(skeleton6);

        //SALA 4
        Vilao skeleton7 = new Vilao("skeleton-Warrior1", 8, 12, 0, 5, getLuck(50));
        Vilao skeleton8 = new Vilao("skeleton1", 5, 9, 0, 2, getLuck(30)); 
        p4.insertPersonagem(skeleton7);
        p4.insertPersonagem(skeleton8);
        Consumable item6 = new Consumable("Potion", "Potion", 2);
        p2.insertItem(item6);

        //SALA 5
        Vilao skeleton9 = new Vilao("skeleton1", 5, 9, 0, 2, getLuck(30)); 
        Vilao skeleton10 = new Vilao("skeleton2", 5, 9, 0, 2, getLuck(30)); 
        Vilao skeleton11 = new Vilao("skeleton3", 5, 9, 0, 2, getLuck(30)); 
        p5.insertPersonagem(skeleton9);
        p5.insertPersonagem(skeleton10);
        p5.insertPersonagem(skeleton11);

        //SALA 6
        Vilao skeleton12 = new Vilao("skeleton-Warrior1", 8, 12, 0, 5, getLuck(50));
        Vilao skeleton13 = new Vilao("skeleton-Warrior2", 8, 12, 0, 5, getLuck(50));
        p6.insertPersonagem(skeleton12);
        p6.insertPersonagem(skeleton13);
        Consumable item7 = new Consumable("Potion", "Potion", 2);
        p2.insertItem(item7);
        Consumable item8 = new Consumable("Food", "Food", 1);
        p2.insertItem(item8);

        //SALA 7
        // new Chefe(nome, energia, defesa, armor, dano, gold)
        Chefe skeletonBoss = new Chefe("skeleton-Leader",15,15,0,7,getLuck(300));
        p7.insertPersonagem(skeletonBoss);
        Permanent weapon1 = new Permanent("Battle-Axe","Battle-Axe(Damage: 10)", 15, 10, 0, 0, 0);
        Permanent weapon2 = new Permanent("Long-Sword","Long-Sword(Damage: 8)", 15, 8, 0, 0, 0);
        Permanent weapon3 = new Permanent("Imperial-Staff","Imperial-Staff(Damage: 14)", 15, 14, 0, 0, 0);
        Permanent armor1 = new Permanent("Leather-Armor","Leather-Armor (Armor: 1)", 20, 0, 0,1,0);
        skeletonBoss.insertItem(armor1);
        skeletonBoss.insertItem(weapon3);
        skeletonBoss.insertItem(weapon2);
        skeletonBoss.insertItem(weapon1);

        //ADICIONANDO O ITEN DA QUEST A SALA
        Permanent item1 = new Permanent("Tusk-of-Zarigoth", "Tusk-of-Zarigoth(+5 Life)", 10, 0, 0, 0, 5);
        p7.insertItem(item1);

        //============ ADICIONANDO VILAO AS SALAS ==========
        // GetLuck(ValorMaximo) é utilizado para gerar atributos aleatorios para os vilões
        // Vilões e Chefe para salas do Chifre de Zarigoth
        //SALA 1
        Vilao imp1 = new Vilao("imp1", 8, 10, 0, 4, getLuck(60));
        Vilao imp2 = new Vilao("imp2", 8, 10, 0, 4, getLuck(60));
        c1.insertPersonagem(imp1);
        c1.insertPersonagem(imp2);
        //SALA 2
        Vilao imp3 = new Vilao("imp1", 8, 10, 0, 4, getLuck(60));
        Vilao imp4 = new Vilao("imp2", 8, 10, 0, 4, getLuck(60));
        Vilao satyr1 = new Vilao("satyr1", 15, 12, 0, 6, getLuck(100));
        c2.insertPersonagem(imp3);
        c2.insertPersonagem(imp4);
        c2.insertPersonagem(satyr1);
        Consumable item9 = new Consumable("Potion", "Potion", 2);
        c2.insertItem(item9);

        //SALA 3
        Vilao satyr2 = new Vilao("satyr1", 15, 12, 0, 6, getLuck(100));
        Vilao satyr3 = new Vilao("satyr2", 15, 12, 0, 6, getLuck(100));
        c3.insertPersonagem(satyr2);
        c3.insertPersonagem(satyr3);

        //SALA 4
        //new Chefe(nome, energia, defesa, armor, dano, gold)
        Chefe succubus = new Chefe("succubus",20,13,0,7,getLuck(500));
        c4.insertPersonagem(succubus);
        Consumable item10 = new Consumable("Potion", "Potion", 2);
        c4.insertItem(item10);
        Permanent weapon4 = new Permanent("War-Axe","War-Axe(Damage: 15)", 15, 15, 0, 0, 0);
        Permanent weapon5 = new Permanent("Broad-Sword","Broad-Sword(Damage: 13)", 15, 13, 0, 0, 0);
        Permanent weapon6 = new Permanent("Thunder-Staff","Thunder-Staff(Damage: 18)", 15, 18, 0, 0, 0);
        Permanent armor2 = new Permanent("Iron-Armor","Iron-Armor (Armor: 2)", 20, 0, 0,2,0);
        Permanent shield1 = new Permanent("Spiked-Shield","Spiked-Shield(Defense: 2)", 10, 0, 2, 0,0);
        succubus.insertItem(armor2);
        succubus.insertItem(weapon4);
        succubus.insertItem(shield1);
        succubus.insertItem(weapon6);
        succubus.insertItem(weapon5);

        //ADICIONANDO ITEN DA QUEST \C1 SALA
        Permanent item2 = new Permanent("Horn-of-Zarigoth", "Horn-of-Zarigoth(+5 Life)", 10, 0, 0, 0, 5);
        c4.insertItem(item2);

        //============ ADICIONANDO VILAO AS SALAS ==========
        // GetLuck(ValorMaximo) é utilizado para gerar atributos aleatorios para os vilões
        // Vilões e Chefe para salas do Corredor para sala final e sala final
        //SALA 1
        Vilao gargoyle1 = new Vilao("gargoyle1", 15, 13, 0, 6, getLuck(150));	
        Vilao gargoyle2 = new Vilao("gargoyle2", 15, 13, 0, 6, getLuck(150));
        m1.insertPersonagem(gargoyle1);
        m1.insertPersonagem(gargoyle2);

        //SALA 2
        Vilao gargoyle3 = new Vilao("gargoyle1", 15, 13, 0, 6, getLuck(150));
        Vilao gargoyle4 = new Vilao("gargoyle2", 15, 13, 0, 6, getLuck(150));
        m2.insertPersonagem(gargoyle3);
        m2.insertPersonagem(gargoyle4);

        //SALA 3
        Chefe morahlghul = new Chefe("morahlghul",30,16,0,11,getLuck(1000));
        m3.insertPersonagem(morahlghul);
        Permanent armor3 = new Permanent("Steel-Armor","Steel-Armor (Armor: 5)", 20, 0, 0,5,0);
        Permanent shield2 = new Permanent("Demon-Shield","Demon-Shield(Defense: 3)", 10, 0, 3, 0,0);
        morahlghul.insertItem(armor3);
        morahlghul.insertItem(shield2);

        //SALA FINAL
        //new Chefe(nome, energia, defesa, armor, dano, gold)
        Chefe thePriest = new Chefe("the-Priest",50,16,0,13,getLuck(1000));
        Permanent weapon = new Permanent("Demon-Sword","Demon-Sword(Damage: 34)", 10, 34, 0, 0,0);
        thePriest.insertItem(weapon);
        b1.insertPersonagem(thePriest);

        this.currentRoom = entrada;  // start game on entrance
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished && !this.heroi.isDead()) {
            Command command = this.parser.getCommand();
            finished = this.processCommand(command);
        }
        System.out.println("\nThank you for playing.  Good bye.\n");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Zarigoth Apocalipse!");
        System.out.println("A priest of a hidden religion want to perform a summoning ritual to bring to earth the great demon Zarigoth.");
        System.out.println("But to complete the ritual, it must give the devil the blood of a virgin belonging to a royal line.");
        System.out.println("Then the priest kidnaps the princess of the castle, taking her to his dungeon.");
        System.out.println("Your mission in this story, of course, is to rescue the princess and bring her to the castle.");
        System.out.println("But for this you need to collect two items around the map, the tusk of the demon, guarded by skeletons and skeleton leader and Left Horn Zarigoth.");
        System.out.println("The gateway to the ritual room is protected by the son of Zarigoth, Morahghul .");
        System.out.println("To open the portal, you must defeat Morahghul, collect their blood with the horn, and, along with the tusk, ");
        System.out.println("perform a small ritual to open the portal and access the summoning ritual room.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(this.currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("\nI don't know what you mean...\n");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            if (this.currentRoom.emptyRoom()){//adicionei essa parte, fazendo com que seja derrotados todos inimigos para sair da sala
                goRoom(command);
            }else{
                System.out.println("\nYou must defeat all enemies to exit this room!\n");
            }
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.LOOK) {
            look();
        }
        else if (commandWord == CommandWord.ATTACK) {
            attack(command);
        }
        else if (commandWord == CommandWord.PICK) {
            if (this.currentRoom.emptyRoom()){//adicionei essa parte, fazendo com que seja derrotados todos inimigos para pegar itens da sala
                pick(command);
            }else{
                System.out.println("\nYou must defeat all enemies to pick the itens of this room!\n");
            }

        }
        else if (commandWord == CommandWord.INVENTORY) {//comando para ver inventario
            inventory();
        }
        else if (commandWord == CommandWord.DROP) {//adicionado comando drop
            drop(command);
        }
        else if (commandWord == CommandWord.USE) {//adicionado comando USE
            use(command);
        }
        else if (commandWord == CommandWord.CHARACTER){ // adicionado comando Character
            character();
        }
        else if(commandWord == CommandWord.SHOP){ // adicionado comando para o Shop
            if(this.currentRoom.getShortDescription()=="Entrace of Dungeon & Shop"){
                shop(command);
            }
            else{
                System.out.println("\nTo use this command you must return the original room!\n");
            }
        }

        // else command not recognised.
        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("\n====================================================");
        System.out.println("You are in a Dungeon looking for the princess.");
        System.out.println("You must to collect some itens do find the princess.");
        System.out.println();
        System.out.println("Your command words are: ");
        this.parser.showCommands();
        System.out.println("====================================================\n");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("\nGo where?\n");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = this.currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("\nThere is no door!\n");
        }
        else {
            if(nextRoom.getShortDescription() == "Demon Horn Room - 1"){
                // Testa se a Presa de Zarigoth está equipada para permitir entrar na sala do chifre
                if(this.heroi.getEquippedJewelry1()!= null && this.heroi.getEquippedJewelry1().getName()=="Tusk-of-Zarigoth" || 
                    /*if quebrado*/	this.heroi.getEquippedJewelry2()!=null && this.heroi.getEquippedJewelry2().getName()=="Tusk-of-Zarigoth"){
                    this.currentRoom = nextRoom;
                    System.out.println("\n"+this.currentRoom.getLongDescription());
                }else{
                    System.out.println("\nTo enter this room you should be equipped with the tusk of Zarigoth!\n");
                }
            }else 
            if(nextRoom.getShortDescription() == "Hall to Morahghul Room - 1"){

                // Testa se a Presa de Zarigoth está equipada para permitir entrar na sala do chifre
                if(this.heroi.getEquippedJewelry1()!= null && this.heroi.getEquippedJewelry1().getName()=="Tusk-of-Zarigoth" ||
                    /*if quebrado*/this.heroi.getEquippedJewelry2()!=null && this.heroi.getEquippedJewelry2().getName()=="Tusk-of-Zarigoth"){

                    // Testa se O chifre de Zarigoth está equipado para permitir entrar na sala de Morahghul
                    if(this.heroi.getEquippedJewelry1()!=null && this.heroi.getEquippedJewelry1().getName()=="Horn-of-Zarigoth" || 
                        /*if quebrado*/this.heroi.getEquippedJewelry2()!=null && this.heroi.getEquippedJewelry2().getName()=="Horn-of-Zarigoth"){

                        this.currentRoom = nextRoom;
                        System.out.println("\n"+this.currentRoom.getLongDescription());
                    }else{
                        System.out.println("\nTo enter this room you should be equipped with the Tusk and Horn of Zarigoth!\n");
                    }
                }else{
                    System.out.println("\nTo enter this room you should be equipped with the Tusk and Horn of Zarigoth!\n");
                }
            }else{
                this.currentRoom = nextRoom;
                System.out.println("\n"+this.currentRoom.getLongDescription());
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * "Look" was entered.
     * Displays the description of the room.
     */
    private void look() {
        System.out.println("\n"+this.currentRoom.getLongDescription());
    }

    /**
     * "Pick" was entered. Checks the rest of the command to handle item.
     * @param command You must have the name of the item.
     */
    private void pick(Command command){ // METODO PARA PEGAR ITENS
        if(!command.hasSecondWord()){
            // if there is no second word , I do not know what to pick.... 
            System.out.println("Pick what?");
            return;
        }

        Item item = this.currentRoom.getItem(command.getSecondWord());
        if(item != null){
            if(this.heroi.insertItem(item)){ // Testa se conseguiu adicionar o item ao inventario do heroi antes de remover o item da sala
                System.out.println("\nCongratulations! You got the item: "+item.getName()+"\n");
                this.currentRoom.removeItem(item);
            }else{
                System.out.println("\nYou can not pick up the item: "+item.getName()+", release the necessary weight for the item: "+item.getWeight()+"\n");
            }
        }else {
            System.out.println("The item '" + command.getSecondWord() + "' is not in the current room.\n");
        }

    }

    /**
     * "Drop" was entered. Checks the rest of the command to handle item.
     * @param command You must have the name of the item.
     */
    private void drop(Command command){//METODO QUE DROPA ITENS
        if(!command.hasSecondWord()){
            // if there is no second word, I do not know what to drop... 
            System.out.println("drop what?");
            return;
        }

        Item item = this.heroi.getItem(command.getSecondWord());
        if(item != null){

            if(this.heroi.getEquippedWeapon()!=null && this.heroi.getEquippedWeapon().getName() == item.getName()){
                System.out.println("\nIt is not possible to drop an item equipped!!\n");
                return;
            }
            if(this.heroi.getEquippedArmor()!= null && this.heroi.getEquippedArmor().getName() == item.getName()){
                System.out.println("\nIt is not possible to drop an item equipped!!\n");
                return;
            }
            if(this.heroi.getEquippedShield()!=null && this.heroi.getEquippedShield().getName() == item.getName()){
                System.out.println("\nIt is not possible to drop an item equipped!!\n");
                return;
            }
            if(this.heroi.getEquippedJewelry1()!=null && this.heroi.getEquippedJewelry1().getName() == item.getName()){
                System.out.println("\nIt is not possible to drop an item equipped!!\n");
                return;
            }else{
                if(this.heroi.getEquippedJewelry2()!=null && this.heroi.getEquippedJewelry2().getName() == item.getName()){
                    System.out.println("\nIt is not possible to drop an item equipped!!\n");
                    return;
                }
            }

            this.heroi.removeItem(item.getName());
            System.out.println("\nThe item "+item.getName()+" has been removed!\n");
            this.currentRoom.insertItem(item);
        }else {
            System.out.println("\nThe item '" + command.getSecondWord() + "' is not on your inventory.\n");
        }

    }

    /**
     * "Attack" was entered. Checks the rest of the command to handle villain's.
     * @param command You should inform the villain's name.
     */
    private void attack(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, I do not know what to attack...
            System.out.println("Attack who?");
            return;
        }

        Personagem vilao = this.currentRoom.getPersonagem(command.getSecondWord());
        if (vilao != null) {
            if(!vilao.isDead()){

                System.out.println("\n"+this.heroi.getName()+" attack "+vilao.getName());
                this.heroi.fight(vilao); //realiza o ataque do heroi

                if(!vilao.isDead()){
                    System.out.println(vilao.getName()+" attack "+heroi.getName());
                    vilao.fight(this.heroi); //realiza o ataque do vilao
                }

                System.out.println("==== STATUS FIGHT ====");
                this.heroi.printOut();
                vilao.printOut();

                if(vilao.isDead()){
                    this.heroi.addGold(vilao.getGold());
                    System.out.println("Drop gold: "+vilao.getGold()+"\n");
                    if(vilao instanceof Chefe){
                        Item drop = ((Chefe) vilao).getDrop(); // Pega um dos itens do inventario do Boss
                        String returnDropString = "\nDrop Boss: "; 
                        returnDropString+="\n\t"+drop.getDescription();
                        this.currentRoom.insertItem(drop);	// Adiciona item a sala
                        drop = ((Chefe) vilao).getDrop();
                        returnDropString+="\n\t"+drop.getDescription();
                        this.currentRoom.insertItem(drop);
                        System.out.println(returnDropString+"\n"); // Imprime uma string com os itens dropados do Boss
                        if(vilao.getName()=="the-Priest"){
                            System.out.println("\nCongratulations, you managed to kill the Priest that was keeping the Princess in her dungeon before he " +
                                "could perform the ritual invocation of Zarigoth demon.\n");
                            System.out.println("\nThank you for playing The Zarigoth Apocalipse!.\n");

                        }
                    }
                    this.currentRoom.removePersonagem(vilao);
                }

                if(this.heroi.isDead()){
                    if(this.heroi.getContinues()>0){
                        this.heroi.decreaseContinues();
                        System.out.println("\nYou Died!\n");
                        System.out.println("\nYou used a continue, remain : "+this.heroi.getContinues()+"\n");
                        for(int i = 0; i<this.heroi.getMaximumLife();i++){
                            this.heroi.increase();
                        }
                    }else{
                        System.out.println("\nYou Died!\n");
                    }
                }
            }else{
                System.out.println("\nThis enemy is already dead!\n");
            }
        } else {
            System.out.println("\nThe character '" + command.getSecondWord() + "' is not in the current room.\n");
        }
    }

    /**
     * "Inventory" was entered.
     * Displays inventory of hero.
     */
    private void inventory() { //METODO QUE VE INVENTARIO, chamando o comando na classe heroi
        System.out.println(this.heroi.getInventoryString());
    }

    /**
     * "Uuse" was entered. Checks the rest of the command to handle itens.
     * @param command You should inform the item name.
     */
    private void use(Command command){//METODO QUE USA ITENS
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know where to go... 
            System.out.println("use what?");
            return;
        }

        Item item = this.heroi.getItem(command.getSecondWord());

        if(item != null){
            if (item instanceof Permanent){//VERIFICA SE E UM ITEM PERMANENTE, CASO FOR, ALTERA O ITEM EQUIPADO DO HEROI PELO NOVO ITEM
                if (((Permanent) item).getAtkBonus()>0){//Verifica se é uma arma
                    Permanent newWeapon = (Permanent)item;
                    this.heroi.setEquippedWeapon(newWeapon);
                    this.heroi.removeItem(newWeapon.getName()); // Retira o item do inventario pois está equipado
                    System.out.println("\nNew Weapon Equipped: "+newWeapon.getName()+"\n");
                }
                if (((Permanent) item).getArmBonus()>0){//Verifica se é uma armadura
                    Permanent newArmor = (Permanent)item;
                    this.heroi.setEquippedArmor(newArmor);
                    this.heroi.removeItem(newArmor.getName()); // Retira o item do inventario pois está equipado
                    System.out.println("\nNew Armor Equipped: "+newArmor.getName()+"\n");
                }
                if (((Permanent) item).getDefBonus()>0){//verifica se é um escudo
                    Permanent newShield = (Permanent)item;
                    this.heroi.setEquippedShield(newShield);
                    this.heroi.removeItem(newShield.getName()); // Retira o item do inventario pois está equipado
                    System.out.println("\nNew Shield Equipped: "+newShield.getName()+"\n");
                }
                if (((Permanent) item).getLifeBonus()>0){//verifica se é uma joia
                    Permanent newjewelry = (Permanent)item;
                    this.heroi.setEquippedJewelry(newjewelry);
                    this.heroi.removeItem(newjewelry.getName()); // Retira o item do inventario pois está equipado
                    System.out.println("\nNew Jewelry Equipped: "+newjewelry.getName()+"\n");
                }

            }else{//CASO NAO E PERMANENTE, ENTAO E UM ITEM CONSUMIVEL
                if(this.heroi.getLife() != this.heroi.getMaximumLife()){
                    if(item.getName()=="Potion"){
                        this.heroi.feed();
                    }else{
                        this.heroi.increase();
                    }

                    this.heroi.removeItem(item.getName());
                    System.out.println("\nThe item "+item.getName()+" was used!");
                    System.out.println("Its current status:");
                    this.heroi.printOut();
                }else{
                    System.out.println("\nHis energy is still full!\n");
                }

            }

        }
        else{
            System.out.println("\nThe item '" + command.getSecondWord() + "' is not on your inventory.\n");
        }
    }

    /**
     * "character" was entered.
     * displays attributes of the hero.
     */
    private void character(){ // Método que retorna a descrição dos atributos do Heroi
        System.out.println("\n"+this.heroi.getCharacter());
    }

    /**
     * "shop" was entered. Checks the rest of the command to handle itens.
     * To view items enter only shop. To purchase an item type shop plus the name of the item.
     * @param command You should inform the item name
     */
    private void shop(Command command){
        if(!command.hasSecondWord()){
            String returnString = "\n====================================================\n"+"SHOP:";
            Set<String> keys = this.shop.keySet();
            for(String nome : keys) {
                returnString += "\n\t" + this.shop.get(nome).getDescription();
                returnString += "\n\tPrice: " + this.shop.get(nome).getPrice()+"\n";			
            }
            returnString +="====================================================\n";

            System.out.println(returnString);
            return;
        }

        Item item = this.shop.get(command.getSecondWord());

        if(item != null){
            if(item.getPrice()<=this.heroi.getGold()){
                this.heroi.removeGold(item.getPrice());
                if (item.getName() == "Continue-Scroll"){
                    this.heroi.increaseContinue();
                }else{
                    this.heroi.insertItem(item);
                }
                System.out.println("\nCongratulations! You bought the item: "+item.getName()+"\n");
                if(!(item instanceof Consumable)){
                    this.shop.remove(item.getName());
                }
            }
            else{
                System.out.println("\nYou do not have enough money to buy this item.\n");
            }
        }else {
            System.out.println("\nThe item '" + command.getSecondWord() + "' is not in Shop.\n");
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
     * Character selection method
     * @return integer value between 1 and 3.
     */
    private int selectPersonagem(){
        int option = 0;
        try{
            do{
                System.out.println("================ Class selection menu ================");
                System.out.println("1 - Barbarian");
                System.out.println("Attributes: 15 Life , 12 Defense, 100 Inventory. \n\t Weapon: Small Axe ( 6 Damage )");
                System.out.println("2 - Warrior");
                System.out.println("Attributes: 18 Life , 15 Defense, 75 Inventory. \n\t Weapon: Short Sword ( 4 Damage )");
                System.out.println("3 - Mage");
                System.out.println("Attributes: 10 Life , 9 Defense, 50 Inventory. \n\t Weapon: Fire Staff ( 8 Damage )");
                System.out.println("======================================================");
                System.out.println("Choose your Class: ");
                option = reader.nextInt();
            }while(option > 3 || option < 1);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println("\n============== ERROR ==============\nYou must enter only numbers !!!!\n");
            return -1;
        }
        return option;
    }

    /**
     * Adds name to the hero.
     * @return string with given name.
     */
    private String selectName(){
        String nome;
        System.out.print("Enter the name of your character: ");
        nome = reader.nextLine();
        System.out.println();
        return nome;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
