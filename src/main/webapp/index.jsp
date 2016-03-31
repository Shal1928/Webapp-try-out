<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Demo: Application Config</title>
    <link rel="stylesheet" href="../../_common/demo.css" media="screen" type="text/css">
    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.10.4/dijit/themes/claro/claro.css" media="screen">
    <style>
        #dialog { min-width: 200px; }
    </style>
</head>
    <body class="claro">
        <h1>Demo: Application Config</h1>

        <!-- Configure Dojo first -->
        <script>
            dojoConfig = {
                has: {
                    "dojo-firebug": true
                },
                app: {
                    userName: "Anonymous"
                }
            };
        </script>
        <script src="//ajax.googleapis.com/ajax/libs/dojo/1.10.4/dojo/dojo.js"></script>

        <script>
            require(["dijit/Dialog", "dijit/registry", "dojo/parser", "dojo/_base/lang",
                        "dojo/json", "dojo/_base/config", "dojo/io-query", "dojo/domReady!"]
                    , function(Dialog, registry, parser, lang, JSON, config, ioQuery) {

                        // pull configuration from the query string
                        // and mix it into our app config
                        var queryParams = ioQuery.queryToObject(location.search.substring(1));
                        lang.mixin(config.app, queryParams);

                        // Create a dialog
                        var dialog = new Dialog({
                            title: "Welcome back " + config.app.userName,
                            content: "<pre>" + JSON.stringify(config, null, "\t") + "```"
                        });

                        // Draw on the app config to put up a personalized message
                        dialog.show();

                    });
        </script>
    </body>
</html>