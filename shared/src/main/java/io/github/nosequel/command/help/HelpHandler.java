package io.github.nosequel.command.help;

import io.github.nosequel.command.data.impl.BaseCommandData;

public interface HelpHandler {

    /**
     * Get the help message to send to a player for help message
     *
     * @param commandData the command data to get the help data from
     * @return the help message to send
     */
    String getHelpMessage(BaseCommandData commandData);

}
