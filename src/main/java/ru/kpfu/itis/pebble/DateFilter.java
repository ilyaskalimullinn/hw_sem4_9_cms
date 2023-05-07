package ru.kpfu.itis.pebble;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateFilter implements Filter {

    private final List<String> argumentNames;

    @Override
    public Object apply(Object o, Map<String, Object> args, PebbleTemplate pebbleTemplate, EvaluationContext evaluationContext, int i) throws PebbleException {
        if (!(o instanceof Date)) {
            throw new IllegalArgumentException("Wrong argument type, line" + i);
        }
        Date date = (Date) o;
        Date currentDate = (Date) args.get("currentDate");
        long difference = currentDate.getTime() - date.getTime();

        if (difference < 60*1000) {
            return "A moment ago";
        }else if (difference < 2*60*1000) {
            return "A minute ago";
        } else if (difference < 60*60*1000) {
            return String.format("%d minutes ago", (difference / (60*1000)));
        } else if (difference < 24*60*60*1000) {
            return difference / (60*60*1000) + " hours ago";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            return dateFormat.format(date);
        }
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    public DateFilter() {
        argumentNames = new ArrayList<>();
        argumentNames.add("date");
        argumentNames.add("currentDate");
    }
}
