package test.query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Classe utilitaire pour requêtes JSON.
 * @author Fabrice Bouyé
 */
public enum QueryUtils {

    INSTANCE;

    /**
     * Récupère un tableau JSON à l'URL indiquée.
     * @param basecode L'URL source.
     * @return Un objet de type <span style="font-family: monospace; padding: 2px; background: #eee">JsonArray</span>    * @throws IOException En cas d'erreur.
     */
    public static JsonArray queryArray(final String basecode) throws IOException {
        final URL url = new URL(basecode);
        try (final InputStream input = url.openStream(); final JsonReader reader = Json.createReader(input)) {
            return reader.readArray();
        }
    }

    /**
     * Récupère un objet JSON à l'URL indiquée.
     * @param basecode L'URL source.
     * @return Un objet de type <span style="font-family: monospace; padding: 2px; background: #eee">JsonArray</span>    * @throws IOException En cas d'erreur.
     */
    public static JsonObject queryObject(final String basecode) throws IOException {
        final URL url = new URL(basecode);
        try (final InputStream input = url.openStream(); final JsonReader reader = Json.createReader(input)) {
            return reader.readObject();
        }
    }
}
