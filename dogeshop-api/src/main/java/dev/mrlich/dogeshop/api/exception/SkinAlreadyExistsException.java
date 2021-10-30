package dev.mrlich.dogeshop.api.exception;

public class SkinAlreadyExistsException extends RuntimeException {

    public SkinAlreadyExistsException() {
        super();
    }

    public SkinAlreadyExistsException(String message) {
        super(message);
    }

    public SkinAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkinAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
