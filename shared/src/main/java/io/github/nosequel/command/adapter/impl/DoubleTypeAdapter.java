package io.github.nosequel.command.adapter.impl;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;

public class DoubleTypeAdapter implements TypeAdapter<Double> {

    /**
     * Convert a {@link String} to the {@link Double} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public Double convert(CommandExecutor executor, String source) throws Exception {
        return Double.parseDouble(source);
    }
}
