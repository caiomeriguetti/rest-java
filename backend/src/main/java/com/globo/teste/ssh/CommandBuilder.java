package com.globo.teste.ssh;

public interface CommandBuilder {
	public String installPackageCommand(String packageName);
	public String uninstallPackageCommand(String packageName);
	public String isPackageInstalledCommand(String packageName);
	public String listPackagesCommand();
}
