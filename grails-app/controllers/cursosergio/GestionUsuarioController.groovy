package cursosergio

import grails.converters.*
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import cursosergio.*
import java.util.Date


class GestionUsuarioController {
    def springSecurityService

    def index() {

        if(springSecurityService.currentUser.authorities[0].authority.equals("ROLE_ADMIN"))
        render(view:'admin')
        else if(springSecurityService.currentUser.authorities[0].authority.equals("ROLE_PARTICIPANTE"))
        render(view:'participante')
        else
        render(view:'index')
    }

    // def solicitarParticipacion(){
    //     try{
    //         def curso = Curso.get(params.id.toLong())
    //         def fechaActual = new Date()
    //         def usuario = springSecurityService.currentUser
    //         if((curso.capacidad - curso.asistencia.findAll{it.estado!=2}.size())>0){
    //             if(AsistenciaCurso.findByUsuarioAndCurso(usuario, curso).is(null)){
    //                 if( curso.fechaInicio.getTime() > fechaActual.getTime() ){
    //                     if(!utileriasService.verificarTraslape(curso)){
    //                         def asistenciaCurso = new AsistenciaCurso(
    //                             curso:curso,
    //                             usuario:springSecurityService.currentUser,
    //                             fechaSolicitud: fechaActual)
    //
    //                         asistenciaCurso.save(flush:true, failOnError:true)
    //                         asistenciaCurso.refresh()
    //
    //                         render(")]}',\n")
    //                         render ([respuesta:true,cursoParticipacion:[
    //                                     id:asistenciaCurso.id,
    //                                     nombre:asistenciaCurso.curso.nombre,
    //                                     fechaInicio: asistenciaCurso.curso.fechaInicio.getTime(),
    //                                     fechaFin: asistenciaCurso.curso.fechaFin.getTime(),
    //                                     estado:asistenciaCurso.estado,
    //                                     fechaSolicitud:asistenciaCurso.fechaSolicitud.getTime()
    //                                 ]] as JSON)
    //                     }else{
    //                         render([respuesta:false, mensaje:"Ya tienes un curso en este periodo de tiempo"] as JSON)
    //
    //                     }
    //                 }else{
    //                     render([respuesta:false, mensaje:"Ya acabo"] as JSON)
    //
    //                 }
    //             }else{
    //                 render([respuesta:false, mensaje:"Ya solicitaste este curso"] as JSON)
    //
    //             }
    //         }else{
    //             render([respuesta:false, mensaje:"No hay cupo"] as JSON)
    //         }
    //     }catch(Exception ex){
    //         println ex.getMessage()
    //         return render ([respuesta:false, mensaje: mensajeError] as JSON);
    //     }catch(ValidationException exve){
    //         println exve.getMessage()
    //         return render ([respuesta:false, mensaje: mensajeError] as JSON);
    //     }
    // }
    //
    // def listarSolicitudesUsuario(){
    //     try{
    //
    //       def user =springSecurityService.currentUser
    //
    //         def asistencias= AsistenciaCurso.findAllByUsuario(user).collect{
    //             return[id:it.id, curso:it.curso, usuario:it.usuario, fechaSolicitud:it.fechaSolicitud, estado:it.estado]
    //         }
    //         render ([respuesta:asistencias?true:false, solicitudes:asistencias] as JSON)
    //     }catch(Exception ex){
    //         println ex.getMessage()
    //         return render([respuesta:false, mensaje:mensajeError] as JSON)
    //     }catch(ValidationException exve){
    //         println exve.getMessage()
    //         return render([respuesta:false, mensaje:mensajeError] as JSON)
    //     }catch(IllegalArgumentException iae){
    //         println iae.getMessage()
    //         return render([respuesta:false, mensaje:mensajeError] as JSON)
    //     }
    // }


}
