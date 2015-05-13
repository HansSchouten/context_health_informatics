package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The list item for the list of imported files.
 * @author Remi
 *
 */
public class FileListItem extends HBox {
	/**
	 * This variable stores a label.
	 */
	private Label label = new Label();

	/**
	 * This variable stores the remove button.
	 */
	private Button remove;

	/**
	 * This variable stores the path to the file.
	 */
	private String path;

	/**
	 * Constructs a file list item.
	 * @param labelText The text on the label (file name)
	 * @param filePath The path to the file
	 * @param list The reference to the parent list
	 */
	FileListItem(String labelText, String filePath, final ObservableList<FileListItem> list) {
		super();
		this.setPath(filePath);

		label.setText(labelText);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setPadding(new Insets(4));
		HBox.setHgrow(label, Priority.ALWAYS);

		// Add button to remove this item from the list
		remove = new Button("x");
		final FileListItem self = this;
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				list.remove(self);
			}
		});

		this.getChildren().addAll(label, remove);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
