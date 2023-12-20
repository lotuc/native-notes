(ns lotuc.dt-example.make-graal
  (:require
   [lotuc.dt-example.libc :as libc]
   [tech.v3.datatype.ffi.graalvm :as graalvm]))

(def class-dir "target/classes")

(defn make-bindings
  []
  (.mkdir (java.io.File. class-dir))
  (with-bindings {#'*compile-path* class-dir}
    (graalvm/define-library
      libc/fn-defs
      nil
      {:classname 'libc.GraalBindings
       :headers ["<string.h>"]
       :instantiate? true})))

(make-bindings)
