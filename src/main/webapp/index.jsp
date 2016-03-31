<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Tutorial: Hello Dojo!</title>
    </head>
    <body>
        <h1 id="greeting">Hi</h1>

        <!-- Configure Dojo first -->
        <script>
            var dojoConfig = {
                has: {
                    "dojo-firebug": true,
                    "dojo-debug-messages": true
                },
                parseOnLoad: true,
                // look for a locale=xx query string param, else default to 'en-us'
                locale: location.search.match(/locale=([\w\-]+)/) ? RegExp.$1 : "en-us"
            };
        </script>
        <script src="//ajax.googleapis.com/ajax/libs/dojo/1.10.4/dojo/dojo.js"></script>

        <script>
            require(["dojo/date/locale", "dijit/Dialog", "dojo/json", "dojo/_base/config",
                        "dojo/_base/window", "dojo/i18n", "dojo/domReady!"]
                    , function(locale, Dialog, JSON, config, win) {
                        var now = new Date();
                        var dialog = new Dialog({
                            id: "dialog",
                            // set a title on the dialog of today's date,
                            // using a localized date format
                            title: "Today: " + locale.format(now, {
                                formatLength:"full",
                                selector:"date"
                            })
                        }).placeAt(win.body());
                        dialog.startup();

                        dialog.set("content", "<pre>" + JSON.stringify(config, null, "\t") + "```");
                        dialog.show();
                    });
        </script>
    </body>
</html>