package code.kofi.mcp.service.command;

public interface Command<T> {
    T execute();
}