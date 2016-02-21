package com.globo.teste.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.globo.teste.services.ServerService;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        this.bind(ServerService.class).to(ServerService.class);
    }
}