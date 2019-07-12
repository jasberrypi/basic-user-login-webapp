<html>
    <body>
        <h2>Delete ${delUsername}?</h2>
<%--        <p>${error}</p>--%>
        <form action="/delete" method="post">
            <button type="submit" name="yes" value="${delUsername}">Yes</button>
            <input type="submit" name="no" value="No">
        </form>
    </body>
</html>