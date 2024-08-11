package org.c4rth.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class MyCustomRecipeTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MyCustomRecipe());
    }

    @Test
    void addHelloToFooBar() {
        // language=java
        rewriteRun(
                java(
                        """
                                    package in.proxolo;
                                
                                    class Hello {
                                    }
                                """,
                        """
                                    package in.proxolo;
                                
                                    class Hello {
                                        public String hello() {
                                            return "Hello World";
                                        }
                                    }
                                """
                )
        );
    }

    @Test
    void doesNotChangeExistingHello() {
        // language=java
        rewriteRun(
                java(
                        """
                                    package in.proxolo;
                                
                                    class FooBar {
                                        public String hello() { return ""; }
                                    }
                                """
                )
        );
    }
}
