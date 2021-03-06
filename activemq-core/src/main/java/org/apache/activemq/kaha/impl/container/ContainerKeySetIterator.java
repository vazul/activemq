/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.kaha.impl.container;

import java.util.Iterator;
import org.apache.activemq.kaha.impl.index.IndexItem;
import org.apache.activemq.kaha.impl.index.IndexLinkedList;

/**
 * Iterator for the set of keys for a container
 * 
 * 
 */
public class ContainerKeySetIterator implements Iterator {
    
    protected IndexItem nextItem;
    protected IndexItem currentItem;

    private MapContainerImpl container;
    private IndexLinkedList list;

    ContainerKeySetIterator(MapContainerImpl container) {
        this.container = container;
        this.list = container.getInternalList();
        this.currentItem = list.getRoot();
        this.nextItem = list.getNextEntry(currentItem);
    }

    public boolean hasNext() {
        return nextItem != null;
    }

    public Object next() {
        currentItem = nextItem;
        Object result = container.getKey(nextItem);
        nextItem = list.getNextEntry(nextItem);
        return result;
    }

    public void remove() {
        if (currentItem != null) {
            container.remove(currentItem);
            if (nextItem != null) {
                list.refreshEntry(nextItem);
            }
        }
    }
}
