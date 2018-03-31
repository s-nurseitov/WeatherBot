package ITStep.Image;

import org.json.JSONException;

import java.io.IOException;

public interface ImageService {
    String getImageUrlByCity(String city) throws IOException, JSONException;
}
