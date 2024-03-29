//import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
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
import java.awt.Dimension;

public class Peripherique extends JPanel implements MouseListener
{

    JButton[] desinst_periph = new JButton[1];
    JButton[] peripheriques = new JButton[1];
    JButton[] applications = new JButton[1];
    JButton[] desinst_appli =  new JButton[1];
    JButton[] droits = new JButton[1];
    JButton[] app_comp = new JButton[1];
    JPanel[] conteneur_periph;
    JPanel[] conteneur_appl;
    String[] id_periph;
    String[] nom_appli;
    String[] id_appli;

    String id_periph_select ="";

    JPanel header = new JPanel();
    JPanel center = new JPanel();
    
    JPanel conteneur_search = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_acc = new JPanel();
    
    JPanel gauche = new JPanel();
    JPanel droite = new JPanel();
    
    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField cherche = new JTextField(20);
   
    JScrollPane scroll_gauche = new JScrollPane(new JLabel("Liste peripheriques"));
    JScrollPane scroll_droite = new JScrollPane(new JLabel("Liste applications"));

    Peripherique()
    {
	cherche.addKeyListener(new ClavierListener());
	setSize(779, 456);
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	center.setLayout(new GridLayout(1, 2));

	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_search.add(cherche);
	header.add(conteneur_deco);
	header.add(conteneur_search);
	header.add(conteneur_acc);
	
	liste_peripherique();
	
	center.add(scroll_gauche);
	center.add(scroll_droite);
	accueil.addMouseListener(this);
	deconnexion.addMouseListener(this);
	add("North", header);
	add("Center", center);
    }

    Peripherique(String[] id, String[] liste_appli, String id_periph)
    {
	id_periph_select = id_periph;
	nom_appli = liste_appli;
	id_appli = id;
	setSize(779, 456);
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	center.setLayout(new GridLayout(1, 2));

	desinst_appli = new JButton[id.length];
	droits = new JButton[id.length];
	
	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_search.add(cherche);
	header.add(conteneur_deco);
	header.add(conteneur_search);
	header.add(conteneur_acc);
      
	applications = new JButton[liste_appli.length];
	for(int i = 0; i < liste_appli.length; i++)
	    {
		applications[i] = new JButton(liste_appli[i]);
	    }
	liste_peripherique();
	
	if(liste_appli.length > 0)
	    {
		droite.setLayout(new GridLayout(liste_appli.length , 1, 20, 20));	
		conteneur_appl = new JPanel[liste_appli.length];
		for(int i=0; i < liste_appli.length; i++)
		    {
			desinst_appli[i] = new JButton("DEL");
			droits[i] = new JButton("Droits");
			conteneur_appl[i] = new JPanel();
			conteneur_appl[i].add(desinst_appli[i]);
			conteneur_appl[i].add(applications[i]);
			conteneur_appl[i].add(droits[i]);
			droite.add(conteneur_appl[i]);
			droits[i].addMouseListener(this);
			desinst_appli[i].addMouseListener(this);
			applications[i].addMouseListener(this);
		    }
		
		scroll_droite = new JScrollPane(droite);
	    }
	else
	    {
		scroll_droite = new JScrollPane(new JLabel("Aucune applications installées sur ce périphérique."));
	    }

	center.add(scroll_gauche);
	center.add(scroll_droite);
	accueil.addMouseListener(this);
	deconnexion.addMouseListener(this);
	add("North", header);
	add("Center", center);	
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

    public void liste_peripherique()
    {
        SqlData r = Client.getInstance().getConnect().request("get_periphMe");	
	gauche.setLayout(new GridLayout(r.getNbLigne(), 1));
	app_comp = new JButton[r.getNbLigne()];
	peripheriques = new JButton[r.getNbLigne()];
	conteneur_periph = new JPanel[r.getNbLigne()];
	desinst_periph = new JButton[r.getNbLigne()];
	id_periph = new String[r.getNbLigne()];
	for(int i= 0; i < r.getNbLigne(); i++)
	    {
		id_periph[i] = r.data[i][0];
		desinst_periph[i] = new JButton("DEL");
		app_comp[i] = new JButton("CMP");
		peripheriques[i] = new JButton("<html>"+r.data[i][5]+"<br>"+r.data[i][5]+" ("+r.data[i][7]+")</html>");
		conteneur_periph[i] = new JPanel();
		conteneur_periph[i].add(desinst_periph[i]);
		conteneur_periph[i].add(peripheriques[i]);
		conteneur_periph[i].add(app_comp[i]);

		gauche.add(conteneur_periph[i]);
		app_comp[i].addMouseListener(this);
		desinst_periph[i].addMouseListener(this);
		peripheriques[i].addMouseListener(this);
	    }
	scroll_gauche = new JScrollPane(gauche);
	
    }
    
    public void liste_application(String peripherique)
    {
	SqlData r = Client.getInstance().getConnect().request("get_appInstalPeriphMe", peripherique);
        nom_appli = new String[r.getNbLigne()];
	id_appli = new String[r.getNbLigne()];
	
	for(int i= 0; i < r.getNbLigne(); i++)
	    {
		SqlData appli = Client.getInstance().getConnect().request("get_appId", r.data[i][0]);
		id_appli[i] = appli.data[0][0];
		nom_appli[i] = appli.data[0][1];
	    }
	Client.getInstance().getFen().setContentPane(new Peripherique(id_appli, nom_appli, peripherique));
    }

    public void voirDroits(MouseEvent e)
    {
	for(int i = 0; i < droits.length; i++)
	    {
		if(e.getSource() == droits[i])
		    {
			Client.getInstance().getFen().setContentPane(new Droits(id_appli[i]));
		    }
	    } 
    }
    
    public void desinstaller_appli(MouseEvent e)
    {
	for(int i = 0; i < desinst_appli.length; i++)
	    {
		if(e.getSource() == desinst_appli[i])
		    {
			int reponse = 
			    JOptionPane.showConfirmDialog(this, 
							  "Etes vous sur de désinstaller cette application??", 
							  "Suppression", 
							  JOptionPane.YES_NO_OPTION);
			if(reponse == JOptionPane.YES_OPTION)
			    {
				SqlData r = Client.getInstance().getConnect().request("del_appPeriph", id_periph_select ,id_appli[i]);
				Client.getInstance().getFen().setContentPane(new Peripherique());
			    }
		    }
	    } 

    }

    public void desintaller_periph(MouseEvent e)
    {
	for(int i = 0; i < desinst_periph.length; i++)
	    {
		if(e.getSource() == desinst_periph[i])
		    {
			int reponse = 
			    JOptionPane.showConfirmDialog(this, 
							  "Etes vous sur de désinstaller ce Peripherique?", 
							  "Suppression", 
							  JOptionPane.YES_NO_OPTION);
			if(reponse == JOptionPane.YES_OPTION)
			    {
				SqlData r = Client.getInstance().getConnect().request("del_periphMe", id_periph[i]);
				Client.getInstance().getFen().setContentPane(new Peripherique());
			    }
		    }
	    } 
    }
    
    public void mouseClicked(MouseEvent e)
    {
	if(e.getSource() == accueil)
            {
                Client.getInstance().getFen().setContentPane(new Accueil());
            }
        if(e.getSource() == deconnexion)
            {
                Client.getInstance().getConnect().dialog("DISCONNECT");
                Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
	    }
	for(int i=0; i < peripheriques.length; i++)
	    {
		if(e.getSource() == peripheriques[i])
		    {
			liste_application(id_periph[i]);
		    }
	    }
	for(int i=0; i < applications.length; i++)
	    {
		if(e.getSource() == applications[i])
		    {
			Client.getInstance().getFen().setContentPane(new Application(id_appli[i] ,nom_appli[i]));
		    }
	    }
	for(int i = 0 ; i < app_comp.length; i++)
	    {
		if(e.getSource() == app_comp[i])
		    {
			Client.getInstance().getFen().setContentPane(new ToutVoir(id_periph[i]));
		    }
	    }
	voirDroits(e);
	desinstaller_appli(e);
	desintaller_periph(e);
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    /* test hors-connexion
       public static void main(String[] args)
       {
       JFrame f = new JFrame();
       f.setSize(779, 456);
       f.setContentPane(new Peripherique());
       f.setVisible(true);
       }
    */
}