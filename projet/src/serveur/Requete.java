package serveur;

import java.sql.*;

class Requete {
	private String name;
	private String description;
	private int[] param;
	private int paramADefinir;
	private int droit;
	private PreparedStatement prepare;
	private boolean isSelect;

	public Requete(String name, String description, int[] param, int droit, String query ){
		try{
			this.prepare = Serveur.conn.prepareStatement(query);
			isSelect=(query.charAt(0)=='S')?true:false;
			this.name= name;
			this.description=description;
			this.param= param;
			paramADefinir=0; if(param!=null)for(int i=0;i<param.length;i++)if(param[i]<=4)paramADefinir++;
			this.droit=droit;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getDroit(){
		return this.droit;
	}
	
	public boolean getIsSelect(){
		return this.isSelect;
	}
	
	public ResultSet execute(int id,String[] args){
		try {
			if( this.param!=null && ((args!=null && paramADefinir==args.length) || (args==null && paramADefinir==0))  ){
				int indice=0;
				for(int i=0;i<this.param.length;i++){
					switch(this.param[i]){
						case 1:
							prepare.setInt(i+1,Integer.parseInt(args[indice++]));
							break;
						case 2: 
							prepare.setString(i+1,args[indice++]);
							break;
						case 3:
							prepare.setBoolean(i+1,(args[indice++].equals("true"))?true:false);
							break;
						case 4:
							prepare.setDate(i+1,new Date( Long.parseLong(args[indice++])));
							break;
						case 5:
							prepare.setInt(i+1,id);
							break;
						case 6:
							prepare.setDate(i+1,new Date(System.currentTimeMillis()));
							break;
						default:
							return null;
					}
				}
				if(!isSelect){prepare.executeUpdate();return null;}
				return prepare.executeQuery();
			}else if(this.param==null){
				return prepare.executeQuery();
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}