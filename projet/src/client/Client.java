package client;

import java.util.Scanner;



public class Client {

  public static final int PORT = 25567;
  public static final String HOST = "multi-craft.fr";

  public static void main(String []arguments) {
    ConnexionClient c = new ConnexionClient(); //se connecte au serveur
	if(c.auth("Stuart","408101")){System.out.println("auth reussi");}
	else{System.out.println("connexion fail");}	// s'authentifie
	Scanner scanner= new Scanner(System.in);
	
	while(true){
		String chaine=scanner.nextLine();
		
		if(chaine.charAt(0)=='R'){
			SqlData s =c.request(chaine.replace("REQUEST ", "")); // execute la requete list_client (=> "SELECT * FROM client") et recupere les donnes dans SqlData 
			
			if(s!=null) { //on verifie que SqlData n'est pas null (e.q que le serveur a bien renvoyer des donnees)
				System.out.println("les client sont"); //un ptit print du resultat
				for(int i=0; i<s.getNbLigne();i++){
					System.out.println(i+": "+s.getString(i,1)+" => "+ ((s.getBoolean(i,3))?"c'est un developpeur":"c'est juste un client") );
				}
			}
		}
		else if(chaine.charAt(0)=='H'){
			System.out.println(c.dialog(chaine));
		}
		
	}
  }
}
