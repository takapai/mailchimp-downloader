(ns mailchimp-downloader.core-test
  (:require [clojure.test :refer :all]
            [mailchimp-downloader.core :refer :all]))

(defn no-spaces?
  [k]
  (not (clojure.string/includes? (str k) " ")))

(deftest s->keyword-test
  (testing "Spaces are removed"
    (is (no-spaces? (s->keyword "hello world"))))
  (testing "Converted to keyword"
    (is (keyword? (s->keyword "yesterday all my troubles were so far away")))))

(deftest transform-header-test
  (testing "Can handle a list of Japanese and spaced string"
    (is (= '(:email :hello_world :ip_address)
           (transform-header '("メールアドレス" "hello world" "IP Address"))))))
