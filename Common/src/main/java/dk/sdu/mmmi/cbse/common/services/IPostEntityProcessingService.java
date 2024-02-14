package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author jcs
 * The `IPostEntityProcessingService` interface is used to describe the post-processing logic for all types of entities.
 * <p>
 * Implementations of this interface have to implement the `process` method,
 * which is called each game update cycle after all `IEntityProcessingServices` have been processed.
 */


public interface IPostEntityProcessingService {

    /**
     * Process game data and world entities after all `IEntityProcessingServices` have been processed.
     *
     * @param gameData The gameData object that contains all relevant information about the game state.
     * @param world    The world object containing all present entities in the game.
     *
     *                 <p>Pre-condition: gameData and world can not be null.
     *                 <p>Post-condition: Entities in the world have been updated,
     *                 that is entities might have been added, removed etc.
     */
    void process(GameData gameData, World world);
}
