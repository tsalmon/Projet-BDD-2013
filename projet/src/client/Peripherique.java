import javax.swing.JFrame;

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
    JPanel center = new JPanel();

    JPanel conteneur_search = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_acc = new JPanel();

    JScrollPane scroll_gauche;
    JScrollPane scroll_droite;

    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField cherche = new JTextField(20);
    
    Peripherique()
    {
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	center.setLayout(new GridLayout(1, 2));

	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_search.add(cherche);
	header.add(conteneur_deco);
	header.add(conteneur_search);
	header.add(conteneur_acc);

	center.add(new JButton("gauche"));
	center.add(new JButton("droite"));
	
	add("North", header);
	add("Center", center);
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

    /* test hors-connexion
      public static void main(String[] args)
      {
      JFrame f = new JFrame();
      f.setSize(779, 456);
      f.setContentPane(new Peripherique());
      f.setVisible(true);
      }
    */
}