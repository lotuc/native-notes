(ns build
  (:require
   [babashka.process :as p]))

(defn native-image-build []
  (let [cmd (concat ["native-image"
                     "--report-unsupported-elements-at-runtime"
                     "--no-fallback"
                     "--features=clj_easy.graal_build_time.InitClojureClasses"
                     "-H:+ReportExceptionStackTraces"
                     "-J-Dclojure.spec.skip-macros=true"
                     "-J-Dclojure.compiler.direct-linking=true"
                     "-J-Dtech.v3.datatype.graal-native=true"
                     "-jar"
                     "dt-example.jar"
                     "lotuc.dt-example.libc-invoking"]
                    *command-line-args*)]
    (println cmd)
    (apply p/shell {:dir "target"} cmd)))

(defn native-image-shared-library-build []
  (let [cmd (concat ["native-image"
                     "--report-unsupported-elements-at-runtime"
                     "--no-fallback"
                     "--features=clj_easy.graal_build_time.InitClojureClasses"
                     "-H:+ReportExceptionStackTraces"
                     "-J-Dclojure.spec.skip-macros=true"
                     "-J-Dclojure.compiler.direct-linking=true"
                     "-J-Dtech.v3.datatype.graal-native=true"
                     "--shared"
                     "-jar"
                     "dt-example.jar"
                     "-H:Name=libgreet"]
                    *command-line-args*)]
    (println cmd)
    (apply p/shell {:dir "target"} cmd)))

(defn compile-libgreet-invoking []
  (let [cmd ["g++" "./src-graal/lotuc/dt_example/libgreet_invoking.cpp"
             "-L" "./target" "-I" "./target" "-lgreet"
             "-o" "./target/libgreet-invoking"]]
    (println cmd)
    (apply p/shell cmd)))
