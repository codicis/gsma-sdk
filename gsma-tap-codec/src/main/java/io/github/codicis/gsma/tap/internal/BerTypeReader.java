package io.github.codicis.gsma.tap.internal;

import com.beanit.asn1bean.ber.BerLength;
import com.beanit.asn1bean.ber.BerTag;
import com.beanit.asn1bean.ber.types.BerType;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public final class BerTypeReader {

    private BerTypeReader() {
    }

    public static <T extends BerType> T read(Path path, Supplier<T> factory, OpenOption... options) throws IOException {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(path, options))) {
            T instance = factory.get();
            instance.decode(in);
            return instance;
        }
    }

    public static <T extends BerType> Optional<T> readByTag(Path path, BerTag tag, Supplier<T> factory, OpenOption... options) throws IOException {
        // TLV-aware selective decoder (as discussed earlier)
        try (SeekableByteChannel sbc = Files.newByteChannel(path, options);
             InputStream raw = Channels.newInputStream(sbc);
             BufferedInputStream in = new BufferedInputStream(raw)) {

            while (in.available() > 0) {
                in.mark(32); // Ensure enough bytes TVL (tag [1-4] + length [1-5] + Value [?])

                BerTag berTag = new BerTag();
                int tagBytes = berTag.decode(in);

                BerLength berLength = new BerLength();
                int lengthBytes = berLength.decode(in);

                if (berTag.equals(tag)) {
                    in.reset(); // rewind to start of tag
                    T instance = factory.get();
                    instance.decode(in); // decode entire structure
                    return Optional.of(instance);
                } else {
                    // skip payload if tag doesn't match
                    long toSkip = berLength.val;
                    long skipped = in.skip(toSkip);
                    if (skipped < toSkip) {
                        throw new EOFException("Unexpected end of stream while skipping payload");
                    }
                }
            }
            return Optional.empty();
        }
    }

    public static List<BerTag> scanTags(Path path, OpenOption... options) throws IOException {
        // Lightweight tag scanner
        throw new UnsupportedOperationException("Not supported yet.");
    }
}