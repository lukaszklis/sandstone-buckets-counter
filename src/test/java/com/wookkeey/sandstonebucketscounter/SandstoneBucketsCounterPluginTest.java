package com.wookkeey.sandstonebucketscounter;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SandstoneBucketsCounterPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SandstoneBucketsCounterPlugin.class);
		RuneLite.main(args);
	}
}
