package edu.kis.powp.jobs2d.command.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.CompoundCommand;

import java.util.ArrayList;
import java.util.List;

public class JsonCommandImporter implements CommandImporter {

    @Override
    public ICompoundCommand importCommands(String text) {
        List<DriverCommand> commandList = new ArrayList<>();

        JsonElement rootElement = JsonParser.parseString(text);
        if (rootElement == null || !rootElement.isJsonArray()) {
            throw new IllegalArgumentException("Expected a JSON array of commands");
        }

        JsonArray jsonArray = rootElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            String type = obj.get("type").getAsString();

            if (!obj.has("x") && !obj.has("posX")) {
                throw new IllegalArgumentException("Missing 'x' or 'posX' coordinate in command: " + type);
            }
            if (!obj.has("y") && !obj.has("posY")) {
                throw new IllegalArgumentException("Missing 'y' or 'posY' coordinate in command: " + type);
            }

            int x = obj.has("x") ? obj.get("x").getAsInt() : obj.get("posX").getAsInt();
            int y = obj.has("y") ? obj.get("y").getAsInt() : obj.get("posY").getAsInt();

            if ("SetPositionCommand".equals(type)) {
                commandList.add(new SetPositionCommand(x, y));
            } else if ("OperateToCommand".equals(type)) {
                commandList.add(new OperateToCommand(x, y));
            } else {
                throw new IllegalArgumentException("Unknown command type: " + type);
            }
        }

        return new CompoundCommand(commandList);
    }
}
