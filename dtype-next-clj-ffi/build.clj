(ns build
  (:require
   [clojure.tools.build.api :as b]))

(def class-dir "target/classes")

(defn- clean-compile [basis]
  (println "\ncleanup...")
  (b/delete {:path "target"})
  (b/delete {:path "classes"})

  (println "\ncompile clojure...")
  (b/compile-clj
   {:basis basis
    :class-dir class-dir}))

(defn- uber [aliases]
  (let [basis (b/create-basis {:project "deps.edn" :aliases aliases})]
    (clean-compile basis)

    (println "\nuber" aliases "...")
    (b/uber
     {:basis basis
      :class-dir class-dir
      :uber-file "target/dt-example.jar"
      :main 'lotuc.dt-example.libc-invoking})))

(defn uber-graal [_]
  (uber [:graal]))

(defn uber-jna [_]
  (uber [:jna]))
