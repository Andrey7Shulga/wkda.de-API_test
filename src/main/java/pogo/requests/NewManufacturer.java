package pogo.requests;

import java.util.Map;

public class NewManufacturer {
    private Map<String, String> wkda;
    public Map<String, String> getWkda() {
        return wkda;
    }

    public NewManufacturer setWkda(Map<String, String> wkda) {
        this.wkda = wkda;
        return this;
    }
}
