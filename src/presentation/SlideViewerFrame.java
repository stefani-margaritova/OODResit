package presentation;

import controller.KeyController;
import controller.MenuController;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.io.Serial;

import javax.swing.JFrame;

/**
 * <p>The application window for a slideViewComponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class SlideViewerFrame extends JFrame {
	// Added @Serial annotation
	@Serial
	private static final long serialVersionUID = 3227L;
	
	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	// Set up the GUI
	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(SlideTags.JABTITLE);
		addWindowListener(new WindowAdapter() {
				// Added an @Override annotation
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
		});
		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(presentation)); //Add a controller
		setMenuBar(new MenuController(this, presentation));	//Add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
		setVisible(true);
	}
}
