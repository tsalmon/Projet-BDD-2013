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
    
    // conteneurs
    JPanel conteneur_est = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel conteneur_ouest = new JPanel();

    //boutons
    JButton deconnexion = new JButton("Deconnexion");
    JButton view_all = new JButton("Toutes les applications");
    JButton modifier_profil = new JButton("modifier");
    JButton view_more_reco = new JButton("Voir plus");
    JButton view_more_top = new JButton("Voir plus");
    JButton view_periph = new JButton("Voir périphériques");
    
    //text
    JTextField recherche = new JTextField(10);
   
    
    Accueil()
    {
	setSize(x, y);

	// on met une grille de 6 lignes dans chacune des trois colonnes
	setLayout(new BorderLayout());
	conteneur_est.setLayout(new GridLayout(6,1));
	conteneur_centre.setLayout(new GridLayout(6,1));
	conteneur_ouest.setLayout(new GridLayout(6,1));
	
	conteneur_est.add(deconnexion);
	conteneur_est.add(new JLabel("Bienvenue"));
	conteneur_est.add(new JLabel("Nom"));
	conteneur_est.add(new JLabel("Nb_mela"));
	conteneur_est.add(view_periph);
	conteneur_est.add(modifier_profil);

	conteneur_centre.add(recherche);
	conteneur_centre.add(new JLabel("Applications recommandées"));
	conteneur_centre.add(new JLabel("App1"));
	conteneur_centre.add(new JLabel("App2"));
	conteneur_centre.add(new JLabel("App3"));
	conteneur_centre.add(view_more_reco);

	conteneur_ouest.add(recherche);
	conteneur_ouest.add(new JLabel("Top Applications"));
	conteneur_ouest.add(new JLabel("App1"));
	conteneur_ouest.add(new JLabel("App2"));
	conteneur_ouest.add(new JLabel("App3"));
	conteneur_ouest.add(view_more_top);

	add("East", conteneur_est);
	add("Center", conteneur_centre);	
	add("West", conteneur_ouest);
	setVisible(true);
    }
}