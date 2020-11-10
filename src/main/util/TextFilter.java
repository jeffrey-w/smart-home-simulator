package main.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The {@code JSONFilter} class imposes a restriction that {@code File} arguments may only refer to text files (i.e.
 * those with the suffix '.txt').
 *
 * @author Ayman Shehri
 */
public class TextFilter extends FileFilter {

    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        String filename = f.getName().toLowerCase();
        return filename.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text files (*.txt)";
    }

}
