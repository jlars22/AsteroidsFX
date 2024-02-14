package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The `IGamePluginService` interface is used to describe the start and stop logic for game plugins.
 * <p>
 * Implementations of this interface have to implement the `start` and `stop` methods,
 * which are called when the game starts or stops.
 */
public interface IGamePluginService {

    /**
     * This is used to start the game plugin, and do any needed setup (e.g. spawn the player entity).
     *
     * @param gameData The gameData object that contains all relevant information about the game state.
     * @param world    The world object containing all present entities in the game.
     *
     *                 <p>Pre-condition: gameData and world can not be null.
     *                 <p>Post-condition: The game plugin has started, entities in the world have been updated,
     *                 that is entities have been added, removed etc.
     */
    void start(GameData gameData, World world);

    /**
     * This is used to stop the game plugin, and do any needed cleanup (e.g. remove the player entity).
     *
     * @param gameData The gameData object that contains all relevant information about the game state.
     * @param world    The world object containing all present entities in the game.
     *
     *                 <p>Pre-condition: gameData and world can not be null.
     *                 <p>Post-condition: The game plugin has stopped, entities in the world have been updated,
     *                 that is entities might have removed or gameData might have been updated.
     */
    void stop(GameData gameData, World world);
}
