package io.github.codicis.gsma.json.std;

import io.github.codicis.gsma.tap.BCDString;
import io.github.codicis.gsma.tap.internal.BerUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

public class BCDStringSerializer extends StdSerializer<BCDString> {

    public BCDStringSerializer() {
        super(BCDString.class);
    }

    @Override
    public void serialize(BCDString value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        ValueSerializer<Object> valueSerializer = provider.findValueSerializer(String.class);
        valueSerializer.serialize(BerUtils.bcdToStringWithPadding(value.value), gen, provider);
    }
}
