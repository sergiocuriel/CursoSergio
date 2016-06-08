package cursosergio

import java.util.Date;

class Curso {

  String nombre
  Date fechaInicio
  Date fechaFin
  int capacidad

  static hasMany =[asistencia: AsistenciaCurso]

    static constraints = {
    }

    static mapping = {
      version false
      asistencia cascade:'all-delete-orphan'
    }
}
