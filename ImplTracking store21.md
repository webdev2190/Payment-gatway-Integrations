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
