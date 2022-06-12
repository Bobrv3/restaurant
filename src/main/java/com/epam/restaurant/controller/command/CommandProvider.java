package com.epam.restaurant.controller.command;

import com.epam.restaurant.controller.command.impl.AuthorizationPage;
import com.epam.restaurant.controller.command.impl.RegistrationPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);
    private final Map<CommandName, Command> repository = new HashMap<>();

    // TODO синглтон?
    public CommandProvider () {
        repository.put(CommandName.AUTHORIZATION_PAGE, new AuthorizationPage());
        repository.put(CommandName.REGISTRATION_PAGE, new RegistrationPage());
    }

    public Command getCommand(String name) {
        Command command = null;

        try {
            CommandName commandName = CommandName.valueOf(name);
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.error("There is no command with that name...", e);
            // TODO
        }
        return command;
    }
}
