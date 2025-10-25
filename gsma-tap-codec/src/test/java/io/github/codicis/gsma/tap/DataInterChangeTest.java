package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(TapFileParameterResolver.class)
public class DataInterChangeTest {

    @Test
    @DisplayName("Decode Notification TAP file")
    public void notification(@TestResource(TapTestFile.NOTIFICATION) Path path) {
        int recordCount = decodeTapFile(path);
        assertEquals(135, recordCount, "Unexpected record count in Notification TAP file");
    }

    @Test
    @DisplayName("Decode Standard TAP file")
    public void decode(@TestResource(TapTestFile.STANDARD) Path path) {
        int recordCount = decodeTapFile(path);
        assertEquals(668, recordCount, "Unexpected record count in Standard TAP file");
    }

    @Test
    @DisplayName("Decode Content Transaction TAP file")
    public void contentTransaction(@TestResource(TapTestFile.CONTENT_TRANSACTION) Path resource) {
        int recordCount = decodeTapFile(resource);
        assertEquals(4448, recordCount, "Unexpected record count in Content Transaction TAP file");
    }

    private int decodeTapFile(Path resource) {
        assertNotNull(resource, "Injected TAP file path is null");

        try (InputStream inputStream = Files.newInputStream(resource, StandardOpenOption.READ)) {
            assertNotNull(inputStream, "Failed to open TAP file: " + resource);
            // Decode or process the stream here
            DataInterChange dataInterChange = new DataInterChange();
            return dataInterChange.decode(inputStream);
        } catch (Exception e) {
            throw new ParameterResolutionException("Failed to resolve path for resource: " + resource, e);
        }
    }
}
