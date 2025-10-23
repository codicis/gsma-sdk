package io.github.codicis.gsma.json.std;

import io.github.codicis.gsma.tap.AsciiString;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Custom serializer for AsciiString objects.
 * This serializer is used to convert AsciiString objects to their String value representation.
 */
public class AsciiStringSerializer extends StdSerializer<AsciiString> {


    /**
     * Constructs a new instance of AsciiStringSerializer with an AsciiString type.
     */
    public AsciiStringSerializer() {
        super(AsciiString.class);
    }

    @Override
    public void serialize(AsciiString value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        provider.findValueSerializer(String.class).serialize(new String(value.value), gen, provider);
    }
}