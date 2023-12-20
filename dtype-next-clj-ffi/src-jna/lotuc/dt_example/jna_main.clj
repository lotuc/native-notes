(ns lotuc.dt-example.jna-main
  (:require
   [lotuc.dt-example.libc :as libc]
   [tech.v3.datatype :as dtype]
   [tech.v3.datatype.ffi :as dt-ffi]
   [tech.v3.datatype.ffi.jna])
  (:gen-class))

(defn -main
  [& _args]
  ;; (dt-ffi/set-ffi-impl! :jna)
  (dt-ffi/library-singleton-set! libc/lib nil)
  (let [nbuf (dtype/make-container :native-heap :float32 (range 10))]
    (println "before memset" nbuf)
    (libc/memset nbuf 0 40)
    (println "after memset" nbuf)
    0))
