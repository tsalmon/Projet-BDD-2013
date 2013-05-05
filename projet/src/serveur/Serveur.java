

import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import javax.swing.Timer;

public class Serveur {

  public static final int PORT = 25567;
  public static  Connection conn;
  public static LinkedList<Requete> requetes = new LinkedList<Requete>();

  public static void main(String []arguments) {
      try {
	  
			// chargement des driver pour mysql
		Class.forName("com.mysql.jdbc.Driver");  
		System.out.println("Driver O.K.");
		
			// connexion a la base de donnee
	    String url = "jdbc:mysql://localhost/bd6db";  
	    String user = "bd6user";
	    String passwd = "bd6projet";	     
	    conn = DriverManager.getConnection(url, user, passwd);  
	    System.out.println("Connexion O.K."); 
		  
		  // Chargment des requetes depuis le fichier dans Serveur.requetes
		  BufferedReader bf = new BufferedReader(new FileReader("requete.req"));
		  String chaine;
		  while((chaine=bf.readLine())!=null){
			if(chaine.charAt(0)!= '#'){ 
				String[] chaineSplit= chaine.split(";");
				int[] paramInt=null;
				if(!chaineSplit[2].equals("")){ 
					String[] paramString = chaineSplit[2].split(" ");
					paramInt = new int[paramString.length];
					for(int i=0; i<	paramString.length; i++){ paramInt[i]=Integer.parseInt(paramString[i]); }
				}
				
				String[]req=new String[chaineSplit.length-4];
				req[0]=chaineSplit[4];
				for(int i=1;i<req.length;i++)
					req[i]=chaineSplit[4+i];
				
				Serveur.requetes.add(new Requete(chaineSplit[0],chaineSplit[1],paramInt,Integer.parseInt(chaineSplit[3]),req));
				System.out.println("\t Requete:"+chaineSplit[0]+" O.K.");
			}
		  }
		  System.out.println("Requetes O.K.");
		  
        ServerSocket socketAttente = new ServerSocket(PORT); // creation d'une socket sur le port 25567
        System.out.println("Socket O.K.");
        
        UpdateGain u =new UpdateGain();
        
        do {  // on attend un connexion et on donne la main au thread Service
			new Thread( new Service(socketAttente.accept())).start();
        } while (true);
	  
      } catch(Exception e) {
         System.err.println("Erreur : "+e);
         e.printStackTrace(); System.exit(1);
      }
  }
}
