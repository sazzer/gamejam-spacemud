import React from 'react';
import {I18nextProvider, translate} from 'react-i18next';
import { configure, addDecorator } from '@storybook/react';
import i18n from "../src/i18n";
import '@storybook/addon-console';
import { withConsole } from '@storybook/addon-console';
import '../public/semantic/semantic.min.css';
import '../public/semantic/semantic.slate.min.css';

addDecorator((story) => {
    const StoryWrapper = () => {
        return (
            <div className="ui">
                {story()}
            </div>
        );
    };

    const TranslatedStoryWrapper = translate(['space'], {wait: true})(StoryWrapper);

    return (
        <I18nextProvider i18n={i18n}>
            <TranslatedStoryWrapper />
        </I18nextProvider>
    );
});

addDecorator((storyFn, context) => withConsole()(storyFn)(context));

configure(() => {
    const req = require.context('../src', true, /\.stories\.js$/);
    req.keys().forEach((filename) => req(filename));
}, module);
