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

    // conteneur_forme
    JPanel content_deco = new JPanel();
    JPanel content_recherche = new JPanel();
    JPanel content_tout = new JPanel();
    JPanel content_titre_bienvenue = new JPanel();
    JPanel content_titre_reco = new JPanel();
    JPanel content_titre_top = new JPanel();
    JPanel content_info = new JPanel();
    JPanel content_nb = new JPanel();
    JPanel content_periph = new JPanel();
    JPanel content_modifier = new JPanel();
    JPanel content_reco_app1 = new JPanel();
    JPanel content_reco_app2 = new JPanel();
    JPanel content_reco_app3 = new JPanel();
    JPanel content_top_app1 = new JPanel();
    JPanel content_top_app2 = new JPanel();
    JPanel content_top_app3 = new JPanel();
    JPanel content_top_view = new JPanel();
    JPanel content_reco_view = new JPanel();


    //boutons
    JButton deconnexion = new JButton("Deconnexion");
    JButton view_all = new JButton("Toutes les applications");
    JButton modifier_profil = new JButton("modifier");
    JButton view_more_reco = new JButton("Voir plus");
    JButton view_more_top = new JButton("Voir plus");
    JButton view_periph = new JButton("Voir périphériques");
    
    //text
    JTextField recherche = new JTextField(30);
    
    Accueil()
    {
	setSize(x, y);

	// on met une grille de 6 lignes dans chacune des trois colonnes
	setLayout(new BorderLayout());
	conteneur_est.setLayout(new GridLayout(6,1));
	conteneur_centre.setLayout(new GridLayout(6,1));
	conteneur_ouest.setLayout(new GridLayout(6,1));
	
	content_deco.add(deconnexion) ;
	content_recherche.add(recherche); 
	content_tout.add(view_all);
	content_titre_bienvenue.add(new JLabel("Bienvenue")); 
	content_titre_reco.add(new JLabel("Applications recommandées"));
	content_titre_top.add(new JLabel("Top Applications"));
	//need req
	content_info.add(new JLabel("<html>Nom: <br/>Prenom:</html>"));
	content_nb.add(new JLabel("<html>Mela: <br/>Nombre App: </html>"));
	content_periph.add(view_periph);
	content_modifier.add(modifier_profil);  
	content_reco_app1.add(new JButton("<html>Appli 1<br/>OS: type:<html>"));
	content_reco_app2.add(new JButton("<html>Appli 2<br/>OS: type:<html>")); 
	content_reco_app3.add(new JButton("<html>Appli 3<br/>OS: type:<html>"));
	content_top_app1.add(new JButton("<html>App1 etoiles<br/>nbcommentaires:</html>"));
	content_top_app2.add(new JButton("<html>App2 etoiles<br/>nbcommentaires:</html>"));
	content_top_app3.add(new JButton("<html>App3 etoiles<br/>nbcommentaires:</html>"));
	content_top_view.add(view_more_top)   ;
	content_reco_view.add(view_more_reco);
	// OUEST
	conteneur_ouest.add(content_deco);
	conteneur_ouest.add(content_titre_bienvenue);
	conteneur_ouest.add(content_info);
	conteneur_ouest.add(content_nb);
	conteneur_ouest.add(content_periph);
	conteneur_ouest.add(content_modifier);
	// CENTRE
	conteneur_centre.add(content_recherche);
	conteneur_centre.add(content_titre_reco);
	conteneur_centre.add(content_reco_app1);
	conteneur_centre.add(content_reco_app2);
	conteneur_centre.add(content_reco_app3);
	conteneur_centre.add(content_reco_view);
	// EST
	conteneur_est.add(content_tout);
	conteneur_est.add(content_titre_top);
	conteneur_est.add(content_top_app1);
	conteneur_est.add(content_top_app2);
	conteneur_est.add(content_top_app3);
	conteneur_est.add(content_top_view);
	// ADD
	add("East", conteneur_est);
	add("Center", conteneur_centre);	
	add("West", conteneur_ouest);
	setVisible(true);
    }
}