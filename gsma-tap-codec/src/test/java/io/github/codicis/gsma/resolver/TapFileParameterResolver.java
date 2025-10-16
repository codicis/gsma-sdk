package io.github.codicis.gsma.resolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.net.URL;
import java.nio.file.Path;

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
            return Path.of(resource.toURI());
        } catch (Exception e) {
            throw new ParameterResolutionException("Failed to resolve path for resource: " + fullPath, e);
        }
    }

}
