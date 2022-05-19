package fr.epita.trackmoviesback.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionTranslator.class);



    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<Object> accessDeniedExceptionHandler(final AccessDeniedException exception) {

        final ErrorModel apiError = ErrorModel.builder() //
                .message(exception.getLocalizedMessage()) //
                .description("Vous n'êtes pas autorisé à faire cette opération !")//
                .build();

        LOG.debug(exception.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}
