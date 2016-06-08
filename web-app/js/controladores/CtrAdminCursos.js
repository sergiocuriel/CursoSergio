angular.module('cursos').controller('gestionCursoController',
    function($scope, $rootScope, $log, $http, CONFIG_URL) {

        $scope.cursos = [];
        $scope.curso = {};
        $scope.participante = {};
        $scope.participantes = [];
        $scope.solicitudes = [];
        $scope.solicitud = {};
        $scope.solicitudesUsuario = [];
        $scope.solicitudUsuario = {};
        $scope.respuesta = {};
        $scope.mensajeError = {};
        $scope.cursoId = {};
        $rootScope.perfil = {};


        //Para la navegacion
        this.accion = 0;
        this.tab = 0;
        $scope.indice = 0;
        $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

        this.verTab = function(tab) {
            return this.tab === tab;
        };

        this.cambiarTab = function(tab) {
            this.tab = tab;
        };

        this.verAccion = function(accion) {
            return this.accion === accion;
        };

        this.cambiarAccion = function(accion) {
            this.accion = accion;
            $scope.respuesta = {};
            if (this.accion < 4) {
                this.cambiarTab(0);
                if(this.accion == 1){
                  $scope.curso = {};
                }
            }else if (this.accion < 7 && this.accion>3) {
                this.cambiarTab(1);
                $scope.participante = {};
            }else if (this.accion === 8 ) {
                this.cambiarTab(2);
            }else if (this.accion > 8 &&  this.accion < 11){
              this.cambiarTab(3)
            }else if(this.accion > 10 ){
              this.cambiarTab(4)
            }
        };

        this.hacerAccion = function() {
            switch (this.accion) {
                case 1:
                    this.agregarCurso();
                    this.cambiarAccion(0);
                    break;
                case 2:
                    this.modificarCurso();
                    this.cambiarAccion(0);
                    
                    break;
                case 5:
                    this.agregarParticipante();
                    this.cambiarAccion(4);
                    break;
                case 10:
                    this.modificarParticipante();
                    this.cambiarAccion(9);
                    break;

                default:
                    break;
            }


        };

        //CURSOS
        this.listarCursos = function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/listarCursos').then(function(res) {
                if (res.data.respuesta) {
                    $scope.cursos = res.data.cur;
                }
            }, $rootScope.errorhttp);
        };

        this.agregarCurso = function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/agregarCurso', {
                    params: {
                        curso: $scope.curso
                    }
                })
                .then(function(res) {
                    console.log(res);
                    if (res.data.respuesta) {
                        console.log(res.data);
                        $scope.cursos.push(res.data.curso);
                    }
                }, $rootScope.errorhttp);
        };

        this.prepararCurso = function(cur, i, a) {

            $scope.curso = angular.extend({}, cur);
            this.cambiarAccion(a);
            $scope.indice = Number(i);
        };

        this.modificarCurso = function() {
            console.log('modifico');

            $http.get(CONFIG_URL.url + 'gestionCurso/modificarCurso', {
                params: {
                    curso: $scope.curso
                },
                headers: {
                    'Per-Res': 'permres'
                }
            }).then(function(res) {
                if (res.data.respuesta) {
                    console.log(res.data);
                    console.log("INDICE "+$scope.indice);
                    $scope.cursos[$scope.indice] = res.data.curso;
                }else{
                  $scope.mensajeError=res.data.mensaje;
                }

                $scope.respuesta = res.data.respuesta;
                $scope.indice = 0;
            }, $rootScope.errorhttp);

        };

        this.eliminarCurso = function(id, i) {
            if (confirm("¿Desea eliminar el curso?")) {
                console.log('si eliminare');
                $http.get(CONFIG_URL.url + 'gestionCurso/eliminarCurso', {
                    params: {
                        id: id
                    }
                }).then(function(res) {
                    if (res.data.respuesta) {
                        $scope.cursos.splice(Number(i), 1);
                        console.log('si borre');
                    }
                }, $rootScope.errorhttp);
            }
        };

        //participantes
        this.listarParticipantes= function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/listarParticipantes').then(function(res) {
                if (res.data.respuesta) {
                    $scope.participantes = res.data.participantes;
                }
            }, $rootScope.errorhttp);
        };

        this.agregarParticipante = function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/registrarUsuario', {
                    params: {
                        participante: $scope.participante
                    }
                })
                .then(function(res) {
                    console.log(res);
                    if (res.data.respuesta) {
                        console.log(res.data);
                        $scope.participantes.push(res.data.participante);
                    }
                }, $rootScope.errorhttp);
        };

        this.prepararParticipante = function(cur, i, a) {

            $scope.participante = angular.extend({}, cur);
            this.cambiarAccion(a);
            $scope.indice = Number(i);
        };

        this.modificarParticipante = function() {
            console.log('modifico');

            $http.get(CONFIG_URL.url + 'gestionCurso/modificarUsuario', {
                params: {
                    participante: $scope.perfil
                },
                headers: {
                    'Per-Res': 'permres'
                }
            }).then(function(res) {
                if (res.data.respuesta) {
                    console.log(res.data);
                    $scope.participante[$scope.indice] = res.data.participante;
                }
                $scope.indice = 0;
            }, $rootScope.errorhttp);

        };

        //FALTA EN EL CONTROLLER GROOVY
        this.eliminarParticipante = function(id, i) {
            if (confirm("¿Desea eliminar el usuario?")) {
                console.log('si eliminare');
                $http.get(CONFIG_URL.url + 'gestionCurso/eliminarParticipante', {
                    params: {
                        id: id
                    }
                }).then(function(res) {
                    if (res.data.respuesta) {
                        $scope.participantes.splice(Number(i), 1);
                        console.log('si borre');
                    }
                }, $rootScope.errorhttp);
            }
        };

        //Solicitudes
        this.responderParticipacion = function(id, i, estado) {
            console.log('respondo');
            $scope.indice = Number(i);

            $http.get(CONFIG_URL.url + 'gestionCurso/responderParticipacion', {
                params: {
                    id: id,
                    estado:estado
                },
                headers: {
                    'Per-Res': 'permres'
                }
            }).then(function(res) {
                if (res.data.respuesta) {
                    console.log(res.data);
                    $scope.solicitudes[Number(i)] = res.data.solicitud;
                }
                $scope.indice = 0;
            }, $rootScope.errorhttp);

        };

        this.listarSolicitudes= function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/listarSolicitudes').then(function(res) {
                if (res.data.respuesta) {
                    $scope.solicitudes = res.data.solicitudes;
                }
            }, $rootScope.errorhttp);
        };

        this.listarSolicitudesUsuario= function() {
            $http.get(CONFIG_URL.url + 'gestionCurso/listarSolicitudesUsuario').then(function(res) {
                if (res.data.respuesta) {
                    $scope.solicitudesUsuario = res.data.solicitudes;
                }
            }, $rootScope.errorhttp);
        };

        this.solicitarParticipacion = function() {


                $http.get(CONFIG_URL.url + 'gestionCurso/solicitarParticipacion', {
                    params: {
                        id: $scope.cursoId
                    }
                }).then(function(res) {
                    if (res.data.respuesta) {
                      $scope.solicitudesUsuario.push(res.data.solicitud);
                    }else{
                      $scope.mensajeError=res.data.mensaje;
                    }

                    $scope.respuesta = res.data.respuesta;


                }, $rootScope.errorhttp);

        };

        this.obtenerPerfil = function () {
                $http.get(CONFIG_URL.url + 'gestionCurso/obtenerPersona').then(function (res) {
                    if (res.data.respuesta) {
                        $rootScope.perfil = res.data.perfil;
                    }
                }, $rootScope.errorhttp);
            };

        this.listarCursos();
        this.listarParticipantes();
        this.listarSolicitudes();
        this.listarSolicitudesUsuario();
        this.obtenerPerfil();

    });
