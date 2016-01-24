package sa.etrendz.zunni.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils 
{
	public final String UTF8 = "UTF-8";

	public final String HTTP_GET = "GET";
	public final String HTTP_POST = "POST";

	public final String CHANGE_LINE = "\r\n";
	public final String END_REQUEST = "--";

	public final String CONTENT_DISPOSITION = "Content-Disposition: ";
	public final String CONTENT_TYPE = "Content-Type: ";
	public final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";

	public final String CHARSET = " charset=";
	public final String FILE_NAME = " filename=";
	public final String NAME = "name=";
	public final String FORM_DATA = "form-data; ";
	public final String BINARY = "binary";

	public final String VIDEO_MP4 = "video/mp4";
	public final String IMAGE_JPEG = "image/jpeg";
	public final String PLAIN_TEXT = "text/plain; ";

	public final String APPLICATION_MULTIPART = "multipart/form-data";
	public final String APPLICATION_JSON = "application/json";


	public String mBoundary = END_REQUEST + END_REQUEST + "WebKitFormBoundaryflDIl9j7fMbC5CDw";

	public HttpURLConnection getUrlConnection(String URL, String httpMethod,
			String contenttype, String boundry) throws Exception 
	{
		URL url = new URL(URL);
		
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		if (httpMethod.equalsIgnoreCase(HTTP_POST) == true)
		{
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
		}
		urlConnection.setRequestMethod(httpMethod);
		
		if (contenttype.equalsIgnoreCase(APPLICATION_MULTIPART))
		{
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Content-Type" , "multipart/form-data; boundary="+ boundry);
			urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			urlConnection.setRequestProperty("Cache-Control", "max-age=0");
		}
		else
		{
			urlConnection.setRequestProperty("Content-Type"
					, contenttype);
		}
		
		//Melltoo Header
		urlConnection.setRequestProperty("version", "1");
		
		return urlConnection;
	}

	public String getResponse(HttpURLConnection urlConnection) throws Exception 
	{
		String response = "";
		int status = urlConnection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            		urlConnection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) 
            {
                response = response + line;
            }
            reader.close();
            urlConnection.disconnect();
        }
        else
        {
            throw new IOException("Server returned non-OK status: " + status);
        }
		return response;
	}
}