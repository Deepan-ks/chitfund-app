import { useTranslation } from "react-i18next";

function App() {
  const { t, i18n } = useTranslation();

  const switchLanguage = (lang: string) => {
    i18n.changeLanguage(lang);
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <h1 className="text-2xl font-bold text-gray-800">{t("welcome")}</h1>

      <div className="mt-4 flex gap-2">
        <button
          onClick={() => switchLanguage("en")}
          className="px-4 py-2 rounded bg-blue-500 text-white"
        >
          English
        </button>
        <button
          onClick={() => switchLanguage("ta")}
          className="px-4 py-2 rounded bg-green-500 text-white"
        >
          தமிழ்
        </button>
      </div>
    </div>
  );
}

export default App;
