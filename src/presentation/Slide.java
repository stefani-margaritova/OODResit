package presentation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	// The title is kept separately
	protected String title;
	// Replaced vector with ArrayList
	protected ArrayList<SlideItem> items;

	public Slide() {
		this.items = new ArrayList<>();
	}

	// Return the title of a slide
	public String getTitle() {
		return this.title;
	}

	// Change the title of a slide
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}
	
	// Returns the presentation.SlideItem
	public SlideItem getSlideItem(int number) {
		return this.items.get(number);
	}
	
	// Add a presentation.SlideItem
	public void append(SlideItem newItem) {
		this.items.add(newItem);
	}
	
	// Return all the SlideItems in the ArrayList
	public ArrayList<SlideItem> getSlideItems() {
		return this.items;
	}
	
	// Create a presentation.TextItem out of a String and add the presentation.TextItem
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	// Returns the size of a slide
	public int getSize() {
		return items.size();
	}

	// Draws the slide
	public void draw(Graphics g, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
	    int y = area.y;
		// The title is treated separately
	    SlideItem slideItem = new TextItem(0, getTitle());
	    Style style = Style.getStyle(slideItem.getLevel());
	    slideItem.draw(area.x, y, scale, g, style, view);
	    y += slideItem.getBoundingBox(g, view, scale, style).height;
	    for (int number = 0; number < getSize(); number++) {
	    	// Removed redundant casting to SlideItem
			slideItem = getSlideItem(number);
			style = Style.getStyle(slideItem.getLevel());
			slideItem.draw(area.x, y, scale, g, style, view);
			y += slideItem.getBoundingBox(g, view, scale, style).height;
	    }
	  }

	// Returns the scale to draw a slide
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float) SlideTags.WIDTH), ((float)area.height) / ((float) SlideTags.HEIGHT));
	}
}
