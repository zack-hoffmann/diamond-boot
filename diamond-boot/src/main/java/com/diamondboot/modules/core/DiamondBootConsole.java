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

import com.diamondboot.core.event.DiamondBootEvent;
import com.diamondboot.core.event.MinecraftServerEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Zack Hoffmann <zachary.hoffmann@gmail.com>
 */
public class DiamondBootConsole {

    private boolean running = false;
    private final Scanner sc = new Scanner(System.in);
    private final EventBus bus;

    @Inject
    public DiamondBootConsole(EventBus bus) {
        this.bus = bus;
    }
    
    @Subscribe
    public void printToStdOut(MinecraftServerEvent e) {
        System.out.println("[" + e.getInstanceMetadata().getId() + "]" + e.getContent());
    }

    public void start() {
        bus.register(this);

        new Thread(() -> {
            while (isRunning()) {
                try {
                    if (System.in.available() > 0 && sc.hasNextLine()) {
                        bus.post(DiamondBootEvent.newAllInstanceEvent(sc.nextLine()));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DiamondBootConsole.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        running = true;
    }

    public void stop() {
        running = false;
    }

    public final boolean isRunning() {
        return running;
    }

}
