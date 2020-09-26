package createData;

import pogo.requests.NewManufacturer;

import java.util.HashMap;
import java.util.Map;

public class AddNewManufacturer {

    public final String key = "603";
    public final String value = "GAZ";

        public NewManufacturer createNewWKDA() {

        Map<String, String> newManufacturer = new HashMap<>();
        newManufacturer.put(key, value);

        return new NewManufacturer()
                .setWkda(newManufacturer);

    }

}
