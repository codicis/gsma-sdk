package io.github.codicis.gsma.tap.internal;

import io.github.codicis.gsma.rap.RapDataInterChange;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class RapFiles {

    private RapFiles() {
    }

    public static RapDataInterChange read(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.read(path, RapDataInterChange::new, options);
    }
}
