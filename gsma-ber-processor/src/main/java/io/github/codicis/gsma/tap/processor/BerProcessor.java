package io.github.codicis.gsma.tap.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

@AutoService(Processor.class)
public class BerProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // Triggered by @GenerateBerRegistry marker
        return Set.of("io.github.codicis.gsma.tap.processor.GenerateBerRegistry");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;

        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();
        TypeElement berTypeElement = elementUtils.getTypeElement("com.beanit.asn1bean.ber.types.BerType");
        TypeElement berTagElement = elementUtils.getTypeElement("com.beanit.asn1bean.ber.BerTag");

        List<TypeElement> candidates = new ArrayList<>();

        for (Element root : roundEnv.getRootElements()) {
            if (root.getKind() == ElementKind.CLASS) {
                TypeElement clazz = (TypeElement) root;
                if (typeUtils.isAssignable(clazz.asType(), berTypeElement.asType())) {
                    candidates.add(clazz);
                }
            }
        }

        if (candidates.isEmpty()) return true;

        // Build REGISTRY field
        TypeName mapType = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get("com.beanit.asn1bean.ber", "BerTag"),
                ParameterizedTypeName.get(ClassName.get(Supplier.class),
                        WildcardTypeName.subtypeOf(ClassName.get("com.beanit.asn1bean.ber.types", "BerType")))
        );

        FieldSpec registryField = FieldSpec.builder(mapType, "REGISTRY", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T<>()", HashMap.class)
                .build();

        // Static init block
        CodeBlock.Builder staticBlock = CodeBlock.builder();
        for (TypeElement type : candidates) {
            if (hasTagField(type, berTagElement)) {
                ClassName fqcn = ClassName.get(type);
                staticBlock.addStatement("REGISTRY.put($T.tag, $T::new)", fqcn, fqcn);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        "Skipping " + type.getQualifiedName() + " (no public static final BerTag tag)");
            }
        }

        // lookup method
        MethodSpec lookup = MethodSpec.methodBuilder("lookup")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(
                        ClassName.get(Optional.class),
                        ParameterizedTypeName.get(ClassName.get(Supplier.class),
                                WildcardTypeName.subtypeOf(ClassName.get("com.beanit.asn1bean.ber.types", "BerType")))
                ))
                .addParameter(ClassName.get("com.beanit.asn1bean.ber", "BerTag"), "tag")
                .addStatement("return $T.ofNullable(REGISTRY.get(tag))", Optional.class)
                .build();

        // Build class
        TypeSpec registry = TypeSpec.classBuilder("BerRegistry")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("Auto-generated registry of BerType implementations.\nDO NOT EDIT MANUALLY.\n")
                .addField(registryField)
                .addStaticBlock(staticBlock.build())
                .addMethod(lookup)
                .build();

        JavaFile javaFile = JavaFile.builder("io.github.codicis.gsma.tap.generated", registry)
                .indent("    ")
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private Optional<String> findTagOwner(TypeElement type, TypeElement berTagElement) {
        Types typeUtils = processingEnv.getTypeUtils();
        Elements elementUtils = processingEnv.getElementUtils();

        TypeMirror current = type.asType();
        while (current != null && current.getKind() == TypeKind.DECLARED) {
            TypeElement currentElement = (TypeElement) typeUtils.asElement(current);
            for (Element enclosed : currentElement.getEnclosedElements()) {
                if (enclosed.getKind() == ElementKind.FIELD
                    && enclosed.getSimpleName().contentEquals("tag")
                    && enclosed.getModifiers().containsAll(Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))
                    && typeUtils.isSameType(enclosed.asType(), berTagElement.asType())) {
                    return Optional.of(currentElement.getQualifiedName().toString());
                }
            }
            current = currentElement.getSuperclass();
        }
        return Optional.empty();
    }

    private boolean hasTagField(TypeElement type, TypeElement berTagElement) {
        for (Element enclosed : type.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.FIELD
                && enclosed.getSimpleName().contentEquals("tag")
                && enclosed.getModifiers().containsAll(Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))
                && processingEnv.getTypeUtils().isSameType(enclosed.asType(), berTagElement.asType())) {
                return true;
            }
        }
        return false;
    }
}