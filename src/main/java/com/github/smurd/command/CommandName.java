package com.github.smurd.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("noCommand"),
    STAT("/stat"),
    ADD_DRUMMER_SUB("/adddrummersub"),
    LIST_DRUMMER_SUB("/listdrummersub");

    private final String commandName;
    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
