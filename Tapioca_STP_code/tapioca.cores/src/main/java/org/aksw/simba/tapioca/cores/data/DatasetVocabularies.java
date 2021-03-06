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

import org.aksw.simba.topicmodeling.utils.doc.AbstractArrayContainingDocumentProperty;
import org.aksw.simba.topicmodeling.utils.doc.ParseableDocumentProperty;
import org.aksw.simba.topicmodeling.utils.doc.StringContainingDocumentProperty;

public class DatasetVocabularies extends AbstractArrayContainingDocumentProperty implements
        StringContainingDocumentProperty, ParseableDocumentProperty {

    private static final String DELIMITTER_WRITING = "|";
    private static final String DELIMITTER_PARSING = "\\|";

    private static final long serialVersionUID = 1L;

    private String vocabularies[];

    public DatasetVocabularies() {
    }

    public DatasetVocabularies(String[] vocabularies) {
        this.vocabularies = vocabularies;
    }

    public Object[] getValueAsArray() {
        return vocabularies;
    }

    public void parseValue(String value) {
        if (value.isEmpty()) {
            vocabularies = new String[0];
        } else {
            vocabularies = value.split(DELIMITTER_PARSING);
        }
    }

    public String getStringValue() {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < vocabularies.length; ++i) {
            if (first) {
                first = false;
            } else {
                result.append(DELIMITTER_WRITING);
            }
            result.append(vocabularies[i]);
        }
        return result.toString();
    }

    public String[] getVocabularies() {
        return vocabularies;
    }

}
