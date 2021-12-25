package api.client;

public enum EndpointURLmanufacturer {

    MANUFACTURER("manufacturer");
    String path;

    EndpointURLmanufacturer(String path) {this.path = path;}

    public String getPath() {return path;}

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
