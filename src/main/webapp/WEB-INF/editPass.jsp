<html>
    <body>
        <h2>Change password for ${editUsername}:</h2>
        <p>${error}</p>
        <form action="/editPass" method="post">
            New Password:
            <br>
            <input type="text" name="newPassword"/>
            <br>
            <br>
            <button type="submit" name="update" value="${editUsername}">Update</button>
        </form>
    </body>
</html>