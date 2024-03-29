//package client;
import java.util.Scanner;
import java.awt.Color;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class Client 
{
    /* variable générales d'affichage */    
    static  volatile Client  instance  = null; 
    static           int     fenetre_x = 779;
    static           int     fenetre_y = 456;
    private          String  identifiant;
    private          String  mot_de_passe;
    private          Fenetre f;
    
    /* var serveur */
    public static final int PORT = 25567;
    public static final String HOST = "multi-craft.fr";
    private static ConnexionClient connexion = new ConnexionClient(); 

    // cette fonction permet de tester la connexion au serveur
    public static void connect() {
	//se connecte au serveur
	if(connexion.auth("Stuart","408101")){System.out.println("auth reussi");}
	else{System.out.println("connexion fail");}	// s'authentifie
	Scanner scanner= new Scanner(System.in);
	while(true){
	    String chaine=scanner.nextLine();
	    if(chaine.charAt(0)=='R'){
		//execute la requete list_client(=> "SELECT * FROM client") et recupere les donnes dans SqlData 
		SqlData s =connexion.request(chaine.replace("REQUEST ", "")); 
		//on verifie que SqlData n'est pas null (e.q que le serveur a bien renvoyer des donnees)
		if(s!=null) {
		    System.out.println("les client sont"); //un ptit print du resultat
		    for(int i=0; i<s.getNbLigne();i++){
			System.out.println(i+": "+s.getString(i,1)+" => "+ ((s.getBoolean(i,3))?"c'est un developpeur":"c'est juste un client") );
		    }
		}
	    }
	    else if(chaine.charAt(0)=='H'){
		System.out.println(connexion.dialog(chaine));
	    }	    
	}
    }
    
    //permet d'utiliser les variables et les fonctions de la classe Client
    public final static Client getInstance()
    {
	if(Client.instance == null){
	    synchronized(Client.class){
		if(Client.instance == null){
		    Client.instance = new Client();
		}
	    }
	}
	return (Client.instance);
    }
    
    private Client()
    {
	super();
    }
    
    // accesseurs
    public ConnexionClient getConnect()
    {
	return (this.connexion);
    }
    
    public String get_Id()
    {
	return (this.identifiant);
    }
    
    public void set_Id(String new_id)
    {
	this.identifiant = new_id;
    }
    
    public String get_mdp()
    {
	return(this.mot_de_passe);
    }

    public Fenetre getFen()
    {
	return (this.f);
    }        
    
    //setters
    public void set_mdp(String new_mdp)
    {
	this.mot_de_passe = new_mdp;
    }
    
    public void setFen(Fenetre newf)
    {
	this.f = newf;
    }

    
    public static void main(String[] args)
    {
	javax.swing.SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{
		    try{
			for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels())
			    {
				if("Nimbus".equals(info.getName())){
				    UIManager.setLookAndFeel(info.getClassName());
				    break;
				}
			    }
		    }
		    catch(Exception e){
			e.printStackTrace();
		    }
		    Client.getInstance().setFen(new Fenetre());
		    Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
		}
	    });
    }
}