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
import javax.swing.JScrollPane;

public class Recherche extends JPanel implements MouseListener
{
    String expr = "";
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();
    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    Recherche(String terme)
    {
	setSize(779, 456);
        setLayout(new BorderLayout());
	expr = terme;
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
	//SqlData tag = Client.getInstance().getConnect().request("get_appTag", "%"+expr+"%", "%"+expr+"%");
	//tag.read_sqldata();
	SqlData periph = Client.getInstance().getConnect().request("get_appTag", "%"+expr+"%", "%"+expr+"%");
	centre = new JScrollPane(new JLabel(expr));
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}