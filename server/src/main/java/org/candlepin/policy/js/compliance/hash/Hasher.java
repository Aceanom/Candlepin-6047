/**
 * Copyright (c) 2009 - 2012 Red Hat, Inc.
 *
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 *
 * Red Hat trademarks are not licensed under GPLv2. No permission is
 * granted to use or replicate Red Hat trademarks that are incorporated
 * in this software or its documentation.
 */

package org.candlepin.policy.js.compliance.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collection;

/**
 * Generates a an SHA256 hash of objects via respective {@link HashableStringGenerator}s.
 */
public class Hasher {

    private StringBuilder sink;

    public Hasher() {
        sink = new StringBuilder();
    }

    /**
     * Products an SHA256 hash of anything that was put into this hasher.
     *
     * @return an SHA256 hex string
     */
    public String hash() {
        // Hash the data with two algorithms to make collisions less likely.
        String data = sink.toString();
        return DigestUtils.sha256Hex(DigestUtils.md5Hex(data) + data);
    }

    /**
     * Adds the specified collection to the result of this hash. The resulting string added to the hash
     * is generated by the specified generator.
     *
     * @see HashableStringGenerators
     *
     * @param toConvert the collection to add
     * @param generator the generator responsible for generating the hash string for each object
     *                  in the collection.
     */
    public <T extends Object> void putCollection(Collection<T> toConvert,
        HashableStringGenerator<T> generator) {
        sink.append(HashableStringGenerators.generateFromCollection(toConvert, generator));
    }

    /**
     * Adds the specified Object to the result of this hash. The string added to the hash will be generated
     * by the specified generator.
     *
     * @see HashableStringGenerators
     *
     * @param toConvert the object to add
     * @param generator the generator responsible for generating the hash string for the object.
     */
    public <T extends Object> void putObject(T toConvert, HashableStringGenerator<T> generator) {
        sink.append(HashableStringGenerators.generateFromObject(toConvert, generator));
    }

}
