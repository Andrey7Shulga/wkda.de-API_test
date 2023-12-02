package api.client;

public enum EndPointUrlMainTypes {
    MAIN_TYPES("main-types");
    final String path;

    EndPointUrlMainTypes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}