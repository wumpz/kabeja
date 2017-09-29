# Kabeja

### How to make a build in IntelliJ IDEA

* Open the project in IntelliJ IDEA
* Right-click `pom.xml` and select **Add as Maven Project**
* Right-click `pom.xml` and select **Maven** > **Reimport**
* Open **View** > **Tool Windows** > **Maven Projects**
* Activate **Profiles** > **blocks**, in the popup choose **Enable Auto-Import**
* Go to **File** > **Project Structure**
    * Under **Project** set your SDK
    * Under **Artifacts** select **+** > **JAR** > **From modules with dependencies...**
    * Select your desired module, e.g. **tfly-kabeja-dxf** and press **Ok**, **Ok**
* Now you can build your **jar** by choosing **Build** > **Build Artifacts...** > **tfly-kabeja-dxf:jar** > **Build** 
    * Your **jar** is now at `out` or `classes` > `/artifacts/tfly_kabeja_dxf_jar/tfly-kabeja-dxf.jar`

### Library for parsing DXF files and conversion to SVG

It is licensed under the Apache Software License 2.0.

Current state: The original Kabeja project has not been updated since 2010. Third parties have since worked on the library. Most importantly the build system was changed from Ant to Maven. Unfortunately this left parts of the program in a partially broken state. The library part itself works but the _GUI and the CLI are currently broken_.

**The *accu:rate* fork adds limited XData support.**

## Limitations
Not all DXF entities are supported yet. Text entities are still problematic.

## Supported entities
* Arc
* Attrib
* Polyline
* Circle
* Line
* Blocks/Insert
* Text
* MText
* LWPolyline
* Solid
* Trace
* Ellipse
* Dimension
* Image
* Leader
* XLine
* Ray
* Hatch
* Spline
* MLine
* **In this fork: XData (limited support)**


## CLI
***launcher.jar is currently not built by the Maven script.***

In the Kabeja-folder try:
* Help and pipeline list:

  `java -jar launcher.jar --help`

* Convert to svg

  `java -jar launcher.jar -nogui -pipeline svg myfile.dxf result.svg`

* Convert to pdf|jpeg|png|...

  `java -jar launcher.jar -nogui -pipeline <pdf|jpeg|png>  myfile.dxf`

If you experience OOM problems, try `java -Xmx256m`

## GUI
The GUI is currently broken.

## Cocoon
[Cocoon](http://cocoon.apache.org/) is an XML Publishing Framework.

Copy `kabeja.jar` and `kabeja-svg2dxf-cocoon.jar` to your `WEB-INF/lib` folder
of your Cocoon web application. Then you can use Kabeja as Generator like:

In your `sitemap/subsitemap`:

```
<map:components>
    .....   
    <map:generators default="file">
        <map:generator name="dxf2svg" src="org.kabeja.cocoon.generation.DXF2SVGGenerator"/>
    </map:generators>

....
<map:pipelines>
    <map:pipeline>
        <map:match pattern="dxf/*.svg">
            <map:generate type="dxf2svg" src="dxf/{1}.dxf"/>
            ...
            <!-- transform things you need -->
            <map:serialize type="xml"/>
        </map:match>
    </map:pipeline>
    ....
</map:pipelines>
```

Note: Large DXF drafts will cause SVGDocument to consume a lot of memory. The Generator is
Cacheable so the first run will take more time.
