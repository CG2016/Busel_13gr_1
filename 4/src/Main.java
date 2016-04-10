import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        MetadataImageInfo meta = new MetadataImageInfo();
        File[] files = new File("C:\\uni\\CG\\Busel_13gr_1\\4\\").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                ImageInfo.main(new String[]{file.getName()});
                meta.readAndDisplayMetadata(file.getName());
            }
        }
    }
}
