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
package com.gmail.socraticphoenix.randores.component.craftable.factories;

import com.gmail.socraticphoenix.collect.Items;
import com.gmail.socraticphoenix.randores.Randores;
import com.gmail.socraticphoenix.randores.RandoresKeys;
import com.gmail.socraticphoenix.randores.component.craftable.CraftableComponent;
import com.gmail.socraticphoenix.randores.component.craftable.CraftableGenerator;
import com.gmail.socraticphoenix.randores.component.enumerable.CraftableTypeRegistry;
import com.gmail.socraticphoenix.randores.plugin.RandoresPlugin;
import com.gmail.socraticphoenix.randores.probability.RandoresProbability;

import java.util.List;
import java.util.Random;

public class BowGenerator implements CraftableGenerator {

    @Override
    public List<CraftableComponent> generate(Random random) {
        return Items.buildList(new CraftableComponent(CraftableTypeRegistry.instance().get(RandoresKeys.BOW), 1));
    }

    @Override
    public boolean test(Random random) {
        return RandoresProbability.percentChance(30, random);
    }

    @Override
    public RandoresPlugin parent() {
        return Randores.INSTANCE;
    }

}
