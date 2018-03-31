package ITStep;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class HttpRequest {
    public static JSONObject doQuery(String url) throws JSONException, IOException {
        String responseBody = null;
        HttpGet httpget = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpget);
        InputStream contentStream = null;

        try {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine == null) {
                throw new IOException(String.format("Unable to get a response from service"));
            } else {
                int statusCode = statusLine.getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    throw new IOException(String.format("Server responded with status code %d: %s", statusCode, statusLine));
                } else {
                    HttpEntity responseEntity = response.getEntity();
                    contentStream = responseEntity.getContent();
                    Reader isReader = new InputStreamReader(contentStream);
                    int contentSize = (int)responseEntity.getContentLength();
                    if (contentSize < 0) {
                        contentSize = 8192;
                    }

                    StringWriter strWriter = new StringWriter(contentSize);
                    char[] buffer = new char[8192];
                    boolean var13 = false;

                    int n;
                    while((n = isReader.read(buffer)) != -1) {
                        strWriter.write(buffer, 0, n);
                    }

                    responseBody = strWriter.toString();
                    contentStream.close();
                    return new JSONObject(responseBody);
                }
            }
        } catch (IOException var18) {
            throw var18;
        } catch (RuntimeException var19) {
            httpget.abort();
            throw var19;
        } finally {
            if (contentStream != null) {
                contentStream.close();
            }
        }
    }
}
