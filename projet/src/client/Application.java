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
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class Application extends JPanel implements MouseListener
{
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;
    
    JScrollPane sp;
    
    JPanel conteneur_ouest = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel conteneur_deconnexion = new JPanel();
    JPanel conteneur_accueil = new JPanel();

    JComboBox elstar = new JComboBox(new String[] {"0", "1", "2", "3", "4", "5"});
    
    JPanel commentaires = new JPanel();
    JPanel header = new JPanel();
    JPanel infos = new JPanel();
    JPanel nom = new JPanel();
    JPanel avis = new JPanel();

    String id = "";
    String nom_appli = "";
    String[] os_compatible = new String[1];
    String prix = "";

    JButton acheter = new JButton("Acheter");
    JButton gratuit = new JButton("Gratuit");
    JButton deconnexion = new JButton("deconnexion");
    JButton accueil = new JButton("Accueil");
    
    JButton valid_avis = new JButton("Envoyer");
    JTextArea txt_avis = new JTextArea(5, 20);

    Application(String id, String nom_application)
    {
	setSize(x, y);

	this.id = id;
	this.nom_appli = nom_application;
	setLayout(new BorderLayout());
	conteneur_ouest.setLayout(new BorderLayout());
	conteneur_centre.setLayout(new BorderLayout());
	header.setLayout(new BorderLayout());
	infos.setLayout(new GridLayout(6,1));
	nom.setLayout(new BorderLayout());

	req_infos(id);
	req_avis(id);
	
	//avis.setPreferredSize(new Dimension(300, 400));

	nom.add("Center", new JLabel(nom_application));

	conteneur_deconnexion.add(deconnexion);
	conteneur_accueil.add(accueil);
	header.add("West", new JPanel().add(conteneur_deconnexion));
	JTextField recherche = new JTextField(20);
	recherche.addKeyListener(new ClavierListener());
	header.add("Center", new JPanel().add(recherche));
	header.add("East",new JPanel().add(conteneur_accueil));
	header.add("South", nom);
	
	sp = new JScrollPane(avis)
	    {
		public Dimension getPreferredSize()
		{
		    return new Dimension(200, 200);
		}
	    };
	
	deconnexion.addMouseListener(this);
	accueil.addMouseListener(this);
	add("North", header);
	add("West", infos);
	add("Center", sp);
    }
    
    public void req_infos(String id)
    {
	SqlData r = Client.getInstance().getConnect().request("get_appId",id);
	nom.add("East", new JLabel("v" + r.data[0][2]+"\t"));
	infos.add(new JLabel("Catégorie:" + r.data[0][11]));
	infos.add(new JLabel("Os:"));
	SqlData os = Client.getInstance().getConnect().request("get_SeApp",id);
	os_compatible = new String[os.getNbLigne()];
	JPanel list_os = new JPanel();
	JPanel content_take = new JPanel();
	
	list_os.setLayout(new GridLayout(os.getNbLigne(), 1));
	for(int i = 0;i < os.getNbLigne(); i++)
	    {
		os_compatible[i] = os.data[i][0];
		list_os.add(new JLabel(os.data[i][1] + "(" + os.data[i][2]+")"));
	    }
	infos.add(list_os);
	if(r.data[0][6].equals("true"))
	    {
		acheter = new JButton("Acheter (" + r.data[0][9] +")");
		prix = r.data[0][9];
		content_take.add(acheter);
		acheter.addMouseListener(this);
	    }
	else
	    {
		content_take.add(gratuit);
		gratuit.addMouseListener(this);
	    }
	infos.add(content_take);
	if(possede())
	    {
		JPanel content_avis_appl = new JPanel();
		content_avis_appl.setLayout(new BorderLayout());
		JPanel content_valid = new JPanel();
		JPanel content_elstar = new JPanel();
		JPanel content_txt = new JPanel();
		content_valid.add(valid_avis);
		content_valid.add(txt_avis);
		content_avis_appl.setBorder(BorderFactory.createLineBorder(Color.black));
		content_avis_appl.add("North", content_valid);
		content_avis_appl.add("Center", content_txt);
		content_elstar.add(new JLabel("Etoiles : "));
		content_elstar.add(elstar);
		infos.add(content_elstar);
		infos.add(content_avis_appl);
		valid_avis.addMouseListener(this);
	    }	
    }
    
    
    public boolean possede()
    {
	SqlData r = Client.getInstance().getConnect().request("get_appInstalMe");
	for(int i = 0 ; i < r.getNbLigne(); i++)
	    {
		if(r.data[i][0].equals(id))
		    {
			return true;
		    }
	    }
	return false;
    }
    
    public void req_avis(String id)
    {
	SqlData r = Client.getInstance().getConnect().request("get_avisApp",id);
	//read_sqldata(r);
	avis.setLayout(new GridLayout(r.getNbLigne(), 1, 10, 10));
	for(int i = r.getNbLigne()-1; i > 0; i--)
	    {
		SqlData nom = Client.getInstance().getConnect().request("get_infoId", r.data[i][0]);		
		JPanel avis_aff = new JPanel();
		avis_aff.setBorder(BorderFactory.createLineBorder(Color.black));
		avis_aff.setLayout(new BorderLayout());		
		avis_aff.add("North", new JLabel(nom.data[0][1]));
		avis_aff.add("Center", new JLabel("<html>"+r.data[i][3]+"<html/>"));
		avis_aff.add("South", new JLabel(r.data[i][2] + "    " + r.data[i][4]));		    
		avis.add(avis_aff);
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
	if(e.getSource() == gratuit)
	    {
		Client.getInstance().getFen().setContentPane(new Achat(id, true, os_compatible, prix, nom_appli));
	    }
	if(e.getSource() == acheter)
	    {
		Client.getInstance().getFen().setContentPane(new Achat(id, false, os_compatible, prix, nom_appli));
	    }
	if(e.getSource() == valid_avis)
	    {	
		System.out.println(elstar.getSelectedIndex());
		Client.getInstance().getConnect().request("add_avis", id, ""+elstar.getSelectedIndex(), txt_avis.getText());		
		Client.getInstance().getFen().setContentPane(new Application(id, nom_appli));
	    }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}