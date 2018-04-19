// @flow

import React from 'react';
import {RegisterFormContainer} from "./RegisterFormContainer";
import {connectStore} from 'redux-box'
import {CREATE_USER_STATUS_LOADING, createUserModule} from "../../users";

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

        return (
            <RegisterFormContainer
                loading={createUserModule.status === CREATE_USER_STATUS_LOADING}
                email={email}
                onSubmit={this._onFormSubmitted} />
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
