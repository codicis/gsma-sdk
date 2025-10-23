package io.github.codicis.gsma.json.std;

import com.beanit.asn1bean.ber.types.BerOctetString;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class BerOctetStringSerializer extends StdSerializer<BerOctetString> {

    public BerOctetStringSerializer() {
        super(BerOctetString.class);
    }

    @Override
    public void serialize(BerOctetString value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        provider.findValueSerializer(String.class).serialize(new String(value.value), gen, provider);
    }
}
