/*
 @author: Ng Zhi Hua
 */

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TaskSerializer implements JsonSerializer<Task> {
	public JsonElement serialize(final Task task, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("Description", new JsonPrimitive(task.getDescription()));
		result.add("start", new JsonPrimitive(task.getStart().toString()));
		result.add("end", new JsonPrimitive(task.getEnd().toString()));
		return result;
	}
}