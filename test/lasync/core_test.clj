(ns lasync.core-test
  (:use clojure.test
        lasync.core)
  (:import [lasync.limitq ArrayLimitedQueue LinkedLimitedQueue]))

(deftest sanity-test
  (testing "checking sanity like size + state"
    (let [threads 3
          pool (limit-pool :threads threads)]

      (is (= threads (.getCorePoolSize pool)))
      (is (instance? ArrayLimitedQueue (.getQueue pool)))
      (is (false? (.isShutdown pool)))
      (is (false? (.isTerminated pool))))))

(deftest queue-test
  (testing "check queue injection"
    (let [capacity 11
          pool (limit-pool :threads 1 :queue (LinkedLimitedQueue. capacity))]
      (is (instance? LinkedLimitedQueue (.getQueue pool)))
      (is (= capacity (.remainingCapacity (.getQueue pool)))))))

(deftest queue-array-test
  (testing "check array blocking queue"
    (let [capacity 11
          pool (limit-pool :threads 1 :queue (ArrayLimitedQueue. capacity))]
      (is (instance? ArrayLimitedQueue (.getQueue pool)))
      (is (= capacity (.remainingCapacity (.getQueue pool)))))))

