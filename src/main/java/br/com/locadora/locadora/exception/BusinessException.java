package br.com.locadora.locadora.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class BusinessException extends RuntimeException {

    @JsonIgnore
    private HttpStatus httpStatusCode;

    private String code;

    private String message;

    private String description;

    private Object object;

    public BusinessException(HttpStatus httpStatus, String... strings) {
        this.httpStatusCode = httpStatus;
        this.message = String.format("",strings);
    }

    public BusinessExceptionBody getOnlyBody(){
        log.info("mensagem={}",this.message);
        return BusinessExceptionBody.builder()
                .code(this.code)
                .message(this.message)
                .description(this.description)
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BusinessExceptionBody{

        private String code;

        private String message;

        private String description;

    }
}
