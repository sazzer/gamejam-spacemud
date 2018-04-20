import React from 'react';
import {StartFormContainer} from "./StartFormContainer";
import {ConnectedLoginForm} from "./ConnectedLoginForm";
import {ConnectedRegisterForm} from "./ConnectedRegisterForm";
import {connectStore} from 'redux-box'
import {
    USER_EXISTS_STATUS_KNOWN,
    USER_EXISTS_STATUS_LOADING,
    USER_EXISTS_STATUS_UNKNOWN,
    userExistsModule
} from "../../authentication";

/**
 * Form for Authentication, both in terms of Login and Registration
 * @constructor
 */
export class AuthenticationForm extends React.Component {
    _onStartFormSubmitted = this._onStartFormSubmittedHandler.bind(this);

    render() {
        const { userExistsModule } = this.props;

        let formBody;
        if (userExistsModule.status === USER_EXISTS_STATUS_KNOWN) {
            formBody = <ConnectedLoginForm
                loading={false}
                email={userExistsModule.email} />;
        } else if (userExistsModule.status === USER_EXISTS_STATUS_UNKNOWN) {
            formBody = <ConnectedRegisterForm
                loading={false}
                email={userExistsModule.email} />;
        } else {
            formBody = <StartFormContainer
                loading={userExistsModule.status === USER_EXISTS_STATUS_LOADING}
                email={userExistsModule.email}
                onSubmit={this._onStartFormSubmitted} />;
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
