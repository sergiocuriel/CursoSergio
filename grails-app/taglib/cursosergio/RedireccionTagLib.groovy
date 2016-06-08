package cursosergio

class RedireccionTagLib {
    static defaultEncodeAs = [taglib:'html']
    def redir = {attrs, body -> response.sendError(403)}
}