package chat;

import java.util.Arrays;

enum CommandType {
    QUIT,
    LOGIN,
    LOGOFF,
    ERROR,
}

public class Command {

    Command(String command) {
        String[] commandString = command.split(" ");

        processCommandType(commandString[0]);

        if (commandType != CommandType.ERROR && commandString.length > 1) {
            args = new String[commandString.length - 1];
            for (int i = 1; i < commandString.length; ++i) {
                args[i - 1] = commandString[i];
            }
        }
        else {
            args = new String[0];
        }
    }

    private void processCommandType(String command) {
        switch(command) {
            case "!quit":
                commandType = CommandType.QUIT;
                break;
            case "!login":
                commandType = CommandType.LOGIN;
                break;
            case "!logoff":
                commandType = commandType.LOGOFF;
                break;
            default:
                commandType = commandType.ERROR;
                break;
        }
    }

    private CommandType commandType;
    private String[] args;

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getArgs() {
        return args;
    }
}
