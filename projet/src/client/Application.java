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

public class Application extends JPanel
{
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;
    
    JPanel conteneur_ouest = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel commentaires = new JPanel();
    JPanel header = new JPanel();
    
    Application(String nom_application)
    {
	Client.getInstance().getFen().setTitle(nom_application);
	setSize(x, y);
	
	setLayout(new BorderLayout());
	conteneur_ouest.setLayout(new BorderLayout());
	conteneur_centre.setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	
	header.add(new JButton("deco"));
	header.add(new JTextField(10));
	header.add(new JButton("Accueil"));
	
	add("North", header);
	add("West", new JButton("fiche"));
	add("Center", new JButton("avis"));
    }
}