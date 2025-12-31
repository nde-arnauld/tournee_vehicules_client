package principale.utils;

import java.util.Map;
import java.util.function.Supplier;

public class VilleParserFactory {

    private static final Map<String, Supplier<VilleParser>> REGISTRY = Map.of(
            "json", JsonVilleParser::new
    );

    public static VilleParser create(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        Supplier<VilleParser> supplier = REGISTRY.get(ext);
        if (supplier == null)
            throw new IllegalArgumentException("Format non supporté : " + ext);

        return supplier.get();
    }
}
