package newbank.server.commands;

public class CommandResponse {
    private String theResponse;

    public CommandResponse(String aResponse) {
        theResponse = aResponse;
    }

    public String getResponse() {
        return theResponse;
    }
}
