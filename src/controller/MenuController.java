package controller;
import presentation.Presentation;

import api.Accessor;
import api.XMLAccessor;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;
import java.io.Serial;

import javax.swing.JOptionPane;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	// Added @Serial annotation
	@Serial
	private static final long serialVersionUID = 227L;
	private Frame parent; // The frame, only used as parent for the Dialogs
	private Presentation presentation; // Commands are given to the presentation
	private MenuItem menuItem;
	
	public MenuController(Frame frame, Presentation p)
	{
		this.presentation = p;
		this.parent = frame;
		// Extracted methods from constructor
		addFileMenu();
		addViewMenu();
		addHelpMenu();
	}
	
	private MenuItem createAndAddMenuItem(Menu menu, String itemName) {
		MenuItem menuItem = new MenuItem(itemName, new MenuShortcut(itemName.charAt(0)));
		menu.add(menuItem);
		return menuItem;
	}
	
	private void savePresentation(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.SAVE);
		menuItem.addActionListener(e -> {
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.saveFile(presentation, MenuTags.SAVEFILE);
			} catch (IOException exc) {
				System.err.println(exc.getMessage());
			}
		});
	}
	
	// Open a new presentation; Will open the demo presentation
	private void openPresentation(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.OPEN);
		presentation.clear();
		Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.loadFile(presentation, MenuTags.TESTFILE);
			presentation.setSlideNumber(0);
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}
		parent.repaint();
	}
	
	private void newPresentation(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.NEW);
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parent.repaint();
		});
	}
	
	private void exitPresentation(Menu menu) {
		//MenuItem menuItem;
		menuItem = createAndAddMenuItem(menu, MenuTags.EXIT);
		menuItem.addActionListener(actionEvent -> presentation.exit());
	}
	
	private void navNextSlide(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.NEXT);
		menuItem.addActionListener(actionEvent -> presentation.nextSlide());
	}
	
	private void navPrevSlide(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.PREV);
		menuItem.addActionListener(actionEvent -> presentation.prevSlide());
	}
	
	private void navToSlide(Menu menu) {
		menuItem = createAndAddMenuItem(menu, MenuTags.GOTO);
		menuItem.addActionListener(actionEvent -> {
			String pageNumberStr = JOptionPane.showInputDialog(MenuTags.PAGENR);
			int pageNumber = Integer.parseInt(pageNumberStr);
			
			if(pageNumber > presentation.getSize()) {
				pageNumber = presentation.getSize() - 1;
			}
			else if (pageNumber > 0)
			{
				pageNumber = pageNumber - 1;
			}
			
			presentation.setSlideNumber(pageNumber);
		});
	}
	
	private void aboutBox(Menu menu)
	{
		menuItem = createAndAddMenuItem(menu, MenuTags.ABOUT);
		menuItem.addActionListener(actionEvent ->
		{
			JOptionPane.showMessageDialog(parent,
					"JabberPoint is a primitive slide-show program in Java(tm). It\n" +
							"is freely copyable as long as you keep this notice and\n" +
							"the splash screen intact.\n" +
							"Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
							"Adapted by Gert Florijn (version 1.1) and " +
							"Sylvia Stuurman (version 1.2 and higher) for the Open" +
							"University of the Netherlands, 2002 -- now.\n" +
							"Author's version available from http://www.darwinsys.com/",
					"About JabberPoint",
					JOptionPane.INFORMATION_MESSAGE
			);
		});
	}
	
	private void addViewMenu()
	{
		Menu viewMenu = new Menu(MenuTags.VIEW);
		
		navNextSlide(viewMenu);
		navPrevSlide(viewMenu);
		navToSlide(viewMenu);
		add(viewMenu);
	}
	
	private void addHelpMenu()
	{
		Menu helpMenu = new Menu(MenuTags.HELP);
		
		aboutBox(helpMenu);
		
		add(helpMenu);
	}
	
	private void addFileMenu()
	{
		Menu fileMenu = new Menu(MenuTags.FILE);
		
		this.newPresentation(fileMenu);
		this.openPresentation(fileMenu);
		this.savePresentation(fileMenu);
		this.exitPresentation(fileMenu);
		
		add(fileMenu);
	}
}
