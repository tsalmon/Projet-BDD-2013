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

public class Accueil extends JPanel
{
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;
    JPanel conteneur = new JPanel();
    Accueil()
    {
	setSize(x, y);
	/*
	conteneur.setLayout(new GridLayout(3, 3));
	conteneur.add(new JButton("a"));
	conteneur.add(new JButton("b"));
	conteneur.add(new JButton("c"));
	
	conteneur.add(new JButton("d"));
	conteneur.add(new JButton("e"));
	conteneur.add(new JButton("f"));
	
	conteneur.add(new JButton("g"));
	conteneur.add(new JButton("h"));
	conteneur.add(new JButton("i"));
	
	add(conteneur);
	*/
	
	
	  conteneur.setLayout(new BorderLayout());
	  conteneur.add("Center",new JButton("Centre"));
	  conteneur.add("North",new JButton("Nord"));
	  conteneur.add("South",new JButton("Sud"));
	  conteneur.add("East",new JButton("est"));
	  conteneur.add("West",new JButton("ouest"));
	  add(conteneur);
	  setVisible(true);
	
    }
}