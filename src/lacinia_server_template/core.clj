(ns lacinia-server-template.core
  (:require
    [clojure.spec.alpha :as spec]
    [clojure.core.async :as async :refer [<! >! <!! >!!]]
    [com.stuartsierra.component :as component]
    [lacinia-server-template.system :as system]
    [taoensso.timbre :as timbre])
  (:gen-class))

(defn parse-int [s]
  (try
    (Integer/parseInt s)
    (catch Exception _ nil)))

(defn get-opts []
  {:port (or (parse-int (System/getenv "PORT")) 8080)})

(defn -main []
  (timbre/set-level! :info)
  (let [cancel-ch (async/chan)
        opts (into (get-opts)
                   {:cancel-ch cancel-ch})]
    (component/start
      (system/system opts))
    (async/go
      (loop []
        (async/alt!
          cancel-ch :canceled
          (async/timeout 3600000) (recur))))))
