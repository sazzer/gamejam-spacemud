import React from 'react';
import { storiesOf } from '@storybook/react';
import { action, decorateAction } from '@storybook/addon-actions';
import { Segment } from 'semantic-ui-react';
import { StartForm } from '../StartForm';

const submitAction = decorateAction([
    ([e]) => {
        e.preventDefault();
        return e;
    }
]);

storiesOf('Authentication/StartForm', module)
    .add('Ready for input', () => (
        <Segment>
            <StartForm loading={false} email="" missingEmailError={false} onEmailChanged={action('emailChanged')} onSubmit={submitAction('submit')} />
        </Segment>
    ))
    .add('Missing Email Address', () => (
        <Segment>
            <StartForm loading={false} email="" missingEmailError={true} onEmailChanged={action('emailChanged')} onSubmit={submitAction('submit')} />
        </Segment>
    ))
    .add('Checking input', () => (
        <Segment>
            <StartForm loading={true} email="test@example.com" missingEmailError={false} onEmailChanged={action('emailChanged')} onSubmit={submitAction('submit')} />
        </Segment>
    ));
