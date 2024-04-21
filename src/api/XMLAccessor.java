package api;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import presentation.*;

/** api.XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor
{
    private String getTitle(Element element, String tag) {
    	NodeList titles = element.getElementsByTagName(tag);
    	return titles.item(0).getTextContent();
    }

	public void loadFile(Presentation presentation, String filename) throws IOException {
		// Removed redundant variables
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			// Create a JDOM document from the XML file
			Document document = builder.parse(new File(filename));
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTitle(doc, XMLTags.SHOWTITLE));

			NodeList slides = doc.getElementsByTagName(XMLTags.SLIDE);
			for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
				// Removed redundant casting to SlideItem
				Element slideElement = (Element) slides.item(slideNumber);
				Slide slide = new Slide();
				slide.setTitle(getTitle(slideElement, XMLTags.TITLE));
				presentation.append(slide);
				
				NodeList items = slideElement.getElementsByTagName(XMLTags.ITEM);
				for (int itemNumber  = 0; itemNumber < items.getLength(); itemNumber ++) {
					loadSlideItem(slide, (Element) items.item(itemNumber ));
				}
			}
		}
		// Removed an unused catch
		catch (IOException | SAXException sax) {
			System.err.println(sax.getMessage());
		}
		catch (ParserConfigurationException pcx) {
			System.err.println(XMLTags.PCE);
		}	
	}

	protected void loadSlideItem(Slide slide, Element item) {
		int level = 1; // default
		NamedNodeMap attributes = item.getAttributes();
		String levelText = attributes.getNamedItem(XMLTags.LEVEL).getTextContent();
		if (levelText != null) {
			try {
				level = Integer.parseInt(levelText);
			}
			catch(NumberFormatException x) {
				System.err.println(XMLTags.NFE);
			}
		}
		String type = attributes.getNamedItem(XMLTags.KIND).getTextContent();
		if (XMLTags.TEXT.equals(type)) {
			slide.append(new TextItem(level, item.getTextContent()));
		}
		else {
			if (XMLTags.IMAGE.equals(type)) {
				slide.append(new BitmapItem(level, item.getTextContent()));
			}
			else {
				System.err.println(XMLTags.UNKNOWNTYPE);
			}
		}
	}

	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.print("<showtitle>");
		out.print(presentation.getTitle());
		out.println("</showtitle>");
		for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			out.println("<slide>");
			out.println("<title>" + slide.getTitle() + "</title>");
			ArrayList<SlideItem> slideItems = slide.getSlideItems();
			for (SlideItem slideItem : slideItems) {
				out.print("<item kind="); 
				if (slideItem instanceof TextItem) {
					out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
					out.print( ( (TextItem) slideItem).getText());
				}
				else {
					if (slideItem instanceof BitmapItem) {
						out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
						out.print( ( (BitmapItem) slideItem).getName());
					}
					else {
						System.out.println("Ignoring " + slideItem);
					}
				}
				out.println("</item>");
			}
			out.println("</slide>");
		}
		out.println("</presentation>");
		out.close();
	}
}
