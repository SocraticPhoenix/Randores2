/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 socraticphoenix@gmail.com
 * Copyright (c) 2016 contributors
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
package com.gmail.socraticphoenix.randores.listener;

import com.gmail.socraticphoenix.randores.component.ability.AbilitySeries;
import com.gmail.socraticphoenix.randores.component.ability.EmpoweredEnchantment;
import com.gmail.socraticphoenix.randores.data.RandoresItemData;
import com.gmail.socraticphoenix.randores.data.RandoresWorldData;
import com.gmail.socraticphoenix.randores.item.RandoresItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmpoweredArmorListener {
    private static EntityEquipmentSlot[] armor = {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    private  Map<EntityEquipmentSlot, Map<UUID, AbilitySeries>> map = new HashMap<>();

    public EmpoweredArmorListener() {
        for(EntityEquipmentSlot slot : armor) {
            map.put(slot, new HashMap<>());
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent ev) {
        DamageSource source = ev.getSource();
        Entity root = source.getTrueSource();
        if (root instanceof EntityLivingBase) {
            EntityLivingBase cause = (EntityLivingBase) root;
            EntityLivingBase hurt = ev.getEntityLiving();
            if (EmpoweredEnchantment.appliedTo(hurt)) {
                EmpoweredEnchantment.doArmor(hurt, cause);
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent ev) {
        EntityLivingBase entity = ev.getEntityLiving();
        for (EntityEquipmentSlot slot : armor) {
            Map<UUID, AbilitySeries> associated = map.get(slot);
            ItemStack stack = entity.getItemStackFromSlot(slot);
            if (!stack.isEmpty() && RandoresItemData.hasData(stack) && stack.getItem() instanceof RandoresItemArmor && EmpoweredEnchantment.appliedTo(stack)) {
                RandoresWorldData.delegateVoid(new RandoresItemData(stack), def -> {
                    associated.put(entity.getPersistentID(), def.getAbilitySeries());
                    def.getAbilitySeries().onArmorUpdate(entity);
                }, () -> {});
            } else if (associated.containsKey(entity.getPersistentID())) {
                associated.remove(entity.getPersistentID()).onArmorCancel(entity);
            }
        }
    }

}
