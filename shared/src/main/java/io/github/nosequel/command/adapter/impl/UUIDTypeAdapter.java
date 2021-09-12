package io.github.nosequel.command.adapter.impl;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

import java.util.UUID;

public class UUIDTypeAdapter implements TypeAdapter<UUID> {

    /**
     * Convert a {@link String} to the {@link UUID} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public UUID convert(CommandExecutor executor, String source) throws Exception {
        return UUID.fromString(source);
    }
}
