(ns codemirror.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [codemirror.subs :as subs]
            [codemirror.events :as events]
            [cljsjs.codemirror]
            [cljsjs.codemirror.mode.clojure]
            [cljsjs.codemirror.addon.edit.matchbrackets]
            [cljsjs.codemirror.addon.comment.comment]))

(defn editor-component [theme]
  (reagent/create-class
   {:component-did-mount
    (fn [ta]
      (println "I mounted")
      (println "theme" @(re-frame/subscribe [::subs/theme]))
      (println (reagent/dom-node ta))
      (let [cm (js/CodeMirror.
                (reagent/dom-node ta)
                #js {:mode "clojure"
                     :theme @(re-frame/subscribe [::subs/theme])
                     :value "(ns test-code)\n\n(count '(1 2 3 4 5))"
                     :lineNumbers true
                     :lineWrapping true
                     :matchBrackets true})]
        (re-frame/dispatch-sync [::events/assoc-cm-editor cm])))

    :component-did-update
    (fn [this old-argv]
      (println "component did update")
      (println "theme" @(re-frame/subscribe [::subs/theme]))
      (println this)
      (println old-argv)
      (let [cm @(re-frame/subscribe [::subs/cm-editor])]
        (.setOption cm "theme" @(re-frame/subscribe [::subs/theme]))))
    ;; name your component for inclusion in error messages
    :display-name "complex-component"

    :component-will-unmount
    (fn []
      (println "component will unmount"))

    ;; note the keyword for this method
    :reagent-render
    (fn []
      (println "reagent-render")
      [:div])}))

(defn main-panel []
  (let [editor @(re-frame/subscribe [::subs/editor])
        theme @(re-frame/subscribe [::subs/theme])]
    (if (= editor :open)
      [:div
       [editor-component theme]
       [:select {:on-change #(re-frame/dispatch [::events/assoc-theme (-> % .-target .-value)])}
        [:option "default"]
        [:option "night"]
        [:option "material"]]
       [:button {:on-click #(re-frame/dispatch [::events/close-editor])} "close"]]
      [:button {:on-click #(re-frame/dispatch [::events/open-editor])} "open"]))
  )
