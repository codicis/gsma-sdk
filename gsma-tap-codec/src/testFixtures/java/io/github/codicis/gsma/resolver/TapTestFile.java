package io.github.codicis.gsma.resolver;

public enum TapTestFile {
    NOTIFICATION("TDAUTPTEUR0100304_Notification.tap311"),
    STANDARD("TDAUTPTEUR0100303.tap311"),
    CONTENT_TRANSACTION("TDAUTPTEUR0100006_CONTRANS.TAP311");

    private final String filename;

    TapTestFile(String filename) {
        this.filename = filename;
    }

    public String filename() {
        return filename;
    }
}
