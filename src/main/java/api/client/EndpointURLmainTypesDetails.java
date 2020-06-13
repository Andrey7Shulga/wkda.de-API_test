package api.client;

public enum  EndpointURLmainTypesDetails {

    MAIN_TYPES_DETAILS("main-types-details");
    String path;


    EndpointURLmainTypesDetails(String path) {

        this.path = path;
    }

    public String getPath() {

        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;

    }




}
