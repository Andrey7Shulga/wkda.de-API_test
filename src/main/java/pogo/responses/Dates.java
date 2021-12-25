package pogo.responses;

import java.util.Map;

public class Dates {
    private Map<String, String> wkda;

    public Dates(Map<String, String> wkda) {
        this.wkda = wkda;
    }
    public Dates() {};

    public Map<String, String> getWkda() {
        return wkda;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
