package t3h.vn.testonline.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ImageUtils {
    private static final Path IMAGE_DIR = Paths.get(Constant.IMAGE_PATH);
    public static Optional<String> upload(HttpServletRequest request) {
        Optional<String> imageName = Optional.empty();
        try {
            Part filePart = request.getPart("image");
            if (filePart.getSize() != 0 && filePart.getContentType().startsWith("image")) {
                if (!Files.exists(IMAGE_DIR)) {
                    Files.createDirectories(IMAGE_DIR);
                }
                Path targetLocation = Files.createTempFile(IMAGE_DIR, "img-", ".jpg");
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, targetLocation, StandardCopyOption.REPLACE_EXISTING);
                }
                imageName = Optional.of(targetLocation.getFileName().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageName;
    }
}
