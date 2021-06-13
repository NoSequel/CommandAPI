package io.github.nosequel.command.data;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.annotation.Param;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Parameter;

@Getter
@RequiredArgsConstructor
public class ParameterData {

    private final TypeAdapter<?> adapter;
    private final Parameter parameter;
    private final Param param;

    private final boolean isLastIndex;

    /**
     * Constructor to make a new parameter data object
     *
     * @param adapter     the adapter to convert the values with
     * @param parameter   the parameter object the data is for
     * @param isLastIndex whether the data is the last index in the method
     */
    public ParameterData(TypeAdapter<?> adapter, Parameter parameter, boolean isLastIndex) {
        this.adapter = adapter;
        this.parameter = parameter;
        this.isLastIndex = isLastIndex;

        this.param = parameter.getAnnotation(Param.class);
    }

    /**
     * Get the name of the parameter
     *
     * @return the name
     */
    public String getParameterName() {
        return this.param == null
                ? this.parameter.getName()
                : this.param.name();
    }

    /**
     * Get the default value of the parameter
     *
     * @return the default value
     */
    public String getDefaultValue() {
        return this.param != null && !this.param.value().isEmpty()
                ? this.param.value()
                : null;
    }

    public boolean isString() {
        return this.parameter.getType().equals(String.class);
    }

}