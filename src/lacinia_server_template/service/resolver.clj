(ns lacinia-server-template.service.resolver
  (:require
    [lacinia-server-template.service.resolver.music :as music]))

(defn resolve-hello [ctx args value]
  "hello world")

(defn resolvers []
  {:query/hello resolve-hello
   :query/artists music/resolve-artists
   :query/artist-by-id music/resolve-artist-by-id
   :query/albums music/resolve-albums
   :query/album-by-id music/resolve-album-by-id
   :query/songs music/resolve-songs
   :query/song-by-id music/resolve-song-by-id})
