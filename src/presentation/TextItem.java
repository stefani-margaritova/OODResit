package presentation;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import java.text.AttributedString;

import java.util.List;
import java.util.ArrayList;

/** <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private static final String EMPTYTEXT = "No Text Given";
	private String text;

	// A textItem of int level with text string
	public TextItem(int level, String string) {
		super(level);
		this.text = string;
	}

	// Returns the text
	public String getText() {
		return this.text == null ? "" : text;
	}

	// Returns the AttributedString for the Item
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		
		if (getText() == null || getText().isEmpty()) {
			attrStr = new AttributedString(EMPTYTEXT);
			return attrStr;
		}

		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.text.length());
		return attrStr;
	}
	
	// Added an @Override annotation
	@Override
	// Returns the bounding box of an Item
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, 
		float scale, Style myStyle) {
		ArrayList<TextLayout> layouts = getLayouts(g, myStyle, scale);
		int xsize = 0, ysize = (int) (myStyle.leading * scale);
  
		// Replaced while loop with a for each
        for (TextLayout layout : layouts)
        {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize)
            {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0)
            {
                ysize += (int) bounds.getHeight();
            }
            ysize += (layout.getLeading() + layout.getDescent());
        }
		return new Rectangle((int) (myStyle.indent*scale), 0, xsize, ysize );
	}
	
	// Added an @Override annotation
	@Override
	// Draws the textItem
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
		if (this.text == null || this.text.isEmpty()) {
			return;
		}
		
		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		Point pen = new Point(x + (int)(myStyle.indent * scale), y + (int) (myStyle.leading * scale));
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(myStyle.color);
		
		// Replaced while loop with a for each
		for (TextLayout layout : layouts)
        {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
  	}

	private ArrayList<TextLayout> getLayouts(Graphics g, Style s, float scale) {
		// Removed the TextLayout argument in <>
		ArrayList<TextLayout> layouts = new ArrayList<>();
		AttributedString attrStr = getAttributedString(s, scale);
    	Graphics2D g2d = (Graphics2D) g;
    	FontRenderContext frc = g2d.getFontRenderContext();
    	LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
    	float wrappingWidth = (SlideTags.WIDTH - s.indent) * scale;
    	
		while (measurer.getPosition() < getText().length()) {
    		TextLayout layout = measurer.nextLayout(wrappingWidth);
    		layouts.add(layout);
    	}
    	return layouts;
	}
	
	// Added an @Override annotation
	@Override
	public String toString() {
		return "presentation.TextItem[" + getLevel()+","+getText()+"]";
	}
}
