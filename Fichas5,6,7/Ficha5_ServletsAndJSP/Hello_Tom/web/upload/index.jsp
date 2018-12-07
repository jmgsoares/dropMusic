<%@ page pageEncoding="UTF-8" %>
<html>
<html lang="en">
    <head>
        <title>Upload File</title>
        <style>label { float: left; display: block; width: 75px; }</style>
    </head>
    <body>
        <form action="FileUpload" method="post" enctype="multipart/form-data">
            <label for="text">Name:</label>
            <input type="text" id="text" name="name" value="" />
            <br>
            <label for="file">File:</label>
            <input type="file" id="file" name="file" />
            <br>
            <input type="submit" value="Upload!" />
        </form>
    </body>
</html>