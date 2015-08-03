package net.toeach.common.utils;

import de.greenrobot.event.EventBus;

/**
 * Maintains a singleton instance for obtaining the mBus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 */
public final class BusProvider {
    private static final EventBus bus = EventBus.getDefault();

    private BusProvider() {
    }

    public static EventBus getInstance() {
        return bus;
    }
}
