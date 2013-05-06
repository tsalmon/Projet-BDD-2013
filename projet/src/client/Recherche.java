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
import javax.swing.JScrollPane;

public class Recherche extends JPanel implements MouseListener
{
    String expr = "";
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    JButton liste_appli[] = new JButton[1];
    String[] id_applis;
    JButton liste_periph[] = new JButton[1];
    String[] id_periph;

    JButton applis;
    JButton periph;

    int selected = 0;
    
    SqlData a_req;
    SqlData p_req;
    Recherche(String terme, int n)
    {
	selected = n;
	expr = terme;

	setSize(779, 456);
        setLayout(new BorderLayout());
	a_req = Client.getInstance().getConnect().request("get_appTag", "%"+expr+"%", "%"+expr+"%");
        p_req = Client.getInstance().getConnect().request("get_periphNom", "%"+expr+"%");
	addheader();
        addcentre(n);
        add("North", header);
        add("Center", centre);
        setVisible(true);
    }

    public void addheader()
    {
	JPanel header_sup = new JPanel();
	JPanel header_inf = new JPanel();
	JPanel content_acc = new JPanel();
        JPanel content_text = new JPanel();
        JPanel content_deco = new JPanel();
        JPanel content_appli = new JPanel();
	JPanel content_periph = new JPanel();
	JTextField recherche = new JTextField(20);

        header_sup.setLayout(new GridLayout(1, 3));
        header_inf.setLayout(new GridLayout(1, 2));
	header.setLayout(new GridLayout(2, 1));

	applis = new JButton("Applications (" +a_req.getNbLigne()+")");
	periph = new JButton("Peripheriques (" +p_req.getNbLigne() +")");
	content_appli.add(applis);
	content_periph.add(periph);
	
        content_acc.add(accueil);
        content_text.add(recherche);
        content_deco.add(deconnexion);
        header_sup.add(content_deco);
        header_sup.add(content_text);
        header_sup.add(content_acc);
	if(a_req.getNbLigne() > 0)
	    header_inf.add(content_appli);
	else
	    header_inf.add(new JLabel(""));
	if(p_req.getNbLigne() > 0)
	    header_inf.add(content_periph);
	else
	    header_inf.add(new JLabel(""));
	header.add(header_sup);
	header.add(header_inf);

	applis.addMouseListener(this);
	periph.addMouseListener(this);
        accueil.addMouseListener(this);
        deconnexion.addMouseListener(this);
        recherche.addKeyListener(new ClavierListener());
    }

    public void liste_applis()
    {
	selected = 1;

	id_applis = new String[a_req.getNbLigne()];
	JPanel[] content = new JPanel[a_req.getNbLigne()];
	JPanel grille = new JPanel();
	grille.setLayout(new GridLayout(a_req.getNbLigne(), 1));
	liste_appli = new JButton[a_req.getNbLigne()];
	for(int i = 0; i < a_req.getNbLigne(); i++)
	    {
		id_applis[i] = a_req.data[i][0];
		content[i] = new JPanel();
		liste_appli[i] = new JButton("<html>" + a_req.data[i][1] + "<br><i>" + a_req.data[i][5] + "</i></html>");
		content[i].add(liste_appli[i]);
		grille.add(content[i]);
		liste_appli[i].addMouseListener(this);
	    }
	centre = new JScrollPane(grille);
    }
    
    public void liste_periphs()
    {
	selected = 2;
	id_periph = new String[p_req.getNbLigne()];
	JPanel[] content = new JPanel[a_req.getNbLigne()];
	JPanel grille = new JPanel();
	grille.setLayout(new GridLayout(p_req.getNbLigne(), 1));
	liste_periph = new JButton[p_req.getNbLigne()];
	for(int i = 0 ; i < p_req.getNbLigne(); i++)
	    {
		id_periph[i] = p_req.data[i][0];
		content[i] = new JPanel();
		liste_periph[i] = 
		    new JButton("<html>" + p_req.data[i][1]+" ("+ p_req.data[i][2] + ")<br>" + 
				p_req.data[i][3] + "</html>");
		content[i].add(liste_periph[i]);
		grille.add(content[i]);
		liste_periph[i].addMouseListener(this);		
	    }
	centre = new JScrollPane(grille);
    }

    public void addcentre(int x)
    {
	if(x == 0)
	    {
		if(a_req.getNbLigne() > 0)
		    liste_applis();
		else if(p_req.getNbLigne() > 0)
		    liste_periphs();
		else
		    centre = new JScrollPane(new JLabel("Pas de resultats"));
	    }
	else if(x == 1)
	    {
		liste_applis();
	    }
	else
	    {
		liste_periphs();		
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
	if(selected != 1 && e.getSource() == applis)
	    {
		Client.getInstance().getFen().setContentPane(new Recherche(expr, 1));
	    }
	if(selected != 2 && e.getSource() == periph)
	    {
		Client.getInstance().getFen().setContentPane(new Recherche(expr, 2));
	    }
	if(selected == 1)
	    {
		for(int i = 0 ; i < liste_appli.length; i++)
		    {
			if(e.getSource() == liste_appli[i])
			    {
				Client.getInstance().getFen().setContentPane(new Application(id_applis[i], liste_appli[i].getText()));
			    }
		    }
	    }
	else if(selected == 2)
	    {
		for(int i = 0 ; i < liste_periph.length; i++)
		    {
			if(e.getSource() == liste_periph[i])
			    {
				Client.getInstance().getFen().setContentPane(new VoirPeripherique(id_periph[i]));
			    }
		    }		
	    }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}