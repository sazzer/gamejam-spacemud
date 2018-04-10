import React from 'react';
import { I18nextProvider } from 'react-i18next';
import { configure, addDecorator } from '@storybook/react';
import 'semantic-ui-css/semantic.min.css';
import i18n from "../src/i18n";
import '@storybook/addon-console';
import { withConsole } from '@storybook/addon-console';

addDecorator((story) => {
    return (
        <I18nextProvider i18n={i18n}>
            <div className="ui">
                {story()}
            </div>
        </I18nextProvider>
    );
});

addDecorator((storyFn, context) => withConsole()(storyFn)(context));

configure(() => {
    const req = require.context('../src', true, /\.stories\.js$/);
    req.keys().forEach((filename) => req(filename));
}, module);
