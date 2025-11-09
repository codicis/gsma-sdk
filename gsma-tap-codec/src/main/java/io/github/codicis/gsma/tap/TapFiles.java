package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.tap.internal.BerTypeReader;
import io.github.codicis.gsma.tap.spi.TransformerRegistry;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;

public class TapFiles {
    private TapFiles() {
    }

    public static Optional<DataInterChange> read(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(path, null, DataInterChange::new, options);
    }

    public static Optional<BatchControlInfo> batchInfo(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(path, BatchControlInfo.tag, BatchControlInfo::new, options);
    }

    public static Optional<AccountingInfo> accountingInfo(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(path, AccountingInfo.tag, AccountingInfo::new, options);
    }

    public static Optional<NetworkInfo> networkInfo(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(path, NetworkInfo.tag, NetworkInfo::new, options);
    }

    public static Optional<AuditControlInfo> auditInfo(Path path, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(path, AuditControlInfo.tag, AuditControlInfo::new, options);
    }

    public static Object transform(Path path, String formatter, Map<String,Object> options) throws Exception {
        DataInterChange dataInterChange = BerTypeReader.readByTag(path, null, DataInterChange::new, StandardOpenOption.READ).orElse(null);
        return TransformerRegistry.getInstance().transform(dataInterChange, formatter, options);
    }

}