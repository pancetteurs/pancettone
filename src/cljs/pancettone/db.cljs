(ns pancettone.db)

(def default-db
  {:active-panel :home-panel
   :cities ["London" "Paris" "Bruxelles"]
   :tickets {}
   :search-form {:date "" :from "" :to ""}
   :user nil
   :root nil})
