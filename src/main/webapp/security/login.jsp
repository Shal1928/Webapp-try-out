<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter to FN test territory</title>
    <link rel="stylesheet" href="login-style.css" media="screen" type="text/css">
    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.10.4/dijit/themes/claro/claro.css" media="screen">
    <link rel="script" href="login-script.js">
</head>
<body>
<script type="text/template" id="dialog-template">
    <div style="width:300px;">

        <div class="dijitDialogPaneContentArea">
            <div data-dojo-attach-point="contentNode">
                {form}
            </div>
        </div>

        <div class="dijitDialogPaneActionBar">
            <div
                    class="message"
                    data-dojo-attach-point="messageNode"
            ></div>
            <button
                    data-dojo-type="dijit.form.Button"
                    data-dojo-props=""
                    data-dojo-attach-point="submitButton"
            >
                OK
            </button>

            <button
                    data-dojo-type="dijit.form.Button"
                    data-dojo-attach-point="cancelButton"
            >
                Cancel
            </button>
        </div>
    </div>
</script>

<script type="text/template" id="login-form-template">
    <form
            data-dojo-type="dijit.form.Form"
            data-dojo-attach-point="form"
    >

        <table class="form">
            <tr>
                <td>Логин</td>
                <td>
                    <input data-dojo-type="dijit.form.ValidationTextBox"
                           data-dojo-props='
                            name: "j_username",
                            required: true,
                            maxLength: 64,
                            trim: true,
                            style: "width: 200px;"
                            '
                    />
                </td>
            </tr>

            <tr>
                <td>Пароль</td>
                <td>
                    <input data-dojo-type="dijit.form.ValidationTextBox"
                           type="password"
                           data-dojo-props='
                            name: "j_password",
                            required: true,
                            style: "width: 200px;"
                            '
                    />
                </td>
            </tr>

        </table>

    </form>
</script>

<span>username: user</span>
<br/>
<span>password: user</span>
</body>
</html>
