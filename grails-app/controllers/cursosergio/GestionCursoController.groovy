package cursosergio


import grails.converters.*
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import cursosergio.*
import java.util.Date
import org.joda.time.LocalDateTime


class GestionCursoController {
    def springSecurityService
    def utileriasService
    final String mensajeError = "Ocurrió un error"

    def index() {

        if(springSecurityService.currentUser.authorities[0].authority.equals("ROLE_ADMIN"))
        render(view:'admin')
        else if(springSecurityService.currentUser.authorities[0].authority.equals("ROLE_PARTICIPANTE"))
        render(view:'participante')
        else
        render(view:'index')
    }

    def obtenerPersona(){
        springSecurityService.currentUser?.perfil
        def user = springSecurityService.currentUser
        render([respuesta:springSecurityService.currentUser?.perfil?true:false,
                perfil:[
                    id:user.id,
                    usuario:user.username,
                    ocupacion:user.perfil?.ocupacion?:"",
                    telefono:user.perfil?.telefono?:"",
                    correo:user.perfil?.correo?:""]] as JSON)
    }

    //Cursos
    def listarCursos(){
        def cursos = Curso.findAll().collect{
            return [
                id:it.id,
                nombre:it.nombre,
                fechaInicio: new LocalDateTime(it.fechaInicio).getLocalMillis(),
                fechaFin: new LocalDateTime(it.fechaFin).getLocalMillis(),
                capacidad: it.capacidad,
                disponibles: it.capacidad-it.asistencia.findAll{it.estado!=2}.size()
            ]
        }

        render ([respuesta:cursos?true:false, cur:cursos] as JSON)
    }

    def agregarCurso(){

        try{
            JsonSlurper json = new JsonSlurper();
            Object cj = json.parseText(params.curso)

            def fechaini = new Date().parse("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'",cj.fechaInicio)
            def fechafin = new Date().parse("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'",cj.fechaFin)


            def curso = new Curso(nombre: cj.nombre,
                fechaInicio: fechaini,
                fechaFin: fechafin,
                capacidad: cj.capacidad)

          // if(utileriasService.verificarTraslapeCursos(curso)){
              curso.save(flush:true, failOnError:true)
              curso.refresh()


              render(")]}',\n")

              render ([respuesta:true, curso:[
                          id:curso.id
                          , nombre:curso.nombre
                          , fechaInicio: new LocalDateTime(curso.fechaInicio).getLocalMillis()
                          , fechaFin: new LocalDateTime(curso.fechaFin).getLocalMillis()
                          , capacidad: curso.capacidad
                          , disponibles: curso.capacidad]] as JSON)
            // }else{
            //   return render([respuesta:false, mensaje:"Ya hay un curso en esas fechas"] as JSON)
            //
            // }

        }catch(Exception ex){
            println ex.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(ValidationException exve){
            println exve.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(IllegalArgumentException iae){
            println iae.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }
    }

    def modificarCurso(){

        try{
            JsonSlurper json = new JsonSlurper()
            Object cj = json.parseText(params.curso)

            println "traje desde params curso"
            def curso = Curso.get(cj.id.toLong())
            println "traje curso desde get"

            curso.nombre = cj.nombre

            if(cj.capacidad >= curso.asistencia.findAll{it.estado!=2}.size()){
                curso.capacidad = cj.capacidad
            }

            def fechaInicio
            def fechaFin

            if(cj.fechaInicio?.toString()?.isLong()){
                fechaInicio = cj.fechaInicio
            }else{
                fechaInicio = new Date().parse("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", cj.fechaInicio)
                curso.fechaInicio = fechaInicio

            }

            if(cj.fechaFin?.toString()?.isLong()){
                fechaFin = cj.fechaFin
            }else{
                fechaFin = new Date().parse("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",cj.fechaFin)
                curso.fechaFin = fechaFin

            }


            //if(utileriasService.verificarTraslapeCursos(curso)){
              curso.save(flush:true, failOnError:true)
              render(")]}',\n")
              render([respuesta:true, curso:[
                id:curso.id,
                          nombre:curso.nombre,
                          fechaInicio:new LocalDateTime(curso.fechaInicio).getLocalMillis(),
                          fechaFin:new LocalDateTime(curso.fechaFin).getLocalMillis(),
                          capacidad:curso.capacidad,
                          disponibles:curso.capacidad-curso.asistencia.findAll{it.estado!=2}.size()
                      ]] as JSON)

            // }else{
            //   render([respuesta:false, mensaje:"Ya tienes un curso en este periodo de tiempo"] as JSON)
            //
            // }


        }catch(Exception ex){
            println ex.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(ValidationException exve){
            println exve.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(IllegalArgumentException iae){
            println iae.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }
    }

    def eliminarCurso(){
        try{
            def curso = Curso.get(params.id.toLong())//Busca un tipo Long con el método get()

            curso.asistencia.clear()
            curso.delete(flush:true); //mismo

            render(")]}',\n")
            render ([respuesta:true] as JSON)
        }catch(Exception ex){
            println ex.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }catch(ValidationException exve){
            println exve.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }
    }

    def listarCursosParticipante(){
        def cursos = AsistenciaCurso.findAllByUsuario(springSecurityService.currentUser).collect{
            return [
                nombre:it.curso.combre,
                fechaInicio:it.curso.fechaInicio.getTime(),
                fechaFin:it.curso.fechaFin.getTime(),
                estado:it.estado,
                fechaSolicitud:it.fechaSolicitud.getTime()
            ]
        }
        render([respuesta: cursosParticipante?true:false, cursoParticipante:cursosParticipante] as JSON)
    }

    //Participantes
        def registrarUsuario(){
            try{
                JsonSlurper json = new JsonSlurper();
                Object pj = json.parseText(params.participante)

                def rol = Rol.findByAuthority("ROLE_PARTICIPANTE")

                println pj
                def correo = pj.correo

                println correo

                def usuario = new Usuario(username:pj.usuario,password:pj.password, perfil: new Perfil(correo: correo, telefono: pj.telefono , ocupacion: pj.ocupacion)).save(flush:true);

                UsuarioRol.create(usuario,rol,true)

                usuario.refresh()

                render(")]}',\n")
                render([respuesta:true, participante:[
                            id:usuario.id,
                            usuario:usuario.username,
                            password:"",
                            perfil:[usuario:username,
                                ocupacion:usuario.perfil?.ocupacion?:"",
                                telefono:usuario.perfil?.telefono?:"",
                                correo:usuario.perfil?.correo?:""]]] as JSON)

            }catch(Exception ex){
                println ex.getMessage()
                return render([respuesta:false, mensaje: ex.getMessage()] as JSON)
            }catch(ValidationException exve){
                println exve.getMessage()
                return render([respuesta:false, mensaje:mensajeError] as JSON)
            }catch(IllegalArgumentException iae){
                println iae.getMessage()
                return render([respuesta:false, mensaje:mensajeError] as JSON)
            }
        }

        def modificarUsuario(){
            try{
                JsonSlurper json = new JsonSlurper();
                Object pj = json.parseText(params.participante)

                def rol = Rol.findByAuthority("ROLE_PARTICIPANTE")



                def usuario = Usuario.get(pj.id.toLong())

                usuario.username = pj.usuario

                if(pj.password){
                    usuario.password = pj.password
                }

                usuario.perfil.correo= pj.correo
                usuario.perfil.telefono = pj.telefono
                usuario.perfil.ocupacion=pj.ocupacion

                usuario.save(flush: true)

                render(")]}',\n")
                render([respuesta:true, participante:[
                            id:usuario.id
                            , usuario:usuario.username,
                            password:"",
                            perfil:[usuario:usuario.username,
                                ocupacion:usuario.perfil?.ocupacion?:"",
                                telefono:usuario.perfil?.telefono?:"",
                                correo:usuario.perfil?.correo?:""]]] as JSON)

            }catch(Exception ex){
                println ex.getMessage()
                return render([respuesta:false, mensaje: ex.getMessage()] as JSON)
            }catch(ValidationException exve){
                println exve.getMessage()
                return render([respuesta:false, mensaje:mensajeError] as JSON)
            }catch(IllegalArgumentException iae){
                println iae.getMessage()
                return render([respuesta:false, mensaje:mensajeError] as JSON)
            }
        }

        def listarParticipantes() {
          try{
                def usuarios = Usuario.findAll().collect{
                    return[
                        id:it.id,
                        usuario: it.username,
                        password:"",
                        perfil:[
                            ocupacion:it.perfil?.ocupacion?:"",
                            telefono:it.perfil?.telefono?:"",
                            correo:it.perfil?.correo?:""
                        ]]

                }

                render(")]}',\n")
                render ([respuesta:true, participantes:usuarios] as JSON)
          }catch(Exception ex){
              println ex.getMessage()
              return render([respuesta:false, mensaje:mensajeError] as JSON)
          }catch(ValidationException exve){
              println exve.getMessage()
              return render([respuesta:false, mensaje:mensajeError] as JSON)
          }catch(IllegalArgumentException iae){
              println iae.getMessage()
              return render([respuesta:false, mensaje:mensajeError] as JSON)
          }
        }

    //Solicitudes
    def listarSolicitudes(){
        try{
            def asistencias= AsistenciaCurso.findAll().collect{
                return[id:it.id, curso:it.curso, usuario:it.usuario, fechaSolicitud:it.fechaSolicitud, estado:it.estado]
            }
            render ([respuesta:asistencias?true:false, solicitudes:asistencias] as JSON)
        }catch(Exception ex){
            println ex.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(ValidationException exve){
            println exve.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(IllegalArgumentException iae){
            println iae.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }
    }

    def responderParticipacion(){
        try{
            def asistenciaCurso = AsistenciaCurso.get(params.id.toLong())



            asistenciaCurso.estado = params.estado as int

            asistenciaCurso.save(flush:true, failOnError:true)
            render(")]}',\n")
            render([respuesta:true, solicitud: [id:asistenciaCurso.id, curso:asistenciaCurso.curso, usuario:asistenciaCurso.usuario, fechaSolicitud:asistenciaCurso.fechaSolicitud, estado:asistenciaCurso.estado]] as JSON)
        }catch(Exception ex){
            println ex.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }catch(ValidationException exve){
            println exve.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }
    }

    def listarSolicitudesUsuario(){
        try{

            def asistencias= AsistenciaCurso.findAllByUsuario(springSecurityService.currentUser).collect{
                return[id:it.id, curso:it.curso, usuario:it.usuario, fechaSolicitud:it.fechaSolicitud, estado:it.estado]
            }
            render ([respuesta:asistencias?true:false, solicitudes:asistencias] as JSON)
        }catch(Exception ex){
            println ex.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(ValidationException exve){
            println exve.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }catch(IllegalArgumentException iae){
            println iae.getMessage()
            return render([respuesta:false, mensaje:mensajeError] as JSON)
        }
    }

    def solicitarParticipacion(){
        try{
            def curso = Curso.get(params.id.toLong())
            def fechaActual = new Date()
            def usuario = springSecurityService.currentUser
            if((curso.capacidad - curso.asistencia.findAll{it.estado!=2}.size())>0){
                if(AsistenciaCurso.findByUsuarioAndCurso(usuario, curso).is(null)){
                    if( curso.fechaInicio.getTime() > fechaActual.getTime() ){
                        if(!utileriasService.verificarTraslape(curso)){
                            def asistenciaCurso = new AsistenciaCurso(
                                curso:curso,
                                usuario:springSecurityService.currentUser,
                                fechaSolicitud: fechaActual)

                            asistenciaCurso.save(flush:true, failOnError:true)
                            asistenciaCurso.refresh()

                            render(")]}',\n")
                            render ([respuesta:true,solicitud:[
                                        id:asistenciaCurso.id,
                                        nombre:asistenciaCurso.curso.nombre,
                                        fechaInicio: asistenciaCurso.curso.fechaInicio.getTime(),
                                        fechaFin: asistenciaCurso.curso.fechaFin.getTime(),
                                        estado:asistenciaCurso.estado,
                                        fechaSolicitud:asistenciaCurso.fechaSolicitud.getTime()
                                    ]] as JSON)
                        }else{
                            render([respuesta:false, mensaje:"Ya tienes un curso en este periodo de tiempo"] as JSON)

                        }
                    }else{
                        render([respuesta:false, mensaje:"Ya acabo"] as JSON)

                    }
                }else{
                    render([respuesta:false, mensaje:"Ya solicitaste este curso"] as JSON)

                }
            }else{
                render([respuesta:false, mensaje:"No hay cupo"] as JSON)
            }
        }catch(Exception ex){
            println ex.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }catch(ValidationException exve){
            println exve.getMessage()
            return render ([respuesta:false, mensaje: mensajeError] as JSON);
        }
    }



}
