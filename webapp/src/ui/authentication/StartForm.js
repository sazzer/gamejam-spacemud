import React from 'react';
import PropTypes from 'prop-types';
import {Button, Form, Header, Label} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

export function StartForm({loading, email, missingEmailError, onEmailChanged, onSubmit}) {
    let missingEmailErrorMessage;

    if (missingEmailError) {
        missingEmailErrorMessage = (
            <Label basic pointing color="red">
                <Interpolate i18nKey="authentication.start.email.validation.missing" />
            </Label>
        );
    }

    return (
        <Form action="#" onSubmit={onSubmit}>
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
                       onChange={onEmailChanged} />
                {missingEmailErrorMessage}
            </Form.Field>
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.start.submit" />
            </Button>
        </Form>
    );
}

StartForm.propTypes = {
    loading: PropTypes.bool.isRequired,
    email: PropTypes.string.isRequired,
    missingEmailError: PropTypes.bool.isRequired,
    onEmailChanged: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired
};

