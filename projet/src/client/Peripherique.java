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

public class Peripherique extends JPanel implements MouseListener
{

    JPanel header = new JPanel();
    JPanel conteneur_search = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_acc = new JPanel();

    JScrollPane scroll_gauche;
    JScrollPane scroll_droite;

    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField cherche = new JTextField(10);
    
    Peripherique()
    {
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(3, 1));
	
	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_search.add(cherche);
	header.add(conteneur_deco);
	header.add(conteneur_search);
	header.add(conteneur_acc);
	
	add("North", header);
	add("West", new JButton("gauche"));
	add("East", new JButton("droite"));
    }
    
    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == accueil)
            {
                System.out.println("acc");
                Client.getInstance().getFen().setContentPane(new Accueil());
            }
        if(e.getSource() == deconnexion)
            {
                Client.getInstance().getConnect().dialog("DISCONNECT");
                Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
	    }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}