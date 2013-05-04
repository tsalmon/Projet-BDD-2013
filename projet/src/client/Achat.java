import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;

public class Achat extends JPanel implements MouseListener
{
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();

    JComboBox liste_peripheriques;

    String id = "";
    String nom = "";

    LinkedList<String> nom_periph = new LinkedList<String>();
    LinkedList<String> id_periph = new LinkedList<String>();
    
    JButton ok = new JButton("acheter");
    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    Achat(String id, boolean gratuit, String[] os, String prix, String nom)
    {
	System.out.println(gratuit);
	setSize(779, 456);
	setLayout(new BorderLayout());
	this.id = id;
	this.nom = nom;
	
	int k = 0;
	for(int i = 0; i < os.length; i++)
	    {
		SqlData r = Client.getInstance().getConnect().request("get_periphMeSe", os[i]);
		if(r.getNbLigne() > 0)
		    {
			nom_periph.add(r.data[i][5]);
			id_periph.add(r.data[i][0]);
		    }
	    }
	addheader();
	addcentre(id_periph.size() == 0);
	addsud(gratuit, prix);
	add("North", header);
	add("Center", centre);
	add("South", sud);
	setVisible(true);
    }
    
    public void addheader()
    {
	header.setLayout(new GridLayout(1, 3));
	JPanel content_acc = new JPanel();
        JPanel content_text = new JPanel();
        JPanel content_deco = new JPanel();
        content_acc.add(accueil);
        JTextField recherche = new JTextField(20);
        recherche.addKeyListener(new ClavierListener());
        content_text.add(recherche);
        content_deco.add(deconnexion);
        header.add(content_deco);
        header.add(content_text);
        header.add(content_acc);
        accueil.addMouseListener(this);
        deconnexion.addMouseListener(this);
    }

    public void addcentre(boolean incompatible)
    {
	if(incompatible == true)
	    {
		JPanel annonce = new JPanel();
		annonce.add(new JLabel("Désolé, l'application est incompatible avec vos peripheriques"));
		centre = new JScrollPane(annonce);
	    }
	else
	    {
		JPanel content_choix = new JPanel();
		JPanel content_label = new JPanel();
		JPanel content_periph = new JPanel();
		content_choix.setLayout(new BorderLayout());
		content_label.add(new JLabel("Choisissez le peripherique:"));
		String[] liste_nom = new String[nom_periph.size()];
		for(int i = 0 ; i < liste_nom.length; i++)
		    {
			liste_nom[i] = nom_periph.get(i);
		    }
		liste_peripheriques = new JComboBox(liste_nom);
		content_periph.add(liste_peripheriques);
		content_choix.add("North", content_label);
		content_choix.add("Center", content_periph);
		centre = new JScrollPane(content_choix);
	    }
    }
    
    public void addsud(boolean gratuit, String prix)
    {
	JPanel content_ok = new JPanel();
	JPanel content_retour = new JPanel();
	if(gratuit)
	    ok = new JButton("Prendre");
	else
	    ok = new JButton("Acheter : " + prix);
	content_ok.add(ok);
	content_retour.add(retour);
	
	sud.setLayout(new BorderLayout());
	sud.add("West", content_ok);
	sud.add("East", content_retour);
	
	ok.addMouseListener(this);
	retour.addMouseListener(this);
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
	if(e.getSource() == ok)
	    {
		int x =  liste_peripheriques.getSelectedIndex();
		SqlData r = Client.getInstance().getConnect().request("add_appPeriph",
								      id_periph.get(x),
								      id
								      );
	    }
	if(e.getSource() == retour)
	    {
		Client.getInstance().getFen().setContentPane(new Application(id, nom));
	    }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
