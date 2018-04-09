(ns codemirror.events
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [codemirror.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/app-db))

(re-frame/reg-event-db
 ::open-editor
 (fn  [_ _]
   (reset! db/app-db {:editor :open :theme "default"})))

(re-frame/reg-event-db
 ::close-editor
 (fn  [_ _]
   (reset! db/app-db {:editor :close})))

(re-frame/reg-event-db
 ::assoc-theme
 (fn [{db :db} event-vector]
   (swap! db/app-db (fn [db] (assoc db :theme (second event-vector))))))

(re-frame/reg-event-db
 ::assoc-cm-editor
 (fn [{db :db} event-vector]
   (swap! db/app-db (fn [db] (assoc db :cm-editor (second event-vector))))))
