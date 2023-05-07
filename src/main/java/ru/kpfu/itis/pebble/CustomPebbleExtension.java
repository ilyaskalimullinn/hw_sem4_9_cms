package ru.kpfu.itis.pebble;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;

import java.util.HashMap;
import java.util.Map;

public class CustomPebbleExtension extends AbstractExtension{
    private final Map<String, Filter> filters;

    public CustomPebbleExtension() {
        filters = new HashMap<>();
        filters.put("date_filter", new DateFilter());
    }

    @Override
    public Map<String, Filter> getFilters() {
        return filters;
    }
}
