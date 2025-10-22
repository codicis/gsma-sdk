package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.tap.internal.BerTypeReader;

import java.io.IOException;
import java.net.URI;
import java.nio.file.OpenOption;
import java.util.Optional;

public class TapFiles {
    private TapFiles() {
    }

    public static DataInterChange read(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.read(uri, DataInterChange::new, options);
    }

    public static Optional<BatchControlInfo> batchInfo(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(uri, BatchControlInfo.tag, BatchControlInfo::new, options);
    }

    public static Optional<AccountingInfo> accountingInfo(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(uri, AccountingInfo.tag, AccountingInfo::new, options);
    }

    public static Optional<NetworkInfo> networkInfo(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(uri, NetworkInfo.tag, NetworkInfo::new, options);
    }

    public static Optional<AuditControlInfo> auditInfo(URI uri, OpenOption... options) throws IOException {
        return BerTypeReader.readByTag(uri, AuditControlInfo.tag, AuditControlInfo::new, options);
    }

}