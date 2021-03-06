/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 socraticphoenix@gmail.com
 * Copyright (c) 2017 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.randores.network.client.handler;

import com.gmail.socraticphoenix.randores.Randores;
import com.gmail.socraticphoenix.randores.component.MaterialDefinitionGenerator;
import com.gmail.socraticphoenix.randores.data.RandoresWorldData;
import com.gmail.socraticphoenix.randores.network.server.packet.RandoresEndDataTransferRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RandoresEndDataTransferRequestHandler implements IMessageHandler<RandoresEndDataTransferRequest, IMessage> {

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(RandoresEndDataTransferRequest message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Randores.debug("Received request from server to end data transfer: " + message.getId());
            World world = Minecraft.getMinecraft().world;
            RandoresWorldData data = RandoresWorldData.getSimply(world);
            data.coalesceCaches();
            data.markDirty();
            Randores.info("Received definitions from server", "Statistics:");
            MaterialDefinitionGenerator.applyBackers(data.getCache());
            MaterialDefinitionGenerator.logStatistics(data.getCache());
        });
        return null;
    }

}
