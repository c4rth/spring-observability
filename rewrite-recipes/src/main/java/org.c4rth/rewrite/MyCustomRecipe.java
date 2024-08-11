package org.c4rth.rewrite;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;

@Slf4j
public class MyCustomRecipe extends Recipe {

    @Override
    public @NotNull String getDisplayName() {
        return "Hello world ! here custom recipe";
    }

    @Override
    public @NotNull String getDescription() {
        return "Adding a hello method to a class.";
    }

    @Override
    public @NotNull TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<>() {

            private final JavaTemplate helloTemplate =
                    JavaTemplate.builder("public String hello() { return \"Hello World\"; }")
                            .build();

            @Override
            public @NotNull J.ClassDeclaration visitClassDeclaration(@NotNull J.ClassDeclaration classDecl, @NotNull ExecutionContext executionContext) {
                if (classDecl.getKind() != J.ClassDeclaration.Kind.Type.Class) {
                    log.info("MyCustomRecipe: {} is not a class: {}", classDecl.getName().getSimpleName(), classDecl.getKind());
                    return classDecl;
                }
                boolean helloMethodExists = classDecl.getBody().getStatements().stream()
                        .filter(statement -> statement instanceof J.MethodDeclaration)
                        .map(J.MethodDeclaration.class::cast)
                        .anyMatch(methodDeclaration -> methodDeclaration.getName().getSimpleName().equals("hello"));
                log.info("MyCustomRecipe: class {} = hello exist ? {}", classDecl.getName().getSimpleName(), helloMethodExists);
                if (helloMethodExists) {
                    return classDecl;
                }
                classDecl = classDecl.withBody(helloTemplate.apply(new Cursor(getCursor(), classDecl.getBody()),
                        classDecl.getBody().getCoordinates().lastStatement()));

                return classDecl;
            }
        };
    }
}
