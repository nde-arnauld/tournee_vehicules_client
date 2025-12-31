package principale.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import principale.model.Ville;

public class JsonVilleParser implements VilleParser {
	@Override
	public List<Ville> parse(String nomFichier) throws Exception {

	    Path path = Path.of("src\\villes", nomFichier);

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    byte[] data = Files.readAllBytes(path.toAbsolutePath());

	    return Arrays.asList(mapper.readValue(data, Ville[].class));
	}

}
