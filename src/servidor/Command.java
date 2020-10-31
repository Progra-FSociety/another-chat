package servidor;

import java.io.IOException;

import com.google.gson.*;

public abstract class Command {
	protected final Gson gsonHelper = new Gson();

	public abstract void execute() throws IOException;
}
