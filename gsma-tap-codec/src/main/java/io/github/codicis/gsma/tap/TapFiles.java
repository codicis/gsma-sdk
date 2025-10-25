package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.tap.internal.BerTypeReader;
import io.github.codicis.gsma.tap.spi.TransformerRegistry;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;

public class TapFiles {

    private TapFiles() {
    }

    public static DataInterChange read(Path path) throws IOException {
        return BerTypeReader.read(path, DataInterChange::new, StandardOpenOption.READ);
    }

    public static Optional<BatchControlInfo> batchInfo(Path path) throws IOException {
        return BerTypeReader.readByTag(path, BatchControlInfo.tag, BatchControlInfo::new, StandardOpenOption.READ);
    }

    public static Optional<AccountingInfo> accountingInfo(Path path) throws IOException {
        return BerTypeReader.readByTag(path, AccountingInfo.tag, AccountingInfo::new, StandardOpenOption.READ);
    }

    public static Optional<NetworkInfo> networkInfo(Path path) throws IOException {
        return BerTypeReader.readByTag(path, NetworkInfo.tag, NetworkInfo::new, StandardOpenOption.READ);
    }

    public static Optional<AuditControlInfo> auditInfo(Path path) throws IOException {
        return BerTypeReader.readByTag(path, AuditControlInfo.tag, AuditControlInfo::new, StandardOpenOption.READ);
    }

    public static Object transform(Path path, String formatter, Map<String,Object> options) throws Exception {
        DataInterChange dataInterChange = BerTypeReader.read(path, DataInterChange::new, StandardOpenOption.READ);
        return TransformerRegistry.getInstance().transform(dataInterChange, formatter, options);
    }

}