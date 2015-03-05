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
	public JsonElement serialize(final Task task, final Type type,
			final JsonSerializationContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		JsonObject result = new JsonObject();
		result.add("Description", new JsonPrimitive(task.getDescription()));
		result.add("start", new JsonPrimitive(task.getStart().format(formatter).toString()));
		result.add("end", new JsonPrimitive(task.getEnd().format(formatter).toString()));
		return result;
	}
}