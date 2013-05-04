import java.lang.Math;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Accueil extends JPanel implements MouseListener
{ 
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;   
    
    // conteneurs
    JPanel conteneur_est = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel conteneur_ouest = new JPanel();
    
    // conteneur_forme
    JPanel content_deco            = new JPanel();
    JPanel content_recherche       = new JPanel();
    JPanel content_tout            = new JPanel();
    JPanel content_titre_bienvenue = new JPanel();
    JPanel content_titre_reco      = new JPanel();
    JPanel content_titre_top       = new JPanel();
    JPanel content_info            = new JPanel();
    JPanel content_nb              = new JPanel();
    JPanel content_periph          = new JPanel();
    JPanel content_modifier        = new JPanel();
    JPanel content_top_view        = new JPanel();
    JPanel content_reco_view       = new JPanel();

    //boutons
    JButton deconnexion = new JButton("Deconnexion");
    JButton view_all = new JButton("Toutes les applications");
    JButton modifier_profil = new JButton("modifier");
    JButton view_more_reco = new JButton("Voir plus");
    //JButton view_more_top = new JButton("Voir plus");
    JButton view_periph = new JButton("Voir périphériques");
    JButton reco_app1, reco_app2, reco_app3;
    JButton developpeur = new JButton("Bienvenue");
    //text
    private JTextField recherche;
    
    //Applications
    JButton[] b_reco_app;
    JPanel[] cont_reco_app; 
    JPanel[] cont_top_app; 
    JButton[] b_top_app;
    String[] n_reco_app;
    String[] n_top_app;
    String[] id_reco_app;
    String[] id_top_app;
    
    Accueil()
    {
	setSize(x, y);
	// on met une grille de 6 lignes dans chacune des trois colonnes

	setLayout(new BorderLayout());
	conteneur_est.setLayout(new GridLayout(6,1));
	conteneur_centre.setLayout(new GridLayout(6,1));
	conteneur_ouest.setLayout(new GridLayout(6,1));
	
	content_deco.add(deconnexion) ;
	recherche = new JTextField(20);
	content_recherche.add(recherche); 
	recherche.addKeyListener(new ClavierListener());
	content_tout.add(view_all);
	content_titre_bienvenue.add(developpeur); 
	content_titre_reco.add(new JLabel("Applications recommandées"));
	content_titre_top.add(new JLabel("Top Applications"));
	//need req
	infos();
       	reco();
	top();
	content_periph.add(view_periph);
	content_modifier.add(modifier_profil);  
	//content_top_view.add(view_more_top)   ;
	content_reco_view.add(view_more_reco);
	// OUEST
	conteneur_ouest.add(content_deco);
	conteneur_ouest.add(content_titre_bienvenue);
	conteneur_ouest.add(content_info);
	conteneur_ouest.add(content_nb);
	conteneur_ouest.add(content_periph);
	conteneur_ouest.add(content_modifier);
	// CENTRE
	conteneur_centre.add(content_recherche);
	conteneur_centre.add(content_titre_reco);
	for(int i=0; i < cont_reco_app.length ; i++)
	    {
		if(cont_reco_app[i] == null)
		    break;
		conteneur_centre.add(cont_reco_app[i]);
		b_reco_app[i].addMouseListener(this);
	    }
	conteneur_centre.add(content_reco_view);
	// EST
	conteneur_est.add(content_tout);
	conteneur_est.add(content_titre_top);
	
	for(int i = 0; i < cont_top_app.length; i++)
	    {
		conteneur_est.add(cont_top_app[i]);
		b_top_app[i].addMouseListener(this);
	    }
	
	// CONTROLE
	deconnexion.addMouseListener(this);
	view_all.addMouseListener(this);
	modifier_profil.addMouseListener(this);
	view_more_reco.addMouseListener(this);
	developpeur.addMouseListener(this);
	//view_more_top.addMouseListener(this);
	view_periph.addMouseListener(this);

	// ADD
	add("East", conteneur_est);
	add("Center", conteneur_centre);	
	add("West", conteneur_ouest);
	setVisible(true);
    }

    /*---Requetes---*/
    private void infos()
    {
	SqlData r = Client.getInstance().getConnect().request("get_infoMe");
	content_info.add(new JLabel("<html>Nom: " + r.data[0][1] + "<br/>Prenom: " + r.data[0][2] + "</html>"));
	String mela = r.data[0][5];
      	r = Client.getInstance().getConnect().request("get_nbApp");
	content_nb.add(new JLabel("<html>Mela: "+ mela +"<br/>Nombre App: "+ r.data[0][0] + "</html>"));
    }
    
    private void read_sqldata(SqlData r)
    {
	for(int i = 0; i < r.getNbCol(); i++)
	    {
		System.out.print(r.nomCol[i]);
	    }
	System.out.println("");
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		for(int j = 0; j < r.getNbCol(); j++)
		    {
			System.out.print(r.data[i][j] + " \t");
		    }
		System.out.println("");
	    }
    }
    
    private void reco()
    {
	SqlData r = Client.getInstance().getConnect().request("get_appRecomande");
	b_reco_app = new JButton[r.getNbLigne()];
	n_reco_app = new String[r.getNbLigne()];
	id_reco_app = new String[r.getNbLigne()];
	cont_reco_app = new JPanel[r.getNbLigne()];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		SqlData ap = Client.getInstance().getConnect().request("get_appId", r.data[i][0]);
		if(ap.getNbLigne() > 0)
		    {
			n_reco_app[i] = ap.data[0][1];
			id_reco_app[i] = ap.data[0][0];
		    }
	    }
	for(int i = 0; i < ((r.getNbLigne() < 3) ? r.getNbLigne() : 3); i++)
	    {
		SqlData ap = Client.getInstance().getConnect().request("get_appId", r.data[i][0]);
		if(ap.getNbLigne() > 0)
		    {
			b_reco_app[i] = new JButton("<html>" + ap.data[0][1] + "<br>"+ap.data[0][11]+"<html>");
		    }
		cont_reco_app[i] = new JPanel();
		cont_reco_app[i].add(b_reco_app[i]);
	    }
    }
    
    private void top()
    {
	SqlData r = Client.getInstance().getConnect().request("get_app");
	r.trie(r.getNbCol()-2);
	
	b_top_app = new JButton[Math.min(r.getNbLigne(),3)];
	n_top_app = new String[Math.min(r.getNbLigne(),3)];
	id_top_app = new String[Math.min(r.getNbLigne(),3)];
	cont_top_app = new JPanel[Math.min(r.getNbLigne(),3)];
	
	
    
	int k = 0;
	while(r.data[r.getNbLigne()-1-k][r.getNbCol()-2].equals("Null"))
	    {
		k++;
	    }
	for(int i = k ; i < k+3 && i < r.getNbLigne(); i++)
	    {
		b_top_app[i-k] =
		    new JButton("<html>" + r.data[r.getNbLigne()-1-i][1] + "<br>"+r.data[r.getNbLigne()-1-i][r.getNbCol()-2]+"<html>");
		n_top_app[i-k] = r.data[r.getNbLigne()-1-i][1];
		id_top_app[i-k] = r.data[r.getNbLigne()-1-i][0];
		cont_top_app[i-k] = new JPanel();
		cont_top_app[i-k].add(b_top_app[i-k]);
	    }
    }
    
    
    /*---CLICKS---*/
    public void mouseClicked(MouseEvent e)
    {
	for(int i = 0;i < 3; i++)
	    {
		if(cont_reco_app.length > i && e.getSource() == b_reco_app[i])
		    {
			Client.getInstance().getFen().setContentPane(new Application(id_reco_app[i], n_reco_app[i]));
		    }
		if(cont_top_app.length > i && e.getSource() == b_top_app[i])
		    {
			Client.getInstance().getFen().setContentPane(new Application(id_top_app[i], n_top_app[i]));
		    }
	    }
	if(e.getSource() == deconnexion)
	    {
		Client.getInstance().getConnect().dialog("DISCONNECT");
		Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
	    }
	if(e.getSource() == developpeur)
	    {
		Client.getInstance().getFen().setContentPane(new Developpeur());		
	    }
	if(e.getSource() == view_all)
	    {
		Client.getInstance().getFen().setContentPane(new ToutVoir('a'));
	    }
	if(e.getSource() == modifier_profil)
	    {
		Client.getInstance().getFen().setContentPane(new ModifierCompte());
	    }
	if(e.getSource() == view_more_reco)
	    {
		Client.getInstance().getFen().setContentPane(new ToutVoir('r'));
	    }
	if(e.getSource() == view_periph)
	    {
		Client.getInstance().getFen().setContentPane(new Peripherique());
	    }
    }

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
}