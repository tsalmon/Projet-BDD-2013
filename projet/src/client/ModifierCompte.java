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

public class ModifierCompte extends JPanel implements MouseListener
{
    
    private SqlData info = Client.getInstance().getConnect().request("get_infoMe");
    private SqlData paye = Client.getInstance().getConnect().request("get_infoMe");

    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();
    JButton annuler = new JButton("Annuler");
    JButton ok = new JButton("Ok");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");

    public ModifierCompte()
    {
	setSize(779, 456);
        setLayout(new BorderLayout());
        addheader();
        addcentre();
        addsud();
        add("North", header);
        add("Center", centre);
        add("South", sud);
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
	//SqlData paye = Client.getInstance().getConnect().request("get_infoMe");
	paye.read_sqldata();
	JPanel grille = new JPanel();
	grille.setLayout(new GridLayout(3, 1));
	JPanel content_mail = new JPanel();
	JPanel content_mdp = new JPanel();
	JPanel content_payement = new JPanel();
	centre = new JScrollPane(grille);
    }
    
    public void addsud()
    {
	sud.setLayout(new GridLayout(1, 2));
	JPanel content_ok = new JPanel();
	JPanel content_ann = new JPanel();
	content_ok.add(ok);
	content_ann.add(annuler);
	sud.add(content_ok);
	sud.add(content_ann);
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