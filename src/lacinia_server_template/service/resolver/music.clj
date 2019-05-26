(ns lacinia-server-template.service.resolver.music
  (:require
    [clojure.core.async :as async :refer [<! >! <!! >!!]]
    [clojure.java.jdbc :as jdbc]
    [clojure.walk :as walk]
    [com.walmartlabs.lacinia.executor :as executor]
    [com.wsscode.pathom.core :as p]
    [walkable.sql-query-builder :as sqb]
    [walkable.sql-query-builder.emitter :as emitter]
    [walkable.sql-query-builder.floor-plan :as floor-plan]
    [walkable.sql-query-builder.impl.postgres]))

(def my-db
  {:dbtype   "postgres"
   :dbname   "postgres"
   :user     "postgres"
   :password "password"})

(def async-parser
  (p/async-parser
    {::p/plugins
     [(p/env-plugin
        {::p/reader
         [sqb/async-pull-entities
          p/map-reader]})]}))

(def compiled-floor-plan
  (floor-plan/compile-floor-plan
    {:emitter emitter/postgres-emitter
     :idents
     {:artist/by-id :artist/id
      :album/by-id :album/id
      :song/by-id :song/id
      :artist/all "artist"
      :album/all "album"
      :song/all "song"}
     :true-columns
     #{:artist/name :album/name :song/name :song/length}
     :cardinality
     {:artist/by-id :one
      :album/by-id :one
      :song/by-id :one
      :artist/albums :many
      :artist/songs :many
      :album/songs :many}
     :joins
     {:artist/albums [:artist/id :album/artist-id]
      :artist/songs [:artist/id :song/artist-id]
      :album/songs [:album/id :song/album-id]}}))

(defn vec->fragment [[k v]]
  (if (empty? (filterv some? v))
    k
    (into {} [[k (mapv vec->fragment (:selections (first v)))]])))

(defn unnamespace-keys [m]
  (walk/walk
    (fn [[k v]]
      [(keyword (name k))
       (if (vector? v)
         (mapv unnamespace-keys v)
         v)])
    identity
    m))

(defn run-query [query]
  (let [run-query (fn [db q]
                    (let [ch (async/promise-chan)]
                      (async/put! ch (jdbc/query db q))
                      ch))]
    (<!! (async-parser
           {::sqb/sql-db my-db
            ::sqb/run-query run-query
            ::sqb/floor-plan compiled-floor-plan}
           query))))

(defn resolve-artists [ctx args value]
  (let [root-key :artist/all
        fragment (mapv vec->fragment (executor/selections-tree ctx))
        query [{root-key fragment}]
        result (run-query query)]
    (-> result
        (get root-key)
        (->> (mapv unnamespace-keys)))))

(defn resolve-artist-by-id [ctx {:keys [id] :as args} value]
  (when id
    (let [root-key [:artist/by-id id]
          fragment (mapv vec->fragment (executor/selections-tree ctx))
          query [{root-key fragment}]
          result (run-query query)]
      (-> result
          (get root-key)
          unnamespace-keys))))

(defn resolve-albums [ctx args value]
  (let [root-key :album/all
        fragment (mapv vec->fragment (executor/selections-tree ctx))
        query [{root-key fragment}]
        result (run-query query)]
    (-> result
        (get root-key)
        (->> (mapv unnamespace-keys)))))

(defn resolve-album-by-id [ctx {:keys [id] :as args} value]
  (when id
    (let [root-key [:album/by-id id]
          fragment (mapv vec->fragment (executor/selections-tree ctx))
          query [{root-key fragment}]
          result (run-query query)]
      (-> result
          (get root-key)
          unnamespace-keys))))

(defn resolve-songs [ctx args value]
  (let [root-key :song/all
        fragment (mapv vec->fragment (executor/selections-tree ctx))
        query [{root-key fragment}]
        result (run-query query)]
    (-> result
        (get root-key)
        (->> (mapv unnamespace-keys)))))

(defn resolve-song-by-id [ctx {:keys [id] :as args} value]
  (when id
    (let [root-key [:song/by-id id]
          fragment (mapv vec->fragment (executor/selections-tree ctx))
          query [{root-key fragment}]
          result (run-query query)]
      (-> result
          (get root-key)
          unnamespace-keys))))
