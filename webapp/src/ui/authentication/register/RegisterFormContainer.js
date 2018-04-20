// @flow

import React from 'react';
import {RegisterForm} from './RegisterForm';

type Props = {
    email: string,
    loading: boolean,
    onSubmit: (string, string) => void
}

type State = {
    password: string,
    missingPasswordError: boolean,
    password2: string,
    missingPassword2Error: boolean,
    mismatchedPasswordsError: boolean,
    displayName: string,
    missingDisplayNameError: boolean
}

/**
 * Form for when we are registering
 * @constructor
 */
export class RegisterFormContainer extends React.Component<Props, State> {
    /** Default state for the component */
    state = {
        password: "",
        missingPasswordError: false,
        password2: "",
        missingPassword2Error: false,
        mismatchedPasswordsError: false,
        displayName: "",
        missingDisplayNameError: false
    };

    /** Pre-bound event handlers */
    _onPasswordChanged = this._onPasswordChangedHandler.bind(this);
    _onPassword2Changed = this._onPassword2ChangedHandler.bind(this);
    _onDisplayNameChanged = this._onDisplayNameChangedHandler.bind(this);
    _onSubmit = this._onSubmitHandler.bind(this);

    /**
     * Render the component as needed
     */
    render() {
        const { loading, email } = this.props;
        const { password, missingPasswordError, password2, missingPassword2Error, mismatchedPasswordsError, displayName, missingDisplayNameError } = this.state;

        return <RegisterForm loading={loading}
                             email={email}
                             password={password}
                             missingPasswordError={missingPasswordError}
                             onPasswordChanged={this._onPasswordChanged}
                             password2={password2}
                             missingPassword2Error={missingPassword2Error}
                             mismatchedPasswordsError={mismatchedPasswordsError}
                             onPassword2Changed={this._onPassword2Changed}
                             displayName={displayName}
                             missingDisplayNameError={missingDisplayNameError}
                             onDisplayNameChanged={this._onDisplayNameChanged}
                             onSubmit={this._onSubmit} />;
    }

    /**
     * Handler for when the password is changed
     * @private
     */
    _onPasswordChangedHandler(password: string) {
        this.setState({
            password: password,
            missingPasswordError: false,
            mismatchedPasswordsError: false
        });
    }

    /**
     * Handler for when the password2 is changed
     * @private
     */
    _onPassword2ChangedHandler(password2: string) {
        this.setState({
            password2: password2,
            missingPassword2Error: false,
            mismatchedPasswordsError: false
        });
    }

    /**
     * Handler for when the displayName is changed
     * @private
     */
    _onDisplayNameChangedHandler(displayName: string) {
        this.setState({
            displayName: displayName,
            missingDisplayNameError: false
        });
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler() {
        const { password, password2, displayName } = this.state;
        const errors = {
            missingPasswordError: password === "",
            missingPassword2Error: password2 === "",
            mismatchedPasswordsError: password !== password2,
            missingDisplayNameError: displayName === ""
        };

        const hasErrors = Object.keys(errors)
            .map(e => errors[e])
            .find(v => v);

        this.setState(errors);
        if (!hasErrors) {
            this.props.onSubmit(password, displayName);
        }
    }
}

