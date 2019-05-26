# lacinia-server-template

## Development

To start your server, execute in REPL,

```
$ lein repl

user=> (dev)
:ok
dev=> (go)
```

## postgresql

```
$ docker run -e POSTGRES_PASSWORD=password -p 5432:5432 -dt postgres:alpine
$ # run init_postgres.sql
```
