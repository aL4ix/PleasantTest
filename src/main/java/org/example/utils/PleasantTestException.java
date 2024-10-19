package org.example.utils;

import java.io.File;

public class PleasantTestException extends RuntimeException {
    public PleasantTestException(File file, int lineNum, String fmt, Object ... args) {
        super(file != null ? fmt.formatted(args)+". %s:%d".formatted(file, lineNum): fmt.formatted(args));
    }
}
