package com.tietoevry.quarkus.resteasy.problem.postprocessing;

import static com.tietoevry.quarkus.resteasy.problem.HttpProblemMother.badRequestProblem;
import static com.tietoevry.quarkus.resteasy.problem.postprocessing.ProblemContextMother.simpleContext;
import static org.assertj.core.api.Assertions.assertThat;

import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PostProcessorsRegistryTest {

    static final int HIGHEST = 11;
    static final int MEDIUM = 10;
    static final int LOW = 9;

    PostProcessorsRegistry registry = new PostProcessorsRegistry();
    List<Integer> invocations = new ArrayList<>();

    @Test
    void shouldIterateFromHighestToLowestPriority() {
        registry.register(processorWithPriority(MEDIUM));
        registry.register(processorWithPriority(LOW));
        registry.register(processorWithPriority(HIGHEST));

        registry.applyPostProcessing(badRequestProblem(), simpleContext());

        assertThat(invocations).containsExactly(HIGHEST, MEDIUM, LOW);
    }

    @Test
    void shouldTolerateDuplicates() {
        registry.register(processorWithPriority(MEDIUM));
        registry.register(processorWithPriority(HIGHEST));
        registry.register(processorWithPriority(MEDIUM));
        registry.register(processorWithPriority(MEDIUM));

        registry.applyPostProcessing(badRequestProblem(), simpleContext());

        assertThat(invocations).containsExactly(HIGHEST, MEDIUM, MEDIUM, MEDIUM);
    }

    ProblemPostProcessor processorWithPriority(int priority) {
        return new TestProcessor(priority);
    }

    class TestProcessor implements ProblemPostProcessor {

        final int priority;

        TestProcessor(int priority) {
            this.priority = priority;
        }

        @Override
        public HttpProblem apply(HttpProblem problem, ProblemContext problemContext) {
            invocations.add(priority);
            return problem;
        }

        @Override
        public int priority() {
            return priority;
        }
    }

}
