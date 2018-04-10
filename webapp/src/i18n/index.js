import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import defaultTranslations from './messages.json';

i18n
    .use(LanguageDetector)
    .init({
        resources: {
            dev: {
                space: defaultTranslations
            }
        },

        // have a common namespace used around the full app
        ns: ['space'],
        defaultNS: 'space',

        debug: true,

        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
