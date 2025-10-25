package io.github.codicis.gsma.tap.internal;

import com.beanit.asn1bean.ber.BerLength;
import com.beanit.asn1bean.ber.BerTag;
import com.beanit.asn1bean.ber.types.BerType;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class BerTypeReader {

    private BerTypeReader() {
    }

    public static <T extends BerType> T read(URI uri, Supplier<T> factory, OpenOption... options) throws IOException {
        return decodeByTag(uri, null, factory, options).orElse(null);
    }

    public static <T extends BerType> Optional<T> readByTag(URI uri, BerTag tag, Supplier<T> factory, OpenOption... options) throws IOException {
        return decodeByTag(uri, tag, factory, options);
    }

    public static List<BerTag> scanTags(Path path, OpenOption... options) throws IOException {
        // Lightweight tag scanner
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static <T extends BerType> Optional<T> decodeByTag(URI uri, BerTag tag, Supplier<T> factory, OpenOption... options) throws IOException {
        String scheme = uri.getScheme();
        if ("jar".equals(scheme)) {
            try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Map.of())) {
                String ssp = uri.getSchemeSpecificPart();
                int separator = ssp.indexOf("!/");
                if (separator == -1) {
                    throw new IllegalArgumentException("Invalid JAR URI: " + uri);
                }
                String entryPath = ssp.substring(separator + 2);
                Path path = fileSystem.getPath(entryPath);
                return readByTag(path, tag, factory, options);
            }
        }

        if ("file".equals(scheme)) {
            Path path = Paths.get(uri);
            return readByTag(path, tag, factory, options);
        }

        // Fallback for other providers (e.g., Jimfs, S3, Hadoop)
        try (FileSystem fs = FileSystems.newFileSystem(uri, Map.of())) {
            Path path = fs.getPath(uri.getPath());
            return readByTag(path, tag, factory, options);
        }
    }


    private static <T extends BerType> Optional<T> readByTag(Path path, BerTag tag, Supplier<T> factory, OpenOption... options) throws IOException {
        // TLV-aware selective decoder (as discussed earlier)
        try (SeekableByteChannel sbc = Files.newByteChannel(path, options);
             InputStream raw = Channels.newInputStream(sbc);
             BufferedInputStream in = new BufferedInputStream(raw)) {

            if (tag == null) {
                T instance = factory.get();
                int decode = instance.decode(in);
                return Optional.of(instance);
            } else {

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
    }
}