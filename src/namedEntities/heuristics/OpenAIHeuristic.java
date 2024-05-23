package namedEntities.heuristics;

import com.openai.api.*;
import com.openai.api.model.v1.*;
import okhttp3.*;
import java.util.*;

public class OpenAIHeuristic {

    public static List<String> extractCandidates(String text) throws Exception {

        // Initialize OpenAI API
        OpenAIAPI api = new OpenAIAPI(System.getenv("OPENAI_API_KEY"));

        // Use OpenAI API to analyze text
        Completion completion = api.completions().create(new CompletionRequest()
                .setPrompt(text)
                .setMaxTokens(60)
                .setTemperature(0.3)
                .setTopP(1.0)
                .setFrequencyPenalty(0.0)
                .setPresencePenalty(0.0)
        );

        // Get named entities from analysis result
        String analysisResult = completion.getChoices().get(0).getText().get();

        return Arrays.asList(analysisResult.split("\\s+"));
    }
}
