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

    JButton[] peripheriques;
    JButton[] applications;
    JPanel[] conteneur_periph;
    JPanel[] conteneur_appl;
    String[] id_periph;
    
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

    Peripherique(JButton[] liste_appli)
    {
	System.out.println(liste_appli.length);
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
      
	applications = liste_appli;
	liste_peripherique();

	if(liste_appli.length > 0)
	    {
		droite.setLayout(new GridLayout(liste_appli.length , 1, 20, 20));	
		conteneur_appl = new JPanel[liste_appli.length];
		for(int i=0; i < liste_appli.length; i++)
		    {
			conteneur_appl[i] = new JPanel();
			conteneur_appl[i].add(applications[i]);
			droite.add(conteneur_appl[i]);
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
	peripheriques = new JButton[r.getNbLigne()];
	conteneur_periph = new JPanel[r.getNbLigne()];
	id_periph = new String[r.getNbLigne()];
	gauche.setLayout(new GridLayout(r.getNbLigne(), 1));
	for(int i= 0; i < r.getNbLigne(); i++)
	    {
		id_periph[i] = r.data[i][0];
		peripheriques[i] = new JButton("<html>"+r.data[i][4]+"<br>"+r.data[i][5]+" "+r.data[i][6]+"</html>");
		conteneur_periph[i] = new JPanel();
		conteneur_periph[i].add(peripheriques[i]);
		gauche.add(conteneur_periph[i]);
		peripheriques[i].addMouseListener(this);
	    }
	scroll_gauche = new JScrollPane(gauche);
    }
    
    public void liste_application(String peripherique)
    {
	System.out.println(peripherique);
	SqlData r = Client.getInstance().getConnect().request("get_appInstalPeriph", peripherique);
	//read_sqldata(r);	
	applications = new JButton[r.getNbLigne()];
	
	for(int i= 0; i < r.getNbLigne(); i++)
	    {
		SqlData appli = Client.getInstance().getConnect().request("get_appId", r.data[i][0]);
		//read_sqldata(appli);
		applications[i] = new JButton(appli.data[0][1]);
	    }
	Client.getInstance().getFen().setContentPane(new Peripherique(applications));
    }


    public void mouseClicked(MouseEvent e)
    {
	for(int i=0; i < peripheriques.length; i++)
	    {
		if(e.getSource() == peripheriques[i])
		    {
			liste_application(id_periph[i]);
		    }
	    }
        if(e.getSource() == accueil)
            {
                System.out.println("acc");
                Client.getInstance().getFen().setContentPane(new Accueil());
            }
        if(e.getSource() == deconnexion)
            {
                Client.getInstance().getConnect().dialog("DISCONNECT");
                Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
	    }
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