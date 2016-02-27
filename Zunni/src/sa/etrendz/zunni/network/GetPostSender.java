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
			
//			CookieManager msCookieManager = new CookieManager();
//			if(msCookieManager.getCookieStore().getCookies().size() > 0)
//			{
//			    //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
//				httpURLConnection.setRequestProperty("Cookie",
//			    TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));    
//				ZuniUtils.LogEvent(TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
//			}
//			else
//			{
//				ZuniUtils.LogEvent("No Cookie Found...!");
//			}
			httpURLConnection.connect();
			String response = getResponse(httpURLConnection, true);
	        
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
	        String response = getResponse(httpURLConnection, true);
	        if(ZunniConstants.isLogEnabled)
	        {
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