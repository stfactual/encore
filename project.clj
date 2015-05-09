(defproject stfactual/encore "1.3.2"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Shared support utils for taoensso.com Clojure libs"
  :url "https://github.com/ptaoussanis/encore"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert* true}
  :dependencies
  [[org.clojure/clojure    "1.4.0"]
   ;; [org.clojure/clojure "1.5.1"] ; Soon...
   ]

  :test-paths ["test" "src"]
  :profiles
  {;; :default [:base :system :user :provided :dev]
   :1.5  {:dependencies [[org.clojure/clojure "1.5.1"]]}
   :1.6  {:dependencies [[org.clojure/clojure "1.6.0"]]}
   :test {:dependencies [[expectations            "1.4.56"]
                         [reiddraper/simple-check "0.5.6"]]
          :plugins [[lein-expectations "0.0.8"]
                    [lein-autoexpect   "1.2.2"]]}
   :dev* [:dev {:jvm-opts ^:replace ["-server"]
                :hooks [cljx.hooks leiningen.cljsbuild]}]
   :dev
   [:1.6 :test
    {:dependencies
     [[org.clojure/clojurescript "0.0-2173"]
      [org.clojure/core.async    "0.1.278.0-76b25b-alpha"]]
     :plugins
     [[lein-ancient                    "0.5.4"]
      [com.keminglabs/cljx             "0.3.2"] ; Must precede Austin!
      [com.cemerick/austin             "0.1.4"]
      [lein-cljsbuild                  "1.0.2"]
      [com.cemerick/clojurescript.test "0.2.2"]
      [codox                           "0.6.7"]]

     :cljx
     {:builds
      [{:source-paths ["src" "test"] :rules :clj  :output-path "target/classes"}
       {:source-paths ["src" "test"] :rules :cljs :output-path "target/classes"}]}

     :cljsbuild
     {:test-commands {"node"    ["node" :node-runner "target/main.js"]
                      "phantom" ["phantomjs" :runner "target/main.js"]}
      :builds
      [{:id :main
        :source-paths ["src" "test" "target/classes"]
        :compiler     {:output-to "target/main.js"
                       :optimizations :advanced
                       :pretty-print false}}]}}]}

  :codox {:sources ["target/classes"]}
  :aliases
  {"test-all"   ["with-profile" "default:+1.5:+1.6" "expectations"]
   ;; "test-all"   ["with-profile" "default:+1.6" "expectations"]
   "test-auto"  ["with-profile" "+test" "autoexpect"]
   "build-once" ["do" "cljx" "once," "cljsbuild" "once"]
   "deploy-lib" ["do" "build-once," "deploy" "clojars," "install"]
   "start-dev"  ["with-profile" "+dev*" "repl" ":headless"]}

  :repositories {"factual" "http://maven.corp.factual.com/nexus/content/groups/public/"
                 "releases" {:url "http://maven.corp.factual.com/nexus/content/repositories/releases"
                             :sign-releases false}
                 "snapshots" {:url      "http://maven.corp.factual.com/nexus/content/repositories/snapshots/"
                              :snapshot {:update :always}}})
