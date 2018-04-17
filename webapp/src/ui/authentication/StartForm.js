// @flow

import React from 'react';
import {Button} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';
import {Field, Form} from '../../components/form';

type Props = {
    loading: boolean;
    email: string;
    missingEmailError: boolean;
    onEmailChanged: (string) => void,
    onSubmit: (any) => void
}

export function StartForm({loading, email, missingEmailError, onEmailChanged, onSubmit}: Props) {
    return (
        <Form labelKey="authentication.start.label" onSubmit={onSubmit}>
            <Field
                labelKey="authentication.start.email.label"
                name="email"
                autoFocus
                readOnly={loading}
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
