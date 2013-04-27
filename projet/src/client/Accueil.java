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
    JPanel conteneur_nord = new JPanel();
    JPanel conteneur_nord_nord = new JPanel();
    JPanel conteneur_nord_sud = new JPanel();
    
    Accueil()
    {
	setSize(x, y);
	
	conteneur_nord.setLayout(new BorderLayout());
	conteneur_nord_nord.setLayout(new BorderLayout());
	conteneur_nord_sud.setLayout(new BorderLayout());
	
	// top 1ere barre 
	conteneur_nord_nord.add("West",new JButton("Deconnexion"));
	conteneur_nord_nord.add("Center", new JTextField(10));
	conteneur_nord_nord.add("East", new JButton("Toutes les applications") );
	
	// top 2e barre
	conteneur_nord_sud.add("West",new JLabel("Bienvenue"));
	conteneur_nord_sud.add("Center", new JLabel("Applications recommandées"));
	conteneur_nord_sud.add("East", new JLabel("Top Applications") );
	
	conteneur_nord.add("North",conteneur_nord_nord);
	conteneur_nord.add("South",conteneur_nord_sud);

	

	setLayout(new BorderLayout());
	add("Center",new JButton("Centre"));
	add("North", conteneur_nord);
	add("East",new JButton("est"));
	add("West",new JButton("ouest"));
	setVisible(true);
	
    }
}