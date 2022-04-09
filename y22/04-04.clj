; Given two strings n and m, return true if they are equal 
; when both are entered into text editors. 
; But: 
; a # means a backspace character (deleting backwards), and
; a % means a delete character (deleting forwards). 

(defn eq-with-del [a b] (= (parse a) (parse b)))

(use '[clojure.string :only (replace)])

(def del-back #"([ a-zA-Z0-9]#)|(^#)")
(def del-fwd #"(%[ a-zA-Z0-9])|(%$)")

(defn parse [s] (loop [res s]
                  (let [p (-> res
                              (replace del-back "")
                              (replace del-fwd "")
                              (replace "%#" "#%"))]
                    (if (or (.contains p "#") (.contains p "%"))
                      (recur p)
                      p))))

(comment

  (def s1 "a##ba")
  (def s2 "a#b#c#%d%f%%ga")

  (.contains "" "#")
  
  (eq-with-del "a" "b")

  (parse "fi##f%%%th %%year #time###")

  (parse s2)

  (use 'clojure.test)
  (run-tests)

  (deftest test-eq-with-del
    (testing "parse"
      (testing "delete"
        (is (= (parse "a#b#c#") ""))))

    (testing "backspace"
      (is (true? (eq-with-del "abc" "abc")))
      (is (true? (eq-with-del "ab#c" "ac")))
      (is (true? (eq-with-del "abc##" "a")))
      (is (true? (eq-with-del "#abc" "abc")))
      (is (true? (eq-with-del "a#b#c#" "")))

      (is (false? (eq-with-del "abc" "ab")))
      (is (false? (eq-with-del "ab#c" "abc")))
      (is (false? (eq-with-del "abc##" "abc")))
      (is (false? (eq-with-del "#abc" "bc")))
      (is (false? (eq-with-del "a#b#c#" "ab"))))

    (testing "delete"
      (is (true? (eq-with-del "ab%c" "ab")))
      (is (true? (eq-with-del "abc%%" "abc")))
      (is (true? (eq-with-del "%abc" "bc")))
      (is (true? (eq-with-del "a%b%c%" "a"))))

    (testing "corners"
      (is (false? (eq-with-del "fi##f%%%th %%year #time###" "fifth year time")))))

  )
