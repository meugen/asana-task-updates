package meugeninua.asana.taskupdates.app.tasks.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import meugeninua.asana.taskupdates.app.models.AddProjectBody;
import meugeninua.asana.taskupdates.app.tasks.TaskInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Objects;

public class MoveToSectionRequest extends AbstractRequest {

    private static final String URL = "https://app.asana.com/api/1.0/tasks/%s/addProject";

    private final String sectionId;

    public MoveToSectionRequest(HttpClient httpClient, String token, String sectionId) {
        super(httpClient, token);
        this.sectionId = Objects.requireNonNull(sectionId);
    }

    @Override
    protected HttpRequest.Builder newHttpRequestBuilder(TaskInfo taskInfo) throws Exception {
        var body = new AddProjectBody(taskInfo.getProjectId(), sectionId);
        var bodyPublisher = HttpRequest.BodyPublishers.ofString(
            new ObjectMapper().writeValueAsString(body)
        );
        var uri = new URI(String.format(URL, taskInfo.getTaskId()));
        return HttpRequest.newBuilder(uri).POST(bodyPublisher);
    }
}
