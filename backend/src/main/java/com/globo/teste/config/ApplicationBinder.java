package com.globo.teste.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.globo.teste.services.ServerService;
import com.globo.teste.services.ServerServiceImpl;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        this.bind(ServerServiceImpl.class).to(ServerService.class);
    }
}