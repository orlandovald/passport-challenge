package com.orlandovald.tree;

import javax.inject.Singleton;
import java.util.List;

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
}
