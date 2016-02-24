package com.globo.teste.ssh;

public class UbuntuCommands implements CommandBuilder {

	@Override
	public String installPackageCommand(String packageName) {
		return "apt-get -y install "+packageName;
	}

	@Override
	public String uninstallPackageCommand(String packageName) {
		return "apt-get -y remove "+packageName;
	}

	@Override
	public String isPackageInstalledCommand(String packageName) {
		return "dpkg -s "+packageName;
	}

	@Override
	public String listPackagesCommand() {
		return "dpkg --get-selections";
	}


}
