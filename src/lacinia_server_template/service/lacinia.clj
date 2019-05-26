(ns lacinia-server-template.service.lacinia
  (:require
    [com.walmartlabs.lacinia.pedestal :as pedestal]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia.util :as util]
    [lacinia-server-template.service.lacinia.schema :as server.schema]
    [lacinia-server-template.service.resolver :as resolver]))

(defn service-map []
  (-> server.schema/schema
      (util/attach-resolvers (resolver/resolvers))
      (schema/compile)
      (pedestal/service-map {:graphiql true
                             :ide-path "/graphiql"})))

(comment
  (service-map))
