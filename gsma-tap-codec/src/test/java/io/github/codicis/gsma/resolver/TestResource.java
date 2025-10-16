package io.github.codicis.gsma.resolver;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestResource {
    TapTestFile value();

    String directory() default "";
}