package io.github.codicis.gsma.tap.internal;

import com.beanit.asn1bean.ber.types.BerInteger;
import com.beanit.asn1bean.ber.types.BerOctetString;
import com.beanit.asn1bean.ber.types.BerType;
import io.github.codicis.gsma.tap.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class BerUtils {
    public static final DateTimeFormatter TAP_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Converts a packed BCD-encoded byte array into a human-readable decimal string.
     * <p>
     * Each byte contains two decimal digits: the high nibble (4 bits) and the low nibble.
     * This method decodes each nibble into its corresponding digit and stops decoding
     * when a nibble exceeds 9, which typically indicates padding (e.g., 0xF).
     * <p>
     * Useful for decoding numeric fields from binary protocols or TLV structures
     * where BCD encoding is used and padded with 0xF.
     *
     * @param value the byte array containing packed BCD digits, possibly with padding
     * @return the decoded numeric string, excluding any padding
     *
     */
    public static String bcdToStringWithPadding(byte[] value) {
        StringBuilder result = new StringBuilder();
        for (byte b : value) {
            int high = (b & 0xF0) >>> 4;
            int low = b & 0x0F;

            if (high <= 9) result.append(high);
            else break; // padding or invalid digit

            if (low <= 9) result.append(low);
            else break; // padding or invalid digit
        }
        return result.toString();
    }

    public static <T extends BerType> String toString(T berValue) {
        return switch (berValue) {
            case null -> "";
            case BCDString bcdString -> bcdToStringWithPadding(bcdString.value);
            case BerOctetString octet -> octet.value != null ? new String(octet.value) : "";
            case BerInteger berInt -> berInt.value != null ? berInt.value.toString() : "";
            case DateTimeLong dateTimeLong -> toZonedDateTime(dateTimeLong).toString();
            default -> berValue.toString()
                    .replaceAll("[\\s\\r\\n]", "")
                    .replaceAll("^\\{", "")
                    .replaceAll("}$", "");
        };

    }

    public static <T extends BerOctetString> String berOctetStringToString(T berOctetString) {
        return berOctetString == null ? "" : new String(berOctetString.value);
    }

    public static <T extends AsciiString> String asciiStringToString(T berOctetString) {
        return berOctetString == null ? "" : new String(berOctetString.value);
    }

    public static <T extends DateTimeLong> ZonedDateTime toZonedDateTime(T type) {
        LocalTimeStamp localTimeStamp = type.getLocalTimeStamp();
        UtcTimeOffset utcTimeOffset = type.getUtcTimeOffset();
        LocalDateTime dtm = LocalDateTime.parse(berOctetStringToString(localTimeStamp), TAP_DATE_TIME);
        ZoneOffset zoneOffset = ZoneOffset.of(asciiStringToString(utcTimeOffset));
        return ZonedDateTime.of(dtm, zoneOffset);
    }
}