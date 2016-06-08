
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="layout" content="main" />
        <script src="${resource(dir: 'js/app', file:'app.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js/controladores', file:'CtrAdminCursos.js')}" type="text/javascript"></script>
        <title>Admin</title>
    </head>
    <body>
        <div class="col-lg-12" ng-controller="gestionCursoController as gcCtr">

                <!--Pestañas-->
                <ul class="nav nav-pills nav-justified">
                        <li role="presentation" ng-click="gcCtr.cambiarAccion(0)" ng-class="{'active':gcCtr.verTab(0)}"><a href="#">Cursos</a></li>
                        <li role="presentation" ng-click="gcCtr.cambiarAccion(4)" ng-class="{'active':gcCtr.verTab(1)}"><a href="#">Participante</a></li>
                        <li role="presentation" ng-click="gcCtr.cambiarAccion(8)" ng-class="{'active':gcCtr.verTab(2)}"><a href="#">Solicitudes</a></li>
                </ul>

                 <!--Listar cursos-->
                <div ng-show="gcCtr.verAccion(0) || gcCtr.verAccion(3)">

                    <button class="btn btn-primary" ng-click="gcCtr.cambiarAccion(1)">
                        <i class="fa fa-plus pull-left">
                            Agregar Curso
                        </i>
                    </button>

                    <table class="table table-striped table-borderer table-hover table-condensend">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Fecha Inicio</th>
                                <th>Fecha Fin</th>
                                <th>Duración</th>
                                <th>Capacidad</th>
                                <th>Disponibles</th>
                                <th>Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="curso in cursos">
                                <td>{{curso.nombre}}</td>
                                <td>
                                    {{curso.fechaInicio | date: 'medium'}}
                                </td>
                                <td>{{curso.fechaFin | date: 'medium'}} </td>
                                <td><span>{{curso.fechaFin | amDifference : curso.fechaInicio : 'hours'}} horas</span></td>
                                <td>{{curso.capacidad}}</td>
                                <td>{{curso.disponibles}}</td>
                                <td>
                                    <i class="fa fa-lg fa-eye text-info" ng-click="gcCtr.prepararCurso(curso, $index, 3)"></i> &nbsp; &nbsp;
                                    <i class="fa fa-lg fa-edit text-primary" ng-click="gcCtr.prepararCurso(curso, $index, 2)"></i> &nbsp; &nbsp;
                                    <i class="fa fa-lg fa-times text-danger" ng-click="gcCtr.eliminarCurso(curso.id, $index)"></i> &nbsp; &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" ng-hide="cursos">
                                    No hay cursos
                                </td>
                            </tr>
                        </tbody>
                    </table>
                  </div>

                <!--Info del curso-->
                <div class="col-lg-12" ng-show="gcCtr.verAccion(3)">
                          <div class="panel panel-default">
                              <div class="panel-body">
                                  <div class="col-lg-2">
                                      <img class="img-circle" src="../images/curso.png" alt="...">
                                  </div>
                                  <div class="col-lg-10">
                                      <h2>{{curso.nombre}}</h2>
                                      <h4>Fecha inicio: <small>{{curso.fechaInicio| date:'medium'}}</small></h4>
                                      <time am-time-ago="curso.fechaInicio" ></time>
                                      <h4>Fecha fin: <small>{{curso.fechaFin | date:'medium' }}</small></h4>
                                      <time am-time-ago="curso.fechaFin" ></time><br>
                                      <strong>
                                          Capacidad: <small>{{curso.capacidad}}</small>
                                          Disponibles: <small>{{curso.disponibles}}</small>
                                      </strong>

                                  </div>
                              </div>
                          </div>
                    </div>

                <!--Form curso-->
                <div class="col-lg-12" ng-show="gcCtr.verAccion(1) || gcCtr.verAccion(2)">
                    <form name="formCurso" class="form-horizontal"  ng-submit=" formCurso.$valid && curso.fechaInicio < curso.fechaFin  && gcCtr.hacerAccion()">
                        <fieldset>
                            <legend>
                                <strong ng-show="gcCtr.verAccion(1)">Registrar</strong>
                                <strong ng-show="gcCtr.verAccion(2)">Modificar</strong>
                            </legend>
                            <div class="form-group"  >
                                <div class="col-lg-10" >
                                    <label class="control-label">Nombre</label>
                                    <div class="form-group">
                                        <input required type="text" name="nombre" id="nombre" ng-model="curso.nombre" class="form-control" placeholder="..."/>
                                    </div>
                                    <label class="control-label ">Capacidad</label>
                                    <div class="form-group ">
                                        <input required type="number" name="capacidad" id="capacidad" ng-model="curso.capacidad" class="form-control" placeholder="..."/>
                                    </div>

                                    <div class="form-group" >
                                        <label class="control-label">Fecha inicio</label>
                                        <div>
                                            <uib-datepicker ng-model="curso.fechaInicio" minDate="curso.fechaInicio" datepicker-option="dateOptions"></uib-datepicker>
                                        </div>
                                        <div>
                                            <uib-timepicker ng-model="curso.fechaInicio" show-meridian="true"></uib-timepicker>
                                            {{curso.fechaInicio}}
                                            {{curso.fechaInicio | date:'medium'}}
                                        </div>
                                    </div>
                                    <div class="form-group" >
                                        <label class="control-label">Fecha inicio</label>
                                        <div>
                                            <uib-datepicker ng-model="curso.fechaFin" datepicker-option="dateOptions"></uib-datepicker>
                                        </div>
                                        <div>
                                            <uib-timepicker ng-model="curso.fechaFin" show-meridian="true"></uib-timepicker>
                                            {{curso.fechaFin}}
                                            {{curso.fechaFin | date:'medium'}}

                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-10 col-lg-offset-2">
                                    <button type="button" ng-click="gcCtr.cambiarAccion(0)" class="btn btn-default">Cancel</button>
                                    <button type="submit" class="btn btn-primary">
                                        <strong ng-show="gcCtr.verAccion(1)">Registrar</strong>
                                        <strong ng-show="gcCtr.verAccion(2)">Modificar</strong>
                                    </button>
                                </div>
                            </div>
                        </fieldset>
                    </form>

                </div>

                <!--Listar Participantes-->
                <div ng-show="gcCtr.verAccion(4) || gcCtr.verAccion(7)">

                    <button class="btn btn-primary" ng-click="gcCtr.cambiarAccion(5)">
                        <i class="fa fa-plus pull-left">
                            Agregar Particpante
                        </i>
                    </button>

                    <table class="table table-striped table-borderer table-hover table-condensend">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Correo</th>
                                <th>Ocupación</th>
                                <th>Telefono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="participante in participantes">
                                <td>{{participante.usuario}}</td>
                                <td>{{participante.perfil.correo}}</td>
                                <td>{{participante.perfil.ocupacion}}</td>
                                <td>{{participante.perfil.telefono}}</td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" ng-hide="participantes">
                                    No hay participantes
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>


                <!--Form participante-->
                <div class="col-lg-12" ng-show="gcCtr.verAccion(5) || gcCtr.verAccion(6)">
                    <form name="formParticipante" class="form-horizontal"  ng-submit=" formParticipante.$valid && gcCtr.hacerAccion()">
                        <fieldset>
                            <legend>
                                <strong ng-show="gcCtr.verAccion(5)">Registrar</strong>
                                <strong ng-show="gcCtr.verAccion(6)">Modificar</strong>
                            </legend>
                            <div class="form-group"  >
                                <div class="col-lg-10" >
                                    <label class="control-label">Usuario</label>
                                    <div class="form-group">
                                        <input required type="text" ng-model="participante.usuario" class="form-control" placeholder="..."/>
                                    </div>
                                    <label class="control-label ">Contraseña</label>
                                    <div class="form-group ">
                                        <input required type="password" ng-model="participante.password" class="form-control" placeholder="..."/>
                                    </div>
                                    <label class="control-label ">Correo</label>
                                    <div class="form-group ">
                                        <input required type="text" ng-model="participante.correo " class="form-control" placeholder="..."/>
                                    </div>
                                    <label class="control-label ">Ocupación</label>
                                    <div class="form-group ">
                                        <input required type="text" ng-model="participante.ocupacion" class="form-control" placeholder="..."/>
                                    </div>
                                    <label class="control-label ">Telefono</label>
                                    <div class="form-group ">
                                        <input required type="text" ng-model="participante.telefono" class="form-control" placeholder="..."/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-10 col-lg-offset-2">
                                    <button type="button" ng-click="gcCtr.cambiarAccion(0)" class="btn btn-default">Cancel</button>
                                    <button type="submit" class="btn btn-primary">
                                        <strong ng-show="gcCtr.verAccion(5)">Registrar</strong>
                                        <strong ng-show="gcCtr.verAccion(6)">Modificar</strong>
                                    </button>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>

                <!--Listar solicitudes-->
                <div ng-show="gcCtr.verAccion(8)">

                    <table class="table table-striped table-borderer table-hover table-condensend">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Curso</th>
                                <th>Fecha Inicio</th>
                                <th>Fecha Fin</th>
                                <th>Duración</th>
                                <th>Capacidad</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="solicitud in solicitudes">
                                <td>{{solicitud.usuario.username}}</td>
                                <td>{{solicitud.curso.nombre}}</td>
                                <td>{{solicitud.curso.fechaInicio | date: 'medium'}}</td>
                                <td>{{solicitud.curso.fechaFin | date: 'medium'}} </td>
                                <td><span>{{solicitud.curso.fechaFin | amDifference : solicitud.curso.fechaInicio : 'hours'}} horas</span></td>
                                <td>{{solicitud.curso.capacidad}}</td>
                                <td>
                                    <span class="text-success" ng-show="solicitud.estado==1">Aceptado</span>
                                    <span class="text-danger" ng-show="solicitud.estado==2">Rechazado</span>
                                    <i class="fa fa-lg fa-check text-success" ng-show="solicitud.estado==0" ng-click="gcCtr.responderParticipacion(solicitud.id, $index, 1 )"></i> &nbsp; &nbsp;
                                    <i class="fa fa-lg fa-times text-danger" ng-show="solicitud.estado==0" ng-click="gcCtr.responderParticipacion(solicitud.id, $index, 2 )"></i> &nbsp; &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" ng-hide="solicitudes">
                                    No hay solicitudes
                                </td>
                            </tr>
                        </tbody>
                    </table>
                  </div>
                  <div  class="alert alert-danger col-lg-4" ng-hide="respuesta">
                      <strong class="">Cambio rechazado:</strong>{{mensajeError}}
                      <button type="button" ng-click="gcCtr.cambiarAccion(0)" class="btn btn-default">Regresar</button>
                  </div>
        </div>
    </body>
</html>
