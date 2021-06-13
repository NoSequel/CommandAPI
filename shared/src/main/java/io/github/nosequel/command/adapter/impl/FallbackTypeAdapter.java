package io.github.nosequel.command.adapter.impl;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

public class FallbackTypeAdapter implements TypeAdapter<String> {

    /**
     * Convert a {@link String} to the {@link String} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public String convert(CommandExecutor executor, String source) throws Exception {
        return source;
    }
}
