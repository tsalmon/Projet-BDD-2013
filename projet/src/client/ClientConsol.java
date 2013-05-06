

import java.util.Scanner;



public class ClientConsol {

  public static final int PORT = 25567;
  public static final String HOST = "multi-craft.fr";
  public static boolean auth=false;

  public static void main(String []arguments) {
    ConnexionClient c = new ConnexionClient(); //se connecte au serveur
    SqlChart chart = new SqlChart();
	//if(c.auth("Stuart","408101")){System.out.println("auth reussi");}
//	else{System.out.println("connexion fail");}	// s'authentifie
	Scanner scanner= new Scanner(System.in);
	System.out.println("taper \"CONNEXION (mail)\" pour vous identifier, \"HELP\" pour plus d'informations");
	while(true){
		String chaine=scanner.nextLine();
		if(chaine.charAt(0)=='R' && chaine.charAt(2)=='Q'){
			String[] chaineTrie =chaine.split("#");
			chaine=chaineTrie[0].replace("REQUEST ", "");
			SqlData s =c.request(chaine); // execute la requete list_client (=> "SELECT * FROM client") et recupere les donnes dans SqlData 
			
			if(s!=null) { //on verifie que SqlData n'est pas null (e.q que le serveur a bien renvoyer des donnees)
				
				if(chaineTrie.length>1){s.trie(Integer.parseInt(chaineTrie[1]));}
				
				String[] chaineSplit=chaine.split("[|]");
				chart.print(chaineSplit[0], s);
				
				int[] tab = new int[s.getNbCol()];
			//	s.trie(4);
				
				for(int i=0;i<s.getNbCol();i++)
					tab[i]=(s.getNomCol(i).length()>tab[i])?s.getNomCol(i).length():tab[i];
			
				for(int l=0; l<s.getNbLigne();l++)
					for(int i=0;i<s.getNbCol();i++)
						tab[i]=(s.getString(l, i).length()>tab[i])?s.getString(l, i).length():tab[i];
						
						
				for(int i=0;i<s.getNbCol();i++){
					String col= s.getNomCol(i);
					String vide="";
					for(int k=0;k<tab[i]-col.length();k++)vide+=" ";
					System.out.print("|"+col+""+vide);
					
				}
				System.out.println("");
				for(int l=0; l<s.getNbLigne();l++){
					for(int i=0;i<s.getNbCol();i++){
						String col= s.getNomCol(i);
						String val= s.getString(l, i);
						String vide="";
						for(int k=0;k<tab[i]-val.length();k++)vide+=" ";
						System.out.print("|"+val+""+vide);
						
					}
					System.out.println("");
				}
				System.out.println("");
			}
		}
		else if(chaine.charAt(0)=='C' && chaine.charAt(1)=='O'){
			if(!auth){
				String user = chaine.replace("CONNEXION ", "");
				System.out.print("veuillez entrer votre mot de pass :");
				String mdp =scanner.nextLine();
				auth=c.auth(user,mdp);
				System.out.println((auth)?"connexion reussi":"echec de connexion");
			}
			else {
				System.out.println("vous etes deja connecter");
			}
		}
		else if(chaine.charAt(0)=='R'){
			chaine=chaine.replace(" ", "|");
			System.out.println(c.dialog(chaine));
		}
		else if(chaine.equals("DISCONNECT")){
			auth=false;
			System.out.println(c.dialog(chaine));
		}
		else if(chaine.equals("CLOSE")){
			c.exit();
			System.exit(1);
		}
		else {
			System.out.println(c.dialog(chaine));
		}
		
	}
  }
}
