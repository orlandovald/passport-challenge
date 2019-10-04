# Passport Coding Challenge

This repo host the components for the Tree View coding challenge

tree-view-frontend: contains the front-end application
tree-view-backend: backend websocket api

### Technology stack:
- Backend: Java + Micronaut framework
- Frontend: VueJS + Materialize
- Database: Postgres

Please take this as a pure MVP with limited time for refactoring or clean up.

### To run this locally
#### Create local area for postgres data
`mkdir -p $HOME/data/postgres`

#### Run postgres with Docker
`docker run --rm --name passport-postgres -e POSTGRES_PASSWORD=passport1234 -d -p 5432:5432 --mount type=bind,source=$HOME/data/postgres,destination=/data/postgres postgres:11.5`

#### Set the below environment variables
```
export OV_POSTGRES_PORT=5432
export OV_POSTGRES_HOST=localhost
export OV_POSTGRES_DB=postgres
export OV_POSTGRES_USER=postgres
export OV_POSTGRES_PASSWORD=passport1234
```

#### Run backend
`cd tree-view-backend`
`./gradlew run`

#### Run frontend
`cd tree-view-frontend`
`npm run serve`