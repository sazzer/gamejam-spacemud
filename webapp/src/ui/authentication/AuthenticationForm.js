import React from 'react';
import { StartForm } from "./StartForm";

/** Form state for when we haven't started anything yet */
const FORM_STATE_INITIAL = "initial";
/** Form state for when we are checking if the entered email address exists or not */
const FORM_STATE_CHECKING = "checking";
/** Form state for when we are registering a new user */
const FORM_STATE_REGISTER = "register";
/** Form state for when we are logging in */
const FORM_STATE_LOGIN = "login";

/**
 * Form for Authentication, both in terms of Login and Registration
 * @constructor
 */
export class AuthenticationForm extends React.Component {
    /**
     * Default initial state for the form
     * @type {{email: null}}
     */
    state = {
        email: null,
        formState: FORM_STATE_INITIAL
    };

    _onStartFormSubmitted = this._onStartFormSubmittedHandler.bind(this);

    render() {
        const { formState } = this.state;
        let formBody;
        if (formState === FORM_STATE_INITIAL) {
            formBody = <StartForm loading={false} onSubmit={this._onStartFormSubmitted} />;
        } else if (formState === FORM_STATE_CHECKING) {
            formBody = <StartForm loading={true} onSubmit={this._onStartFormSubmitted} />;
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
            formState: FORM_STATE_CHECKING
        })
    }
}
