package ZerzeraRE.common;

import java.io.File;
import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

import ZerzeraRE.common.core.CommonProxy;
import ZerzeraRE.common.core.ConfigurationSettings;
import ZerzeraRE.common.core.Version;
import ZerzeraRE.common.block.ModBlocks;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.network.PacketHandler;

@Mod(modid=DefaultProps.MOD_ID, version=Version.VERSION, useMetadata = false, name=DefaultProps.FULL_MOD_NAME)
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={DefaultProps.NET_CHANNEL_NAME}, packetHandler = PacketHandler.class)

public class ZerzeraRE {
	
	public static boolean debug 		= false;
	public static boolean modifyWorld 	= false;
	
	public static Logger log = Logger.getLogger(DefaultProps.MOD_ID);
	
	public static ConfigurationSettings REConf;
	
	@Instance("ZerzeraRE")
	public static ZerzeraRE instance;
	
	@SidedProxy(clientSide="ZerzeraRE.client.core.ClientProxy", serverSide="ZerzeraRE.common.core.CommonProxy")
	public static CommonProxy proxy;
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {}
	
	@PreInit
	public void initialize(FMLPreInitializationEvent evt) {
		Version.versionCheck();

		log.setParent(FMLLog.getLogger());
		log.info("Starting " + DefaultProps.FULL_MOD_NAME + " " + Version.getVersion());
		log.info("Copyright (c) Zerzera, 2012");
		
		REConf = new ConfigurationSettings(new File(evt.getModConfigurationDirectory(), "ZerzeraRE/"+DefaultProps.MOD_ID+".conf"));
		try
		{
			REConf.load();

			/*
			Property REbenchId = REConf.getBlock("re_bench.id", DefaultProps.RE_BENCH_ID);
			
			Property modifyWorld = REConf.getOrCreateBooleanProperty("modifyWorld", Configuration.CATEGORY_GENERAL, true);
			modifyWorld.comment = "set to false if you don't want the world to be modified";

			ZerzeraRE.modifyWorld = modifyWorld.getBoolean(true);
*/				
		}
		finally
		{
			REConf.save();
		}
		
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		
		// -- Register modded blocks
		ModBlocks.init();
		
		// -- Preload textures and renderers (Client only)
		ZerzeraRE.proxy.initRendering();
		
		// -- Register TileEntity
		ZerzeraRE.proxy.initRenderBlocks();
		
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		
	}

	
	
}
