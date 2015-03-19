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
	private static final String STRING_COMPLETED = "Completed";
	private static final String STRING_END = "End";
	private static final String STRING_START = "Start";
	private static final String STRING_DESCRIPTION = "Description";
	private static final String EMPTY_STRING = "";

	public JsonElement serialize(final Task task, final Type type,
			final JsonSerializationContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		JsonObject result = new JsonObject();
		result.add(STRING_DESCRIPTION, new JsonPrimitive(task.getDescription()));
		
		if (task.getStart() == null){
			result.add(STRING_START, new JsonPrimitive(EMPTY_STRING));
		}
		else{
		result.add(STRING_START, new JsonPrimitive(task.getStart().format(formatter).toString()));
		}
		
		if (task.getEnd() == null){
			result.add(STRING_END, new JsonPrimitive(EMPTY_STRING));
		}
		else{
		result.add(STRING_END, new JsonPrimitive(task.getEnd().format(formatter).toString()));
		}
		result.add(STRING_COMPLETED, new JsonPrimitive(task.getIsCompleted()));
		
		return result;
	}
}