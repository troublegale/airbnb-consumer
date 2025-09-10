package itmo.tg.airbnb_consumer.service.senders.jira;

public interface JiraConnection {

    void createIssue(String summary, String payload);

}
