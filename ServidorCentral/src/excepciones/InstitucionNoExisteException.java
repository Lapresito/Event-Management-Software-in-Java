package excepciones;

public class InstitucionNoExisteException extends Exception {
    private static final long serialVersionUID = 1L;

    public InstitucionNoExisteException(String mensaje) {
        super(mensaje);
    }
}