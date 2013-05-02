import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class VoirPeripherique extends JPanel implements MouseListener
{
    JButton accueil = new JButton("accueil");
    JButton deconnexion = new JButton("deconnexion");
    
    JButton achete = new JButton("Acheter");
    JButton retirer = new JButton("Retirer");
    
    VoirPeripherique(String id)
    {
	JPanel header = new JPanel();
	JPanel centre = new JPanel();

	JPanel content_acc = new JPanel();
	JPanel content_deco = new JPanel();
	JPanel content_text = new JPanel();
	JPanel content_bouton = new JPanel();
	SqlData r = Client.getInstance().getConnect().request("get_periphId",id);
	setSize(779, 456);
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	centre.setLayout(new GridLayout(3, 1));

	content_acc.add(accueil);
	content_deco.add(deconnexion);	
	content_text.add(new JTextField(20));
	
	header.add(content_deco);
	header.add(content_text);
	header.add(content_acc);
	
	centre.add(new JLabel(r.data[0][0]));
	centre.add(new JLabel("Type: " + r.data[0][1] + "    fabricant: " + r.data[0][2]));
	
	SqlData bouton = Client.getInstance().getConnect().request("get_periphMe");
	boolean exist = false;
	for(int i = 0; i < bouton.getNbLigne(); i++)
	    {
		if(bouton.data[i][0].equals(id))
		    {
			exist = true;
			break;
		    }
	    }
	content_bouton.add(((exist) ? retirer : achete));
	centre.add(content_bouton);
	add("North", header);
	add("Center", centre);
	
	retirer.addMouseListener(this);
	achete.addMouseListener(this);
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
    }

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
   
}