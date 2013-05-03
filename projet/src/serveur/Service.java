//package serveur;

import java.net.*;
import java.io.*;
import java.sql.*;


public class Service implements Runnable  {
  private Socket service;
  private BufferedReader bf;
  private PrintWriter pw;
  private int droit =0;
  private int id;

  public Service(Socket service) {
    this.service = service;
  }

  public void run() {
    try {
	
		bf = new BufferedReader(new InputStreamReader(service.getInputStream())); 
		pw = new PrintWriter(new OutputStreamWriter(service.getOutputStream()));
	
		// debut authentification ///////////////////////////////////////////////////////////////
		String chaine;	
		boolean auth=true;
		
		while(auth && (chaine=bf.readLine())!=null ){ //on boucle tant que l'utilisateur ne c'est pas authentifier
			String[] chaineSplit= chaine.split(" ");  // on split la requete
			if(chaineSplit[0].equals("AUTH")){   
			
				ResultSet result = Serveur.conn.createStatement().executeQuery
					("SELECT ID,mdp,developpeur FROM client WHERE nom=\""+chaineSplit[1]+"\""); // on cherche une corespondancd dans la bdd

				if(result.next() && chaineSplit[2].equals(result.getString(2))){ // on verifie le mot de pass
					auth=false;  // on stop la boucle
					id=result.getInt(1);
					droit= (result.getBoolean(3))? 2:1; // on recupere les droit de l'utilisateur en fonction de developeur ou non
					droit= (chaineSplit[1].equals("gerant"))?3:droit; // si c'est la gerante
					pw.println("AUTH true");
					pw.flush();
				}
				
				else {
					pw.println("AUTH false");
					pw.flush();
				}
				
			}
			else {
				pw.println("AUTH false");
				pw.flush();
			}
		}	
		// fin authentification  ////////////////////////////////////////////////////////////////
		
		
		
		//debut traitment des requete //////////////////////////////////////////////////////////
		while( (chaine=bf.readLine())!=null ){ //on boucle tant que l'utilisateur est connecter
			String[] chaineSplit= chaine.split(" ");  // on split la requete
			if(chaineSplit[0].equals("REQUEST")){   
			
				boolean find=true;
				for(int i=0;i<Serveur.requetes.size();i++){
				
					if(Serveur.requetes.get(i).getName().equals(chaineSplit[1])){
						find=false;
						
						if(Serveur.requetes.get(i).getDroit()<=this.droit){
							String[] param = new String[chaineSplit.length-2];
							for(int j=0;j<param.length;j++){
								param[j]=chaineSplit[j+2];
							}
							ResultSet result= Serveur.requetes.get(i).execute(id,param);
							if(result!=null){
								ResultSetMetaData resultMeta = result.getMetaData();
								pw.println("SelectDone");
								
								result.last();
								pw.println((resultMeta.getColumnCount())+" "+result.getRow());
								result.first();
								
						
							  //On affiche le nom des colonnes
							  pw.print(resultMeta.getColumnName(1));
							  for(int l = 2; l <= resultMeta.getColumnCount(); l++)
								pw.print("|"+resultMeta.getColumnName(l));
							  pw.println("");
								
							   pw.print(resultMeta.getColumnTypeName(1));
							  for(int l = 2; l <= resultMeta.getColumnCount(); l++)
								pw.print("|"+resultMeta.getColumnTypeName(l));
							  pw.println("");;

								
							  pw.print(result.getObject(1).toString());
								for(int l = 2; l <= resultMeta.getColumnCount(); l++)
								  pw.print("|" + result.getObject(l).toString());
								pw.println("");
							  while(result.next()){   
								pw.print(result.getObject(1).toString());
								for(int l = 2; l <= resultMeta.getColumnCount(); l++)
								  pw.print("|" + result.getObject(l).toString());
								pw.println("");
								}	
							   pw.flush();
							}
							else if(!Serveur.requetes.get(i).getIsSelect()){
								pw.println("UpdateDone");
								pw.flush();
							}
							else{
								pw.println("Eror");
								pw.println("requete est null");
								pw.flush();
							}
						}
						else {
							pw.println("Eror");
							pw.println("droit refuse");
							pw.flush();
						}

					}
					
				}
				if(find){
					pw.println("Eror");
					pw.println("requete non trouve");
					pw.flush();
				}
				
				
			}
			else if(chaineSplit[0].equals("HELP")){   
				if(chaineSplit.length==1){
					for(int i=0;i<Serveur.requetes.size();i++){
						if(Serveur.requetes.get(i).getDroit()<=this.droit){
							String name=Serveur.requetes.get(i).getName();
							String espace="";
							for(int l=name.length();l<25;l++)espace+=" ";
							pw.println("\t"+name+espace+" => "+Serveur.requetes.get(i).getDescription());
						}
					}
					pw.println("eof");
					pw.flush();
				}
			}
			else {
				pw.println("Eror");
				pw.println("commande non trouve");
				pw.flush();
			}

		}
		
		
		// fin traitement des requete ///////////////////////////////////////////////////
		
		
		
		pw.close(); bf.close(); service.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
