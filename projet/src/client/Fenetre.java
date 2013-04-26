import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame
{
    int taille_x = Client.fenetre_x ;
    int taille_y = Client.fenetre_y ;
    
    Fenetre()
    {
	// init
	setTitle("GoldenStore");
	setSize(taille_x, taille_y);
	
	//positionne la fenetre au centre
	setLocationRelativeTo(null);
	// on ne peut pas modifier les dimensions de la fenetre
	setResizable(false);
	// la croix permet de fermer la fenetre
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// afficher fenetre a l'écran
	setVisible(true);
    }  
}
