package api.client;

public enum EndpointURLmainTypes {

    MAIN_TYPES("main-types");
    String path;

    EndpointURLmainTypes(String path) {this.path = path;}

    public String getPath() {return path;}

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }

}
