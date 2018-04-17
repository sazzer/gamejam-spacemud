// @flow

import React from 'react';
import {Button, Form, Header} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';
import {Field} from '../../components/form';

type Props = {
    loading: boolean;
    email: string;
    password: string;
    missingPasswordError: boolean;
    onPasswordChanged: (string) => void,
    onSubmit: (any) => void
}

export function LoginForm({loading, email, password, missingPasswordError, onPasswordChanged, onSubmit}: Props) {
    const onSubmitWrapper = (e) => {
        e.preventDefault();
        onSubmit();
    };

    return (
        <Form action="#" onSubmit={onSubmitWrapper}>
            <Header dividing as="h4">
                <Interpolate i18nKey="authentication.login.label" />
            </Header>
            <Field
                labelKey="authentication.login.email.label"
                name="email"
                type="email"
                value={email}
                readOnly
                onChange={() => {}} />
            <Field
                labelKey="authentication.login.password.label"
                name="password"
                autoFocus
                type="password"
                value={password}
                errorKey={missingPasswordError ? "authentication.login.password.validation.missing" : undefined}
                onChange={onPasswordChanged} />
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.login.submit" />
            </Button>
        </Form>
    );
}
