package org.aksw.simba.tapioca.analyzer.sparql;

public class EndpointConfig {
    public String uri;
    public String graph;

    public EndpointConfig(String uri) {
        this.uri = uri;
    }

    public EndpointConfig(String uri, String graph) {
        this.uri = uri;
        this.graph = graph;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((graph == null) ? 0 : graph.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EndpointConfig other = (EndpointConfig) obj;
        if (graph == null) {
            if (other.graph != null)
                return false;
        } else if (!graph.equals(other.graph))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EndpointCfg [uri=" + uri + ", graph=" + graph + "]";
    }

}