(defproject babel "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :global-vars {*warn-on-reflection* true}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [fungp "0.3.2"]]

  :jvm-opts ["-Xms1g" "-Xmx1g" "-XstartOnFirstThread"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :omit-source true

  :source-paths ["src"]
  :java-source-paths ["gen" "base"]
  :resource-paths ["resources"]

  :jar-name "babel.jar"
  :uberjar-name "babel-standalone.jar"

  :aot :all
  :main babel.main)
