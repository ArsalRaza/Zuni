package sa.etrendz.zunni.network;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import sa.etrendz.zunni.utils.ZunniConstants;
import android.util.Log;

public class GetPostSender extends NetworkUtils
{
	public String sendGet(String url)
	{
		HttpURLConnection httpURLConnection;
		try
		{
			httpURLConnection = getUrlConnection(url, HTTP_GET, PLAIN_TEXT, "");
			httpURLConnection.connect();
			String response = getResponse(httpURLConnection);
	        
	        return response;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String sendPostJSON(String url, String params)
	{
		HttpURLConnection httpURLConnection;
		try 
		{
			if(ZunniConstants.isLogEnabled){
				Log.e("URL", ZunniConstants.BASE_URL + url);
				Log.e("Request Params", params.toString() );
			}
			
			httpURLConnection = getUrlConnection(ZunniConstants.BASE_URL + url, HTTP_POST, APPLICATION_JSON, "");
			httpURLConnection.connect();
			
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"),
	                true);
			writer.write(params.toString());
			writer.flush();
	        String response = getResponse(httpURLConnection);
	        if(ZunniConstants.isLogEnabled){
				Log.e("Response", response );
			}
	        return response;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "false";
		}
	}
}