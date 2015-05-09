package com.tinkerpop.blueprints.impls.neo4j2;


import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ExceptionFactory;
import com.tinkerpop.blueprints.util.StringFactory;

import org.neo4j.graphdb.Relationship;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class Neo4j2Edge extends Neo4j2Element<Relationship> implements Edge {

    public Neo4j2Edge(final Relationship relationship, final Neo4j2Graph graph) {
        super(graph);
        this.rawElement = relationship;
    }

    public String getLabel() {
        this.graph.autoStartTransaction(false);
        return this.rawElement.getType().name();
    }

    public Vertex getVertex(final Direction direction) {
        this.graph.autoStartTransaction(false);
        if (direction.equals(Direction.OUT))
            return new Neo4j2Vertex(this.rawElement.getStartNode(), this.graph);
        else if (direction.equals(Direction.IN))
            return new Neo4j2Vertex(this.rawElement.getEndNode(), this.graph);
        else
            throw ExceptionFactory.bothIsNotSupported();

    }
    
    @Override
	public void remove() {
    	this.graph.removeEdge(this);
	}

    public boolean equals(final Object object) {
        return object instanceof Neo4j2Edge && ((Neo4j2Edge) object).getId().equals(this.getId());
    }

    public String toString() {
        return StringFactory.edgeString(this);
    }

    /**
	 * Deprecated, use getRawElement() instead.
	 * @return The underlying Neo4j Relationship object.
	 */
    @Deprecated
    public Relationship getRawEdge() {
        return this.rawElement;
    }
}
