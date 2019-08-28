package example;

public class LambdaResponse {
    private int statusCode;
    private String body;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LambdaResponse(){}

    public LambdaResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }
}
