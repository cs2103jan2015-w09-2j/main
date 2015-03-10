/*
 @author: Ng Zhi Hua
 */

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TaskSerializer implements JsonSerializer<Task> {
	private static final String EMPTY_STRING = "";

	public JsonElement serialize(final Task task, final Type type,
			final JsonSerializationContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		JsonObject result = new JsonObject();
		result.add("Description", new JsonPrimitive(task.getDescription()));
		
		if (task.getStart() == null){
			result.add("start", new JsonPrimitive(EMPTY_STRING));
		}
		else{
		result.add("start", new JsonPrimitive(task.getStart().format(formatter).toString()));
		}
		
		if (task.getEnd() == null){
			result.add("end", new JsonPrimitive(EMPTY_STRING));
		}
		else{
		result.add("end", new JsonPrimitive(task.getEnd().format(formatter).toString()));
		}
		
		return result;
	}
}