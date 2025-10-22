package io.github.codicis.gsma.json;

import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import io.github.codicis.gsma.tap.DataInterChange;
import io.github.codicis.gsma.tap.TapFiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URI;
import java.nio.file.StandardOpenOption;

@ExtendWith(TapFileParameterResolver.class)
class TapJsonServiceTest {

    @Test
    void testTapJsonService(@TestResource(TapTestFile.STANDARD) URI uri) throws IOException {
        DataInterChange dataInterChange = TapFiles.read(uri, StandardOpenOption.READ);
        TapJsonService tapJsonService = new TapJsonService();
        String json = tapJsonService.toJson(dataInterChange, true);
        System.out.println(json);
    }
}