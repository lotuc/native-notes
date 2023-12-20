(ns build
  (:require
   [clojure.tools.build.api :as b]))

(def class-dir "target/classes")

(defn- clean-compile [basis]
  (println "\ncleanup...")
  (b/delete
   {:path "target"})

  (println "\ncompile clojure...")
  (b/compile-clj
   {:basis basis
    :class-dir class-dir}))

(defn uber-graal [_]
  (let [basis (b/create-basis {:project "deps.edn" :aliases [:graal]})]
    (clean-compile basis)

    (println "\nuber...")
    (b/uber
     {:basis basis
      :class-dir class-dir
      :uber-file "target/libc-graal.jar"
      :main 'lotuc.dt-example.graal-main})))

(defn uber-jna [_]
  (let [basis (b/create-basis {:project "deps.edn" :aliases [:jna]})]
    (clean-compile basis)

    (println "\nuber...")
    (b/uber
     {:class-dir class-dir
      :uber-file "target/libc-jna.jar"
      :basis basis
      :main 'lotuc.dt-example.jna-main})))
