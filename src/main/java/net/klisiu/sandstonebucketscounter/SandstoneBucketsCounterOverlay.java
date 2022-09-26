package net.klisiu.sandstonebucketscounter;

import java.awt.*;
import javax.inject.Inject;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class SandstoneBucketsCounterOverlay extends OverlayPanel
{
	private final SandstoneBucketsCounterPlugin plugin;
	private final SandstoneBucketsCounterConfig sandstoneBucketsCounterConfig;

	private static final Font BOLD_FONT = FontManager.getRunescapeBoldFont();
	private static final Font NORMAL_FONT = FontManager.getRunescapeFont();

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

		graphics.setFont(BOLD_FONT);
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Buckets of sand")
			.color(Color.ORANGE)
			.build());

		graphics.setFont(NORMAL_FONT);

		int inventoryCount = plugin.getInventoryCount() > 0 ? plugin.getInventoryCount() : 0;
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Inventory:")
			.right(Integer.toString(inventoryCount))
			.build());

		int grinderCount = plugin.getGrinderCount() > 0 ? plugin.getGrinderCount() : 0;
		panelComponent.getChildren().add(LineComponent.builder()
				.left("Grinder:")
				.right(Integer.toString(grinderCount))
				.build());


		return super.render(graphics);
	}
}
