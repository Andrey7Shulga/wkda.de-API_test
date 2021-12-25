package createData;

import pogo.requests.NewManufacturer;

import java.util.HashMap;
import java.util.Map;

public class AddNewManufacturer {

        public NewManufacturer createNewWKDA(String key, String value) {

        Map<String, String> newManufacturer = new HashMap<>();
        newManufacturer.put(key, value);

        return new NewManufacturer().setWkda(newManufacturer);
    }
}
