package test.query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

import static test.query.QueryUtils.*;

/**
 * Permet de faire des requêtes sur l'endpoint Quaggans.
 * @author Fabrice Bouyé
 */
public enum QuaggansQuery {

    INSTANCE;

    /**
     * L'URL de base de cet endpoint.
     */
    private static final String basecode = "https://api.guildwars2.com/v2/quaggans"; // NOI18N.

    /*
     * Récupère la liste de tous les identifiants des images de Quaggans.
     * @return Une instance de {@code List<String>}.
     * @throws IOException En cas d'erreur.
     */
    public static List<String> list() throws Exception {
        final JsonArray array = queryArray(basecode);
        // On transforme le JsonArray<JsonString> en List<String>.
        final List<String> values = array.getValuesAs(JsonString.class)
                .stream()
                .map(value -> value.getString())
                .collect(Collectors.toList());
        return Collections.unmodifiableList(values);
    }

    /**
     * Récupère l'URL de l'image pour un identifiant donné.
     * @return Une URL sous forme de {@code String}.
     * @throws IOException En cas d'erreur.
     */
    public static String imageURLForId(final String quaggan) throws Exception {
        final JsonObject object = queryObject(basecode + "/" + quaggan); // NOI18N.
        final JsonString url = object.getJsonString("url"); // NOI18N.
        return url.getString();
    }
}
