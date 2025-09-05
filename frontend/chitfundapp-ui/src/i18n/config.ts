import i18n from "i18next";
import { initReactI18next } from "react-i18next";

i18n.use(initReactI18next).init({
    resources: {
        en: { translation: { welcome: "Welcome to Chitfund App" } },
        ta: { translation: { welcome: "சிட்ஃபண்ட் செயலிக்கு வரவேற்கிறோம்" } }
    },
    lng: "en",
    fallbackLng: "en",
    interpolation: { escapeValue: false },
});

export default i18n;