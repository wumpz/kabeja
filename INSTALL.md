There are no up-to-date binary packages.

## Building Kabeja
Kabeja is structured as a multi-module Maven project.
The root POM only depends on the core.

```
# kabeja-core
mvn compile
mvn install

# DXF, SVG, ...
mvn -P blocks compile
mvn -P blocks install
```

## IDE like Eclipse/Netbeans:

You can import it as an existing Maven repository.

Eclipse will automatically create a project for each Maven submodule.
