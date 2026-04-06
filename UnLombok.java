import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class UnLombok {
    public static void main(String[] args) throws Exception {
        Path startPath = Paths.get("d:/OTCNEW/otc-spring-boot-api/src/main/java/com/otc/api/model");
        Files.walk(startPath)
            .filter(path -> path.toString().endsWith(".java"))
            .forEach(UnLombok::processFile);
    }
    
    private static void processFile(Path file) {
        try {
            String content = new String(Files.readAllBytes(file), "UTF-8");
            
            // Assuming the script was already run once, but in case there are still @Data, we remove them (already removed).
            // But wait, the previous run appended getters and setters to the end of the file.
            // If I run it again, it will append them TWICE!
        } catch (Exception e) {}
    }
}
