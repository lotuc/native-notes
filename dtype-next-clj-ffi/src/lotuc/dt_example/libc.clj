(ns lotuc.dt-example.libc
  (:require
   [tech.v3.datatype.ffi :as dt-ffi]))

(def fn-defs
  {:memset {:rettype :pointer
            :argtypes [['buffer :pointer]
                       ['byte-value :int32]
                       ['n-bytes :size-t]]}
   :memcpy {:rettype :pointer
            ;; dst src size-t
            :argtypes [['dst :pointer]
                       ['src :pointer]
                       ['n-bytes :size-t]]}})

(def lib (dt-ffi/library-singleton #'fn-defs))
(dt-ffi/library-singleton-reset! lib)

(defmacro check-error
  [_fn-data & body]
  `(let [retval# (do ~@body)]
     retval#))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(do
  (declare memset)
  (declare memcpy))

(dt-ffi/define-library-functions
  fn-defs
  #(dt-ffi/library-singleton-find-fn lib %)
  ;; Check-error is called with the function defintion as the first argument
  ;; allowing you to do both pre/post error checking according to information in
  ;; the fn defintion above. The fn defintion must have also have :check-errors?
  ;; set to truthy
  check-error)
