package api.client;

public enum EndPointUrlBuiltDates {
    BUILT_DATES("built-dates");
    final String path;

    EndPointUrlBuiltDates(String path) {this.path = path;}

    public String getPath() { return path; }
    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
