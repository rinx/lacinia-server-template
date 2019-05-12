(ns lacinia
  (:require
    [lacinia-server-template.service.lacinia :as server.lacinia]
    [lacinia-server-template.service.lacinia.schema :as server.schema]
    [lacinia-server-template.service.resolver :as resolver]
    [com.walmartlabs.lacinia :as lacinia]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia.util :as util]))

(defn run-query [query]
  (let [schema (-> server.schema/schema
                   (util/attach-resolvers (resolver/resolvers))
                   (schema/compile))]
    (lacinia/execute schema query nil nil)))

(comment
  (run-query "query { hello }")
  )
