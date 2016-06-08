package cursosergio

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Usuario implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Usuario(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this)*.rol
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

  static hasOne = [perfil: Perfil]

	static hasMany = [asistencia: AsistenciaCurso]

	static constraints = {
		username blank: false, unique: true
		password blank: false
		perfil unique: true
	}

	static mapping = {
		password column: '`password`'
		version false
		asistencia cascade:'all-delete-orphan'
	}
}
