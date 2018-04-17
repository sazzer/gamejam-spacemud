import React from 'react';
import { storiesOf } from '@storybook/react';
import { action } from '@storybook/addon-actions';
import { Form } from 'semantic-ui-react';
import { Field } from '../Field';

storiesOf('Components/Form/Field', module)
    .addDecorator((story) => (
            <Form>
                {story()}
            </Form>
        )
    )
    .addWithJSX('Empty', () => (
        <Field labelKey="authentication.start.email.label"
               type="text"
               name="example"
               value=""
               onChange={action('change')} />
    ), { showFunctions: false })
    .addWithJSX('Empty and Focused', () => (
        <Field labelKey="authentication.start.email.label"
               type="text"
               name="example"
               value=""
               autoFocus
               onChange={action('change')} />
    ), { showFunctions: false })
    .addWithJSX('Populated', () => (
        <Field labelKey="authentication.start.email.label"
               type="text"
               name="example"
               value="test@example.com"
               onChange={action('change')} />
    ), { showFunctions: false })
    .addWithJSX('Populated and Read Only', () => (
        <Field labelKey="authentication.start.email.label"
               type="text"
               name="example"
               value="test@example.com"
               readOnly
               onChange={action('change')} />
    ), { showFunctions: false })
    .addWithJSX('Error', () => (
        <Field labelKey="authentication.start.email.label"
               errorKey="authentication.start.email.validation.missing"
               type="text"
               name="example"
               value=""
               onChange={action('change')} />
    ), { showFunctions: false })
;
