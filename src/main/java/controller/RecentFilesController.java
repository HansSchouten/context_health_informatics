package controller;

import java.io.File;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class to manage a file which contains the path to recently opened files.
 * @author Remi
 *
 */
public class RecentFilesController {
    /** The file path to the recent files file. */
    private Preferences prefs;
    /** The maximum amount of files to store. */
    private int maxSize;
    /** The list of files. */
    private ObservableList<File> files = FXCollections.observableArrayList();
    /** The key for finding a recent file. */
    private String key;

    /**
     * Constructs a new Recent Files controller.
     * @param itemKey The key for storing and retrieving the recent items.
     * @param size The maximum amount of recent files to store.
     */
    public RecentFilesController(String itemKey, int size) {
        key = itemKey;
        maxSize = size;
        prefs = Preferences.userRoot().node(this.getClass().getName());
        loadPreferences();
    }

    /**
     * Loads the recent files with JavaPreferences.
     */
    private void loadPreferences() {
        files.clear();
        for (int i = 0; i < maxSize; i++) {
            String path = prefs.get(key + i, "");
            File f = new File(path);
            if (f.exists()) {
                files.add(f);
            }
        }
    }

    /**
     * Stores the recent files with JavaPreferences.
     */
    private void storePreferences() {
        for (int i = 0; i < maxSize; i++) {
            if (i < files.size()) {
                prefs.put(key + i, files.get(i).getPath());
            } else {
                prefs.remove(key + i);
            }
        }
    }

    /**
     * Adds a file to the list of files. When the list of over capacity, the oldest file gets removed.
     * @param file The file to add.
     */
    public void add(File file) {
        if (file != null && file.exists()) {
            // Check if the path already exists
            int idx = files.stream().map(x -> x.getPath()).collect(Collectors.toList()).indexOf(file.getPath());
            if (idx >= 0) {
                files.remove(idx);
            }

            files.add(0, file);

            if (files.size() > maxSize) {
                files.remove(maxSize - 1);
            }
            storePreferences();
        }
    }

    /**
     * Returns the list of files that were recently opened.
     * @return The list of files.
     */
    public ObservableList<File> getFiles() {
        return files;
    }

    /**
     * Clears the preferences and the list of files.
     */
    public void clear() {
        for (int i = 0; i < maxSize; i++) {
            prefs.remove(key + i);
        }
        files.clear();
    }
}
