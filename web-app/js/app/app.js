angular.module('cursos', ['ui.bootstrap', 'angularMoment', 'angular-loading-bar'  ])
        .constant('CONFIG_URL', {url: window.location.origin + "/CursoSergio/"
        })
        .run(function(amMoment){
                    amMoment.changeLocale('es');
        })
        .config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
                cfpLoadingBarProvider.includeSpinner = true;
            }])

//funcion que devuelva una cadena de letras aletorias indicando el numero de letras
//formularios en GESTION CURSO META LAYOUT INDEX

        .controller('principal', ['$rootScope', '$scope', '$http', 'CONFIG_URL',
            function ($rootScope, $scope, $http, CONFIG_URL) {
                $rootScope.info = {};
                $rootScope.notificaciones = [];
                $scope.isCollapsed = true;
                this.curl = CONFIG_URL;


                $scope.cargarInformacion = function () {
                    if (window.localStorage.getItem('iiinnn')) {
                        $rootScope.info = JSON.parse(window.localStorage.getItem('iiinnn'));
                    } else {
                        $http.get(CONFIG_URL.url + 'gestionCurso/obtenerPersona', {
                            headers: {'Per-Res': 'permres'}}).then(function (res) {
                            window.localStorage.setItem("iiinnn", JSON.stringify(res.data.perfil));
                            $rootScope.info = res.data.perfil;

                        }, $rootScope.errorhttp);

                    }
                };



                $rootScope.errorhttp = function (res) {
                    switch (Number(res.status)) {
                        case 403:
                            $rootScope.notificacion("danger", "No tiene permisos");
                            break;
                        case 500:
                            $rootScope.notificacion("danger", "Problemas en el servidor, notifique al administrador");
                            break;
                        case 400:
                            $rootScope.notificacion("danger", "Problemas en el servidor, notifique al administrador 404");

                        default:
                            $rootScope.notificacion("danger", "Ocurrio un problema");
                            break;
                    }
                };

                $rootScope.notificacion = function (tipo, mensaje) {
                    $rootScope.notificaciones.push({tipo: tipo, mensaje: mensaje});
                };




                $scope.cargarInformacion();
            }]);
