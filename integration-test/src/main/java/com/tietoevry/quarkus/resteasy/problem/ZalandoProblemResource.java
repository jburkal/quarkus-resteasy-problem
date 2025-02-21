package com.tietoevry.quarkus.resteasy.problem;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

@Path("/throw/zalando-problem/")
@Produces(MediaType.APPLICATION_JSON)
public class ZalandoProblemResource {

    @GET
    public void throwProblem(@QueryParam("status") int status, @QueryParam("title") String title,
            @QueryParam("detail") String detail) {
        throw new TestProblem(title, Status.valueOf(status), detail);
    }

    static final class TestProblem extends AbstractThrowableProblem {
        TestProblem(String title, StatusType status, String detail) {
            super(URI.create("/business-problem"), title, status, detail, URI.create("/problem/special-case"), null);
        }
    }

}
