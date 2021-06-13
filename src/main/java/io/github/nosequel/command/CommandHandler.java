package io.github.nosequel.command;

import io.github.nosequel.command.adapter.TypeAdapter;
import lombok.Getter;

public class CommandHandler {

    @Getter
    private static CommandHandler commandHandler;

    public TypeAdapter<?> findTypeAdapter(Class<?> clazz) {
        return null;
    }

}
