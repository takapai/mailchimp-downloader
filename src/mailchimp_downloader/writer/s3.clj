(ns mailchimp-downloader.writer.s3
  (:require [clojure.java.io :as io])
  (:require [cognitect.aws.client.api :as aws]))

(defonce BACKUPBUCKET (System/getenv "MCBACKUPBUCKET"))

(def s3 (aws/client {:api :s3}))

(defn file->bytes [file]
  (with-open [xin (io/input-stream file)
              xout (java.io.ByteArrayOutputStream.)]
    (io/copy xin xout)
    (.toByteArray xout)))

(defn save-fname-to-backups3
  [fname]
  (let [f (io/file fname)
        s3cmd {:op :PutObject
               :request {:Bucket BACKUPBUCKET
                         :Key fname
                         :Body (file->bytes f)}}]
    (:http-response (meta (aws/invoke s3 s3cmd)))))
