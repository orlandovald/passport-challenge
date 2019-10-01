CREATE TABLE tree_nodes (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    lower_bound INTEGER NOT NULL,
    upper_bound INTEGER NOT NULL,
    childs      INTEGER[],
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX tree_nodes_id ON tree_nodes (id);