package cursosergio

import java.util.Date;

class AsistenciaCurso {
  Date fechaSolicitud
  int estado = 0 //0 en espera 1 aceptado 2 rechazado

  static belongsTo = [usuario: Usuario, curso: Curso];


    static constraints = {
    }

    static mapping = {
      version false
    }
}
