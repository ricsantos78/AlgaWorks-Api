package com.algafoods.api.exceptionHandler;

import com.algafoods.domain.exception.BusinessException;
import com.algafoods.domain.exception.EntityInUseException;
import com.algafoods.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.Reference;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
            = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
            + "o problema persistir, entre em contato com o administrador do sistema.";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                           WebRequest request){

        var status = HttpStatus.NOT_FOUND;
        var troubleType = TroubleType.ENTITY_NOT_FOUND;
        var detail = ex.getMessage();

        var trouble = createTroubleBuilder(status,troubleType,detail)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, trouble, new HttpHeaders()
                , status,request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(EntityInUseException ex,
                                                        WebRequest request){
        var status = HttpStatus.CONFLICT;
        var troubleType = TroubleType.ENTITY_IN_USE;
        var detail = ex.getMessage();

        var trouble = createTroubleBuilder(status,troubleType,detail)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, trouble, new HttpHeaders()
                , status,request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex,
                                                     WebRequest request){

        var status = HttpStatus.BAD_REQUEST;
        var troubleType = TroubleType.BUSINESS_ERROR;
        var detail = ex.getMessage();

        var trouble = createTroubleBuilder(status,troubleType,detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble, new HttpHeaders()
                , status,request);
    }

    private Trouble.TroubleBuilder createTroubleBuilder(HttpStatus status,
                                                        TroubleType troubleType,
                                                        String detail){
        return Trouble.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(troubleType.getUri())
                .title(troubleType.getTitle())
                .detail(detail);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        if(body ==null) {
            body = Trouble.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if ((body) instanceof String) {
            body = Trouble.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(ex);

        if ((rootCause) instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }else if ((rootCause) instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        var troubleType = TroubleType.INVALID_REQUEST;
        var detail = "o corpo da requisição esta inválido, Verifique erro de sintaxe.";

        var trouble = createTroubleBuilder(status,troubleType,detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble, headers
                , status,request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {

        var path = joinPath(ex.getPath());

        var troubleType = TroubleType.INVALID_REQUEST;
        var detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        var trouble = createTroubleBuilder(status, troubleType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

        var path = joinPath(ex.getPath());

        var troubleType = TroubleType.INVALID_REQUEST
                ;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        var trouble = createTroubleBuilder(status,troubleType,detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        var problemType = TroubleType.INVALID_PARAMETER;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Trouble trouble = createTroubleBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var troubleType = TroubleType.RESOURCE_NOT_FOUND;

        var detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());
        var trouble = createTroubleBuilder(status, troubleType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, trouble,headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var troubleType = TroubleType.SYSTEM_ERROR;

        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();

        var trouble = createTroubleBuilder(status, troubleType, MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, trouble, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        var troubleType = TroubleType.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Trouble.Field> troubleFields = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> Trouble.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build()).toList();

        var trouble = createTroubleBuilder(status, troubleType, detail)
                .userMessage(detail)
                .fieldList(troubleFields)
                .build();

        return handleExceptionInternal(ex, trouble, headers, status, request);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

}
