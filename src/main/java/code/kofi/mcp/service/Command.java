package code.kofi.mcp.service;

public interface Command<T> {
    T execute();
}