package io.github.codicis.gsma.json;

import com.beanit.asn1bean.ber.types.BerInteger;
import com.beanit.asn1bean.ber.types.BerOctetString;
import com.beanit.asn1bean.ber.types.BerType;
import io.github.codicis.gsma.json.std.*;
import io.github.codicis.gsma.tap.AsciiString;
import io.github.codicis.gsma.tap.BCDString;
import io.github.codicis.gsma.tap.NumberString;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;

public class TapJsonService {
    private final JsonMapper mapper;

    public TapJsonService() {
        this.mapper = init();
    }

    private JsonMapper init() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BCDString.class, new BCDStringSerializer());
        simpleModule.addSerializer(AsciiString.class, new AsciiStringSerializer());
        simpleModule.addSerializer(BerInteger.class, new BerIntegerSerializer());
        simpleModule.addSerializer(BerOctetString.class, new BerOctetStringSerializer());
        simpleModule.addSerializer(NumberString.class, new NumberStringSerializer());

        return JsonMapper.builder()
                .addModules(simpleModule)
                .build();
    }

    public <T extends BerType> String toJson(T o, boolean pretty) throws JacksonException {
        if (pretty) return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        return mapper.writeValueAsString(o);
    }

    public <T extends BerType> void writeJsonToFile(T object, File file, boolean pretty) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
    }
}
