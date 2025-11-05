package io.github.codicis.gsma.tap.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE) // or TYPE
public @interface GenerateBerRegistry {
}