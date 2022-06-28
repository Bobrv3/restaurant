package com.epam.restaurant.controller.command;

import com.epam.restaurant.controller.command.impl.AddToOrder;
import com.epam.restaurant.controller.command.impl.Authorization;
import com.epam.restaurant.controller.command.impl.ChangeLocale;
import com.epam.restaurant.controller.command.impl.FindDishesBy;
import com.epam.restaurant.controller.command.impl.GetCategories;
import com.epam.restaurant.controller.command.impl.GetMenu;
import com.epam.restaurant.controller.command.impl.NoNameCommand;
import com.epam.restaurant.controller.command.impl.PlaceOrder;
import com.epam.restaurant.controller.command.impl.Registration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);
    private final Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider () {
        repository.put(CommandName.AUTHORIZATION, new Authorization());
        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.NONAME_COMMAND, new NoNameCommand());
        repository.put(CommandName.CHANGE_LOCALE, new ChangeLocale());
        repository.put(CommandName.GET_CATEGORIES, new GetCategories());
        repository.put(CommandName.GET_MENU, new GetMenu());
        repository.put(CommandName.FIND_DISHES_BY, new FindDishesBy());
        repository.put(CommandName.ADD_TO_ORDER, new AddToOrder());
        repository.put(CommandName.PLACE_ORDER, new PlaceOrder());
    }

    public Command getCommand(String name) {
        Command command = null;

        try {
            CommandName commandName = CommandName.valueOf(name);
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.info("There is no command with that name...", e);
            command = repository.get(CommandName.NONAME_COMMAND);
        }
        return command;
    }
}
