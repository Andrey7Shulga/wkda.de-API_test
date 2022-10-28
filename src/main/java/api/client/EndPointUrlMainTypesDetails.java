package api.client;

public enum EndPointUrlMainTypesDetails {
    MAIN_TYPES_DETAILS("main-types-details");
    final String path;

    EndPointUrlMainTypesDetails(String path) {this.path = path;}

    public String getPath() {return path;}
    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
