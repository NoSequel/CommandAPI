package io.github.nosequel.command.adapter.impl;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {

    /**
     * Convert a {@link String} to the {@link Integer} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public Integer convert(CommandExecutor executor, String source) throws Exception {
        return Integer.parseInt(source);
    }
}
