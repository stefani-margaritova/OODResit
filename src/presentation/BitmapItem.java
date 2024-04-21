package presentation;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem
{
	protected static final String FILE = "File ";
	protected static final String NOTFOUND = " not found";
  	private BufferedImage bufferedImage;
	// Made imageName final
  	private final String imageName;
  
  	// Level indicates the item-level; name indicates the name of the file with the image
	public BitmapItem(int level, String name) {
		super(level);
		this.imageName = name;
		try {
			this.bufferedImage = ImageIO.read(new File(this.imageName));
		}
		catch (IOException e) {
			System.err.println(FILE + this.imageName + NOTFOUND) ;
		}
	}

	// Returns the filename of the image
	public String getName() {
		return this.imageName;
	}

	// Returns the bounding box of the image
	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle((int) (myStyle.indent * scale), 0,
			(int) (this.bufferedImage.getWidth(observer) * scale),
			((int) (myStyle.leading * scale)) +
			(int) (this.bufferedImage.getHeight(observer) * scale));
	}

	// Draws the image
	@Override
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.indent * scale);
		int height = y + (int) (myStyle.leading * scale);
		g.drawImage(this.bufferedImage, width, height,(int) (this.bufferedImage.getWidth(observer)*scale),
		(int) (this.bufferedImage.getHeight(observer)*scale), observer);
	}
	
	@Override
	public String toString() {
		return "presentation.BitmapItem[" + getLevel() + "," + this.imageName + "]";
	}
}
