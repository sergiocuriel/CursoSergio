package cursosergio

import grails.transaction.Transactional
import org.joda.time.Interval
import org.joda.time.Period

@Transactional
class UtileriasService {
    def springSecurityService
    def utileriasService

    def verificarTraslape = {

        def cursoParticipaUsuario = AsistenciaCurso.findAllByUsuario(springSecurityService.currentUser)
        def intervalCurso = new Interval(it.fechaInicio.getTime(), it.fechaFin.getTime())

        def intervalCursoPrevio

        def sum =  cursoParticipaUsuario.findAll{
            intervalCursoPrevio = new Interval(it.curso.fechaInicio.getTime(), it.curso.fechaFin.getTime())
            intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getMonths()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getDays()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getHours()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getMinutes()?:0 > 0
        }.size()>0

    }

    def verificarTraslapeCursos = {

        def cursos = Curso.findAll()
        def intervalCurso = new Interval(it.fechaInicio.getTime(), it.fechaFin.getTime())

        def intervalCursoPrevio

        def sum =  cursos.findAll{
            intervalCursoPrevio = new Interval(it.fechaInicio.getTime(), it.fechaFin.getTime())
            intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getMonths()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getDays()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getHours()?:intervalCurso.overlap(intervalCursoPrevio)?.toPeriod()?.getMinutes()?:0 > 0
        }.size()>0

    }


}
