// @flow

import React from 'react';
import {LoginForm} from './LoginForm';

type Props = {
    email: string,
    loading: boolean,
    onSubmit: (string) => void
}

type State = {
    password: string,
    missingPasswordError: boolean
}

/**
 * Form for when we are logging in
 * @constructor
 */
export class LoginFormContainer extends React.Component<Props, State> {
    /** Default state for the component */
    state = {
        password: "",
        missingPasswordError: false
    };

    /** Pre-bound event handlers */
    _onPasswordChanged = this._onPasswordChangedHandler.bind(this);
    _onSubmit = this._onSubmitHandler.bind(this);

    /**
     * Render the component as needed
     */
    render() {
        const { loading, email } = this.props;
        const { password, missingPasswordError } = this.state;

        return <LoginForm loading={loading}
                          email={email}
                          password={password}
                          missingPasswordError={missingPasswordError}
                          onPasswordChanged={this._onPasswordChanged}
                          onSubmit={this._onSubmit} />;
    }

    /**
     * Handler for when the password is changed
     * @private
     */
    _onPasswordChangedHandler(password: string) {
        this.setState({
            password: password,
            missingPasswordError: false
        })
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler() {
        const { password } = this.state;
        if (password === "") {
            this.setState({
                missingPasswordError: true
            });
        } else {
            this.props.onSubmit(password);
        }
    }
}

