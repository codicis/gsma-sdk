package io.github.codicis.gsma.json;

import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import io.github.codicis.gsma.tap.TapFiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.util.Map;

@ExtendWith(TapFileParameterResolver.class)
class JsonTransformerTest {

    @Test
    void testTapJson(@TestResource(TapTestFile.STANDARD) Path path) throws Exception {
        Object transform = TapFiles.transform(path, "json", Map.of());
    }

    @Test
    void testTapPrettyJson(@TestResource(TapTestFile.STANDARD) Path path) throws Exception {
        Object transform = TapFiles.transform(path, "json", Map.of("pretty", true));
    }
}