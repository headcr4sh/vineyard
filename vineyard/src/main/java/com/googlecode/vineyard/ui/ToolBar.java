package com.googlecode.vineyard.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.googlecode.vineyard.ui.action.RemoveWineAction;
import com.googlecode.vineyard.ui.action.EditWineAction;
import com.googlecode.vineyard.ui.action.NewWineAction;
import com.googlecode.vineyard.ui.action.ViewWinesAsGridAction;
import com.googlecode.vineyard.ui.action.ViewWinesAsListAction;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class ToolBar extends JToolBar {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 2076527835876181411L;

	@Inject private NewWineAction newWineAction;
	@Inject private EditWineAction editWineAction;
	@Inject private RemoveWineAction removeWineAction;
	@Inject private ViewWinesAsListAction viewWinesAsListAction;
	@Inject private ViewWinesAsGridAction viewWinesAsGridAction;

	private JTextField searchField;

	public ToolBar() {
		super();
		setFloatable(false);
	}

	@Inject
	protected void postConstruct() {

		searchField = new JTextField();
		searchField.setColumns(20);
		searchField.putClientProperty("JTextField.variant", "search");

		final JButton newWineButton = createSegmentButton("segmentedTextured", "only", null);
		newWineButton.setAction(newWineAction);
		newWineButton.setText(null);
		this.add(newWineButton);

		final JButton removeWineButton = createSegmentButton("segmentedTextured", "only", null);
		removeWineButton.setAction(removeWineAction);
		removeWineButton.setText(null);
		this.add(removeWineButton);

		this.addSeparator(new Dimension(20, 0));

		final JButton editWineButton = createSegmentButton("segmentedTextured", "only", null);
		editWineButton.setAction(editWineAction);
		editWineButton.setText(null);
		this.add(editWineButton);

		this.addSeparator(new Dimension(20, 0));

		final ButtonGroup viewAsButtonGroup = new ButtonGroup();
		final JToggleButton viewWinesAsListButton = createSegmentToggleButton("segmentedTextured", "first", viewAsButtonGroup);
		viewWinesAsListButton.setAction(viewWinesAsListAction);
		viewWinesAsListButton.setText(null);
		final JToggleButton viewWinesAsGridButton = createSegmentToggleButton("segmentedTextured", "last", viewAsButtonGroup);
		viewWinesAsGridButton.setAction(viewWinesAsGridAction);
		viewWinesAsGridButton.setText(null);
		final JComponent viewAsButtonsLayoutComponent = createLayoutComponent(viewWinesAsListButton, viewWinesAsGridButton);
		this.add(viewAsButtonsLayoutComponent);

		this.add(new JToolBar.Separator() {
			/** @see java.io.Serializable */
			private static final long serialVersionUID = 1393621790236552869L;
			@Override
			public Dimension getPreferredSize() {
				setSeparatorSize(new Dimension(0, 0));
				final Dimension toolBarDimension = ToolBar.this.getSize();
				return new Dimension(toolBarDimension.width - 200, 0);
			}
		});
		this.add(searchField);
	}




	// Create a Layout component that will ensure the buttons abut each other
	public static JComponent createLayoutComponent(JButton ... segmentButtons) {
		Box layoutBox = Box.createHorizontalBox();
		for(JButton button : segmentButtons) {
			layoutBox.add(button);
		}
		return layoutBox;
	}

	// Create a Layout component that will ensure the buttons abut each other
	public static JComponent createLayoutComponent(JToggleButton ... segmentButtons) {
		Box layoutBox = Box.createHorizontalBox();
		for(JToggleButton button : segmentButtons) {
			layoutBox.add(button);
		}
		return layoutBox;
	}

	public static JButton createSegmentButton(String style, String position, ButtonGroup buttonGrp) {
		JButton button = new JButton();
		button.putClientProperty("JButton.buttonType", style);
		button.putClientProperty("JButton.segmentPosition", position);
		if (buttonGrp != null) {
			buttonGrp.add(button);
		}
		return button;
	}

	public static JToggleButton createSegmentToggleButton(String style, String position, ButtonGroup buttonGrp) {
		JToggleButton button = new JToggleButton();
		button.putClientProperty("JButton.buttonType", style);
		button.putClientProperty("JButton.segmentPosition", position);
		if (buttonGrp != null) {
			buttonGrp.add(button);
		}
		return button;
	}

	// Bottleneck for creating the buttons for the button group
	public static List<JButton> createSegmentButtonsWithStyle(int numButtons, ButtonGroup buttonGrp, String style){
	  // Allocate a list of JButtons
	  List<JButton> buttons = new ArrayList<JButton>();
	  if(numButtons == 1) {
	    // If 1 button is requested, then it gets the "only" segment position
	    buttons.add(createSegmentButton(style, "only", buttonGrp));
	  } else {
	    // If more than 1 button is requested, then
	    // the first one gets "first" the last one gets "last" and the rest get "middle"
	    buttons.add(createSegmentButton(style, "first", buttonGrp));
	    for(int i = 0; i < buttons.size() - 2; ++i) {
	      buttons.add(createSegmentButton(style, "middle", buttonGrp));
	    }
	    buttons.add(createSegmentButton(style, "last", buttonGrp));
	  }
	  return buttons;
	}

	// Convenience methods that pass in the correct button style for each segmented button style
	public static List<JButton> createSegmentedButtons(int numButtons, ButtonGroup buttonGroup) {
	  return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmented");
	}

	public static List<JButton> createSegmentedRoundRectButtons(int numButtons, ButtonGroup buttonGroup) {
	  return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedRoundRect");
	}

	public static List<JButton> createSegmentedCapsuleButtons(int numButtons, ButtonGroup buttonGroup) {
	  return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedCapsule");
	}

	public static List<JButton> createSegmentedTexturedButtons(int numButtons, ButtonGroup buttonGroup) {
	  return createSegmentButtonsWithStyle(numButtons, buttonGroup, "segmentedTextured");
	}

}
