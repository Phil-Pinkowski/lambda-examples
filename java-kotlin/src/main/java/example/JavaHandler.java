package example;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class JavaHandler implements RequestHandler<LambdaRequest, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest input, Context context) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode json = objectMapper.readTree(input.getBody());

            String textUrl = json.get("textUrl").asText();
            String text = Request.Get(textUrl).execute().returnContent().asString();

            int words = text.split(" ").length;
            String longestWord = Arrays.stream(text.split(" ")).max(Comparator.comparingInt(String::length)).get();

            return new LambdaResponse(String.format("{\"wordCount\":%s,\"longestWord\":\"%s\"}", words, longestWord), 200);
        } catch (IOException e) {
            return new LambdaResponse(e.getMessage(), 500);
        }
    }
}
