// @flow

import React from 'react';
import {LoginFormContainer} from "./LoginFormContainer";
import {connectStore} from 'redux-box'
import {AUTHENTICATE_STATUS_LOADING, authenticateModule} from "../../authentication";
import { ErrorMessage } from "../../components/ErrorMessage";

/** Mapping of error keys to the message keys */
const errorKeys = {
    "tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found/unknown-email": "authentication.login.problems.unknown-user",
    "tag:grahamcox.co.uk,2018,spacemud/users/problems/authentication/invalid-password": "authentication.login.problems.invalid-password",
};

type Props = {
    email: string,
    authenticateModule: any
}

/**
 * Wrapper around the Login Form that handles the interactions with Redux
 */
class ReduxAwareLoginForm extends React.Component<Props> {
    _onFormSubmitted = this._onFormSubmittedHandler.bind(this);

    render() {
        const { authenticateModule, email } = this.props;

        let error;
        if (authenticateModule.error) {
            const errorCode = errorKeys[authenticateModule.error];
            error = <ErrorMessage
                titleKey="authentication.login.problems.title"
                messageKey={errorCode} />;
        }

        return (
            <div>
                {error}
                <LoginFormContainer
                    loading={authenticateModule.status === AUTHENTICATE_STATUS_LOADING}
                    email={email}
                    onSubmit={this._onFormSubmitted} />
            </div>
        )
    }

    /**
     * Handler for when the Login form is submitted
     * @param {string} password The password that was submitted
     * @private
     */
    _onFormSubmittedHandler(password) {
        const { authenticateModule, email } = this.props;
        authenticateModule.authenticate(email, password);
    }
}

export const ConnectedLoginForm = connectStore({
    authenticateModule
})(ReduxAwareLoginForm);
