package com.optum.pure.trackingstore;

import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;

import java.io.IOException;
import java.util.List;

/**
 * Interface for TrackingStore
 *
 * @author Nandu
 */
public interface TrackingStore {

    /**
     * Retrieves the Tracking Status based on the provided TrackingId.
     *
     * @param trackingId the ID used to fetch the tracking status
     * @return TrackingStatus object created using the corresponding TrackingRecord
     * @throws IOException if there's an error during the tracking store query
     */
    TrackingStatus getTrackingStatus(String trackingId) throws IOException;

    /**
     * Retrieves the full Tracking Record for the given TrackingId.
     *
     * @param trackingId the ID used to fetch the tracking record
     * @return TrackingRecord object
     * @throws IOException if there's an error during the tracking store query
     */
    TrackingRecord getTrackingRecord(String trackingId) throws IOException;

    /**
     * Inserts a new TrackingRecord into the TrackingStore.
     *
     * @param trackingRecord the record to be inserted
     * @throws IOException if the insertion fails
     */
    void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException;

    /**
     * Updates multiple fields in the TrackingRecord for the given TrackingId.
     *
     * @param trackingId the ID of the record to be updated
     * @param fields     a list of field names to update
     * @param values     a list of corresponding field values
     * @throws IOException if the update fails
     */
    void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException;
}

//=================================================Future Proof Code java 21===========================

package com.optum.pure.trackingstore;

import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;

import java.io.IOException;
import java.util.List;

/**
 * Java 21 upgraded sealed interface for TrackingStore.
 * Sealed interfaces enforce strict inheritance, improving design safety and clarity.
 */
public sealed interface TrackingStore
        permits com.optum.pure.trackingstore.impl.ESTrackingStore { // Explicitly restricts implementation to known class

    /**
     * Get the tracking status from the store using the trackingId.
     *
     * @param trackingId the ID of the record
     * @return the TrackingStatus object
     * @throws IOException if any error occurs while fetching from the store
     */
    TrackingStatus getTrackingStatus(String trackingId) throws IOException;

    /**
     * Retrieve a full TrackingRecord using the trackingId.
     *
     * @param trackingId the ID of the record
     * @return the TrackingRecord object
     * @throws IOException if any error occurs during lookup or parsing
     */
    TrackingRecord getTrackingRecord(String trackingId) throws IOException;

    /**
     * Insert a new TrackingRecord into the store.
     *
     * @param trackingRecord the record to insert
     * @throws IOException if the operation fails
     */
    void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException;

    /**
     * Update fields in an existing tracking record.
     *
     * @param trackingId the record ID
     * @param fields     list of field names
     * @param values     corresponding list of values
     * @throws IOException if the update fails
     */
    void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException;
}

| Feature            | Description                                                                   | Benefit                                                                         |
| ------------------ | ----------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| `sealed interface` | Restricts which classes can implement this interface (`ESTrackingStore` only) | Enforces control over the class hierarchy; improves security and predictability |
| `permits` clause   | Explicitly declares the permitted implementation class                        | Avoids accidental or rogue implementations                                      |
| Enhanced Javadoc   | Descriptive parameter and exception documentation                             | Improves maintainability and auto-generated docs                                |

================================================Java 21 Code=================================================>
package com.optum.pure.trackingstore;

import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for TrackingStore
 *
 * Java 21 Modernization:
 * - Use Optional for potentially missing data instead of returning null (for `getTrackingRecord`).
 * - Improved JavaDoc.
 * - Use `var` in examples, but not in interfaces (var not allowed in method signatures).
 * - Suggest using records and sealed interfaces where appropriate.
 */
public interface TrackingStore {

    /**
     * Gets the Tracking Status from Tracking store based on trackingId.
     * Never returns null.
     *
     * @param trackingId The trackingId for which the status is fetched.
     * @return TrackingStatus for the provided trackingId.
     * @throws IOException If querying Tracking Store fails.
     */
    TrackingStatus getTrackingStatus(String trackingId) throws IOException;

    /**
     * Returns the TrackingRecord for a given trackingId.
     * Returns Optional.empty() if not found, avoiding nulls.
     *
     * @param trackingId The trackingId for which the record is fetched.
     * @return Optional of TrackingRecord.
     * @throws IOException If querying Tracking Store fails.
     */
    Optional<TrackingRecord> getTrackingRecord(String trackingId) throws IOException;

    /**
     * Inserts a TrackingRecord into the TrackingStore.
     *
     * @param trackingRecord The record to insert.
     * @throws IOException If the insert fails.
     */
    void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException;

    /**
     * Updates a list of fields in TrackingStore.
     *
     * @param trackingId The ID of the record to update.
     * @param fields The list of field names to update.
     * @param values The new values for the fields.
     * @throws IOException If the update fails.
     */
    void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException;
}
