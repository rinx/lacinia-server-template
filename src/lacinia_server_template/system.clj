(ns lacinia-server-template.system
  (:require
    [com.stuartsierra.component :as component]
    [lacinia-server-template.usecase.server :as server]))

(defn system [{:keys [port cancel-ch] :as conf}]
  (component/system-map
    :server (server/start-server port)))
