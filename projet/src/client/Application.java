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

public class Application extends JPanel implements MouseListener
{
    int x = Client.fenetre_x;
    int y = Client.fenetre_y;
    
    JScrollPane sp;
    
    JPanel conteneur_ouest = new JPanel();
    JPanel conteneur_centre = new JPanel();
    JPanel conteneur_deconnexion = new JPanel();
    JPanel conteneur_accueil = new JPanel();

    JPanel commentaires = new JPanel();
    JPanel header = new JPanel();
    JPanel infos = new JPanel();
    JPanel nom = new JPanel();
    JPanel avis = new JPanel();

    JButton deconnexion = new JButton("deconnexion");
    JButton accueil = new JButton("Accueil");

    Application(String id, String nom_application)
    {
	Client.getInstance().getFen().setTitle(nom_application);
	setSize(x, y);
	
	setLayout(new BorderLayout());
	conteneur_ouest.setLayout(new BorderLayout());
	conteneur_centre.setLayout(new BorderLayout());
	header.setLayout(new BorderLayout());
	infos.setLayout(new GridLayout(4,1));
	nom.setLayout(new BorderLayout());

	req_infos(id);
	req_avis(id);
	
	//avis.setPreferredSize(new Dimension(300, 400));

	nom.add("Center", new JLabel(nom_application));

	conteneur_deconnexion.add(deconnexion);
	conteneur_accueil.add(accueil);
	header.add("West", new JPanel().add(conteneur_deconnexion));
	header.add("Center", new JPanel().add(new JTextField(10)));
	header.add("East",new JPanel().add(conteneur_accueil));
	header.add("South", nom);
	
	sp = new JScrollPane(avis)
	    {
		public Dimension getPreferredSize()
		{
		    return new Dimension(200, 200);
		}
	    };

	deconnexion.addMouseListener(this);
	accueil.addMouseListener(this);
	add("North", header);
	add("West", infos);
	add("Center", sp);
    }

    private void read_sqldata(SqlData r)
    {
        for(int i = 0; i < r.getNbCol(); i++)
            {
                System.out.print(r.nomCol[i]);
            }
        System.out.println("");
        for(int i = 0; i < r.getNbLigne(); i++)
            {
                for(int j = 0; j < r.getNbCol(); j++)
                    {
                        System.out.print(r.data[i][j] + " \t");
                    }
                System.out.println("");
            }
    }

    public void req_infos(String id)
    {
	SqlData r = Client.getInstance().getConnect().request("get_appId",id);
	nom.add("East", new JLabel("v" + r.data[0][2]+"\t"));
	infos.add(new JLabel("Catégorie:" + r.data[0][11]));
	infos.add(new JLabel("Os:"));
	SqlData os = Client.getInstance().getConnect().request("get_SeApp",id);
	JPanel list_os = new JPanel();
	list_os.setLayout(new GridLayout(os.getNbLigne(), 1));
	for(int i = 0;i < os.getNbLigne(); i++)
	    {
		list_os.add(new JLabel(os.data[i][1]));
	    }
	infos.add(list_os);
	if(r.data[0][6].equals("1"))
	    {
		infos.add(new JLabel("prix:" + r.data[0][6]));
		
	    }
	else
	    {
		infos.add(new JLabel("Gratuit"));
	    }
    }

    public void req_avis(String id)
    {
	SqlData r = Client.getInstance().getConnect().request("get_avisApp",id);
	//read_sqldata(r);
	avis.setLayout(new GridLayout(r.getNbLigne(), 1, 10, 10));
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		SqlData nom = Client.getInstance().getConnect().request("get_infoId", r.data[i][0]);		
		JPanel avis_aff = new JPanel();
		avis_aff.setBorder(BorderFactory.createLineBorder(Color.black));
		avis_aff.setLayout(new BorderLayout());		
		avis_aff.add("North", new JLabel(nom.data[0][1]));
		avis_aff.add("Center", new JLabel("<html>"+r.data[i][3]+"<html/>"));
		avis_aff.add("South", new JLabel(r.data[i][4]));		    
		avis.add(avis_aff);
	    }
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
                Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion")\
	    }
    }
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}