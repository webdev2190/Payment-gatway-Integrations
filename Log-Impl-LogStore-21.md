package com.optum.pure.logstore;

import com.optum.pure.model.requestobjects.common.LogRecord;
import java.io.IOException;

/**
 * Java 21 modernized LogStore interface.
 * - Javadoc is present for clarity and documentation tools.
 * - Marked as @FunctionalInterface since there is only one abstract method.
 * - Ready for lambdas or method references if needed.
 */
@FunctionalInterface
public interface LogStore {

    /**
     * Inserts a LogRecord into the LogStore.
     * 
     * @param logRecord The log record to insert.
     * @throws IOException If there is an I/O error while storing the log record.
     */
    void insertLogRecord(LogRecord logRecord) throws IOException;

    // Java 21+ allows static or default methods if you need utility or fallback logic.
    // Example (optional, not required):
    // default void safeInsert(LogRecord logRecord) {
    //     try {
    //         insertLogRecord(logRecord);
    //     } catch (IOException e) {
    //         // Handle/log as needed
    //     }
    // }
}
