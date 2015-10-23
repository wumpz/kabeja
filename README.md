# Kabeja

### Library for parsing DXF files converting the same to SVG

It is licensed under the Apache Software License 2.0.

Current state: The original Kabeja project was abandoned around 2010 amidst a refactoring effort in preparation for version 0.5.
Unfortunately this has left the library in a partially broken state. Don't expect it to work flawlessly. One of the refactoring efforts was to switch the old Ant build system to Maven. This is mostly complete but the _GUI and the CLI are currently broken_ as a result.

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
