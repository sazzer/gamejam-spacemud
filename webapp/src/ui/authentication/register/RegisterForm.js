// @flow

import React from 'react';
import {Button} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';
import {Field, Form} from '../../../components/form';

type Props = {
    loading: boolean;
    email: string;
    password: string;
    missingPasswordError: boolean;
    onPasswordChanged: (string) => void,
    password2: string;
    missingPassword2Error: boolean;
    mismatchedPasswordsError: boolean;
    onPassword2Changed: (string) => void,
    displayName: string;
    missingDisplayNameError: boolean;
    onDisplayNameChanged: (string) => void,
    onSubmit: (any) => void
}

export function RegisterForm({
                                 loading,
                                 email,
                                 password,
                                 missingPasswordError,
                                 onPasswordChanged,
                                 password2,
                                 missingPassword2Error,
                                 mismatchedPasswordsError,
                                 onPassword2Changed,
                                 displayName,
                                 missingDisplayNameError,
                                 onDisplayNameChanged,
                                 onSubmit
                             }: Props) {
    return (
        <Form labelKey="authentication.register.label" onSubmit={onSubmit}>
            <Field
                labelKey="authentication.register.email.label"
                name="email"
                type="email"
                value={email}
                readOnly
                onChange={() => {}} />
            <Field
                labelKey="authentication.register.password.label"
                name="password"
                autoFocus
                readOnly={loading}
                type="password"
                value={password}
                errorKey={missingPasswordError ? "authentication.register.password.validation.missing" : undefined}
                onChange={onPasswordChanged} />
            <Field
                labelKey="authentication.register.password2.label"
                name="password2"
                readOnly={loading}
                type="password"
                value={password2}
                errorKey={
                    missingPassword2Error ? "authentication.register.password2.validation.missing" :
                        mismatchedPasswordsError ? "authentication.register.password2.validation.different" : undefined}
                onChange={onPassword2Changed} />
            <Field
                labelKey="authentication.register.displayName.label"
                name="displayName"
                readOnly={loading}
                type="text"
                value={displayName}
                errorKey={missingDisplayNameError ? "authentication.register.displayName.validation.missing" : undefined}
                onChange={onDisplayNameChanged} />
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.register.submit" />
            </Button>
        </Form>
    );
}
