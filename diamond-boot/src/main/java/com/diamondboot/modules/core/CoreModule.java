/*
 * Copyright 2015 Zack Hoffmann <zachary.hoffmann@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diamondboot.modules.core;

import com.diamondboot.core.DiamondBootConsole;
import com.diamondboot.core.LocalFileDiamondBootContext;
import com.diamondboot.core.DiamondBootContext;
import com.diamondboot.launcher.Launcher;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 *
 * @author Zack Hoffmann <zachary.hoffmann@gmail.com>
 */
public class CoreModule extends AbstractModule {
    
    private final String appDir;
    
    public CoreModule(String appDir) {
        this.appDir = appDir;
    }
    
    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("appDir")).toInstance(appDir);
        bind(String.class).annotatedWith(Names.named("diamondBootVersion")).toInstance(Launcher.DIAMOND_BOOT_VERSION);
        bind(DiamondBootContext.class).to(LocalFileDiamondBootContext.class).in(Scopes.SINGLETON);
        bind(DiamondBootConsole.class).in(Scopes.SINGLETON);
    }
    
}
