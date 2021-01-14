package net.klisiu.sandstonebucketscounter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("sandstonebucketscounter")
public interface SandstoneBucketsCounterConfig extends Config
{
	@ConfigItem(
		keyName = "showOverlay",
		name = "Show Overlay",
		description = "Toggles the counter overlay"
	)
	default boolean showOverlay()
	{
		return true;
	}
}
