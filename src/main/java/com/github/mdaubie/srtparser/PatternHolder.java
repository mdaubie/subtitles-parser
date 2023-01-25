package com.github.mdaubie.srtparser;

import com.github.mdaubie.srtparser.model.PatternedObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PatternHolder extends HashMap<Class<? extends PatternedObject>, Pattern> {
    private static final PatternHolder instance = new PatternHolder();

    private PatternHolder() {
        super();
    }

    public static Pattern getPattern(Class<? extends PatternedObject> patternedObjectClass) {
        if (!instance.containsKey(patternedObjectClass)) {
            try {
                PatternedObject patternedObject = patternedObjectClass.getConstructor().newInstance();
                instance.put(patternedObjectClass, patternedObject.getPattern());
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
        return instance.getOrDefault(patternedObjectClass, null);
    }
}
