package sa.etrendz.zunni.adapter;

import java.io.IOException;

import sa.etrendz.zunni.bean.BeanGetAllCategory;
import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MainCategoryGSONTypedAdapter extends TypeAdapter<BeanGetAllCategory>
{

	@Override
	public BeanGetAllCategory read(JsonReader reader) throws IOException 
	{
	     // the first token is the start object
        JsonToken token = reader.peek();
        BeanGetAllCategory dataset = new BeanGetAllCategory();
        if (token.equals(JsonToken.BEGIN_OBJECT)) {
            reader.beginObject();
            while (!reader.peek().equals(JsonToken.END_OBJECT))
            {
                if (reader.peek().equals(JsonToken.NAME)) 
                {
                	Log.e(reader.nextName() + "asd", "asd");
//                    if (reader.nextName().equals("album_url"))
//                        dataset.setAlbum_title(reader.nextString());
//                    else
//                        reader.skipValue();
                }
            }
            reader.endObject();
        }
        return dataset;
    }

	@Override
	public void write(JsonWriter jsonWriter, BeanGetAllCategory bean)
			throws IOException 
	{
		
	}
}
