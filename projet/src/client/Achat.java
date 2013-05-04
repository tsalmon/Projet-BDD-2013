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

public class Achat extends JPanel implements MouseListener
{
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();

    String id = "";
    
    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    Achat(String id, boolean gratuit)
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

    public void addcentre()
    {
	
    }
    
    public void addsud()
    {
	
    }
	
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}


}
