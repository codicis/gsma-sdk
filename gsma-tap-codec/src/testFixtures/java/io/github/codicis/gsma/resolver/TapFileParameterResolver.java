package io.github.codicis.gsma.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

public class TapFileParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return parameter.getType().equals(Path.class) &&
               parameter.isAnnotationPresent(TestResource.class);

    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        TestResource annotation = parameterContext.getParameter().getAnnotation(TestResource.class);
        Path fullPath = Path.of(annotation.directory(), annotation.value().filename());
        URL resource = getClass().getClassLoader().getResource(fullPath.toString());
        if (resource == null) {
            throw new ParameterResolutionException("Resource not found: " + fullPath);
        }

        try {
            URI uri = resource.toURI();
            FileSystem fs;
            try {
                fs = FileSystems.getFileSystem(uri);
            } catch (FileSystemNotFoundException e) {
                fs = FileSystems.newFileSystem(uri, Map.of(), ClassLoader.getSystemClassLoader());
                // Register for cleanup using AutoCloseable
                extensionContext.getStore(ExtensionContext.Namespace.create(getClass(), extensionContext.getUniqueId())).put("fs:" + uri, fs);
            }
            return fs.getPath(fullPath.toString());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to resolve path: " + fullPath, e);
        }
    }

}
