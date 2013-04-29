import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import java.awt.Dimension;
public class Application extends JPanel implements MouseListener
{
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;

    JScrollPane sp = new JScrollPane();
    
    JPanel conteneur_ouest = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel commentaires = new JPanel();
    JPanel header = new JPanel();
    JPanel infos = new JPanel();
    JPanel nom = new JPanel();
    JPanel avis = new JPanel();
    JPanel avis_canvas = new JPanel();
    Application(String nom_application)
    {
	
	Client.getInstance().getFen().setTitle(nom_application);
	setSize(x, y);
	
	setLayout(new BorderLayout());
	conteneur_ouest.setLayout(new BorderLayout());
	conteneur_centre.setLayout(new BorderLayout());
	header.setLayout(new BorderLayout());
	infos.setLayout(new GridLayout(5,1));
	nom.setLayout(new BorderLayout());

	
	//avis.setPreferredSize(new Dimension(300, 400));

	nom.add("Center", new JLabel(nom_application));
	nom.add("East", new JLabel("v47545"));
	
	header.add("West", new JButton("deco"));
	header.add("Center", new JTextField(10));
	header.add("East",new JButton("Accueil"));
	header.add("South", nom);
	
	infos.add(new JLabel("Catégorie:"));
	infos.add(new JLabel("Os:"));
	
	infos.add(new JLabel("prix:"));
	infos.add(new JLabel("mensuel:"));

	
	for(int i=0; i < 1000; i++)
	    {
		avis.add(new JLabel("avis" + i));
	    }
	sp.setViewportView(avis);
	avis_canvas.add(sp);

	add("North", header);
	add("West", infos);
	add("Center", avis_canvas);
    }

    public void mouseClicked(MouseEvent e)
    {
	
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}