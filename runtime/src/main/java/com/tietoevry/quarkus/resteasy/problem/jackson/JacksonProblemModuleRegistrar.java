package com.tietoevry.quarkus.resteasy.problem.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import io.quarkus.jackson.ObjectMapperCustomizer;
import javax.inject.Singleton;

@Singleton
public final class JacksonProblemModuleRegistrar implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule("RFC7807 problem");
        module.addSerializer(HttpProblem.class, new JacksonProblemSerializer());
        mapper.registerModule(module);
    }

}
