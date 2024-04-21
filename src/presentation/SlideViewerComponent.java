package presentation;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.io.Serial;

import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>presentation.SlideViewerComponent is a graphical component that ca display Slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {
		
	private Slide slide; //The current slide
	private Font labelFont;
	private Presentation presentation;
	private JFrame frame;
	
	// Added @Serial annotation
	@Serial
	private static final long serialVersionUID = 227L;

	public SlideViewerComponent(Presentation pres, JFrame frame) {
		setBackground(SlideTags.BGCOLOR);
		this.presentation = pres;
		this.labelFont = new Font(SlideTags.FONTNAME, SlideTags.FONTSTYLE, SlideTags.FONTHEIGHT);
		this.frame = frame;
	}

	public Dimension getPreferredSize() {
		return new Dimension(SlideTags.WIDTH, SlideTags.HEIGHT);
	}

	public void update(Presentation presentation, Slide data) {
		if (data == null) {
			repaint();
			return;
		}
		
		this.presentation = presentation;
		this.slide = data;
		repaint();
		this.frame.setTitle(presentation.getTitle());
	}

	// Draw the slide
	public void paintComponent(Graphics g) {
		g.setColor(SlideTags.BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		
		if (this.presentation.getSlideNumber() < 0 || slide == null) {
			return;
		}
		
		g.setFont(labelFont);
		g.setColor(SlideTags.COLOR);
		g.drawString("presentation.Slide " + (1 + this.presentation.getSlideNumber()) + " of " + this.presentation.getSize(), SlideTags.XPOS, SlideTags.YPOS);
		Rectangle area = new Rectangle(0, SlideTags.YPOS, getWidth(), (getHeight() - SlideTags.YPOS));
		slide.draw(g, area, this);
	}
}
