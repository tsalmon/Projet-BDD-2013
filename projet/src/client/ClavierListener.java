import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.String;
class ClavierListener implements KeyListener
{
    String expr = "";
    public void keyPressed(KeyEvent event)
    {
	if(event.getKeyCode() == 10)
	    {
		Client.getInstance().getFen().setContentPane(new Recherche(expr));
	    }
	else if(event.getKeyCode() == 8 && expr.length() > 0)
	    {
		expr = expr.substring(0, expr.length() - 1);
	    }
	else
	    {
		expr += event.getKeyChar();
	    }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
