package com.tietoevry.quarkus.resteasy.problem.security;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import javax.annotation.Priority;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.Priorities;

/**
 * Mapper overriding default Quarkus exception mapper to make all error responses compliant with RFC7807.
 *
 * @see io.quarkus.resteasy.runtime.UnauthorizedExceptionMapper
 */
@Priority(Priorities.USER)
public final class UnauthorizedExceptionMapper extends ExceptionMapperBase<UnauthorizedException> {

    @Override
    protected HttpProblem toProblem(UnauthorizedException exception) {
        return HttpUnauthorizedUtils.toProblem(currentVertxRequest().getCurrent(), exception)
                .await().indefinitely();
    }

    volatile CurrentVertxRequest currentVertxRequest;

    private CurrentVertxRequest currentVertxRequest() {
        if (currentVertxRequest == null) {
            currentVertxRequest = CDI.current().select(CurrentVertxRequest.class).get();
        }
        return currentVertxRequest;
    }

}
