package io.github.codicis.gsma.json;

import com.beanit.asn1bean.ber.types.BerInteger;
import com.beanit.asn1bean.ber.types.BerOctetString;
import com.beanit.asn1bean.ber.types.BerType;
import io.github.codicis.gsma.json.std.*;
import io.github.codicis.gsma.tap.AsciiString;
import io.github.codicis.gsma.tap.BCDString;
import io.github.codicis.gsma.tap.NumberString;
import io.github.codicis.gsma.tap.spi.Transformer;

import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.io.File;
import java.util.Map;

public class JsonTransformer implements Transformer {
    private final JsonMapper mapper;
    private boolean prettyPrint = false;
    private OutputType outputType  = OutputType.CONSOLE;
    private String filePath;

    public enum OutputType {
        CONSOLE, FILE
    }


    public JsonTransformer() {
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

    @Override
    public String getName() {
        return "Json";
    }

    @Override
    public String transform(BerType input) throws Exception {
        ObjectWriter writer = prettyPrint
                ? mapper.writerWithDefaultPrettyPrinter()
                : mapper.writer();

        switch (outputType) {
            case FILE:
                if (filePath == null || filePath.isEmpty()) {
                    throw new IllegalArgumentException("File path not configured for FILE output.");
                }
                writer.writeValue(new File(filePath), input);
                return null;

            case CONSOLE:
                writer.writeValue(System.out, input);
                return null;

            default:
                return writer.writeValueAsString(input);
        }
    }

    @Override
    public void configure(Map<String, Object> options) {
        if (options.containsKey("prettyPrint")) {
            this.prettyPrint = Boolean.parseBoolean(options.get("prettyPrint").toString());
        }
        if (options.containsKey("outputType")) {
            this.outputType = OutputType.valueOf(options.get("outputType").toString().toUpperCase());
        }
        if (options.containsKey("filePath")) {
            this.filePath = options.get("filePath").toString();
        }
    }
}
