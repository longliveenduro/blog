package de.threedimensions.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blog implements EntryPoint {

	private SplitLayoutPanel mainPanel = new SplitLayoutPanel();
	private FlexTable flexTable = new FlexTable();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		flexTable.add(new HTML("blog1"));
		flexTable.add(new HTML("blog2"));
		mainPanel.add(flexTable);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("blog").add(mainPanel);
	}
}
