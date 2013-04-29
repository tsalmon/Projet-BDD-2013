// package client;

import java.net.*;
import java.io.*;


public class ConnexionClient { // gere la connexion au serveur , et l'envoi des requetes

    public static final int PORT = 25567;
    public static final String HOST = "multi-craft.fr";
    private PrintWriter pw ;
    private BufferedReader bf;
    private Socket service; 
  
    public ConnexionClient() {
	try {
	  
	    service = new Socket("multi-craft.fr",PORT); // connexion au serveur mode tcp 
	    bf = new BufferedReader(new InputStreamReader(service.getInputStream()));   
	    pw = new PrintWriter(new OutputStreamWriter(service.getOutputStream())); 
	  
	  
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }
  
    public void exit(){  
	try { pw.close(); bf.close(); service.close();} catch(Exception e) {} 
    } 
  
    public boolean auth(String user, String passwd){
	try{
	    String reponse= this.dialog("AUTH|"+user+"|"+passwd);
	    if(reponse.equals("AUTH true\n")) return true;
	    return false;
	} catch(Exception e) {
	    e.printStackTrace();
	}
	return false;
    }
  
    public SqlData request(String nom,String... args){
	try{
	    String chaine="REQUEST|"+nom;
	    for(int i=0;i< args.length;i++) chaine+="|"+args[i];
	    chaine =chaine.replace("\n", "\\n");
	    pw.println(chaine);
	    pw.flush();
	    String reponse;
	    reponse=bf.readLine();
	    if(reponse.equals("SelectDone")){
		reponse=bf.readLine();
		String[] nb = reponse.split(" ");
		String[] nomCol = new String[Integer.parseInt(nb[0])];
		String[] typCol = new String[Integer.parseInt(nb[0])];
		String[][] data = new String[Integer.parseInt(nb[1])][Integer.parseInt(nb[0])];
		
		reponse=bf.readLine();
		String[] col = reponse.split("[|]");
		for(int i=0; i<nomCol.length; i++) nomCol[i]=col[i];
		reponse=bf.readLine();
		String[] typ = reponse.split("[|]");
		for(int i=0; i<typCol.length; i++) typCol[i]=typ[i];
		
		for(int k=0; k<data.length;k++){ //recupere les données de la requete
		    reponse=bf.readLine(); 
		    reponse =reponse.replace("\\n", "<br>");
		    String[] d = reponse.split("[|]");
		    for(int i=0; i<typCol.length; i++) data[k][i]=d[i];
		}
		return new SqlData(nomCol,typCol,data);
	    }
	    else if(reponse.equals("UpdateDone")){
		return null;
	    }
	    System.out.println("Error: "+bf.readLine());
	    return null;
	} catch(Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
  
    public String dialog(String chaine,String... args){
	try{
	    pw.println(chaine);
	    pw.flush();
	    String reponse;
	    chaine="";
	    while(!(reponse=bf.readLine()).equals("eof")){
		chaine+=reponse+"\n";
	    }
	    return chaine;
	} catch(Exception e) {
	    e.printStackTrace();
	}
	return null;
    } 
}
