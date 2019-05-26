(ns lacinia-server-template.service.lacinia.schema)

(def schema
  {:objects
   {:artist
    {:description "artist info"
     :fields
     {:id {:type '(non-null Int)}
      :name {:type '(non-null String)}
      :albums {:type '(non-null (list :album))}
      :songs {:type '(non-null (list :song))}}}
    :album
    {:description "album info"
     :fields
     {:id {:type '(non-null Int)}
      :name {:type '(non-null String)}
      :songs {:type '(non-null (list :song))}}}
    :song
    {:description "song info"
     :fields
     {:id {:type '(non-null Int)}
      :name {:type '(non-null String)}
      :length {:type '(non-null Int)}}}}
   :queries
   {:hello
    {:type 'String
     :resolve :query/hello}
    :artists
    {:type '(list :artist)
     :resolve :query/artists}
    :artist_by_id
    {:type :artist
     :args {:id {:type '(non-null Int)}}
     :resolve :query/artist-by-id}
    :albums
    {:type '(list :album)
     :resolve :query/albums}
    :album_by_id
    {:type :album
     :args {:id {:type '(non-null Int)}}
     :resolve :query/album-by-id}
    :songs
    {:type '(list :song)
     :resolve :query/songs}
    :song_by_id
    {:type :song
     :args {:id {:type '(non-null Int)}}
     :resolve :query/song-by-id}}})

(comment

  (do
    (require 'com.walmartlabs.lacinia.schema)
    (require 'com.walmartlabs.lacinia.util)
    (require 'lacinia-server-template.service.resolver)
    (-> schema
        (com.walmartlabs.lacinia.util/attach-resolvers (lacinia-server-template.service.resolver/resolvers))
        (com.walmartlabs.lacinia.schema/compile)))

  )
