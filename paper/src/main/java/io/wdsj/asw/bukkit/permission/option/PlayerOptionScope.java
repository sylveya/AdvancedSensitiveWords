package io.wdsj.asw.bukkit.permission.option;

import java.lang.ScopedValue;
import java.util.Objects;

/**
 * Binds a player's resolved options to one synchronous filtering operation.
 * Bindings are lexical and are not implicitly propagated through arbitrary executors.
 */
public final class PlayerOptionScope {
    private static final ScopedValue<PlayerOptionView> CURRENT = ScopedValue.newInstance();

    private PlayerOptionScope() {
    }

    /**
     * Runs an operation with the supplied options visible to nested filtering calls.
     */
    public static void run(PlayerOptionView options, Runnable action) {
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(action, "action");
        ScopedValue.where(CURRENT, options).run(action);
    }

    /**
     * Returns the options bound to the current operation, or {@code null} outside a player scope.
     */
    public static PlayerOptionView currentOrNull() {
        return CURRENT.isBound() ? CURRENT.get() : null;
    }
}
