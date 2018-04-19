// @flow

import React from 'react';
import {RegisterFormContainer} from "./RegisterFormContainer";
import {connectStore} from 'redux-box'
import {CREATE_USER_STATUS_LOADING, createUserModule} from "../../users";
import { ErrorMessage } from "../../components/ErrorMessage";

/** Mapping of error keys to the message keys */
const errorKeys = {
    "tag:grahamcox.co.uk,2018,spacemud/users/problems/duplicate/duplicate-email": "authentication.register.problems.duplicate-email"
};

type Props = {
    email: string,
    createUserModule: any
}

/**
 * Wrapper around the Register Form that handles the interactions with Redux
 */
class ReduxAwareRegisterForm extends React.Component<Props> {
    _onFormSubmitted = this._onFormSubmittedHandler.bind(this);

    render() {
        const { createUserModule, email } = this.props;

        let error;
        if (createUserModule.error) {
            const errorCode = errorKeys[createUserModule.error];
            error = <ErrorMessage
                titleKey="authentication.register.problems.title"
                messageKey={errorCode} />;
        }

        return (
            <div>
                {error}
                <RegisterFormContainer
                    loading={createUserModule.status === CREATE_USER_STATUS_LOADING}
                    email={email}
                    onSubmit={this._onFormSubmitted} />
            </div>
        )
    }

    /**
     * Handler for when the Register form is submitted
     * @param {string} password The password that was submitted
     * @param {string} displayName The display name that was submitted
     * @private
     */
    _onFormSubmittedHandler(password, displayName) {
        const { createUserModule, email } = this.props;
        createUserModule.createUser(email, password, displayName);
    }
}

export const ConnectedRegisterForm = connectStore({
    createUserModule
})(ReduxAwareRegisterForm);
