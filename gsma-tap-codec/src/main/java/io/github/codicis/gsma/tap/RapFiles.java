package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.rap.RapDataInterChange;
import io.github.codicis.gsma.tap.internal.BerTypeReader;

import java.io.IOException;
import java.net.URI;
import java.nio.file.OpenOption;

public class RapFiles {

    private RapFiles() {
    }

    public static RapDataInterChange read(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.read(uri, RapDataInterChange::new, options);
    }
}
