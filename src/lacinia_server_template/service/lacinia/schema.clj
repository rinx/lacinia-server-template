(ns lacinia-server-template.service.lacinia.schema)

(def schema
  {:queries
   {:hello
    {:type 'String
     :resolve :query/hello}}})
