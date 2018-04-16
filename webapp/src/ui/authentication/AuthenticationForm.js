import React from 'react';
import { StartForm } from "./StartForm";
import { LoginForm } from "./LoginForm";

/** Form state for when we haven't started anything yet */
const FORM_STATE_INITIAL = "initial";
/** Form state for when we are logging in */
const FORM_STATE_LOGIN = "login";

/**
 * Form for Authentication, both in terms of Login and Registration
 * @constructor
 */
export class AuthenticationForm extends React.Component {
    /**
     * Default initial state for the form
     */
    state = {
        email: null,
        formState: FORM_STATE_INITIAL,
        loading: false
    };

    _onStartFormSubmitted = this._onStartFormSubmittedHandler.bind(this);

    render() {
        const { formState, email, loading } = this.state;
        let formBody;
        if (formState === FORM_STATE_INITIAL) {
            formBody = <StartForm loading={loading} onSubmit={this._onStartFormSubmitted} />;
        } else if (formState === FORM_STATE_LOGIN) {
            formBody = <LoginForm loading={loading} email={email} onSubmit={this._onStartFormSubmitted} />
        }

        return (
            <div className="ui">
                {formBody}
            </div>
        );
    }

    /**
     * Handler for when the Start form is submitted
     * @param {string} email The email address that was submitted
     * @private
     */
    _onStartFormSubmittedHandler(email) {
        this.setState({
            email: email,
            formState: FORM_STATE_LOGIN
        })
    }
}
