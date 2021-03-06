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
package org.aksw.simba.tapioca.cores.extraction;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.jena.riot.system.StreamRDF;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.Quad;

/**
 * A simple decorator that offers the additional {@link #waitToStart()} method
 * with which a thread that wants to read from the stream can synchronize with
 * the stream's producing thread.
 * 
 * @author Michael R&ouml;der (roeder@informatik.uni-leipzig.de)
 *
 */
public class BlockingStreamRDFDecorator implements StreamRDFDecorator {

    /**
     * The decorated stream.
     */
    private final StreamRDF stream;
    /**
     * A {@link Lock} used to secure the {@link #flagSet} condition.
     */
    private final Lock flagLock = new ReentrantLock();
    /**
     * A {@link Condition} used to make the consumer wait for the start signal.
     */
    private final Condition flagSet = flagLock.newCondition();
    /**
     * A flag used to store whether the stream already has started. It is needed
     * if the start signal occurs before the consumer entered the
     * {@link #waitToStart()} method.
     */
    private boolean started = false;

    public BlockingStreamRDFDecorator(StreamRDF stream) {
        this.stream = stream;
    }

    @Override
    public void start() {
        stream.start();
        started = true;
        flagLock.lock();
        try {
            flagSet.signalAll();
        } finally {
            flagLock.unlock();
        }
    }

    @Override
    public void triple(Triple triple) {
        stream.triple(triple);
    }

    @Override
    public void quad(Quad quad) {
        stream.quad(quad);
    }

    @Override
    public void base(String base) {
        stream.base(base);
    }

    @Override
    public void prefix(String prefix, String iri) {
        stream.prefix(prefix, iri);
    }

    @Override
    public void finish() {
        stream.finish();
    }

    @Override
    public StreamRDF getDecorated() {
        return stream;
    }

    /**
     * This methods blocks until the stream has started.
     * 
     * @throws InterruptedException
     */
    public void waitToStart() throws InterruptedException {
        while (!started) {
            flagLock.lock();
            try {
                flagSet.await();
            } finally {
                flagLock.unlock();
            }
        }
        
    }

}
