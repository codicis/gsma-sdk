package io.github.codicis.gsma.tap;

import com.beanit.asn1bean.ber.BerTag;
import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import io.github.codicis.gsma.tap.internal.BerTypeReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@ExtendWith(TapFileParameterResolver.class)
class TapFilesTest {

    @Test
    @DisplayName("Scan Tags")
    public void scanTags(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            List<BerTag> berTags = BerTypeReader.scanTags(path);
            Assertions.assertNotNull(berTags);
        });
    }

    @Test
    @DisplayName("Read TAP file")
    public void read(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            DataInterChange dataInterChange = TapFiles.read(path, StandardOpenOption.READ).orElse(null);
            Assertions.assertNotNull(dataInterChange);
        });
    }

    @Test
    @DisplayName("Read BatchControlInfo")
    public void batchInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            BatchControlInfo batchControlInfo = TapFiles.batchInfo(path, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertEquals("AUTPT", new String(batchControlInfo.getSender().value));
            Assertions.assertEquals("EUR01", new String(batchControlInfo.getRecipient().value));
            Assertions.assertNotNull(batchControlInfo);
        });
    }

    @Test
    @DisplayName("Read AccountingInfo")
    public void accountingInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            AccountingInfo accountingInfo = TapFiles.accountingInfo(path, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(accountingInfo);
        });
    }

    @Test
    @DisplayName("Read NetworkInfo")
    public void networkInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            NetworkInfo networkInfo = TapFiles.networkInfo(path, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(networkInfo);
        });
    }

    @Test
    @DisplayName("Read AuditControlInfo")
    public void auditInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            AuditControlInfo auditInfo = TapFiles.auditInfo(path, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(auditInfo);
        });
    }

}