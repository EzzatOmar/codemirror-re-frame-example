(ns codemirror.db
  (:require [reagent.core :as reagent]))

(def app-db (reagent/atom {}))

(def watcher (add-watch app-db :logger #(-> %4 clj->js js/console.log)))
