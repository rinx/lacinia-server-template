(ns lacinia-server-template.usecase.server
  (:require
    [clojure.spec.alpha :as spec]
    [com.stuartsierra.component :as component]
    [io.pedestal.http :as http]
    [lacinia-server-template.service.lacinia :as lacinia]
    [taoensso.timbre :as timbre]))

(defrecord ServerComponent [options]
  component/Lifecycle
  (start [this]
    (timbre/info "Starting server...")
    (let [smap (merge (lacinia/service-map)
                      (:service-map options))
          server (http/create-server smap)]
      (http/start server)
      (assoc this :server server)))
  (stop [this]
    (timbre/info "Stopping server...")
    (let [server (:server this)]
      (when server
        (http/stop server)))
    (assoc this :server nil)))

(defn start-server [port]
  (map->ServerComponent
    {:options {:service-map {::http/port port
                             ::http/host "0.0.0.0"}}}))
