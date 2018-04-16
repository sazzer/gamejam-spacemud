import React from 'react';
import { storiesOf } from '@storybook/react';
import { action } from '@storybook/addon-actions';
import { Segment } from 'semantic-ui-react';
import { StartForm } from '../StartForm';

storiesOf('Authentication/StartForm', module)
    .add('Ready for input', () => (
        <Segment>
            <StartForm loading={false} onSubmit={action('submit')} />
        </Segment>
    ))
    .add('Checking input', () => (
        <Segment>
            <StartForm loading={true} onSubmit={action('submit')} />
        </Segment>
    ));
