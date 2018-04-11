import 'babel-polyfill';

import React from 'react';
import ReactDOM from 'react-dom';
import { I18nextProvider, translate } from 'react-i18next';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/redux'
import registerServiceWorker from './registerServiceWorker';
import { UI } from "./ui";
import i18n from './i18n';
import { httpClient } from "./api";

/**
 * The contents of the app, wrapped in the translations layer
 */
const TranslatedAppContents = translate(['space'], {wait: true})(UI);

/**
 * The wrapper around the main application to set everything up
 * @return {*} the main application
 */
const AppWrapper = () => (
    <I18nextProvider i18n={ i18n }>
        <Provider store={store}>
            <Router>
                <TranslatedAppContents />
            </Router>
        </Provider>
    </I18nextProvider>
);

ReactDOM.render(<AppWrapper />, document.getElementById('root'));
registerServiceWorker();

httpClient.get('/')
    .then(console.log);
