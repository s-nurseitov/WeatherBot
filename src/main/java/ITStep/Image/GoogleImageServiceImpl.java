package ITStep.Image;


import ITStep.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GoogleImageServiceImpl implements ImageService {
    private final static String APPID = "AIzaSyCHpqNbtZDz4qwS1kwwPkHt2v0BXnjuCDg";

    @Override
    public String getImageUrlByCity(String city) throws IOException, JSONException {
        String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&maxheight=500&photoreference=";
        String placeId = getPlaceId(city);
        System.out.println(placeId);
        List<String> photos = getImagesUrls(placeId);
        int randomPhoto = new Random().nextInt(photos.size());
        return photoUrl + photos.get(randomPhoto) + "&key=" + APPID;
    }

    private String getPlaceId(String city) throws IOException, JSONException {
        String placeSearchUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json";
        placeSearchUrl += "?query=" + city + "&key=" + APPID;
        JSONObject responseBody = HttpRequest.doQuery(placeSearchUrl);
        JSONArray results = responseBody.getJSONArray("results");
        return results.getJSONObject(0).getString("place_id");
    }

    private List<String> getImagesUrls(String placeId) throws IOException, JSONException {
        String placeDetailsUrl = "https://maps.googleapis.com/maps/api/place/details/json";
        placeDetailsUrl += "?placeid=" + placeId + "&key=" + APPID;
        System.out.println(placeDetailsUrl);
        JSONObject jsonBody = HttpRequest.doQuery(placeDetailsUrl);
        System.out.println(jsonBody);
        JSONObject requestResult = jsonBody.getJSONObject("result");
        JSONArray photos = requestResult.getJSONArray("photos");
        List<String> result = new LinkedList<>();
        for (int i = 0; i < photos.length(); i++) {
            result.add(photos.getJSONObject(i).getString("photo_reference"));
        }
        return result;
    }
}
