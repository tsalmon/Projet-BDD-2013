import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class ToutVoir extends JPanel implements MouseListener
{    

    JButton all_applis = new JButton("Applications");
    JButton all_periph = new JButton("Peripheriques");
    JButton all_OS = new JButton("OS");

    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField recherche = new JTextField(20);
    
    JPanel header = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_recherche = new JPanel();
    JPanel conteneur_acc = new JPanel();

    SqlData r = Client.getInstance().getConnect().request("get_app");
    
    ToutVoir(char a_voir)
    {
	//applications
	if(a_voir == 'a')
	    {
		barre();
		centre_applis();
		setVisible(true);
	    }
	// recommandées
	else if(a_voir == 'r')
	    {
		barre();
		
	    }
    }
    
    // trier en fonction d'un champs
    ToutVoir(int col)
    {
	barre();
	r.trie(col);
	centre_applis();
	setVisible(true);

    }

    private void centre_applis()
    {
	DefaultTableModel dm = new DefaultTableModel()
	    {
		@Override
		    public boolean isCellEditable(int row, int column)
		{
		    return false;
		}
	    };
	Object [][] donnees = new Object[r.getNbLigne()][6];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		donnees[i][0] = r.data[i][0]+"."+r.data[i][1]; // nom
		donnees[i][1] = r.data[i][11]; // categorie
		donnees[i][2] = (r.data[i][9].equals("Null")) ? "Gratuit" : r.data[i][9]; // prix
		donnees[i][3] = r.data[i][8]; // mela
		donnees[i][4] = r.data[i][5]; // tag
		donnees[i][5] = 
		    (r.data[i][12].equals("Null")) ? "Aucune notes" : r.data[i][12].charAt(0)+ "." + r.data[i][12].charAt(2); //average(elstar)
	    }
        dm.setDataVector(donnees, new Object[] { "Noms", "Categories", "Prix", "Mela", "Tags", "Elstar" });
	JTable table = new JTable(dm);
        table.getColumn("Noms").setCellRenderer(new ButtonRenderer());
        table.getColumn("Noms").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scroll = new JScrollPane(table);
        add("Center",scroll);
	deconnexion.addMouseListener(this);
	accueil.addMouseListener(this);
    }

    private void barre()
    {
	JPanel barre_sup = new JPanel();
	JPanel barre_inf = new JPanel();

	setSize(779, 456);
	setLayout(new BorderLayout());
	barre_sup.setLayout(new GridLayout(1, 3));
	barre_inf.setLayout(new GridLayout(1, 3));
	header.setLayout(new GridLayout(2,1));
	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_recherche.add(recherche);
	
	barre_sup.add(conteneur_deco);
	barre_sup.add(conteneur_recherche);
	barre_sup.add(conteneur_acc);
	
	barre_inf.add(all_applis);
	barre_inf.add(all_periph);
	barre_inf.add(all_OS);
	
	header.add(barre_sup);
	header.add(barre_inf);
	add("North", header);
    }
    
    public void mouseClicked(MouseEvent e)
    {
	if(e.getSource() == deconnexion)
	    {
		Client.getInstance().getConnect().dialog("DISCONNECT");
                Client.getInstance().getFen().setContentPane(new MenuConnexion("GoldenStore - Connexion"));
	    }
	if(e.getSource() == accueil)
	    {
		Client.getInstance().getFen().setContentPane(new Accueil());
	    }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

class ButtonRenderer extends JButton implements TableCellRenderer {
    
    public ButtonRenderer() {
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) 
    {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
	String s = value.toString();
	String retour_str = "";
	int k = 0;
	while(s.charAt(k++) != '.'){}
	while(k < s.length()){ retour_str += s.charAt(k++);}
	setText((value == null) ? "" : retour_str);
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    
    private String label;
    
    private boolean isPushed;
    
    public ButtonEditor(JCheckBox checkBox) {
	super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
	
	label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }
    
    public Object getCellEditorValue() {
        if (isPushed) {
	    String s = label;
	    String retour_str = "";
	    String retour_id = "";
	    int k = 0;
	    while(s.charAt(k) != '.'){retour_id += s.charAt(k++);}
	    k++;
	    while(k < s.length()){retour_str += s.charAt(k++);}
	    Client.getInstance().getFen().setContentPane(new Application(retour_id, retour_str));
        }
        isPushed = false;
        return new String(label);
    }
    
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
    
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public boolean isCellEditable()
    {
	return false;
    }
    
}

}