(defproject lacinia-server-template "0.1.0-SNAPSHOT"
  :description "lacinia server template"
  :url "https://github.com/rinx/lacinia-server-template"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0-beta3"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [org.clojure/core.async "0.4.474"]
                 [com.stuartsierra/component "0.3.2"]
                 [com.taoensso/timbre "4.10.0"]
                 [orchestra "2018.09.10-1"]
                 [io.pedestal/pedestal.service "0.5.5"]
                 [io.pedestal/pedestal.immutant "0.5.5"]
                 [io.pedestal/pedestal.log "0.5.5"]
                 [io.pedestal/pedestal.interceptor "0.5.5"]
                 [io.pedestal/pedestal.route "0.5.5"]
                 [com.walmartlabs/lacinia "0.33.0-alpha-3"]
                 [com.walmartlabs/lacinia-pedestal "0.11.0"]
                 [metosin/jsonista "0.2.2"]
                 [camel-snake-kebab "0.4.0"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.5"]
                 [walkable "1.2.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main lacinia-server-template.core}})
