package io.github.nosequel.command.adapter.impl;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

public class BooleanTypeAdapter implements TypeAdapter<Boolean> {

    /**
     * Convert a {@link String} to the {@link Boolean} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public Boolean convert(CommandExecutor executor, String source) throws Exception {
        return Boolean.parseBoolean(source);
    }
}
