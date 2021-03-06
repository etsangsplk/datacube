5.1.3
=====
- Bug fix the batch increment method

5.1.2
=====
- Sort rollups before creating metrics for them, so different jvms create them
  in the same order
- precreate metrics (with zero counts) for each rollup

5.1.1
=====
- Move writes meter to writeAsync(Batch batch), which is always called, as opposed to writeAsync(WriteBuilder, Op),
  which clients may choose not to call.

5.1.0
=====
- Add per-rollup metrics to DataCubeIo
- Due to possible high cardinality, DataCubeIo constructors now require a boolean
  controlling whether those metrics are maintained
- New constructor for HBaseIdService
- Remove DataCubeIo constructor missing metrics scope

5.0.0
=====
- Bump to java 1.8.
- Move from gauava to java optionals
- Format every file according to intellij auto format.
- Remove commented out code
- Remove the "hacklog"
- Remove unnecessary public and static declarations from enums and interfaces.
- Remove unused parameters and classes
- Add a batch increment operation, give the db harness a shutdown method.
- New constructor for HBaseIdService

4.1.1
=====
- Use a single thread pool for async id service lookups
- Make BoxedByteArray serializable

4.1.0
=====
- Execute id service lookups for multi-gets in a thread pool

4.0.5
=====
- Timer around entire multi-get reads

4.0.4
=====
- Metrics for id services in multi-get

4.0.3
=====
- Naive implementation of multiget for the map db harness.

4.0.2
=====
- Add metrics around retrying after IOExceptions when flushing to hbase
  and apply some jittered backoff as retry attempts increase.

4.0.1
=====
- Removes `bufferpool`, `garbagecollector`, and `memorypool` metrics from static `Metrics` registry. 
  If clients would like these metrics to be reported on they can be added manually.

4.0.0
=====
- Modifies how JmxReporter does object naming.

3.0.0
=====
- Updates metrics library to io.dropwizard.metrics instead of com.yammer.metrics. This will
cause slight changes in the naming of metrics. Fixes bug where a checkAndPut
operation checking for absence of a cell value is done with an empty array instead of
a null value.

2.0.0
=====
- Non compatible update with existing data cubes.  The DataCube constructor in
previous versions with the useAddressPrefixByteHash parameter set to true is
compatible with PREFIX_MODE.NO_ADDRESS_PREFIX, but is not compatible with the
new mode PREFIX_MODE.MOD_ADDRESS_PREFIX because there was a bug since 1.4.0
that didn't implement this feature correctly.

1.5.0
=====
- Prevent getId calls from creating a new identifier
- Add a getOrCreateId Method to the idmapper interface
- Optimize id creation.

1.4.0
=====
- Add functionality to optionally add a hash in front of row keys, which permits
users to ignore dimension order when considering performance.

1.3.0
=====
- Add on-success callback to the HBaseDbharness
- Update many dependencies and increase minor version, 1.2.x -> 1.3.0
- Support multiGet
- Fix bug in Cas operations
- Add Double op
- Name the flusher threads better.
- Allow for larger caches that will invalidate under memory pressure.
- Expose cache metrics.
- Redesign bucketer system for multiple buckets per dimension
- Add release notes
- Add instructions/workflow for doing a release
- Add worked example about counting tweets, initial simple version
- Include tests as a build artifact

1.2.3
=====
- Deprecated RollupFilter in favor of low-level Address/Batch access
- Bug fix for overcounting in hbase backfill merger when cubes share a table
- Bug fix for EnumSerializable returning incorrect values
- Nullable dimensions
- Add week and year buckets to HourDayMonthBucketer

