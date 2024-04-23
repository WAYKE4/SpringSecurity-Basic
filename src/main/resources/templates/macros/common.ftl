<#macro common_macros title>
    <html>
    <head>
        <title>${title}</title>
    </head>
    <body>
    <#nested>
    </body>
    </html>
</#macro>

<#macro user_macros user >
    Your information
    <h2>User id : [[${user.id}</h2>
    <h2>${user.username}</h2>
    <h2>${user.created}</h2>
</#macro>