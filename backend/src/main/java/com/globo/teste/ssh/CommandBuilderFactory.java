package com.globo.teste.ssh;

import java.util.HashMap;
import java.util.Map;

import com.globo.teste.model.Server;

/**
 * Factory to create specific command builders
 * for each distribution. UbuntuCommands is the default
 * */
public class CommandBuilderFactory {
	private static Map<String, CommandBuilder> instances = new HashMap<>();
	public static CommandBuilder getBuilder(Server server) {
		if (!instances.containsKey(server.getDistribution())) {
			CommandBuilder builder = null;
			
			if ("Ubuntu".equals(server.getDistribution())) {
				builder = new UbuntuCommands();
			} else if ("Fedora".equals(server.getDistribution())) {
				//TODO: fedora command builder
			} else {
				builder = new UbuntuCommands();
			}
			
			instances.put(server.getDistribution(), builder);
		}
		
		return instances.get(server.getDistribution());
	}
}
