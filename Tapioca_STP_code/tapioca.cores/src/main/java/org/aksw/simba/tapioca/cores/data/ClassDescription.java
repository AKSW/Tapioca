/**
 * This file is part of tapioca.cores.
 *
 * tapioca.cores is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tapioca.cores is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with tapioca.cores.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.simba.tapioca.cores.data;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;

/**
 * This class represents the description of a RDF class.
 */
public class ClassDescription extends VoidInformation {
	
    @Override
    /**
     * The implemented functionality for the abstract function.
     */
    public void addToCount(ObjectIntOpenHashMap<String> countedClasses,
            ObjectIntOpenHashMap<String> countedProperties) {
    	
        if (this.isComplete()) {
            countedClasses.putOrAdd(uri, count, count);
        }
    }
}
