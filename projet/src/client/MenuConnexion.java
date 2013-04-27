import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
/**
   @author: Thomas Salmon
 **/
public class MenuConnexion extends JPanel
{
    
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;
    JPanel conteneur = new JPanel();
    Bouton connexion = new Bouton("connexion");
    Bouton apropos = new Bouton("A propos");
    JTextField pseudo = new JTextField(10);
    JPasswordField mdp = new JPasswordField(10);
    
    MenuConnexion(String titre)
    {
	setSize(x, y);
	conteneur.setLayout(new GridLayout(5, 5, 30, 15));
	conteneur.add(new JLabel("Pseudo:"));
	conteneur.add(pseudo);
	conteneur.add(new JLabel("Mot de passe:"));
	conteneur.add(mdp);
	conteneur.add(connexion);
	
	conteneur.add(apropos);
	add(conteneur);
	setVisible(true);
    }
    
    public class Bouton extends JButton implements MouseListener
    {
	public Bouton(String s)
	{
	    super(s);
	    JLabel texte = new JLabel(s);
	    //texte.setFont(new Font("Verdana", Font.PLAIN, 8));
	    //texte.setForeground(new Color(30,30,60));
	    JPanel textepan = new JPanel();
	    textepan.add(texte); 
	    textepan.setOpaque(false);  
	    add(textepan);
	    this.addMouseListener(this);
	    
	}
	
	public void mouseClicked(MouseEvent e)
	{
	    if(e.getSource() == connexion) 
		{
		    System.out.println("Tentative de connexion");
		    ConnexionClient c = new ConnexionClient();
		    if(c.auth("Stuart", "408101"))
			{
			    System.out.println("authentification reussie");
			}
		    else
			{
			    System.out.println("Echec");
			}
		}
	    if(e.getSource() == apropos)
		System.out.println("Fenetre a propos");
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
    }
}