package com.orlandovald.tree;

import com.orlandovald.tree.pojo.Node;
import com.orlandovald.tree.pojo.TreeException;
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
            "FROM tree_nodes ORDER BY created_at DESC";
    public static final String SQL_DELETE_NODE = "DELETE FROM tree_nodes WHERE id = $1 RETURNING *";
    public static final String SQL_DELETE_CHILD = "UPDATE tree_nodes SET childs = array_remove(childs, $1) WHERE id = $2 RETURNING *";
    public static final String SQL_SELECT_NODE = "SELECT id, name, lower_bound, upper_bound, childs, created_at FROM tree_nodes WHERE id = $1";
    public static final String SQL_UPDATE_CHILDS = "UPDATE tree_nodes SET childs = $1 WHERE id = $2 RETURNING *";
    public static final String SQL_UPDATE_NODE = "UPDATE tree_nodes SET name = $1, lower_bound = $2, upper_bound = $3 WHERE id = $4 RETURNING *";
    public static final String SQL_DELETE_ALL_NODES = "DELETE FROM tree_nodes";

    public enum Columns {
        name, lower_bound, upper_bound, childs;
    }

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
     * Retrieves a single node based on its id
     * @param id
     * @return
     */
    public Node findById(int id) {
        return client.rxPreparedQuery(SQL_SELECT_NODE, Tuple.of(id))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if(it.hasNext()) {
                        Row row = it.next();
                        Node node = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5));
                        return node;
                    }
                    Node notFound = new Node();
                    notFound.setId(-1);
                    return notFound;
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

    /**
     * Deletes a parent Node
     * @param id - id of the Node to delete
     * @return the deleted Node
     */
    public Node deleteNode(int id) {
        return client.rxPreparedQuery(SQL_DELETE_NODE, Tuple.of(id))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if(it.hasNext()) {
                        Row row = it.next();
                        Node deletedNode = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5));
                        return deletedNode;
                    }
                    Node notFound = new Node();
                    notFound.setId(-1);
                    return notFound;
                }).blockingGet();
    }

    /**
     * Removes a child from a node
     * @param id id of the Node
     * @param child num to be removed
     * @return
     */
    public Node deleteChild(int id, int child) {
        return client.rxPreparedQuery(SQL_DELETE_CHILD, Tuple.of(child, id))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if(it.hasNext()) {
                        Row row = it.next();
                        Node updatedNode = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5));
                        return updatedNode;
                    }
                    Node notFound = new Node();
                    notFound.setId(-1);
                    return notFound;
                }).blockingGet();
    }

    /**
     * Update the childs of the given node
     * @param node
     * @return
     */
    public Node updateChilds(Node node) {
        return client.rxPreparedQuery(SQL_UPDATE_CHILDS, Tuple.of(node.getChilds(), node.getId()))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if(it.hasNext()) {
                        Row row = it.next();
                        Node updatedNode = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5));
                        return updatedNode;
                    }
                    Node notFound = new Node();
                    notFound.setId(-1);
                    return notFound;
                }).blockingGet();
    }

    /**
     * Update Node values
     * @param node
     * @return
     */
    public Node updateNode(Node node) {
        return client.rxPreparedQuery(SQL_UPDATE_NODE, Tuple.of(node.getName(), node.getLowerBound(), node.getUpperBound(), node.getId()))
                .map(rows -> {
                    PgIterator it = rows.iterator();
                    if(it.hasNext()) {
                        Row row = it.next();
                        Node updatedNode = new Node(
                                row.getInteger(0),
                                row.getString(1),
                                row.getInteger(2),
                                row.getInteger(3),
                                row.getIntegerArray(4),
                                row.getLocalDate(5));
                        return updatedNode;
                    }
                    Node notFound = new Node();
                    notFound.setId(-1);
                    return notFound;
                }).blockingGet();
    }

    /**
     * Deletes all nodes
     */
    public void clearTree() {
        client.rxQuery(SQL_DELETE_ALL_NODES).blockingGet();
    }

    /**
     * Is the column updatable
     * @param column
     * @return true if the column is updatable, false otherwise
     */
    private boolean isUpdatable(String column) {
        for (Columns c : Columns.values()) {
            if (c.name().equals(column)) {
                return true;
            }
        }
        return false;
    }
}
