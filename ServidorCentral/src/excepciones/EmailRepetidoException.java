package excepciones;

@SuppressWarnings("serial")
public class EmailRepetidoException extends Exception {
	public EmailRepetidoException(String string) {
        super(string);
    }
}
