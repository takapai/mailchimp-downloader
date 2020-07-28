(defproject mailchimp-downloader "0.1.0-SNAPSHOT"
  :description "Automated backups of mailchimp audience to S3"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.csv "1.0.0"]
                 [clj-http "3.10.1"]
                 [cheshire "5.10.0"]
                 [com.cognitect.aws/api "0.8.469"]
                 [com.cognitect.aws/endpoints "1.1.11.837"]
                 [com.cognitect.aws/s3 "807.2.729.0"]]
  :main mailchimp-downloader.core
  :repl-options {:init-ns mailchimp-downloader.core})
