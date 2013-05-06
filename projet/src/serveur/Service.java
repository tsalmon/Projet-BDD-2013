
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
		id=0;
		
		
		String chaine;	
		
		//debut traitment des requete //////////////////////////////////////////////////////////
		while( (chaine=bf.readLine())!=null ){ //on boucle
			
			
			String[] chaineSplit= chaine.split("[|]");System.out.println(chaine);  // on split la requete
			
			
			if(chaineSplit[0].equals("REGISTER")&& id==0){
				ResultSet result = Serveur.conn.createStatement().executeQuery
						("SELECT ID,mdp,developpeur FROM client WHERE nom=\""+chaineSplit[3]+"\"  OR mail=\""+chaineSplit[1]+"\"");
				if(!result.next()){
					Serveur.conn.createStatement().executeUpdate
					    ("INSERT INTO client (mail,mdp,nom,prenom) VALUES (\""+chaineSplit[1]+"\",\""+chaineSplit[2]+"\",\""+chaineSplit[3]+"\",\""+chaineSplit[4]+"\")");
				}
				pw.println("identifiant deja utilise");
				pw.println("eof");
				pw.flush();
			}
			
			else if(chaineSplit[0].equals("AUTH")&& id==0){   
				
				ResultSet result = Serveur.conn.createStatement().executeQuery
					("SELECT ID,mdp,developpeur FROM client WHERE mail=\""+chaineSplit[1]+"\""); // on cherche une corespondancd dans la bdd

				if(result.next() && chaineSplit[2].equals(result.getString(2))){ // on verifie le mot de pass
					Serveur.conn.createStatement().executeUpdate
						("UPDATE client SET date_lastconnect=\""+new Timestamp(System.currentTimeMillis())+"\" WHERE ID="+result.getInt(1));
					id=result.getInt(1);
					droit= (result.getBoolean(3))? 2:1; // on recupere les droit de l'utilisateur en fonction de developeur ou non
					droit= (chaineSplit[1].equals("gerante@GoldenStore.com"))?3:droit; // si c'est la gerante
					pw.println("AUTH true");
					pw.println("eof");
					pw.flush();
				}
				
				else {
					pw.println("AUTH false");
					pw.println("eof");
					pw.flush();
				}
				
			}
			else if(chaineSplit[0].equals("REQUEST") && id!=0){   
				
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
								int row =result.getRow();
								pw.println((resultMeta.getColumnCount())+" "+row);
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

								
							  if (row!=0){
								    if (result.getObject(1)!=null)pw.print(result.getObject(1).toString());
								    else pw.print("Null");
									for(int l = 2; l <= resultMeta.getColumnCount(); l++)
										if (result.getObject(l)!=null)pw.print("|" + result.getObject(l).toString());
										else pw.print("|Null");
									pw.println("");
								  while(result.next()){   
									pw.print(result.getObject(1).toString());
									for(int l = 2; l <= resultMeta.getColumnCount(); l++)
									  if (result.getObject(l)!=null)pw.print("|" + result.getObject(l).toString());
									  else pw.print("|Null");
									pw.println("");
									}	
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
					String aide="\t pour plus d'information sur une commande faite HELP|[commande]\n\n";
					aide+="\t\t-REGISTER (mail)|(mdp)|(nom)|(prenom) pour vous inscrire au serveur \n";
					aide+="\t\t-CONNEXION (mail) pour vous connecter au serveur \n";
					aide+="\t\t-DISCONNECT pour vous deconnecter du serveur\n";
					aide+="\t\t-REQUEST (requete)|[param1]|[param2]...[#ColTrie] envoi la requete pour traitement au serveur avec ses parametres trie sur la colonne \n";
					aide+="\t\t-CLOSE quite l'application\n";
					pw.println(aide);
				}
				else if(chaineSplit[1].equals("CONNEXION")){
					pw.println("\t-CONNEXION (mail) pour vous connecter au serveur, \n\t\t mail:l'adresse email que vous avez fourni a votre inscription\n ");
				}
				else if(chaineSplit[1].equals("DISCONNECT")&& id!=0){ 
					id=0;
					pw.println("\t-DISCONNECT pour vous deconnecter du serveur\n");
				}
				else if(chaineSplit[1].equals("REQUEST")){
					pw.println("\t-REQUEST (requete)|[param1]|[param2]...[# ColTrie] envoi la requete pour traitement au serveur avec ses parametres trie sur la colonne \n\t la liste des requete est :");
					for(int i=0;i<Serveur.requetes.size();i++){
						if(Serveur.requetes.get(i).getDroit()<=this.droit){
							String name=Serveur.requetes.get(i).getName();
							String espace="";
							for(int l=name.length();l<25;l++)espace+=" ";
							pw.println("\t\t"+name+espace+" => "+Serveur.requetes.get(i).getDescription());
						}
						if(droit==0){
							pw.println("\t\t vous devez etre connecte ");
						}
					}
				}
				pw.println("eof");
				pw.flush();
			}
			else if(chaineSplit[0].equals("DISCONNECT") && id!=0){   
				pw.println("vous alez etre deconecter");
				pw.println("eof");
				pw.flush();
				id=0;
				run();
			}
			else {
				pw.println("Eror");
				pw.println((id==0)?"vous n'etes pas authentifie":"commande non trouve");
				pw.println("eof");
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
