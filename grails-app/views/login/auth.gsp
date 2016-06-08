<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Capacitacion</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file:'paper.css')}" type="text/css"/>
        
    </head>
    <body>
        <h1>Login</h1>
        <small><g:message code="springSecurity.login.header"/></small><br/>
        <form action="${postUrl}" method="POST" id="loginForm" class="form-horizontal col-lg-10" autocomplete="off">
            <div class="form-group">
                <input type="text" class="form-control text-primary" placeholder="Usuario" name="j_username" id="username"/>
            </div>
            <div class="form-group">
                <input type="password" class="form-control text-primary" placeholder="Pass" name="j_password" id="password"/>
            </div>
            <div class="form-group">
                
                <input type="checkbox" class="chk" name="${rememberMeParameter}" id="remember_me" <g:if test="${hasCookie}">checked = 'checked'</g:if>/>
                <label for="remember_me"> <g:message code="springSecurity.login.remember.me.label"/>
            </div>
            <div class="form-group">
            <input type="submit" class="btn btn-danger" id="submit" value="${message(code: "springSecurity.login.button")}"/>
            </div>
        </form>   
        
        <script type='text/javascript'>
            (function(){
                document.forms['loginForm'].elements['j_username'].focus();
                window.localStorage.removeItem('iiinnn');
            })();
        </script>
    </body>
</html>

