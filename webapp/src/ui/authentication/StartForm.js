// @flow

import React from 'react';
import {Button, Form, Header, Label} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

type Props = {
    loading: boolean;
    email: string;
    missingEmailError: boolean;
    onEmailChanged: (string) => void,
    onSubmit: (any) => void
}

export function StartForm({loading, email, missingEmailError, onEmailChanged, onSubmit}: Props) {
    let missingEmailErrorMessage;

    if (missingEmailError) {
        missingEmailErrorMessage = (
            <Label basic pointing color="red">
                <Interpolate i18nKey="authentication.start.email.validation.missing" />
            </Label>
        );
    }

    const onSubmitWrapper = (e) => {
        e.preventDefault();
        onSubmit();
    };

    const onEmailChangedWrapper = (e) => {
        onEmailChanged(e.target.value);
    };

    return (
        <Form action="#" onSubmit={onSubmitWrapper}>
            <Header dividing as="h4">
                <Interpolate i18nKey="authentication.start.label" />
            </Header>
            <Form.Field required error={missingEmailError}>
                <label>
                    <Interpolate i18nKey="authentication.start.email.label" />
                </label>
                <input type="email"
                       autoFocus
                       name="email"
                       placeholder=""
                       value={email}
                       readOnly={loading}
                       onChange={onEmailChangedWrapper} />
                {missingEmailErrorMessage}
            </Form.Field>
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.start.submit" />
            </Button>
        </Form>
    );
}
