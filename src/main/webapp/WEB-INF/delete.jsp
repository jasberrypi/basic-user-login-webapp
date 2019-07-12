<html>
    <body>
        <h2>Delete ${delUsername}?</h2>
        <form action="/delete" method="post">
            <button type="submit" name="yes" value="${delUsername}">Yes</button>
            <input type="submit" name="no" value="No">
        </form>
    </body>
</html>