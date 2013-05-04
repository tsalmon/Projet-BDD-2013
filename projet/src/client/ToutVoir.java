import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class ToutVoir extends JPanel implements MouseListener
{    

    int select = -1;

    JButton all_applis = new JButton("Applications");
    JButton all_periph = new JButton("Peripheriques");
    JButton all_OS = new JButton("OS");
    JButton recommande = new JButton("Recommandés");

    JButton deconnexion = new JButton("déconnexion");
    JButton accueil = new JButton("accueil");
    JTextField recherche = new JTextField(20);
    
    JPanel header = new JPanel();
    JPanel conteneur_deco = new JPanel();
    JPanel conteneur_recherche = new JPanel();
    JPanel conteneur_acc = new JPanel();

    DefaultTableModel boutons = new DefaultTableModel();
    DefaultTableModel dm = new DefaultTableModel()
	{
	    
	    @Override
		public boolean isCellEditable(int row, int column)
	    {
		return false;
	    }
	    
	};
    
    

    ToutVoir(char a_voir)
    {
	barre();
	//applications
	if(a_voir == 'a')
	    {
		select = 0;
		centre(0);
	    }
	// peripheriques
	else if(a_voir == 'p')
	    {
		select = 1;
		centre(1);
	    }
	// OS
	else if(a_voir == 'o')
	    {
		select = 2;
		centre(2);
	    }
	else if(a_voir == 'r')
	    {
		centre(3);
		select = 3;
	    }
	setVisible(true);
    }

    /*
    // rechercher recommandées
    ToutVoir(String[] id)
    {
	barre();
	setVisible(true);
	
    }
    */
    private Object[][] recuperer_applis()
    {
	SqlData r = Client.getInstance().getConnect().request("get_app");
	Object [][] donnees = new Object[r.getNbLigne()][5];
	Object [][] button = new Object[r.getNbLigne()][1];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		button[i][0] = "0." + r.data[i][0]+"."+r.data[i][1]; // id.nom
		donnees[i][0] = r.data[i][11]; // categorie
		donnees[i][1] = (r.data[i][9].equals("Null")) ? "Gratuit" : r.data[i][9]; // prix
		donnees[i][2] = r.data[i][8]; // mela
		donnees[i][3] = r.data[i][5]; // tag
		donnees[i][4] = 
		    (r.data[i][12].equals("Null")) ? "Aucune notes" : r.data[i][12].charAt(0)+ "." + r.data[i][12].charAt(2); //average(elstar)
	    }
	boutons.setDataVector(button, new Object[] {"Noms"});
	return donnees;
    }

    private Object[][] recuperer_periph()
    {
	SqlData r = Client.getInstance().getConnect().request("get_periph");
	Object [][] donnees = new Object[r.getNbLigne()][2];
	Object [][] button = new Object[r.getNbLigne()][1];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		button[i][0] = "1."+ r.data[i][0] + "." + r.data[i][1];//id.nom
		donnees[i][0] = r.data[i][2]; // type
		donnees[i][1] = r.data[i][3]; //fabricant
	    }
	boutons.setDataVector(button, new Object[] {"Noms"});
	return donnees;
    }
    
    private Object[][] appli_reco()
    {
	SqlData a = Client.getInstance().getConnect().request("get_appRecomande");
	Object [][] donnees = new Object[a.getNbLigne()][5];
	Object [][] button = new Object[a.getNbLigne()][1];

	for(int i = 0 ; i < a.getNbLigne(); i++)
	    {
		SqlData r = Client.getInstance().getConnect().request("get_appId", a.data[i][0]);
		button[i][0] = "0." + r.data[i][0]+"."+r.data[i][1]; // id.nom
		donnees[i][0] = r.data[i][11]; // categorie
		donnees[i][1] = (r.data[i][9].equals("Null")) ? "Gratuit" : r.data[i][9]; // prix
		donnees[i][2] = r.data[i][8]; // mela
		donnees[i][3] = r.data[i][5]; // tag
		donnees[i][4] = 
		    (r.data[i][12].equals("Null")) ? "Aucune notes" : r.data[i][12].charAt(0)+ "." + r.data[i][12].charAt(2); //average(elstar)
	    }
	boutons.setDataVector(button, new Object[] {"Noms"});
	return donnees;
    }
    
    private Object[][] recuperer_os()
    {
	SqlData r = Client.getInstance().getConnect().request("get_Se");
	Object [][] donnees = new Object[r.getNbLigne()][1];
	Object [][] button = new Object[r.getNbLigne()][1];
	for(int i = 0; i < r.getNbLigne(); i++)
	    {
		button[i][0] = "2."+ r.data[i][0] + "." + r.data[i][1];//id.nom
		donnees[i][0] = r.data[i][2]; //fabricant
	    }
	boutons.setDataVector(button, new Object[] {"Noms"});
	return donnees;
    }
    
    private DefaultTableModel recuperer_info(int i)
    {
	
	if(i == 0)
	    {
		dm.setDataVector(recuperer_applis(), new Object[] { "Categories", "Prix", "Mela", "Tags", "Elstar" }); 
	    }
	else if(i == 1)
	    {
		dm.setDataVector(recuperer_periph(), new Object[] { "Type", "Fabricant"});
	    }
	else if(i == 2)
	    {
		dm.setDataVector(recuperer_os(), new Object[] { "Versions"});
	    }
	else if(i == 3)
	    {
		dm.setDataVector(appli_reco(), new Object[] { "Categories", "Prix", "Mela", "Tags", "Elstar" }); 
	    }

	return dm;
    }


    private void centre(int i)
    {
	JTable tab_boutons = new JTable(boutons);
	JTable table = new JTable(recuperer_info(i));
	tab_boutons.getColumn("Noms").setCellRenderer(new ButtonRenderer());
	tab_boutons.getColumn("Noms").setCellEditor(new ButtonEditor(new JCheckBox()));
	tab_boutons.getColumnModel().getColumn(0).setPreferredWidth(200);
	JPanel content_table = new JPanel();
	content_table.setLayout(new BorderLayout());
	content_table.add("West", tab_boutons);
	content_table.add("Center", table);
	
	JScrollPane scroll = new JScrollPane(content_table);
	add("Center",scroll);
    }
    private void barre()
    {
	JPanel barre_sup = new JPanel();
	JPanel barre_inf = new JPanel();

	JPanel content_os = new JPanel();
	JPanel content_periph = new JPanel();
	JPanel content_applis = new JPanel();
	JPanel content_reco = new JPanel();
	
	setSize(779, 456);
	setLayout(new BorderLayout());
	barre_sup.setLayout(new GridLayout(1, 3));
	barre_inf.setLayout(new GridLayout(1, 4));
	header.setLayout(new GridLayout(2,1));
	conteneur_deco.add(deconnexion);
	conteneur_acc.add(accueil);
	conteneur_recherche.add(recherche);
	
	barre_sup.add(conteneur_deco);
	barre_sup.add(conteneur_recherche);
	barre_sup.add(conteneur_acc);
	
	content_os.add(all_OS);
	content_periph.add(all_periph);
	content_applis.add(all_applis);
	content_reco.add(recommande);

	barre_inf.add(content_reco);
	barre_inf.add(content_applis);
	barre_inf.add(content_periph);
	barre_inf.add(content_os);
	
	header.add(barre_sup);
	header.add(barre_inf);
	add("North", header);
	recherche.addKeyListener(new ClavierListener());
	recommande.addMouseListener(this);
	all_OS.addMouseListener(this);
	all_periph.addMouseListener(this);
	all_applis.addMouseListener(this);
	deconnexion.addMouseListener(this);
	accueil.addMouseListener(this);
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
	if(e.getSource() == all_applis && select != 0){
	    Client.getInstance().getFen().setContentPane(new ToutVoir('a'));
	}
	if(e.getSource() == all_periph && select != 1){
	    Client.getInstance().getFen().setContentPane(new ToutVoir('p'));
	}
	if(e.getSource() == all_OS && select != 2){
	    Client.getInstance().getFen().setContentPane(new ToutVoir('o'));
	}
	if(e.getSource() == recommande && select != 3){
	    Client.getInstance().getFen().setContentPane(new ToutVoir('r'));
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
	    int k = 2;
	    while(s.charAt(k++) != '.'){}
	    while(k < s.length()){ retour_str += s.charAt(k++);}
	    setText((value == null) ? "" : retour_str);
	    
	    //setText(( value == null) ? "" : value.toString());
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
		int k = 2;
		while(s.charAt(k) != '.'){retour_id += s.charAt(k++);}
		k++;
		while(k < s.length()){retour_str += s.charAt(k++);}
		if(s.charAt(0) == '0')
		    {
			Client.getInstance().getFen().setContentPane(new Application(retour_id, retour_str));
		    }
		else if(s.charAt(0) == '1')
		    {
			Client.getInstance().getFen().setContentPane(new VoirPeripherique(retour_id));
		    }
	    }
	    isPushed = false;
	    return label;
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