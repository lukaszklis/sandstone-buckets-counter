package net.klisiu.sandstonebucketscounter;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import static net.runelite.api.ItemID.SANDSTONE_10KG;
import static net.runelite.api.ItemID.SANDSTONE_1KG;
import static net.runelite.api.ItemID.SANDSTONE_2KG;
import static net.runelite.api.ItemID.SANDSTONE_5KG;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Sandstone Buckets Counter",
	description = "Show helpful counter of how many buckets of sand in sandstones player has in their inventory.",
	tags = {"sandstone", "desert quarry", "quarry", "bucket of sand"}
)
@Slf4j
public class SandstoneBucketsCounterPlugin extends Plugin
{
	private static final int DESERT_QUARRY_REGION = 12589;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SandstoneBucketsCounterOverlay overlay;

	@Getter(AccessLevel.PACKAGE)
	private int inventoryCount;

	@Getter(AccessLevel.PACKAGE)
	private boolean isInDesertQuarry;

	@Override
	protected void startUp() throws Exception
	{
		reset();
		overlayManager.add(overlay);
		clientThread.invokeLater(() ->
		{
			final ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);

			if (container != null)
			{
				calculateInventory(container.getItems());
			}
		});
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		reset();
	}

	private void reset()
	{
		inventoryCount = 0;
	}

	private boolean isInDesertQuarryRegion()
	{
		if (client.getLocalPlayer() != null)
		{
			return client.getLocalPlayer().getWorldLocation().getRegionID() == DESERT_QUARRY_REGION;
		}

		return false;
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		if (!isInDesertQuarryRegion())
		{
			if (isInDesertQuarry)
			{
				log.debug("Left Desert Quarry region");
				reset();
			}

			isInDesertQuarry = false;

			return;
		}

		if (!isInDesertQuarry)
		{
			reset();
			log.debug("Entered Desert Quarry region");
		}

		isInDesertQuarry = true;
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		final ItemContainer container = event.getItemContainer();

		if (!isInDesertQuarry || container != client.getItemContainer(InventoryID.INVENTORY))
		{
			return;
		}

		calculateInventory(container.getItems());
	}

	@Provides
	SandstoneBucketsCounterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SandstoneBucketsCounterConfig.class);
	}

	private void calculateInventory(Item[] inv)
	{
		inventoryCount = 0;

		for (Item item : inv)
		{
			inventoryCount += getBucketsPotentialCount(item.getId());
		}
	}

	private static int getBucketsPotentialCount(int id)
	{
		switch (id)
		{
			case SANDSTONE_1KG:
				return 1;
			case SANDSTONE_2KG:
				return 2;
			case SANDSTONE_5KG:
				return 4;
			case SANDSTONE_10KG:
				return 8;
			default:
				return 0;
		}
	}
}
