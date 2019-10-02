package com.orlandovald.tree;

import com.orlandovald.tree.pojo.Node;
import io.reactiverse.reactivex.pgclient.PgIterator;
import io.reactiverse.reactivex.pgclient.PgPool;
import io.reactiverse.reactivex.pgclient.Row;
import io.reactiverse.reactivex.pgclient.Tuple;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository objects for Tree Nodes
 */
@Singleton
public class TreeRepository {

    public static final String SQL_INSERT_NODE = "INSERT INTO tree_nodes (name, lower_bound, upper_bound, childs) " +
            "VALUES ($1, $2, $3, $4) RETURNING id";
    public static final String SQL_SELECT_ALL_NODES = "SELECT id, name, lower_bound, upper_bound, childs, created_at " +
            "FROM tree_nodes";

    private final PgPool client;

    public TreeRepository(PgPool client) {
        this.client = client;
    }

    /**
     * Retrieve all nodes
     * @return
     */
    List<Node> getFullTree() {
        return client.rxQuery(SQL_SELECT_ALL_NODES)
                .map(rows -> {
                    PgIterator it = rows.iterator();
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

    /**
     * Create a node
     * @param node
     * @return the created node with its new Id
     */
    public Node create(Node node) {
        Integer id = client.rxPreparedQuery(SQL_INSERT_NODE,
                Tuple.of(node.getName(), node.getLowerBound(), node.getUpperBound(), node.getChilds()))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if (it.hasNext()) {
                        return it.next().getInteger("id");
                    }
                    return -1;
                }).blockingGet();
        node.setId(id);
        return node;
    }
}