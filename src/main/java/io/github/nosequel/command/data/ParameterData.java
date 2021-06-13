package io.github.nosequel.command.data;

import io.github.nosequel.command.adapter.TypeAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;

@Getter
@RequiredArgsConstructor
public class ParameterData {

    private final TypeAdapter<?> adapter;
    private final Parameter parameter;

    private final boolean isLastIndex;

    public boolean isString() {
        return this.parameter.getType().equals(String.class);
    }

    public boolean isPlayer() {
        return this.parameter.getType().equals(Player.class);
    }

}