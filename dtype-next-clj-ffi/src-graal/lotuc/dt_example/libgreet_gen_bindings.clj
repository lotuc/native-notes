(ns lotuc.dt-example.libgreet-gen-bindings
  (:require
   [tech.v3.datatype.ffi.graalvm :as graalvm]))

(require '[lotuc.dt-example.libgreet :as libgreet])

(with-bindings {#'*compile-path* "target/classes"}
  (graalvm/expose-clojure-functions
   {#'libgreet/greet {:rettype :int64
                      :argtypes [['s :pointer]]}}
   'lotuc.LibGreet
   nil))
