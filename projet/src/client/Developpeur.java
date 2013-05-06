import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
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
import javax.swing.JComboBox;

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
    JButton[] applis = new JButton[1];
    JButton[] maj = new JButton[1];
    String[] id_applis = new String[1];
    JButton ok = new JButton("OK");

    JTextField txt_nom = new JTextField(10);
    JTextField txt_version = new JTextField(10);
    JComboBox list_categorie;
    JTextField txt_tag = new JTextField(10);
    JTextField txt_prix = new JTextField(10);
    JTextField txt_mela = new JTextField(10);

    JCheckBox[] check_os = new JCheckBox[1];
    String[]  id_os    = new String[1];
 
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
	SqlData os = Client.getInstance().getConnect().request("get_Se");
	SqlData c = Client.getInstance().getConnect().request("get_categorie");
	String[] cate = new String[c.getNbLigne()];
	for(int i = 0 ; i < c.getNbLigne(); i++)
	    {
		cate[i] = c.data[i][1];
	    }
	list_categorie = new JComboBox(cate);
	/* INIT */
	JPanel content_all = new JPanel();
	JPanel table = new JPanel();
	JPanel os_liste = new JPanel();
	JPanel pan_os[] = new JPanel[os.getNbLigne()];
	JPanel content_nom = new JPanel(); 
	JPanel content_version = new JPanel(); 
	JPanel content_tag = new JPanel(); 
	JPanel content_prix = new JPanel(); 
	JPanel content_mela = new JPanel(); 
	JPanel content_categorie = new JPanel();
	JPanel content_list_categorie = new JPanel();
	JPanel content_txt_nom = new JPanel();
	JPanel content_txt_version = new JPanel();
	JPanel content_txt_tag = new JPanel();
	JPanel content_txt_prix = new JPanel();
	JPanel content_txt_mela = new JPanel();

	table.setLayout(new GridLayout(6, 2));
	os_liste.setLayout(new GridLayout(os.getNbLigne(), 1));
	check_os = new JCheckBox[os.getNbLigne()];
	id_os = new String[os.getNbLigne()];
	for(int i = 0 ; i < os.getNbLigne(); i++)
	    {
		check_os[i] = new JCheckBox();
		id_os[i] = os.data[i][0];
		pan_os[i] = new JPanel();
		pan_os[i].add(new JLabel(os.data[i][1] + "("+os.data[i][2]+")"));
		pan_os[i].add(check_os[i]);
		os_liste.add(pan_os[i]);
	    }
	
	/* ADD */
	//add nom 
	content_nom.add(new JLabel("Nom: "));
	table.add(content_nom);
	content_txt_nom.add(txt_nom);
	table.add(content_txt_nom);

	//add version
	content_version.add(new JLabel("Version: "));
	table.add(content_version);
	content_txt_version.add(txt_version);
	table.add(content_txt_version);
	
	//add tag
	content_tag.add(new JLabel("Tag: "));
	table.add(content_tag);
	content_txt_tag.add(txt_tag);
	table.add(content_txt_tag);

	//add categorie
	content_categorie.add(new JLabel("Categorie:"));
	table.add(content_categorie);
	content_list_categorie.add(list_categorie);
	table.add(content_list_categorie);

	//add prix
	content_prix.add(new JLabel("Prix: "));
	table.add(content_prix);
	content_txt_prix.add(txt_prix);
	table.add(content_txt_prix);
	
	//add mela
	content_mela.add(new JLabel("Nombre de points mela:"));
	table.add(content_mela);
	content_txt_mela.add(txt_mela);
	table.add(content_txt_mela);

	//grand final :
	content_all.add("Center",table);
	content_all.add("East", os_liste);
	centre = new JScrollPane(content_all);
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
    
    public void affiche_applis()
    {
	JPanel grille = new JPanel();
	JPanel[] tab = new JPanel[r.getNbLigne()];
	grille.setLayout(new GridLayout(r.getNbLigne(), 1));
	id_applis = new String[r.getNbLigne()];
	applis = new JButton[r.getNbLigne()];
	maj = new JButton[r.getNbLigne()];
	for(int i = 0 ; i < r.getNbLigne(); i++)
	    {
		id_applis[i] = r.data[i][0];
		applis[i] = new JButton("<html>" + r.data[i][1] + " (" + r.data[i][2] + ")<br><i>" 
					+ r.data[i][5]+"</i><b> mela: "+r.data[i][8]+"</b></html>" );
		applis[i].addMouseListener(this);	
		maj[i] = new JButton("MAJ");
		maj[i].addMouseListener(this);
		tab[i] = new JPanel();
		tab[i].setLayout(new GridLayout(1, 2));
		JPanel conteneur_app = new JPanel();
		JPanel conteneur_maj = new JPanel();
		
		conteneur_app.add(applis[i]);
		conteneur_maj.add(maj[i]);
		tab[i].add(conteneur_app);
		tab[i].add(conteneur_maj);
		grille.add(tab[i]);
	    }
	centre = new JScrollPane(grille);
    }
    
    public void addcentre(int n)
    {
	if(n == 0)
	    {
		affiche_applis();
	    }
	else
	    {
		nouvelle_application();
	    }
    }

    public void addsud()
    {
	sud.setLayout(new BorderLayout());
	sud.add("West", ok);
	sud.add("East", retour);
	ok.addMouseListener(this);
	retour.addMouseListener(this);
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
		Client.getInstance().getConnect().request("add_app", 
							  txt_nom.getText(), 
							  txt_version.getText(),
							  ""+(list_categorie.getSelectedIndex() + 1),
							  txt_tag.getText(),
							  txt_prix.getText(),
							  txt_mela.getText()
							  );
		r = Client.getInstance().getConnect().request("get_appMe");
		for(int i = 0 ; i < check_os.length; i++)
		    {
			if(check_os[i].isSelected())
			    {
				Client.getInstance().getConnect().request("set_appSe", r.data[r.getNbLigne()-1][0], id_os[i]);
			    }
		    }
		
		Client.getInstance().getFen().setContentPane(new Developpeur(0));
	    }
	if(e.getSource() == retour)
	    {
		Client.getInstance().getFen().setContentPane(new Developpeur(0));
	    }
	for(int i = 0 ; i < applis.length; i++)
	    {
		if(e.getSource() == applis[i])
		    {
			Client.getInstance().getFen().setContentPane(new Application(id_applis[i], applis[i].getText()));
		    }
	    }
	for(int i = 0 ; i < maj.length; i++)
	    {
		if(e.getSource() == maj[i])
		    {
			String vers = JOptionPane.showInputDialog(null, "Nouvelle version de l'application");
			SqlData ancien = Client.getInstance().getConnect().request("get_appId", id_applis[i]); 
			SqlData r = Client.getInstance().getConnect().request("set_app", 
									      ancien.data[0][1], 
									      vers,
									      ancien.data[0][5],
									      ancien.data[0][8],
									      ancien.data[0][0]
									      ); 
			Client.getInstance().getFen().setContentPane(new Developpeur(0));
		    }
	    }
 
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

}