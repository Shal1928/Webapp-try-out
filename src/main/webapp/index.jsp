<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Tutorial: Hello Dojo!</title>
    </head>
    <body>
        <h1 id="greeting">Hi</h1>

        <!-- set Dojo configuration, load Dojo -->
        <script>
            dojoConfig= {
                has: {
                    "dojo-firebug": true
                },
                parseOnLoad: false,
                foo: "bar",
                async: true
            };
        </script>
        <script src="//ajax.googleapis.com/ajax/libs/dojo/1.10.4/dojo/dojo.js"></script>

        <script>
            // Require the registry, parser, Dialog, and wait for domReady
            require(["dijit/registry",
                     "dojo/parser",
                     "dojo/json",
                     "dojo/_base/config",
                     "dijit/Dialog",
                     "dojo/domReady!"],
                    function(registry, parser, JSON, config) {
                        // Explicitly parse the page
                        parser.parse();
                        // Find the dialog
                        var dialog = registry.byId("dialog");
                        // Set the content equal to what dojo.config is
                        dialog.set("content", "<pre>" + JSON.stringify(config, null, "\t") + "```");
                        // Show the dialog
                        dialog.show();
                    });
        </script>

        <div id="dialog" data-dojo-type="dijit/Dialog" data-dojo-props="title: 'dojoConfig / dojo/_base/config'"></div>
    </body>
</html>