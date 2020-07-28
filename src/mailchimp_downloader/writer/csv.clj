(ns mailchimp-downloader.writer.csv
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io]))

(defn write-csv
  [file csv-data]
  (with-open [writer (io/writer file)]
    (csv/write-csv writer csv-data)))

(defn mcrecords->csvformat
  [mcrecords]
  (let [columns (-> mcrecords first keys)
        headers (mapv name columns)
        rows (mapv #(mapv % columns) mcrecords)]
    (into [headers] rows)))

(defn write-mcrecords
  "Write the records in the format of list of maps to the csvfile"
  [file mcrecords]
  (->> mcrecords mcrecords->csvformat (write-csv file)))
