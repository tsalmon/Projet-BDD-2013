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
    
    ToutVoir()
    {
	barre();
	add("Center", new JButton("c"));	
	setVisible(true);
    }
    
    // trier en fonction d'un champs
    ToutVoir(int col)
    {
	barre();
	add("Center", new JButton("c"));
	setVisible(true);

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