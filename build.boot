(set-env!
 :source-paths #{"src"}
 :resource-paths #{"input"}
 :dependencies
 '[[adzerk/boot-cljs                        "1.7.228-2"]
   [adzerk/boot-reload                      "0.5.2" :scope "test"]
   [adzerk/boot-cljs-repl                   "0.3.3"]
   [crisptrutski/boot-cljs-test             "0.3.5-SNAPSHOT"]
   [eftest                                  "0.5.2"]
   [org.clojure/math.combinatorics          "0.1.4"]
   [com.cemerick/piggieback                 "0.2.1" :scope "test"]
   [com.google.cloud/google-cloud-pubsub    "0.44.0-beta"]
   [com.google.cloud/google-cloud-firestore "0.40.0-beta"]
   [com.google.cloud/google-cloud-storage   "1.29.0"]
   [deraen/boot-sass                        "0.3.1"]
   [metosin/bat-test                        "0.4.0" :scope "test"]
   [org.clojure/core.async                  "0.4.474"]
   [org.clojure/core.logic                  "0.8.11"]
   [org.clojure/clojure                     "1.9.0"]
   [org.clojure/clojurescript               "1.10.238"]
   [org.clojure/test.check                  "0.10.0-alpha2" :scope "test"]
   [org.clojure/tools.logging               "0.3.1"]
   [org.clojure/tools.nrepl                 "0.2.12" :scope "test"]
   [pandeiro/boot-http                      "0.7.6"]
   [reagent                                 "0.7.0"]
   [re-frame                                "0.10.5"]
   [sablono                                 "0.7.7"]
   [weasel                                  "0.7.0" :scope "test"]])

(require '[adzerk.boot-cljs      :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload    :refer [reload]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]]
         '[deraen.boot-sass :refer [sass]]
         '[metosin.bat-test :refer [bat-test]]
         '[pandeiro.boot-http    :refer [serve]])

(task-options!
 serve     {:port 10080}
 reload    {:port 10950}
 repl      {:port 10930
            :bind "0.0.0.0"})

(deftask deps [] (repl :server true))

(deftask build
  "Builds cljs and code for production"
  []
  (comp
   (cljs)
   (sass)
   (target)))

(deftask testing []
  (merge-env! :source-paths #{"test"})
  identity)

(deftask test
  "Runs all test"
  []
  (comp
   (testing)
   (bat-test :parallel true :fail-fast? true :report {:type :progress})))

(deftask autotest
  "Runs all test; loops on file modification"
  []
  (comp
   (watch)
   (notify :audible true :theme "woodblock" :soundfiles {:success "silence.wav"})
   (test)))

(deftask development
  "Launch Immediate Feedback Development Environment"
  []
  (comp
   (serve)
   (watch)
   (reload)
   (cljs-repl) ;; order is important!
   (cljs)
   (sass)
   (target)))
