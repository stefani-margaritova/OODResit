package controller;
import presentation.Presentation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/** <p>This is the controller.KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class KeyController extends KeyAdapter {
	private Presentation presentation; //Commands are given to the presentation

	public KeyController(Presentation p) {
		this.presentation = p;
	}
	
	// Added an override
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// Refactored the switch case to save space
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, (int) '+' -> presentation.nextSlide();
			case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, (int) '-' -> presentation.prevSlide();
			case (int) 'q', (int) 'Q' -> System.exit(0);
			default -> {
				// Shouldn't be reached
			}
		}
	}
}
