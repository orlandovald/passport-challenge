package com.orlandovald.tree;

import com.orlandovald.tree.pojo.Node;
import com.orlandovald.tree.pojo.ResponseType;
import com.orlandovald.tree.pojo.TreeRequest;
import com.orlandovald.tree.pojo.TreeResponse;

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

    TreeResponse process(TreeRequest req) {

        switch (req.getType()) {
            case NODE_CREATE:
                return nodeCreate(req.getNode(), req.getCount());
        }
        System.out.println();
        return null;
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
