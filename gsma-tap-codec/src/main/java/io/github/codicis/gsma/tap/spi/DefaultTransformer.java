package io.github.codicis.gsma.tap.spi;

import com.beanit.asn1bean.ber.types.BerType;

public class DefaultTransformer implements Transformer {
    @Override
    public String getName() {
        return "text";
    }

    @Override
    public Object transform(BerType input) throws Exception {
        return input.toString();
    }
}
