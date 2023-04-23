package utils;

@FunctionalInterface
public interface Func<FuncArgs> {
    void invoke(FuncArgs args);
}