package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URI;
import java.nio.file.*;

@ExtendWith(TapFileParameterResolver.class)
class TapFilesTest {

    @Test
    @DisplayName("Read TAP file")
    public void read(@TestResource(TapTestFile.STANDARD) URI path) {
        Assertions.assertDoesNotThrow(() -> {
            DataInterChange dataInterChange = TapFiles.read(path, StandardOpenOption.READ);
            Assertions.assertNotNull(dataInterChange);
        });
    }

    @Test
    @DisplayName("Read BatchControlInfo")
    public void batchInfo(@TestResource(TapTestFile.STANDARD) URI uri) {
        Assertions.assertDoesNotThrow(() -> {
            BatchControlInfo batchControlInfo = TapFiles.batchInfo(uri, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(batchControlInfo);
            Assertions.assertEquals("AUTPT", new String(batchControlInfo.getSender().value));
            Assertions.assertEquals("EUR01", new String(batchControlInfo.getRecipient().value));
            Assertions.assertNotNull(batchControlInfo);
        });
    }

    @Test
    @DisplayName("Read AccountingInfo")
    public void accountingInfo(@TestResource(TapTestFile.STANDARD) URI uri) {
        Assertions.assertDoesNotThrow(() -> {
            AccountingInfo accountingInfo = TapFiles.accountingInfo(uri, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(accountingInfo);
        });
    }

    @Test
    @DisplayName("Read NetworkInfo")
    public void networkInfo(@TestResource(TapTestFile.STANDARD) URI uri) {
        Assertions.assertDoesNotThrow(() -> {
            NetworkInfo networkInfo = TapFiles.networkInfo(uri, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(networkInfo);
        });
    }

    @Test
    @DisplayName("Read AuditControlInfo")
    public void auditInfo(@TestResource(TapTestFile.STANDARD) URI uri) {
        Assertions.assertDoesNotThrow(() -> {
            AuditControlInfo auditInfo = TapFiles.auditInfo(uri, StandardOpenOption.READ).orElseGet(() -> null);
            Assertions.assertNotNull(auditInfo);
        });
    }

}