/**
 * tapioca.analyzer - ${project.description}
 * Copyright © 2015 Data Science Group (DICE) (michael.roeder@uni-paderborn.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of tapioca.analyzer.
 *
 * tapioca.analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tapioca.analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with tapioca.analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.simba.tapioca.analyzer.sparql;

import java.io.File;
import java.io.FileOutputStream;

import org.aksw.simba.topicmodeling.concurrent.tasks.Task;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;

public class EndpointAnalyzingTask implements Task {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndpointAnalyzingTask.class);

	private EndpointConfig endpointCfg;
	private String endpointName;
	private File outputFile;
	private String cacheDirectory;

	public EndpointAnalyzingTask(String endpoint, File outputFile) {
		this(new EndpointConfig(endpoint), endpoint, null, outputFile);
	}

	public EndpointAnalyzingTask(EndpointConfig endpointCfg, String endpointName, File outputFile) {
		this(endpointCfg, endpointName, null, outputFile);
	}

	public EndpointAnalyzingTask(EndpointConfig endpointCfg, String endpointName, String cacheDirectory, File outputFile) {
		this.endpointCfg = endpointCfg;
		this.endpointName = endpointName;
		this.outputFile = outputFile;
		this.cacheDirectory = cacheDirectory;
	}

	@Override
	public void run() {
		LOGGER.info("Starting extraction from \"" + endpointName + "\"...");
		SPARQLEndpointAnalyzer analyzer = new SPARQLEndpointAnalyzer(cacheDirectory);
		try {
			if (outputFile.exists()) {
				LOGGER.info("There already is a file for \"" + endpointName + "\". Jumping over this endpoint.");
			} else {
				Model voidModel = analyzer.extractVoidInfo(endpointCfg);
				if (voidModel != null) {
					voidModel.setNsPrefix("void", "http://rdfs.org/ns/void#");
					// NTripleWriter writer = new NTripleWriter();
					FileOutputStream fout = new FileOutputStream(outputFile);
					// writer.write(voidModel, fout, "");
					RDFDataMgr.write(fout, voidModel, RDFFormat.TURTLE_PRETTY);
					fout.close();
				} else {
					LOGGER.error("Error while requesting the void information of \"" + endpointName + "\".");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while requesting and storing the void information of \"" + endpointName + "\".", e);
		} finally {
			LOGGER.info("Finished extraction from \"" + endpointName + "\"...");
		}
	}

	@Override
	public String getId() {
		return endpointName;
	}

	@Override
	public String getProgress() {
		return "";
	}
}
