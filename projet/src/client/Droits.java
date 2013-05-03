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

public class Droits extends JPanel implements MouseListener
{
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();

    String id = ""; 
    
    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");
    
    Droits(String id)
    {
	setSize(779, 456);
	setLayout(new BorderLayout());
	this.id = id;
	addheader();
	addcentre();
	addsud();
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
    
    public String conv(String s)
    {
	return (s.equals("true") ? "Oui" : "Non");
    }
    
    public void addcentre()
    {
	SqlData r = Client.getInstance().getConnect().request("get_droitApp", id);
	JPanel droits = new JPanel();
	droits.setLayout(new GridLayout(r.getNbCol() - 1, 2));
	for(int i = 1; i < r.getNbCol(); i++)
	    {
		droits.add(new JLabel(r.getNomCol(i)));
		droits.add(new JLabel((conv(r.data[0][i]))));
	    }
	centre = new JScrollPane(droits);
    }
    
    public void addsud()
    {
	sud.add(retour);
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
	if(e.getSource() == retour)
	    {
		Client.getInstance().getFen().setContentPane(new Peripherique());
	    }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}


}

