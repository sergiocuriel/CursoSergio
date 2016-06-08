import cursosergio.*

class BootStrap {

    def init = { servletContext ->

      def rolAdm
       rolAdm = Rol.findByAuthority("ROLE_ADMIN")
       if(rolAdm.is(null)){
           rolAdm = new Rol(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
       }

       def rolParticipante
       rolParticipante = Rol.findByAuthority("ROLE_PARTICIPANTE")
       if(rolParticipante.is(null))
       rolParticipante = new Rol(authority:"ROLE_PARTICIPANTE").save(flush:true, failOnError:true)

       def usuarioAdm
       usuarioAdm = Usuario.findByUsername("admin")
       if(usuarioAdm.is(null)) {
           usuarioAdm = new Usuario(username:"admin", password:"admin", perfil:new Perfil(correo:"admin@gmail.com", telefono:"7774089165", ocupacion:"admin")).save(flush:true, failOnError:true)
           UsuarioRol.create(usuarioAdm, rolAdm, true)
       }

       def usuarioDemo
       usuarioDemo = Usuario.findByUsername("alan")
       if(usuarioDemo.is(null)) {
           usuarioDemo= new Usuario(username:"alan", password:"alan", perfil:new Perfil(correo:"alan@gmail.com", telefono:"7774089165", ocupacion:"estudiante")).save(flush:true, failOnError:true)
           UsuarioRol.create( usuarioDemo, rolParticipante, true)
       }

       //Permisos
        for(String url in ['/', '/**/partials/**', '/dbconsole/**', '/index', '/index.gsp', '/**/favicon.ico',
                            '/assets/**', '/**/js/**', '/**/css/**', '/**/images/**', '/**/fonts/**',
            '/login', '/login.*', '/login/*', '/logout', '/logout.*', '/logout/**']){
            if(!Recursos.findByUrl(url)){
                new Recursos(url: url, configAttribute: 'permitAll').save(flush:true, failOnError:true)
            }
        }
         if(!Recursos.findByUrl('/gestionCurso/**')){
        new Recursos(url: '/gestionCurso/**', configAttribute: 'IS_AUTHENTICATED_FULLY, ROLE_ADMIN, ROLE_PARTICIPANTE').save(flush: true, failOnError:true)
        }
        
        



    }
    def destroy = {
    }
}
