package com.tietoevry.quarkus.resteasy.problem.jsonb;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * Low level JsonB serializer for Problem type.
 */
public final class JsonbProblemSerializer implements JsonbSerializer<HttpProblem> {

    @Override
    public void serialize(HttpProblem problem, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartObject();
        if (problem.getType() != null) {
            generator.write("type", problem.getType().toASCIIString());
        }
        if (problem.getStatus() != null) {
            generator.write("status", problem.getStatus().getStatusCode());
        }
        if (problem.getTitle() != null) {
            generator.write("title", problem.getTitle());
        }
        if (problem.getDetail() != null) {
            generator.write("detail", problem.getDetail());
        }
        if (problem.getInstance() != null) {
            generator.write("instance", problem.getInstance().toASCIIString());
        }

        problem.getParameters().forEach((key, value) -> ctx.serialize(key, value, generator));

        generator.writeEnd();
    }
}
