(ns codemirror.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::editor
 (fn [db]
   (:editor db)))

(re-frame/reg-sub
 ::theme
 (fn [db]
   (:theme db)))

(re-frame/reg-sub
 ::cm-editor
 (fn [db]
   (:cm-editor db)))
