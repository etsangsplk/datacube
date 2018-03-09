/*
Copyright 2012 Urban Airship and Contributors
*/

package com.urbanairship.datacube;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * A service for translating byte[]s consistently into unique IDs. If the given byte[] has been
 * seen before, the same identifier will be returned as the previous call. If this is the first
 * time byte[] has been seen, a new identifier will be assigned.
 *
 * Different identifier lengths can be used to save space. For example, if you have a dimension
 * that stores a boolean, it can only have two distinct values, so you only need one byte to encode
 * datapoints in that dimension. On the other hand, to store the name of a city, you would need
 * more bytes to encode it since there are many cities.
 *
 * Implementations can assume that numIdBytes will never change over the life of a cube for a
 * given dimension.
 *
 * Implementations are required to be thread safe. Also, implementations must assume that other JVMs
 * will be sharing the underlying database, so some kind of external concurrency control is required
 * when assigning IDs. It's not OK for the same input to be translated differently; if a
 * particular input translates into a particular unique ID once, it must translate in the same
 * way everywhere else forever. Also, a unqiue ID must never be reused for different inputs. This
 * means you must use locks, transactions, or compare-and-swap on your database when assigning
 * IDs.
 */

public interface IdService {
    /**
     * create an id for the value if it doesn't exist
     *
     * @param dimensionNum dimension index-- e.g. this is an id for the fifth dimension of our rollup. Helps avoid
     *                     collisions
     * @param input        The input bytes for which we're establishing an id.
     * @param numIdBytes   The number of bytes to reserve in the destination table for the value
     *
     * @return the short identifier to use in place of the {@param input}
     *
     * @throws IOException
     * @throws InterruptedException
     */
    byte[] getOrCreateId(int dimensionNum, byte[] input, int numIdBytes) throws IOException, InterruptedException;

    Optional<byte[]> getId(int dimensionNum, byte[] input, int numIdBytes) throws IOException, InterruptedException;

    /**
     * @param dimensionNum the index of the dimension for which we're retrieving the value
     * @param id           the short id found in the hbase row.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    Optional<byte[]> getValueForId(int dimensionNum, byte[] id) throws IOException, InterruptedException;

    /**
     * Utilities to make implementation of IdService easier.
     */
    class Validate {
        /**
         * @throws IllegalArgumentException if dimensionNum is ridiculously large or <0.
         */
        public static void validateDimensionNum(int dimensionNum) {
            if (dimensionNum > Short.MAX_VALUE) {
                throw new IllegalArgumentException("More than " + Short.MAX_VALUE +
                        " dimensions are not supported, saw " + dimensionNum);
            } else if (dimensionNum < 0) {
                throw new IllegalArgumentException("dimensionNum should be non-negative, saw " +
                        dimensionNum);
            }
        }

        public static void validateNumIdBytes(int numIdBytes) {
            if (numIdBytes <= 0 || numIdBytes > 7) {
                throw new IllegalArgumentException("Strange numIdBytes key " + numIdBytes);
            }
        }
    }
}
