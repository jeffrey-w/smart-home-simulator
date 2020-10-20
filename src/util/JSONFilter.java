package util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The {@code JSONFilter} class imposes a restriction on {@code File} arguments to JSON files (i.e. those with the
 * suffix '.json').
 *
 * @author Jeff Wilgus
 */
public class JSONFilter extends FileFilter {

    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        String filename = f.getName().toLowerCase();
        return filename.endsWith(".json");
    }

    @Override
    public String getDescription() {
        return "JSON files (*.json)";
    }

}
