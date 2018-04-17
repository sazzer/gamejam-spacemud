// @flow

import React from 'react';
import {Button, Form, Header} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';
import {Field} from '../../components/form';

type Props = {
    loading: boolean;
    email: string;
    missingEmailError: boolean;
    onEmailChanged: (string) => void,
    onSubmit: (any) => void
}

export function StartForm({loading, email, missingEmailError, onEmailChanged, onSubmit}: Props) {
    const onSubmitWrapper = (e) => {
        e.preventDefault();
        onSubmit();
    };

    return (
        <Form action="#" onSubmit={onSubmitWrapper}>
            <Header dividing as="h4">
                <Interpolate i18nKey="authentication.start.label" />
            </Header>
            <Field
                labelKey="authentication.start.email.label"
                name="email"
                autoFocus
                type="email"
                value={email}
                errorKey={missingEmailError ? "authentication.start.email.validation.missing" : undefined}
                onChange={onEmailChanged} />
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.start.submit" />
            </Button>
        </Form>
    );
}
