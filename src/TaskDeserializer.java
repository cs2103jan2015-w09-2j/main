import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TaskDeserializer implements JsonDeserializer<Task> {

  private static final String EMPTY_STRING = "";

@Override
  public Task deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context){
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	if (json == null){
		return null;
	}
	
	JsonObject jsonObject = json.getAsJsonObject();
	String description = jsonObject.get("Description").getAsString();
    String start = jsonObject.get("start").getAsString();
    String end = jsonObject.get("end").getAsString();
    
    final Task task = new Task();
    task.setDescription(description);
    if (!start.equals(EMPTY_STRING)){
    	task.setStart(LocalDateTime.parse(start, formatter));
    }
    if (!end.equals(EMPTY_STRING)){
    task.setEnd(LocalDateTime.parse(end, formatter));
    }
    return task;
  }
}