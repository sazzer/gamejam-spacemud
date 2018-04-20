// @flow

import React from 'react';
import {StartForm} from './StartForm';

type Props = {
    loading: boolean,
    onSubmit: (string) => void
}

type State = {
    email: string,
    missingEmailError: boolean
}

/**
 * Form for when we are starting authentication
 * @constructor
 */
export class StartFormContainer extends React.Component<Props, State> {
    /** Default state for the component */
    state = {
        email: "",
        missingEmailError: false
    };

    /** Pre-bound event handlers */
    _onEmailChanged = this._onEmailChangedHandler.bind(this);
    _onSubmit = this._onSubmitHandler.bind(this);

    /**
     * Render the component as needed
     */
    render() {
        const { loading } = this.props;
        const { email, missingEmailError } = this.state;

        return <StartForm loading={loading}
                          email={email}
                          missingEmailError={missingEmailError}
                          onEmailChanged={this._onEmailChanged}
                          onSubmit={this._onSubmit} />;
    }

    /**
     * Handler for when the email address is changed
     * @private
     */
    _onEmailChangedHandler(email: string) {
        this.setState({
            email: email,
            missingEmailError: false
        })
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler() {
        const { email } = this.state;
        if (email === "") {
            this.setState({
                missingEmailError: true
            });
        } else {
            this.props.onSubmit(email);
        }
    }
}

