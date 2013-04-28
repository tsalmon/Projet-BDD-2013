package client;

import java.lang.reflect.Array;
import java.util.Comparator;


class SqlData { //stock juste les donnes dune requete 
	String[] nomCol;
	String[] typCol;
	String[][] data;
	
	public SqlData(String[] nomCol, String[] typCol, String[][] data){
		this.nomCol= nomCol;
		this.typCol=typCol;
		this.data=data;
	}
	
	public int getNbLigne(){
		return data.length;
	}
	
	public int getNbCol(){
		return nomCol.length;
	}

	public String[] getNomCol(){
		return nomCol;
	}

	public String getNomCol(int i){
		return nomCol[i];
	}
		
	public String[] getTypCol(){
		return typCol;
	}
	
	public String getTypCol(int i){
		return typCol[i];
	}
	
	public int getInt(int ligne, int col){
		return Integer.parseInt(data[ligne][col]);
	}
	
	
	public String getString(int ligne, int col){
		return data[ligne][col];
	}
	
	public boolean getBoolean(int ligne, int col){
		return (data[ligne][col].equals("true"))? true:false;
	}
	
	public void trie(final int col){
		  java.util.Arrays.sort(data, new Comparator(){
			  public int compare(Object o1, Object o2) {
			      return ((String) ((Object[]) o1)[col]).compareTo((String) ((Object[]) o2)[col]);
			   }
			}); 
	}

}
