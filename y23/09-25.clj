(require '[clojure.data.json :as json]
         '[clojure.string :as str])

(def pokeapi-base-url "https://pokeapi.co/api/v2/type/")

(defn oxford-commaize
  "Joins the list in an oxford-comma way. If only one element, return that"
  [xs] (if (= 1 (count xs))
         (first xs)
         (str (str/join ", " (butlast xs)) ", and " (last xs))))


(defn strenghts-weaknesses
  "Given a pokemon type, returns what that type is weak against, and strong against."
  [type]
  (let [damages (get type "damage_relations")
        strong (map #(get % "name") (get damages "double_damage_to"))
        weak (map #(get % "name") (get damages "double_damage_from"))]
    (str "Weak against " (oxford-commaize weak) ". Strong against " (oxford-commaize strong))))

(defn fetch-type-info
  "Fetches the type information from the pokeapi, and parses it into a map"
  [type] (json/read-str (slurp (str pokeapi-base-url type))))

(def not-found-msgs
  ["%s is not an existing type, dingus!"
   "What do you mean %s? That's not a type!"
   "I'm sorry, what? %s? Never heard of it"
   "Don't know about %s, but I bet it's weak against common sense."
   "Are you sure you don't mean fighting? %s sounds kinda weird"
   "I think I once ate %s, but not sure"])


(defn not-found 
  "Prints a random message from the list of not-found messages, with the type formatted in"
  [type]
  (format (rand-nth not-found-msgs) type))


(defn type-matchup
  "Fetches the type information, then presents the information in a nice way, or a not found message if not found."
  [type]
  (try
    (strenghts-weaknesses (fetch-type-info type))
    (catch Exception _e (not-found type))))




(comment
  
  (type-matchup "fairy")


  (def fire (fetch-type-info "fire"))
  (def fighting (fetch-type-info "fighting"))

  (def fweak (get-in fighting ["damage_relations" "double_damage_from" "name"]))

  (get-in fighting ["damage_relations" "double_damage_from"])
  (map #(get % "name") fweak)

  (fetch-type-info "asdd")

  (type-matchup (fetch-type-info "asd"))

  (oxford-commaize ["test" "asd"])

  (select-keys fighting ["damage_relations"])
  (select-keys (select-keys fighting ["damage_relations"]) ["double_damage_from"])





  (let [damages (get fighting "damage_relations")
        strong (map #(get % "name") (get damages "double_damage_to"))
        weak (map #(get % "name") (get damages "double_damage_from"))]
    {:strong strong
     :weak weak})

  (def types ["normal" "rock" "steel" "ice" "dark"])

  (oxford-commaize types)

  (oxford-commaize ["x"])

  (str/join ", " types)

  (->> (get-in fighting ["damage_relations" "double_damage_from"])
       (map #(get % "name")))

  (json/read-str (slurp pokeapi-base-url)))