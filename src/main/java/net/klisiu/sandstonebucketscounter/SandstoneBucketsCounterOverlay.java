package net.klisiu.sandstonebucketscounter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class SandstoneBucketsCounterOverlay extends OverlayPanel
{
	private final SandstoneBucketsCounterPlugin plugin;
	private final SandstoneBucketsCounterConfig sandstoneBucketsCounterConfig;

	@Inject
	private SandstoneBucketsCounterOverlay(SandstoneBucketsCounterPlugin plugin, SandstoneBucketsCounterConfig sandstoneBucketsCounterConfig)
	{
		super(plugin);
		this.plugin = plugin;
		this.sandstoneBucketsCounterConfig = sandstoneBucketsCounterConfig;
		setPosition(OverlayPosition.TOP_LEFT);
		getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Sandstone Buckets Counter Overlay"));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isInDesertQuarry() || !this.sandstoneBucketsCounterConfig.showOverlay())
		{
			return null;
		}

		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Sandstones mining")
			.color(Color.ORANGE)
			.build());

		int inventoryCount = plugin.getInventoryCount() > 0 ? plugin.getInventoryCount() : 0;
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Buckets of sand:")
			.right(Integer.toString(inventoryCount))
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("(inventory)")
			.build());

		panelComponent.getChildren().add(TitleComponent.builder()
				.text("Grinder")
				.color(Color.ORANGE)
				.build());

		int grinderCount = plugin.getGrinderCount() > 0 ? plugin.getGrinderCount() : 0;
		panelComponent.getChildren().add(LineComponent.builder()
				.left("Buckets of sand:")
				.right(Integer.toString(grinderCount))
				.build());


		return super.render(graphics);
	}
}
