package com.orlandovald.tree;

import com.orlandovald.tree.pojo.*;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

/**
 * Service to handle Tree Nodes logic
 */
@Singleton
public class TreeService {

    private final TreeRepository repo;

    public TreeService(TreeRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all nodes
     * @return
     */
    List<Node> getFullTree() {
        return repo.getFullTree();
    }

    /**
     * Main request processor. It will route the request based on its type
     * @param req
     * @return
     */
    TreeResponse process(TreeRequest req) {

        switch (req.getType()) {
            case NODE_CREATE:
                return nodeCreate(req.getNode(), req.getCount());
            case NODE_DELETE:
                return nodeDelete(req.getNode());
            case CHILD_DELETE:
                return childDelete(req.getNode());
        }

        throw new TreeException("Operation not supported");
    }

    private TreeResponse nodeCreate(Node node, int count) {
        final List<Integer> nums = generateRandomNumbers(count, node.getLowerBound(), node.getUpperBound());
        node.setChilds(nums.toArray(new Integer[nums.size()]));
        Node newNode = repo.create(node);
        TreeResponse resp = new TreeResponse(ResponseType.NODE_CREATED);
        resp.setNode(newNode);
        return resp;
    }

    /**
     * Deletes a Node. Throws an exception if the node id is not found
     * @param node
     * @return
     */
    private TreeResponse nodeDelete(Node node) {
        Node deletedNode = repo.deleteNode(node.getId());
        if(deletedNode != null && deletedNode.getId() > 0) {
            TreeResponse resp = new TreeResponse(ResponseType.NODE_DELETED);
            resp.setNode(deletedNode);
            return resp;
        } else {
            throw new TreeException(String.format("Unable to find Node with id of [%d]", node.getId()));
        }
    }

    /**
     * Deletes a child. Throws an exception if the node id is not found
     * @param node
     * @return
     */
    private TreeResponse childDelete(Node node) {
        if(node.getChilds().length != 1 || node.getChilds()[0] == null) {
            throw new TreeException("Invalid delete number request");
        }
        int num = node.getChilds()[0].intValue();
        Node updatedNode = repo.deleteChild(node.getId(), num);
        if(updatedNode != null && updatedNode.getId() > 0) {
            TreeResponse resp = new TreeResponse(ResponseType.CHILD_DELETED);
            resp.setNode(updatedNode);
            return resp;
        } else {
            throw new TreeException(String.format("Unable to find Node with id of [%d]", node.getId()));
        }
    }

    /**
     * Generate {@code count} random numbers from min (inclusive) to max (inclusive)
     * @param count Number of numbers to generate
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return List of Integers of size {@code count}
     */
    public List<Integer> generateRandomNumbers(int count, int min, int max) {
        if(count <= 0) {
            return new ArrayList<Integer>();
        }
        Random r = new Random();
        return r.ints(count, min, max + 1).boxed().collect(toList());
    }
}
