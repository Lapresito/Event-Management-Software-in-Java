package excepciones;

@SuppressWarnings("serial")
public class NicknameRepetidoException extends Exception {
	public NicknameRepetidoException(String string) {
        super(string);
    }
}
