package com.tietoevry.quarkus.resteasy.problem.jsonb;

import static org.assertj.core.api.Assertions.assertThat;

import javax.json.bind.JsonbException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class RestEasyClassicJsonbExceptionMapperTest {

    RestEasyClassicJsonbExceptionMapper mapper = new RestEasyClassicJsonbExceptionMapper();

    @Test
    void processingExceptionShouldProduceHttp500() {
        ProcessingException exception = new ProcessingException("Something is wrong");

        Response response = mapper.toResponse(exception);

        assertThat(response.getStatus()).isEqualTo(500);
    }

    @Test
    void processingExceptionWithJsonbExceptionCauseShouldProduceHttp400() {
        ProcessingException exception = new ProcessingException(new JsonbException("Something is wrong"));

        Response response = mapper.toResponse(exception);

        assertThat(response.getStatus()).isEqualTo(400);
    }
}
