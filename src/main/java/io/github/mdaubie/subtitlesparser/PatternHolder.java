package io.github.mdaubie.subtitlesparser;

import io.github.mdaubie.subtitlesparser.model.PatternedObject;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * A class registering and distributing the model patterns
 */
public class PatternHolder extends HashMap<Class<? extends PatternedObject>, Pattern> {
    private static final PatternHolder instance = new PatternHolder();

    private PatternHolder() {
        super();
    }

    /**
     * Map the class of a patterned object to its corresponding pattern
     * @param patternedObjectClass The class of the patterned object
     * @return The corresponding pattern
     */
    public static Pattern getPattern(Class<? extends PatternedObject> patternedObjectClass) {
        if (!instance.containsKey(patternedObjectClass)) {
            try {
                PatternedObject patternedObject = patternedObjectClass.getConstructor().newInstance();
                instance.put(patternedObjectClass, patternedObject.getPattern());
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
        return instance.get(patternedObjectClass);
    }
}
