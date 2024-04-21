package api;

import presentation.DemoPresentation;
import presentation.Presentation;
import java.io.IOException;

/**
 * <p>An api.Accessor makes it possible to read and write data
 * for a presentation.</p>
 * <p>Non-abstract subclasses should implement the load and save methods.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class Accessor {

	public static Accessor getDemoAccessor() {
		return new DemoPresentation();
	}

	abstract public void loadFile(Presentation p, String fn) throws IOException;

	abstract public void saveFile(Presentation p, String fn) throws IOException;

}
