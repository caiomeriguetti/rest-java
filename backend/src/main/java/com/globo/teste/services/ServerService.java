package com.globo.teste.services;

import com.globo.teste.model.Server;
import com.globo.teste.model.ServerPackage;

public interface ServerService {
    public boolean uninstallPackage(String id, String packageName);
    public boolean hasPackage(Server server, String packageName);
    public boolean hasPackage(String id, String packageName);
    public String installPackage(String id, String packageName);
    public ServerPackage[] getPackages (String id);
    public Server[] list();
    public Server getServerById(String id);
    public boolean delete(String id);
    public boolean update(String id, Server server);
    public boolean update(String id, Server updatedServerData, boolean onlyNotEmptyFields);
    public Server save(Server newServer);

}
