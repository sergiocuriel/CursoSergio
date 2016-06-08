<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="layout" content="main" />
        <script src="${resource(dir: 'js/app', file:'app.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/controladores', file:'CtrAdminCursos.js')}" type="text/javascript"></script>
        <title>Participante</title>
    </head>
    <body>
      <div class="col-lg-12" ng-controller="gestionCursoController as gcCtr" ng-init="gcCtr.accion = 9">

          <!--Tabs-->
          <ul class="nav nav-pills nav-justified">
              <li role="presentation" ng-click="gcCtr.cambiarAccion(9)" ng-class="{'active':gcCtr.verTab(3)}"><a href="#">Perfil</a></li>
              <li role="presentation" ng-click="gcCtr.cambiarAccion(11)" ng-class="{'active':gcCtr.verTab(4)}"><a href="#">Solicitudes</a></li>
          </ul>

          <!--Boton modificar-->
          <div class="col-lg-12" ng-show="gcCtr.verAccion(9)">
              <br/>
              <button class="btn btn-primary" ng-click="gcCtr.cambiarAccion(10)">
                  <i class="fa fa-plus pull-left">
                      Modificar Perfil
                  </i>
              </button>
          </div>


<!--INFO DE perfil-->

          <div class="col-lg-12" ng-show="gcCtr.verAccion(9)">
              <div class="panel panel-default">
                  <div class="panel-body">
                      <div class="col-lg-2">
                          <img class="img-circle" src="../images/usuario.png" alt="...">
                      </div>

                      <div class="col-lg-10">
                          <h4>Usuario <small>{{perfil.usuario}}</small></h4>
                          <h4>Ocupacion <small>{{perfil.ocupacion}}</small></h4>
                          <h4>Correo <small>{{perfil.correo}}</small></h4>
                          <h4>Telefono <small>{{perfil.telefono}}</small></h4>
                      </div>
                  </div>
              </div>
          </div>
<!--FORM MODIFICA-->
          <div class="col-lg-12" ng-show="gcCtr.verAccion(10)">
              <form name="formPersona" class="form-horizontal" ng-submit="formPersona.$valid && gcCtr.hacerAccion()">
                  <fieldset>
                      <legend>
                          <strong >Modificar</strong>
                      </legend>
                      <div class="form-group">

                          <div class="col-lg-10">
                            <label class="control-label">Usuario</label>
                            <div class="form-group">
                                <input required type="text" ng-model="perfil.usuario" class="form-control" placeholder="..."/>
                            </div>
                            <label class="control-label ">Contraseña</label>
                            <div class="form-group ">
                                <input required type="password" ng-model="perfil.password" class="form-control" placeholder="..."/>
                            </div>
                            <label class="control-label ">Correo</label>
                            <div class="form-group ">
                                <input required type="text" ng-model="perfil.correo " class="form-control" placeholder="..."/>
                            </div>
                            <label class="control-label ">Ocupación</label>
                            <div class="form-group ">
                                <input required type="text" ng-model="perfil.ocupacion" class="form-control" placeholder="..."/>
                            </div>
                            <label class="control-label ">Telefono</label>
                            <div class="form-group ">
                                <input required type="text" ng-model="perfil.telefono" class="form-control" placeholder="..."/>
                            </div>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-lg-10 col-lg-offset-2">
                              <button type="button" ng-click="gcCtr.cambiarAccion(9)" class="btn btn-default">Cancel</button>
                              <button type="submit" class="btn btn-primary">
                                  <strong >Modificar</strong>
                              </button>
                          </div>
                      </div>
                  </fieldset>
              </form>
          </div>
          <!--LISTAR ASISTENCIAS-->
          <div class="col-lg-12" ng-show="gcCtr.verAccion(11)">
              <br/>
              <button class="btn btn-primary" ng-click="gcCtr.cambiarAccion(12)">
                  <i class="fa fa-plus pull-left">
                      Solicitar asistencia
                  </i>
              </button>
              <br/>


                      <div class="panel panel-body" ng-repeat="as in solicitudesUsuario" >

                      <h4>Curso: <small>{{as.curso.nombre}}</small></h4>
                      <h4>Fecha de solicitud: <small>{{as.fechaSolicitud | date:'medium' }} <small><time am-time-ago="as.fechaSolicitud"></time></small> </small> </h4>

                      <h4>Estado:<small>
                              <span ng-show="{{as.estado}}==2" class="text-danger">Rechazado</span>
                              <span ng-show="{{as.estado}}==1" class="text-success">Aceptado</span>
                              <span ng-show="{{as.estado}}==0" class="text-warning">Pendiente</span>
                          </small></h4>


                  </div>

          </div>


<!--FORM Solicitudes-->
          <div class="col-lg-12" ng-show="gcCtr.verAccion(12)">
              <div class="col-lg-12" >
                  <form name="formSolicitud" class="form-horizontal" ng-submit="gcCtr.solicitarParticipacion()">
                      <fieldset>
                          <legend>
                              <strong >Solicitar asistencia</strong>
                          </legend>
                          <div class="form-group">

                              <div class="col-lg-10">
                                  <label class="control-label" >Curso</label>
                                  <select required type="text" ng-model="cursoId" ng-options="c.id as c.nombre for c in cursos" class="form-control" placeholder="..."></select>


                                  <button type="button" ng-click="gcCtr.cambiarAccion(11)" class="btn btn-default">Cancel</button>
                                  <button type="submit" class="btn btn-primary" >

                                      <strong>Agregar</strong>
                                  </button>
                              </div>
                          </div>
                      </fieldset>
                  </form>
              </div>
              <div  class="alert alert-danger col-lg-4" ng-hide="respuesta">
                  <strong class="">Asistencia Rechazada:</strong>{{mensajeError}}
                  <button type="button" ng-click="gcCtr.cambiarAccion(11)" class="btn btn-default">Regresar</button>
              </div>
          </div>
      </div>
    </body>
</html>
