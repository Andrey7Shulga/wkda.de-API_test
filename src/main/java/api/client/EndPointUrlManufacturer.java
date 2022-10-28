package api.client;

public enum EndPointUrlManufacturer {

    MANUFACTURER("manufacturer");
    final String path;

    EndPointUrlManufacturer(String path) {this.path = path;}

    public String getPath() {return path;}
    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}