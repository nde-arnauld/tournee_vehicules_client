package principale.utils;

import java.util.List;

import principale.model.Ville;

public interface VilleParser {
	List<Ville> parse(String nomFichier) throws Exception;
}
