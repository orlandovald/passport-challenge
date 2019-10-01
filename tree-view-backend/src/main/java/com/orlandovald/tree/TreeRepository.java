package com.orlandovald.tree;

import io.reactiverse.reactivex.pgclient.PgIterator;
import io.reactiverse.reactivex.pgclient.PgPool;
import io.reactiverse.reactivex.pgclient.Row;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository objects for Tree Nodes
 */
@Singleton
public class TreeRepository {

    private final PgPool client;

    public TreeRepository(PgPool client) {
        this.client = client;
    }

    /**
     * Retrieve all nodes
     * @return
     */
    List<Node> getFullTree() {
        return client.rxQuery("SELECT id, name, lower_bound, upper_bound, childs, created_at FROM tree_nodes")
                .map(pgRowSet -> {
                    PgIterator it = pgRowSet.iterator();
                    List<Node> nodes = new ArrayList<>();
                    while (it.hasNext()) {
                        Row row = it.next();
                        Node node = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5)
                        );
                        nodes.add(node);
                    }
                    return nodes;
                }).blockingGet();
    }

}
