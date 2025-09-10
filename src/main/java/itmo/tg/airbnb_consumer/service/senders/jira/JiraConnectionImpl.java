package itmo.tg.airbnb_consumer.service.senders.jira;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class JiraConnectionImpl implements JiraConnection {

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.username}")
    private String jiraUsername;

    @Value("${jira.api-token}")
    private String jiraApiToken;

    @Value("${jira.project-key}")
    private String jiraProjectKey;

    @Override
    public void createIssue(String summary, String description) {

        String issueJSON = buildIssueJSON(summary, description);
        String authHeader = buildAuthHeader();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(jiraUrl + "/rest/api/3/issue"))
                .header("Authorization", "Basic " + authHeader)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(issueJSON, StandardCharsets.UTF_8))
                .build();

        try {

            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() >= 300) {
                log.error("Jira error: {} {}", response.statusCode(), response.body());
            } else {
                log.info(response.body());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private String buildIssueJSON(String summary, String description) {
        String escapedSummary = escapeJson(summary);
        String escapedDescription = escapeJson(description);
        return "{\n" +
                "  \"fields\": {\n" +
                "    \"project\": {\n" +
                "      \"key\": \"" + jiraProjectKey + "\"\n" +
                "    },\n" +
                "    \"summary\": \"" + escapedSummary + "\",\n" +
                "    \"description\": {\n" +
                "      \"type\": \"doc\",\n" +
                "      \"version\": 1,\n" +
                "      \"content\": [\n" +
                "        {\n" +
                "          \"type\": \"paragraph\",\n" +
                "          \"content\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"" + escapedDescription + "\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"issuetype\": {\n" +
                "      \"name\": \"Task\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"");
    }

    private String buildAuthHeader() {
        return Base64.getEncoder()
                .encodeToString((jiraUsername + ":" + jiraApiToken).getBytes(StandardCharsets.UTF_8));
    }

}
