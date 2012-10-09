package ZerzeraRE.common.core;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigurationSettings extends Configuration {

	public ConfigurationSettings(File file) {
		super(file);
	}

	@Override
	public void save() {
		this.get("general","version", Version.VERSION);
		
		super.save();
	}

}
