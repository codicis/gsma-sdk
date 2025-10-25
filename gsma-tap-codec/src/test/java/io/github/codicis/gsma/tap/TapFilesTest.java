package io.github.codicis.gsma.tap;

import io.github.codicis.gsma.resolver.TapFileParameterResolver;
import io.github.codicis.gsma.resolver.TapTestFile;
import io.github.codicis.gsma.resolver.TestResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;

@ExtendWith(TapFileParameterResolver.class)
class TapFilesTest {

    @Test
    @DisplayName("Read TAP file")
    public void read(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            DataInterChange dataInterChange = TapFiles.read(path);
            Assertions.assertNotNull(dataInterChange);
        });
    }

    @Test
    @DisplayName("Read BatchControlInfo")
    public void batchInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            BatchControlInfo batchControlInfo = TapFiles.batchInfo(path).orElse(null);
            Assertions.assertNotNull(batchControlInfo);
            Assertions.assertEquals("AUTPT", new String(batchControlInfo.getSender().value));
            Assertions.assertEquals("EUR01", new String(batchControlInfo.getRecipient().value));
            Assertions.assertNotNull(batchControlInfo);
        });
    }

    @Test
    @DisplayName("Read AccountingInfo")
    public void accountingInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            AccountingInfo accountingInfo = TapFiles.accountingInfo(path).orElse(null);
            Assertions.assertNotNull(accountingInfo);
        });
    }

    @Test
    @DisplayName("Read NetworkInfo")
    public void networkInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            NetworkInfo networkInfo = TapFiles.networkInfo(path).orElse(null);
            Assertions.assertNotNull(networkInfo);
        });
    }

    @Test
    @DisplayName("Read AuditControlInfo")
    public void auditInfo(@TestResource(TapTestFile.STANDARD) Path path) {
        Assertions.assertDoesNotThrow(() -> {
            AuditControlInfo auditInfo = TapFiles.auditInfo(path).orElse(null);
            Assertions.assertNotNull(auditInfo);
        });
    }

}