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
    SqlData r = Client.getInstance().getConnect().request("get_appMe");

    int page = 0;
    
    JPanel header = new JPanel();
    JScrollPane centre = new JScrollPane();
    JPanel sud    = new JPanel();

    JButton retour = new JButton("Retour");
    JButton accueil = new JButton("Accueil");
    JButton deconnexion = new JButton("Déconnexion");
    JButton new_app = new JButton("Nouvelle application");
    
    JButton ok = new JButton("OK");
    JButton retour = new JButton("Retour");
    
    Developpeur(int n)
    {
        setSize(779, 456);
        setLayout(new BorderLayout());
        addheader(n);
        addcentre(n);
        add("North", header);
        add("Center", centre);
	if(n == 1)
	    {
		addsud();
		add("South", sud);
	    }
        setVisible(true);
    }

    public void nouvelle_application()
    {
	
    }


    public void addheader(int n)
    {
	JTextField recherche = new JTextField(20);
	JPanel content_acc = new JPanel();
	JPanel content_text = new JPanel();
	JPanel content_deco = new JPanel();
	
	content_text.add(recherche);
	content_deco.add(deconnexion);
	content_acc.add(accueil);
	
	if(n == 0)
	    {
		header.setLayout(new GridLayout(2, 1));
		
		JPanel header_sup = new JPanel();
		JPanel header_inf = new JPanel();
		
		header_sup.setLayout(new GridLayout(1, 3));
		
		header_sup.add(content_deco);
		header_sup.add(content_text);
		header_sup.add(content_acc);
		
		header_inf.add(new_app);
		
		header.add(header_sup);
		header.add(header_inf);
	    }
	else
	    {
		header.setLayout(new GridLayout(1, 3));
		header.add(content_deco);
		header.add(content_text);
		header.add(content_acc);
	    }
	
	recherche.addKeyListener(new ClavierListener());
	new_app.addMouseListener(this);
        accueil.addMouseListener(this);
        deconnexion.addMouseListener(this);
    }

    public void addcentre(int n)
    {
	
    }

    public void addsud()
    {
	sud.setLayout(new BorderLayout());
	sud.add("West", ok);
	sud.add("East", retour);
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
	if(e.getSource() == new_app)
	    {
		Client.getInstance().getFen().setContentPane(new Developpeur(1));
	    }
	if(e.getSource() == ok)
	    {
		
		Client.getInstance().getFen().setContentPane(new Developpeur(0));
	    }
	if(e.getSource() == retour)
	    {
		Client.getInstance().getFen().setContentPane(new Developpeur(0));
	    }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

}