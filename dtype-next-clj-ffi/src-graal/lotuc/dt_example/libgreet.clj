(ns lotuc.dt-example.libgreet
  (:require
   [tech.v3.datatype.array-buffer]
   [tech.v3.datatype.ffi :as dt-ffi]
   [tech.v3.datatype.ffi.graalvm-runtime]
   [tech.v3.datatype.native-buffer :as native-buffer]
   [tech.v3.datatype.protocols :as dtype-proto]))

(defmacro ptr->string [pointer]
  `(let [ptr-data# (dt-ffi/->pointer ~pointer)
         nbuf#
         (native-buffer/wrap-address
          (.address ptr-data#) Integer/MAX_VALUE :int8
          (dtype-proto/platform-endianness) nil)
         zero-pad-idx#
         (long (loop [idx# 0
                      finished?# (== 0 (long (nbuf# 0)))]
                 (if finished?#
                   idx#
                   (let [nidx# (inc idx#)]
                     (recur nidx# (== 0 (long (nbuf# nidx#))))))))]
     (native-buffer/native-buffer->string nbuf# 0 zero-pad-idx#)))

(set! *warn-on-reflection* true)

(defn greet [data]
  (let [s (ptr->string data)]
    (println "hello" (ptr->string data))
    (count s)))
