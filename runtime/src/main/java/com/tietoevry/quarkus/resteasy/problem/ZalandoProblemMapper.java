package com.tietoevry.quarkus.resteasy.problem;

import java.util.Optional;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import org.zalando.problem.ThrowableProblem;

/**
 * Mapper for ThrowableProblem exception from Zalando Problem library.
 */
@Priority(Priorities.USER)
public final class ZalandoProblemMapper extends ExceptionMapperBase<ThrowableProblem> {

    @Override
    protected HttpProblem toProblem(ThrowableProblem exception) {
        Response.StatusType status = Optional.ofNullable(exception.getStatus())
                .map(problemStatus -> Response.Status.fromStatusCode(problemStatus.getStatusCode()))
                .orElse(Response.Status.INTERNAL_SERVER_ERROR);

        HttpProblem.Builder builder = HttpProblem.builder()
                .withType(exception.getType())
                .withTitle(exception.getTitle())
                .withStatus(status)
                .withDetail(exception.getDetail())
                .withInstance(exception.getInstance());
        exception.getParameters().forEach(builder::with);
        return builder.build();
    }

}
