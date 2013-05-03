import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class VoirPeripherique extends JPanel implements MouseListener, ListSelectionListener
{
    JButton accueil = new JButton("accueil");
    JButton deconnexion = new JButton("deconnexion");
    
    String id = "";
    String os = "";

    JButton acheter = new JButton("Acheter");
    JButton retirer = new JButton("Retirer");
    
    JList os_list = new JList();
    
    VoirPeripherique(String id)
    {
	
	JPanel header = new JPanel();
	JPanel centre = new JPanel();
	
	JPanel content_acc = new JPanel();
	JPanel content_deco = new JPanel();
	JPanel content_text = new JPanel();
	JPanel content_bouton = new JPanel();
	SqlData r = Client.getInstance().getConnect().request("get_periphId",id);

	SqlData os_req = Client.getInstance().getConnect().request("get_Se");
	String[] os_noms = new String[os_req.getNbLigne()]; 
	for(int i = 0; i < os_req.getNbLigne(); i++)
	    {
		os_noms[i] = os_req.data[i][1] + "(" + os_req.data[i][2] + ")";
	    }
	os_list = new JList(os_noms);
	os_list.addListSelectionListener(this);
	JScrollPane scroll_os = new JScrollPane(os_list);
		
	setSize(779, 456);
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	centre.setLayout(new GridLayout(3, 1));
	
	this.id = id;

	content_acc.add(accueil);
	content_deco.add(deconnexion);	
	JTextField recherche = new JTextField(20);
	recherche.addKeyListener(new ClavierListener());
	content_text.add(recherche);
	
	header.add(content_deco);
	header.add(content_text);
	header.add(content_acc);
	
	centre.add(new JLabel(r.data[0][0]));
	centre.add(new JLabel("Type: " + r.data[0][1] + "    fabricant: " + r.data[0][2]));
	
	SqlData bouton = Client.getInstance().getConnect().request("get_periphMe");
	boolean exist = false;
	for(int i = 0; i < bouton.getNbLigne(); i++)
	    {
		if(bouton.data[i][3].equals(id))
		    {
			exist = true;
			break;
		    }
	    }
	content_bouton.add(((exist) ? retirer : acheter));
	add("North", header);
	add("Center", centre);
	add("South", content_bouton);
	add("East", scroll_os);
	retirer.addMouseListener(this);
	acheter.addMouseListener(this);
	accueil.addMouseListener(this);
	deconnexion.addMouseListener(this);
	
	setVisible(true);
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == deconnexion){
            Client.getInstance().getConnect().dialog("DISCONNECT");
            Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
        }
        if(e.getSource() == accueil){
            Client.getInstance().getFen().setContentPane(new Accueil());
        }
	if(e.getSource() == retirer){
	    SqlData r = Client.getInstance().getConnect().request("del_periphMe",id);
	    Client.getInstance().getFen().setContentPane(new VoirPeripherique(id));
	}
	if(e.getSource() == acheter){
	    SqlData r = Client.getInstance().getConnect().request("add_periphMe", id, os );
	    Client.getInstance().getFen().setContentPane(new VoirPeripherique(id));
	}
    }

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}    
    
    public void valueChanged(ListSelectionEvent evt)
    {
	os = (1+os_list.getSelectedIndex())+"";
    }
}