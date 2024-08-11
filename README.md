# Jaeger

## start

```bash
./jaeger-all-in-one --collector.zipkin.host-port 9411
```

## url

http://localhost:16686/



# OpenRewrite

```bash
./mvnw org.openrewrite.maven:rewrite-maven-plugin:run -Drewrite.recipeArtifactCoordinates=org.c4rth:rewrite-recipes:0.0.1-SNAPSHOT -Drewrite.activeRecipes=org.c4rth.rewrite.UpdateAll
```