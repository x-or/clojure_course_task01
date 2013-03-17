(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))


; индексы внутри элемента html
(def html:attributes 1)
(def html:sub-element0 2)

(defn scan-links [html-data]
    (let [; выполнить функцию на вложенных элементах и избавиться от вложения списков
          ; пустые подспиcки также удаляются
          other-links (mapcat scan-links (filter vector? html-data))
          ; получить значение атрибута class елемента текущего уровня в html-data
          html-att-class (:class (get html-data html:attributes))]
      ; этот элемент содержит {:class \"r\"} ?
      (if (= "r" html-att-class)
          ; да. извлечь href из вложенного элемента :a
          (let [href-str (:href (get (get html-data html:sub-element0) html:attributes))]
            ; добавить href в голову списка
            (cons href-str other-links))
          ; нет. передать полученные ссылки или пустой список вызывающей функции
          other-links)))

(defn get-links []
" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]
"
  (let [data (parse "clojure_google.html")]
    (vec (scan-links data))))

(defn -main []
  (println (str "Links: " (get-links)))
  (println (str "Found " (count (get-links)) " links!")))


