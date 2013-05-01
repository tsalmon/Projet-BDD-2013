import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ToutVoir extends JPanel implements MouseListener
{    
    
    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField recherche = new JTextField(20);
    
    JPanel header = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_recherche = new JPanel();
    JPanel conteneur_acc = new JPanel();

    SqlData r = Client.getInstance().getConnect().request("get_app");
    
    ToutVoir()
    {
	barre();
	centre();
	setVisible(true);
    }
    
    // trier en fonction d'un champs
    ToutVoir(int col)
    {
	barre();
	r.trie(col);
	centre();
	setVisible(true);

    }

    private void centre()
    {
	DefaultTableModel dm = new DefaultTableModel();
	Object [][] donnees = new Object[r.getNbLigne()][6];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		donnees[i][0] = r.data[i][1]; // nom
		donnees[i][1] = r.data[i][11]; // categorie
		donnees[i][2] = r.data[i][9]; // prix
		donnees[i][3] = r.data[i][8]; // mela
		donnees[i][4] = r.data[i][5]; // tag
		donnees[i][5] = r.data[i][12]; // average(elstar)
	    }
        dm.setDataVector(donnees, new Object[] { "Noms", "Categories", "Prix", "Mela", "Tags", "Elstar" });
	JTable table = new JTable(dm);
        table.getColumn("Noms").setCellRenderer(new ButtonRenderer());
        table.getColumn("Noms").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scroll = new JScrollPane(table);
        add("Center",scroll);
    }

    private void barre()
    {
	setSize(779, 456);
	setLayout(new BorderLayout());
	header.setLayout(new GridLayout(1, 3));
	
	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_recherche.add(recherche);
	
	header.add(conteneur_deco);
	header.add(conteneur_recherche);
	header.add(conteneur_acc);
	
	add("North", header);
    }
    
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    
    /*
    public static void main(String [] args)
    {
	JFrame f = new JFrame();
	f.setContentPane(new ToutVoir());
	f.setVisible(true);
    }
    */

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
        setText((value == null) ? "" : value.toString());
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
            //                                                                                                
            //                                                                                                
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");                                                         
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
    
}

}