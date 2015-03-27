import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TaskDeserializer implements JsonDeserializer<Task> {

	private static final String STRING_COMPLETED = "Completed";
	private static final String STRING_END = "End";
	private static final String STRING_START = "Start";
	private static final String STRING_DESCRIPTION = "Description";
	private static final String EMPTY_STRING = "";

	@Override
	public Task deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd-MM-yyyy HH:mm");
		if (json == null) {
			return null;
		}

		JsonObject jsonObject = json.getAsJsonObject();
		String description = jsonObject.get(STRING_DESCRIPTION).getAsString();
		String start = jsonObject.get(STRING_START).getAsString();
		String end = jsonObject.get(STRING_END).getAsString();
		boolean completed = jsonObject.get(STRING_COMPLETED).getAsBoolean();
		final Task task = new Task();
		if (!description.equals(EMPTY_STRING)){
		task.setDescription(description);
		}
		if (!start.equals(EMPTY_STRING)) {
			task.setStart(LocalDateTime.parse(start, formatter));
		}
		if (!end.equals(EMPTY_STRING)) {
			task.setEnd(LocalDateTime.parse(end, formatter));
		}
		task.setIsCompleted(completed);

		return task;
	}
}