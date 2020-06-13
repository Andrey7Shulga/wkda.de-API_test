package api.client;

public enum  EndpointURLbuiltDates {

    BUILT_DATES("built-dates");
    String path;

    EndpointURLbuiltDates(String path) {

        this.path = path;
    }

    public String getPath() {

        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;

    }
}
