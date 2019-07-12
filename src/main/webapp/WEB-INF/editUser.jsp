<html>
    <body>
        <h2>Change username for ${editUsername}:</h2>
        <p>${error}</p>
        <form action="/editUser" method="post">
            New Username:
            <br>
            <input type="text" name="newUsername"/>
            <br>
            <br>
            <button type="submit" name="update" value="${editUsername}">Update</button>
        </form>
    </body>
</html>