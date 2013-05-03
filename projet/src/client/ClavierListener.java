import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class ClavierListener implements KeyListener
{
    public void keyPressed(KeyEvent event)
    {
	System.out.println("pressed : " + event.getKeyCode() + "(" + event.getKeyChar() + ")");
	if(event.getKeyCode() == 10)
	    {
		System.out.println("go");
	    }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
