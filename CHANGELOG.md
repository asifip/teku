# Changelog

## Upcoming Breaking Changes
- The `/teku/v1/beacon/states/:state_id` endpoint has been deprecated in favor of the standard API `/eth/v1/debug/beacon/states/:state_id` which now returns the state as SSZ when the `Accept: application/octet-stream` header is specified on the request.
- The `/eth/v1/debug/beacon/states/:state_id` endpoint has been deprecated in favor of the v2 Altair endpoint `/eth/v2/debug/beacon/states/:state_id`
- The `/eth/v1/beacon/blocks/:block_id` endpoint has been deprecated in favor of the v2 Altair endpoint `/eth/v2/beacon/blocks/:block_id`
- The `/eth/v1/validator/blocks/:slot` endpoint has been deprecated in favor of the v2 Altair endpoint `/eth/v2/validator/blocks/:slot`
- The `-jdk14` and `-jdk15` docker image variants will be removed in a future release. JDK 14 and 15 are no longer receiving security updates from upstream vendors.
  Note that the default docker image usage JDK 16 and is still receiving security updates.
- The commandline option `--validators-performance-tracking-enabled` has been deprecated in favour of `--validators-performance-tracking-mode`
- The commandline option `--network` of the `validator-client` subcommand introduces a new option value `auto`, which automatically 
fetches network configuration information from the configured beacon node endpoint. Other `--network` option values for an external validator client 
 are now deprecated in favour of this option value (usage: `teku validator-client --network=auto`)
 
## Current Releases
For information on changes in released versions of Teku, see the [releases page](https://github.com/ConsenSys/teku/releases).

## Unreleased Changes

### Additions and Improvements
- Sets Altair fork epoch for MainNet to 74240
- Upgraded to BLST 0.3.5.
- `/teku/v1/admin/readiness` endpoint now accepts a `target_peer_count` param to require a minimum number of peers before the node is considered ready.
- Support for building on JDK 17.


### Bug Fixes
- Fix `NoSuchElementException` reported at startup by validator performance tracking module. 
- Fix `IllegalStateException` reported on altair networks, when none of your validators are in a sync committee for the current epoch.
- Fix failed to export/import slashing protection data.
- Fix incorrect gossip validation when processing duplicate sync committee messages which could lead to an error being reported instead of ignoring the duplicate.