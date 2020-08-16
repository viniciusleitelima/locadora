package br.com.locadora.locadora.enums;

import br.com.locadora.locadora.exception.BusinessException;
import org.springframework.http.HttpStatus;

public enum Message {

    ILLEGAL_ARGUMENT_EXCEPTION("Argumento informado n�o � v�lido.", HttpStatus.BAD_REQUEST),
    MOVIE_NOT_FOUND("Filme n�o encontrado", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND("Cliente n�o encontrado", HttpStatus.NOT_FOUND),
    MOVIE_UNAVAILABLE("Filme n�o dispon�vel", HttpStatus.CONFLICT),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION("Media type informado n�o � suportado", HttpStatus.BAD_REQUEST),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION("Metodo HTTP n�o suportado", HttpStatus.BAD_REQUEST),
    BIND_EXCEPTION("N�o foi poss�vel converter a solicita��o", HttpStatus.BAD_REQUEST);

    private String message;

    private HttpStatus statusCode;

    private Message(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public final String getMessage() {
        return this.message;
    }

    protected void setMessage(String value) {
        this.message = value;
    }

    public HttpStatus getStatus() {
        return this.statusCode;
    }

    protected void setStatus(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public BusinessException asBusinessException(String... strings) {
        return BusinessException.builder().httpStatusCode(this.getStatus()).message(this.formatMessage(strings)).build();
    }

    public BusinessException asBusinessException(Object object, String... strings) {
        return BusinessException.builder().httpStatusCode(this.getStatus()).message(this.formatMessage(strings)).object(object).build();
    }

    public String formatMessage(String... strings) {
        return String.format(this.getMessage(), strings);
    }
}
