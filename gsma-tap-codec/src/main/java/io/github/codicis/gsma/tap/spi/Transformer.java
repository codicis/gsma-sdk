package io.github.codicis.gsma.tap.spi;

import com.beanit.asn1bean.ber.types.BerType;

import java.util.Map;

public interface Transformer {

    String getName();

    Object transform(BerType input) throws Exception;

    default void configure(Map<String, Object> options) {
        // Optional override
    }

}
