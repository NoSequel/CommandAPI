package io.github.nosequel.command.executor;

public interface CommandExecutor {

    /**
     * Send a message to an executor
     *
     * @param message the message to send
     */
    void sendMessage(String message);

}
