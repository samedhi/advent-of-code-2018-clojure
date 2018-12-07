2(set-env!
 :source-paths #{"src"}
 :resource-paths #{"input"}
 :dependencies
 '[[eftest                                  "0.5.2"]
   [org.clojure/math.combinatorics          "0.1.4"]
   [metosin/bat-test                        "0.4.0" :scope "test"]
   [org.clojure/core.async                  "0.4.474"]
   [org.clojure/core.logic                  "0.8.11"]
   [org.clojure/clojure                     "1.9.0"]
   [org.clojure/test.check                  "0.10.0-alpha2" :scope "test"]
   [org.clojure/tools.logging               "0.3.1"]
   [org.clojure/tools.nrepl                 "0.2.12" :scope "test"]])

(task-options!
 repl      {:port 10930
            :bind "0.0.0.0"})

