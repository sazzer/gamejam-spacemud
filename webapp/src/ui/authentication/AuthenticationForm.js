import React from 'react';
import {StartFormContainer} from "./StartFormContainer";
import {LoginFormContainer} from "./LoginFormContainer";
import {RegisterFormContainer} from "./RegisterFormContainer";
import {connectStore} from 'redux-box'
import {userExistsModule} from "../../authentication";

/** Form state for when we haven't started anything yet */
const FORM_STATE_INITIAL = "initial";
/** Form state for when we are logging in */
const FORM_STATE_LOGIN = "login";
/** Form state for when we are registering */
const FORM_STATE_REGISTER = "register";

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
    };

    _onStartFormSubmitted = this._onStartFormSubmittedHandler.bind(this);

    render() {
        const { formState, email } = this.state;

        const { userExistsModule } = this.props;

        let formBody;
        if (formState === FORM_STATE_INITIAL) {
            formBody = <StartFormContainer loading={userExistsModule.status === 'loading'} onSubmit={this._onStartFormSubmitted} />;
        } else if (formState === FORM_STATE_LOGIN) {
            formBody = <LoginFormContainer loading={false} email={email} onSubmit={this._onStartFormSubmitted} />
        } else if (formState === FORM_STATE_REGISTER) {
            formBody = <RegisterFormContainer loading={false} email={email} onSubmit={this._onStartFormSubmitted} />
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
        const { userExistsModule } = this.props;
        userExistsModule.checkUserExists(email);
    }
}

export const ConnectedAuthenticationForm = connectStore({
    userExistsModule
})(AuthenticationForm);
