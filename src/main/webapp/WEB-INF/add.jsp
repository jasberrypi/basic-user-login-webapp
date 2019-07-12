<html>
    <body>
        <h2>Add New User</h2>
        <p>${error}</p>
        <form action="/add" method="post">
            Username:
            <br>
            <input type="text" name="addUsername"/>
            <br>
            Password:
            <br>
            <input type="password" name="addPassword">
            <br>
            <br>
            <input type="submit" value="Submit">
        </form> 
    </body>
</html>
