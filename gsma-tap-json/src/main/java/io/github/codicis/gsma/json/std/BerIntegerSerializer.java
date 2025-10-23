package io.github.codicis.gsma.json.std;

import com.beanit.asn1bean.ber.types.BerInteger;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Custom serializer for BerInteger objects.
 * This serializer is used to convert BerInteger objects to their long value representation.
 */
public class BerIntegerSerializer extends StdSerializer<BerInteger> {

    /**
     * Constructs a new instance of BerIntegerSerializer with a BerInteger type.
     */
    public BerIntegerSerializer() {
        super(BerInteger.class);
    }

    @Override
    public void serialize(BerInteger value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        provider.findValueSerializer(Long.class).serialize(value.value.longValue(), gen, provider);
    }
}
