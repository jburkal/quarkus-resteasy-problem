package com.tietoevry.quarkus.resteasy.problem.jsonb;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ProcessingException;

@Priority(Priorities.USER)
public final class RestEasyClassicJsonbExceptionMapper extends ExceptionMapperBase<ProcessingException> {

    /**
     * Unfortunately Quarkus+JsonB throws ProcessingException, not JsonbException in case of malformed payload body, so `cause`
     * needs to be checked explicitly.
     *
     * For native mode compatibility instanceof operator is not used to check cause type.
     */
    @Override
    protected HttpProblem toProblem(ProcessingException exception) {
        if (exception.getCause() != null
                && exception.getCause().getClass().getName().equals("javax.json.bind.JsonbException")) {
            return HttpProblem.valueOf(BAD_REQUEST, exception.getCause().getMessage());
        } else {
            return HttpProblem.valueOf(INTERNAL_SERVER_ERROR);
        }
    }
}
