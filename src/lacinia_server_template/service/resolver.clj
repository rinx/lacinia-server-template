(ns lacinia-server-template.service.resolver
  (:require
    [lacinia-server-template.service.lacinia.schema :as schema]))

(defn resolve-hello [ctx args value]
  "hello world")

(defn resolvers []
  {:query/hello resolve-hello})
