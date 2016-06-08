<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js" ng-app="cursos"><!--<![endif]-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${resource(dir:'css', file:'paper.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir:'css', file:'font-awesome.min.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir:'css', file:'app.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir:'css', file:'loading-bar.min.css')}" type="text/css">


        <script src="${resource(dir: 'js/ext', file:'jquery-1.11.3.min.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext', file:'moment.min.js')}" type="text/javascript"></script>
        <!--<script src="${resource(dir: 'js/ext', file:'moment-timezone-with-data.js')}" type="text/javascript"></script>-->
        <script src="${resource(dir: 'js/ext', file:'es.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext', file:'angular.min.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext', file:'angular-moment.min.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext/i18n', file:'angular-locale_es-mx.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext', file:'loading-bar.min.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/ext', file:'ui-bootstrap-tpls.min.js')}" type="text/javascript"></script>

        <g:layoutHead/>
    </head>
    <sec:ifNotLoggedIn>
        <g:redir/>
    </sec:ifNotLoggedIn>
    <body ng-controller="principal as pctr">
           <nav class="navbar navbar-default navbar-inverse"  >
            <div class="container-fluid">
              <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button ng-click="isCollapsed = !isCollapsed" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">CURSOS</a>

                </div>



    <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse" uib-collapse="isCollapsed" >

                    <ul class="nav navbar-nav">
                    </ul>

                    <ul class="nav navbar-nav navbar-right">

                        <li> <a popover-trigger="outsideClick" popover-placement="bottom-right" uib-popover-template="'../partials/perfil.html'"><i class="fa fa-bars"></i></a></li>

                    </ul>

                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="container-fluid">
        <g:layoutBody/>
        </div>
        <uib-alert ng-repeat= "notificacion in notificaciones" type="{{notificacion.tipo}}"
        dismiss-on-timeout="5000"
        close="notificaciones.splice($index,1)" >
        {{notificacion.mensaje}}
        </uib-alert>

    </body>
</html>
