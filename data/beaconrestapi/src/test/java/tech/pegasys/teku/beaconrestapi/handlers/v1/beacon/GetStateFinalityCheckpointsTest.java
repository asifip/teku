/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.beaconrestapi.handlers.v1.beacon;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import tech.pegasys.teku.beaconrestapi.AbstractBeaconHandlerTest;
import tech.pegasys.teku.infrastructure.async.SafeFuture;
import tech.pegasys.teku.infrastructure.restapi.endpoints.RestApiRequest;
import tech.pegasys.teku.spec.datastructures.metadata.StateAndMetaData;
import tech.pegasys.teku.spec.datastructures.state.beaconstate.BeaconState;
import tech.pegasys.teku.spec.util.DataStructureUtil;

public class GetStateFinalityCheckpointsTest extends AbstractBeaconHandlerTest {
  private final DataStructureUtil dataStructureUtil = new DataStructureUtil(spec);

  @Test
  public void shouldReturnFinalityCheckpointsInfo() throws Exception {
    final GetStateFinalityCheckpoints handler = new GetStateFinalityCheckpoints(chainDataProvider);
    final BeaconState beaconState = dataStructureUtil.randomBeaconState();
    final StateAndMetaData stateAndMetaData =
        new StateAndMetaData(beaconState, spec.getGenesisSpec().getMilestone(), false, false, true);
    when(chainDataProvider.getBeaconStateAndMetadata(eq("head")))
        .thenReturn(SafeFuture.completedFuture(Optional.of(stateAndMetaData)));
    when(context.pathParamMap()).thenReturn(Map.of("state_id", "head"));
    RestApiRequest request = new RestApiRequest(context, handler.getMetadata());

    handler.handleRequest(request);

    String expected =
        String.format(
            "{\"data\":{\"previous_justified\":{\"epoch\":\"%s\",\"root\":\"%s\"},\"current_justified\":"
                + "{\"epoch\":\"%s\",\"root\":\"%s\"},\"finalized\":{\"epoch\":\"%s\",\"root\":\"%s\"}}}",
            beaconState.getPreviousJustifiedCheckpoint().getEpoch(),
            beaconState.getPreviousJustifiedCheckpoint().getRoot(),
            beaconState.getCurrentJustifiedCheckpoint().getEpoch(),
            beaconState.getCurrentJustifiedCheckpoint().getRoot(),
            beaconState.getFinalizedCheckpoint().getEpoch(),
            beaconState.getFinalizedCheckpoint().getRoot());
    AssertionsForClassTypes.assertThat(getResultString()).isEqualTo(expected);
  }
}
