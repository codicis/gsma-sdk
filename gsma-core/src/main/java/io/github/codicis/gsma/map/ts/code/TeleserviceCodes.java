package io.github.codicis.gsma.map.ts.code;

import java.util.Arrays;

public enum TeleserviceCodes {
    /**
     * Represents all teleservices.
     * ASN.1: <code>TeleserviceCode ::= '00000000'B</code>
     */
    ALL_TELESERVICES(new TeleserviceCode(new byte[]{(byte) 0x00})),
    /**
     * All speech transmission services.
     * ASN.1: <code>TeleserviceCode ::= '00010000'B</code>
     */
    ALL_SPEECH_TRANSMISSION_SERVICES(new TeleserviceCode(new byte[]{(byte) 0x10})),
    /**
     * Basic telephony service.
     * ASN.1: <code>TeleserviceCode ::= '00010001'B</code>
     */
    TELEPHONY(new TeleserviceCode(new byte[]{(byte) 0x11})),
    /**
     * Emergency call service.
     * ASN.1: <code>TeleserviceCode ::= '00010010'B</code>
     */
    EMERGENCY_CALLS(new TeleserviceCode(new byte[]{(byte) 0x12})),
    /**
     * All short message services.
     * ASN.1:<code>TeleserviceCode ::= '00100000'B</code>
     */
    ALL_SHORT_MESSAGE_SERVICES(new TeleserviceCode(new byte[]{(byte) 0x20})),
    /**
     * Short message mobile-terminated point-to-point.
     * ASN.1: <code>TeleserviceCode ::= '00100001'B</code>
     */
    SHORT_MESSAGE_MT_PP(new TeleserviceCode(new byte[]{(byte) 0x21})),
    /**
     * Short message mobile-originated point-to-point.
     * ASN.1: <code>TeleserviceCode ::= '00100010'B</code>
     */
    SHORT_MESSAGE_MO_PP(new TeleserviceCode(new byte[]{(byte) 0x22})),

    /**
     * All facsimile transmission services.
     * ASN.1: <code>TeleserviceCode ::= '01100000'B</code>
     */
    ALL_FACSIMILE_TRANSMISSION_SERVICES(new TeleserviceCode(new byte[]{(byte) 0x60})),

    /**
     * Facsimile Group 3 and alternate speech.
     * ASN.1: <code>TeleserviceCode ::= '01100001'B</code>
     */
    FACSIMILE_GROUP3_AND_ALTER_SPEECH(new TeleserviceCode(new byte[]{(byte) 0x61})),

    /**
     * Automatic Facsimile Group 3.
     * ASN.1: <code>TeleserviceCode ::= '01100010'B</code>
     */
    AUTOMATIC_FACSIMILE_GROUP3(new TeleserviceCode(new byte[]{(byte) 0x62})),

    /**
     * Facsimile Group 4.
     * ASN.1: <code>TeleserviceCode ::= '01100011'B</code>
     */
    FACSIMILE_GROUP4(new TeleserviceCode(new byte[]{(byte) 0x63})),

    /**
     * All data teleservices (includes facsimile and SMS).
     * ASN.1: <code>TeleserviceCode ::= '01110000'B</code>
     */
    ALL_DATA_TELESERVICES(new TeleserviceCode(new byte[]{(byte) 0x70})),

    /**
     * All teleservices except SMS (includes speech and facsimile).
     * ASN.1: <code>TeleserviceCode ::= '10000000'B</code>
     */
    ALL_TELESERVICES_EXCEPT_SMS(new TeleserviceCode(new byte[]{(byte) 0x80})),

    /**
     * All voice group call services.
     * ASN.1: <code>TeleserviceCode ::= '10010000'B</code>
     */
    ALL_VOICE_GROUP_CALL_SERVICES(new TeleserviceCode(new byte[]{(byte) 0x90})),

    /**
     * Voice group call.
     * ASN.1: <code>TeleserviceCode ::= '10010001'B</code>
     */
    VOICE_GROUP_CALL(new TeleserviceCode(new byte[]{(byte) 0x91})),

    /**
     * Voice broadcast call.
     * ASN.1: <code>TeleserviceCode ::= '10010010'B</code>
     */
    VOICE_BROADCAST_CALL(new TeleserviceCode(new byte[]{(byte) 0x92})),

    /**
     * All PLMN-specific teleservices.
     * ASN.1: <code>TeleserviceCode ::= '11010000'B</code>
     */
    ALL_PLMN_SPECIFIC_TS(new TeleserviceCode(new byte[]{(byte) 0xD0})),

    /**
     * PLMN-specific teleservice 1 — ASN.1: <code>'11010001'B</code>
     */
    PLMN_SPECIFIC_TS_1(new TeleserviceCode(new byte[]{(byte) 0xD1})),

    /**
     * PLMN-specific teleservice 2 — ASN.1: <code>'11010010'B</code>
     */
    PLMN_SPECIFIC_TS_2(new TeleserviceCode(new byte[]{(byte) 0xD2})),

    /**
     * PLMN-specific teleservice 3 — ASN.1: <code>'11010011'B</code>
     */
    PLMN_SPECIFIC_TS_3(new TeleserviceCode(new byte[]{(byte) 0xD3})),

    /**
     * PLMN-specific teleservice 4 — ASN.1: <code>'11010100'B</code>
     */
    PLMN_SPECIFIC_TS_4(new TeleserviceCode(new byte[]{(byte) 0xD4})),

    /**
     * PLMN-specific teleservice 5 — ASN.1: <code>'11010101'B</code>
     */
    PLMN_SPECIFIC_TS_5(new TeleserviceCode(new byte[]{(byte) 0xD5})),

    /**
     * PLMN-specific teleservice 6 — ASN.1: <code>'11010110'B</code>
     */
    PLMN_SPECIFIC_TS_6(new TeleserviceCode(new byte[]{(byte) 0xD6})),

    /**
     * PLMN-specific teleservice 7 — ASN.1: <code>'11010111'B</code>
     */
    PLMN_SPECIFIC_TS_7(new TeleserviceCode(new byte[]{(byte) 0xD7})),

    /**
     * PLMN-specific teleservice 8 — ASN.1: <code>'11011000'B</code>
     */
    PLMN_SPECIFIC_TS_8(new TeleserviceCode(new byte[]{(byte) 0xD8})),

    /**
     * PLMN-specific teleservice 9 — ASN.1: <code>'11011001'B</code>
     */
    PLMN_SPECIFIC_TS_9(new TeleserviceCode(new byte[]{(byte) 0xD9})),

    /**
     * PLMN-specific teleservice A — ASN.1: <code>'11011010'B</code>
     */
    PLMN_SPECIFIC_TS_A(new TeleserviceCode(new byte[]{(byte) 0xDA})),

    /**
     * PLMN-specific teleservice B — ASN.1: <code>'11011011'B</code>
     */
    PLMN_SPECIFIC_TS_B(new TeleserviceCode(new byte[]{(byte) 0xDB})),

    /**
     * PLMN-specific teleservice C — ASN.1: <code>'11011100'B</code>
     */
    PLMN_SPECIFIC_TS_C(new TeleserviceCode(new byte[]{(byte) 0xDC})),

    /**
     * PLMN-specific teleservice D — ASN.1: <code>'11011101'B</code>
     */
    PLMN_SPECIFIC_TS_D(new TeleserviceCode(new byte[]{(byte) 0xDD})),

    /**
     * PLMN-specific teleservice E — ASN.1: <code>'11011110'B</code>
     */
    PLMN_SPECIFIC_TS_E(new TeleserviceCode(new byte[]{(byte) 0xDE})),

    /**
     * PLMN-specific teleservice F — ASN.1: <code>'11011111'B</code>
     */
    PLMN_SPECIFIC_TS_F(new TeleserviceCode(new byte[]{(byte) 0xDF}));

    private final TeleserviceCode code;

    TeleserviceCodes(TeleserviceCode teleserviceCode) {
        this.code = teleserviceCode;
    }

    public TeleserviceCode getCode() {
        return code;
    }

    public static TeleserviceCodes fromBytes(byte[] value) {
        for (TeleserviceCodes c : values()) {
            if (Arrays.equals(c.code.value, value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown teleservice code: " + Arrays.toString(value));
    }

}
