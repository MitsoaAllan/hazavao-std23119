package hei.school.hazavao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class HazavaoService {
  private static final String API_KEY = "mitantsoa";
  private static final String API_URL = "https://api.openai.com/v1/chat/completions";
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public String getDefinition(String mot) throws Exception {
    Map<String, Object> message =
        Map.of(
            "role",
            "user",
            "content",
            "Omeo famaritana tsotra amin'ny teny malagasy ny teny : " + mot);

    Map<String, Object> requestBody =
        Map.of("model", "gpt-4", "messages", List.of(message), "temperature", 0.3);

    String jsonRequest = objectMapper.writeValueAsString(requestBody);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Authorization", "Bearer " + API_KEY)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException("Hadisoana OpenAI : " + response.body());
    }

    Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
    Map<String, Object> messageReturned = (Map<String, Object>) choices.get(0).get("message");

    return messageReturned.get("content").toString().trim();
  }
}
