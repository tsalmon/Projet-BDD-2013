package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;



public class Connect {
	public static Statement stmt;
	public static void main(String[] args) {      
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("Driver O.K.");

	      String url = "jdbc:mysql://localhost/bd6db";
	      String user = "bd6user";
	      String passwd = "bd6projet";
	     
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	      Statement stmt = conn.createStatement();
	      System.out.println("Connexion effective !"); 
   
	      //300 application
	      
	      
      //Création d'un objet Statement
      Statement state = conn.createStatement();
      Statement state2 = conn.createStatement();
      //L'objet ResultSet contient le résultat de la requête SQL
      ResultSet result = state.executeQuery("SELECT * FROM client");
      //On récupère les MetaData
      ResultSetMetaData resultMeta = result.getMetaData();
          
      BufferedReader bf = new BufferedReader(new FileReader("data.csv"));
	  String chaine;int indice=1;
	  while((chaine=bf.readLine())!=null){
		  String[] tab = chaine.split("[|]");
		  if(tab[0].equals("10") && tab.length>10){ System.out.println(chaine);
			// result = state.executeQuery("SELECT ID FROM systeme_exploitation WHERE nom='"+tab[9]+"' AND  version='"+tab[10]+"-"+tab[11]+"'");
		  	
		  
			/*	
					 result = state.executeQuery("SELECT application FROM application_installe WHERE peripherique_client='"+tab[7]+"'");
					 while( result.next()){
						 
						 int p=  (int)(Math.random()*100)+1;
						 if(p<Integer.parseInt(tab[5])){
							 int e =  (int)(Math.random()*5)+1;
							 String c="";
							 p=  (int)(Math.random()*100)+1;
							 if(p<Integer.parseInt(tab[5])){
								 c= (e==1)?"Muavaise application":c;
								 c= (e==2)?"tres moyen":c;
								 c= (e==3)?"ni bon, ni muavais":c;
								 c= (e==4)?"bonne application":c;
								 c= (e==5)?"exelente application, je recomande":c;
							 }
							 int date= (int)(Math.random()*30)+1;
							 String s = "INSERT INTO avis VALUES("+tab[1]+","+result.getInt(1)+","+e+",\""+c+"\", \"2013-05-"+date+"\") ";
							 System.out.println(s);
							 state2.executeUpdate(s);
							 
						 }
							 

					 }
					 
				  
				 
				  indice++;
			 
		  
		  
		  /*
		  int nb = Integer.parseInt(tab[8]); 
			 while(nb!=0){
				 result = state.executeQuery("SELECT systeme_exploitation FROM peripheriques_client WHERE ID='"+tab[7]+"'");
				 if( result.next()){
					 int se = result.getInt(1);
					 boolean verif= true;
					 int r=0;int boucle=0;
					 while(verif && boucle++ <500){
						 int ap = (int)(Math.random()*300)+1;
						 result = state.executeQuery("SELECT application FROM applicationsSE WHERE Systeme_exploitation='"+se+"' AND application > '"+ap+"'");
						 result.next();
						 r=result.getInt(1);
						 result = state.executeQuery("SELECT application FROM application_installe WHERE application='"+r+"' AND peripherique_client='"+tab[7]+"'");
						 if(!result.next()){
							 verif = false; 
							 int date= (int)(Math.random()*30)+1;
							 //int p= (int)(Math.random()*12)+1;
							 //int cb= (tab[2].equals("cb"))?1:0;
							 //String date= (tab[4].equals("\"\""))?"2000-00-00":tab[4];
							 String s = "INSERT INTO application_installe VALUES("+r+","+tab[7]+", \"2013-05-"+date+"\") ";
							 System.out.println(s);
							 state.executeUpdate(s);
						 }
					 }
				 }	 
				 
				  indice++;
				 nb--;
			 }
				 
				 
				 
				 
				 
				 
				/*
				 String s = "UPDATE applications SET  mela=\""+tab[14]+"\" WHERE ID="+indice;
				 System.out.println(s);
				 state.executeUpdate(s);
				 
				 
				  
				  
				 System.out.println(tab[1]);System.out.println(tab[2]);System.out.println(tab[3]);System.out.println(tab[4]);
				 int mdp = (int) (Math.random()*1000000);
				 int date= (int)(Math.random()*30); 
				 
				 int categorie=0;
				 if(tab[12].equals("jeux"))categorie=1;
				 if(tab[12].equals("utilitaire"))categorie=2;
				 if(tab[12].equals("info"))categorie=3;
				 if(tab[12].equals("autre"))categorie=4;
				 
				 boolean payante = (tab[3].equals("1"))? true:false;
				 
				 
				 String s = "INSERT INTO applications VALUES("+(indice)+",\""+tab[1]+"\",\""+tab[6]+"-"+tab[7]+"\","+categorie+" ,"+tab[11]+", \""+tab[13]+"\", "+payante+", \"2013-04-"+date+"\") ";
				 System.out.println(s);
				 state.executeUpdate(s);
				 if(payante){
					 s = "INSERT INTO payante VALUES("+(indice)+","+tab[2]+","+tab[4]+") ";
					 System.out.println(s);
					 state.executeUpdate(s);
				 }
				 int d ,d1,d2,d3,d4;
				 d= Integer.parseInt(tab[5]);
				 d4 = (d%2==1)?1:0;
				 d1 =(d/8==1)?1:0;
				 d2 =((d-d1*8)/4==1)?1:0;
				 d3=((d-d1*8-d2*4)/2==1)?1:0;
				 
				 s = "INSERT INTO droits VALUES("+(indice)+","+d1+","+d2+","+d3+","+d4+") ";
				 System.out.println(s);
				 state.executeUpdate(s);
				 
				 
				 result = state.executeQuery("SELECT ID FROM systeme_exploitation WHERE nom='"+tab[8]+"' AND  version='"+tab[9]+"-"+tab[10]+"'");
				 result.next();
				 s = "INSERT INTO applicationsSE VALUES("+(indice)+","+result.getInt("ID")+") ";
				 System.out.println(s);
				 state.executeUpdate(s);
				 
				 
				 
				 
				 */
				
		  }
	  }
      
 
      result.close();
      state.close();
          
    } catch (Exception e) {
      e.printStackTrace();
    }     
  }
}

