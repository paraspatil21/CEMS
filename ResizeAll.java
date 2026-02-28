import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ResizeAll {
    public static void main(String[] args) throws Exception {
        Path uiDir = Paths.get("frontend/ui");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(uiDir, "*.java")) {
            for (Path entry : stream) {
                String content = new String(Files.readAllBytes(entry));
                String updated = content.replace(
                        "setMinimumSize(new java.awt.Dimension(1024, 600));n        setExtendedState(JFrame.MAXIMIZED_BOTH);",
                        "setMinimumSize(new java.awt.Dimension(1024, 600));\n        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);");
                Files.write(entry, updated.getBytes());
                System.out.println("Updated " + entry.getFileName());
            }
        }
    }
}
