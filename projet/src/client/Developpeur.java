import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Developpeur extends JPanel implements MouseListener
{
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();

    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    Developpeur()
    {
        setSize(779, 456);
        setLayout(new BorderLayout());
        addheader();
        addcentre();
        add("North", header);
        add("Center", centre);
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
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

}