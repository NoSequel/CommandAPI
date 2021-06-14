package io.github.nosequel.command.executor;

public interface CommandExecutor {

    /**
     * Send a message to an executor
     *
     * @param message the message to send
     */
    void sendMessage(String message);

    /**
     * Check if the executor has a permission
     *
     * @param permission the permission to check for
     * @return whether the executor has the permission
     */
    boolean hasPermission(String permission);

    /**
     * Check if the executor is a user, instead of the console/terminal.
     *
     * @return whether the executor is a user or not
     */
    boolean isUser();

}
