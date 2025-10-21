package io.github.codicis.gsma.json.std;

import io.github.codicis.gsma.tap.NumberString;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class NumberStringSerializer extends StdSerializer<NumberString> {

    public NumberStringSerializer() {
        super(NumberString.class);
    }

    @Override
    public void serialize(NumberString value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        provider.findValueSerializer(String.class).serialize(new String(value.value), gen, provider);

    }
}
