package ZerzeraRE.common.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigurationSettings extends Configuration {

	public ConfigurationSettings(File file) {
		super(file);
	}

	@Override
	public void save() {
		Property versionProp = null;

		if (!generalProperties.containsKey("version")) {
			versionProp = new Property();
			versionProp.setName("version");
			generalProperties.put("version", versionProp);
		} else
			versionProp = generalProperties.get("version");

		versionProp.value = Version.VERSION;

		super.save();
	}

}
