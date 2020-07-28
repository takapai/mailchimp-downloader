(ns mailchimp-downloader.core
  (:require [clojure.pprint :as printer])
  (:require [clojure.string :as str])
  (:require [mailchimp-downloader.writer.csv :as mccsv])
  (:require [mailchimp-downloader.writer.s3 :as s3])
  (:require [clj-http.client :as http])
  (:require [cheshire.core :as json]))

(defonce MAILCHIMPKEY (System/getenv "MAILCHIMPKEY"))
(defonce MAILCHIMPLISTID (System/getenv "MAILCHIMPLISTID"))

(defn extract-list
  [apikey listid]
  (let [dc (last (str/split apikey #"-"))
        apiurl (str "https://" dc ".api.mailchimp.com/export/1.0/list/")]
    (http/post apiurl
               {:form-params {:apikey apikey, :id listid}
                :content-type :json})))

(defn colname->keyword
  "Column names with spaces and Japanese transformed into keywords"
  [colname]
  (-> colname
      (str/replace #"メールアドレス" "email")
      (str/replace #"[ ]" "_")
      (str/lower-case)
      (keyword)))

(defn transform-header
  [header]
  (map colname->keyword header))

(defn apiresp->mcaudience
  "Parse the Mailchimp API export response and returns a list of
  records keyed by header name."
  [apiresp]
  (let [body (:body apiresp)
        rows (str/split-lines body)
        header (json/parse-string (first rows))
        reckeys (transform-header header)
        records (map json/parse-string (rest rows))]
    (map #(zipmap reckeys %) records)))

(defn -main [& args]
  (let [resp (extract-list MAILCHIMPKEY MAILCHIMPLISTID)
        mcaudience (apiresp->mcaudience resp)
        timestr (str (java.time.LocalDateTime/now))
        cleanedtime (str/replace timestr #"[:\-T\.]" "")
        fname (str "mailchimpbkup-" cleanedtime ".csv")]
    (mccsv/write-mcrecords fname mcaudience)
    (print "Writing to S3, HTTP response:\n")
    (printer/pprint (s3/save-fname-to-backups3 fname))))

(comment
  (defonce resp (extract-list MAILCHIMPKEY MAILCHIMPLISTID))
  (def header (json/parse-string (first (str/split-lines (:body resp)))))
  (def kotaro (json/parse-string (second (str/split-lines (:body resp)))))
  (def mcaudience (apiresp->mcaudience resp))
  (mccsv/write-mcrecords "/tmp/mcextract3.csv" mcaudience)
  )
