package io.github.codicis.gsma.tap.spi;

import com.beanit.asn1bean.ber.types.BerType;

import java.util.*;

public class TransformerRegistry {
    private static final List<Transformer> transformers;

    static {
        List<Transformer> list = new ArrayList<>();
        for (Transformer transformer : ServiceLoader.load(Transformer.class, ClassLoader.getSystemClassLoader())) {
            String name = transformer.getName();
            boolean duplicate = list.stream().anyMatch(t -> t.getName().equalsIgnoreCase(name));
            if (!duplicate) list.add(transformer);
        }
        list.addFirst(new DefaultTransformer());
        transformers = Collections.unmodifiableList(list);
    }

    private TransformerRegistry() {
    }

    public static TransformerRegistry getInstance() {
        return Holder.INSTANCE;
    }

    public Object transform(BerType input, String transformerName, Map<String,Object> options) throws Exception {
        for (Transformer transformer : transformers) {
            if (transformer.getName().equalsIgnoreCase(transformerName)) {
                transformer.configure(options);
                return transformer.transform(input);
            }
        }
        throw new IllegalArgumentException("Transformer not found");
    }

    private static class Holder {
        private static final TransformerRegistry INSTANCE = new TransformerRegistry();
    }

    public List<Transformer> getTransformers() {
        return transformers;
    }
}
