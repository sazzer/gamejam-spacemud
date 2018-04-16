import React from 'react';
import { storiesOf } from '@storybook/react';
import { action } from '@storybook/addon-actions';
import { Segment } from 'semantic-ui-react';
import { LoginForm } from '../LoginForm';

storiesOf('Authentication/LoginForm', module)
    .add('Ready for input', () => (
        <Segment>
            <LoginForm email="test@example.com" loading={false} onSubmit={action('submit')} />
        </Segment>
    ))
    .add('Checking input', () => (
        <Segment>
            <LoginForm email="test@example.com" loading={true} onSubmit={action('submit')} />
        </Segment>
    ));
