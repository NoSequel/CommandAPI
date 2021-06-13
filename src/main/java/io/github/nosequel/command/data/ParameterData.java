package io.github.nosequel.command.data;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.annotation.Param;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;

@Getter
@RequiredArgsConstructor
public class ParameterData {

    private final TypeAdapter<?> adapter;
    private final Parameter parameter;
    private final Param param;

    private final boolean isLastIndex;

    /**
     * Get the default value of the parameter
     *
     * @return the default value
     */
    public String getDefaultValue() {
        if(this.param != null && !this.param.value().isEmpty()) {
            return this.param.value();
        }

        return null;
    }

    public boolean isString() {
        return this.parameter.getType().equals(String.class);
    }

}