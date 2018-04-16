import React from 'react';
import PropTypes from 'prop-types';
import { StartForm } from './StartForm';

/**
 * Form for when we are starting authentication
 * @constructor
 */
export class StartFormContainer extends React.Component {
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
    _onEmailChangedHandler(e) {
        this.setState({
            email: e.target.value,
            missingEmailError: false
        })
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler(e) {
        e.preventDefault();
        if (this.state.email === "") {
            this.setState({
                missingEmailError: true
            });
        } else {
            this.props.onSubmit(this.state.email);
        }
    }
}

StartFormContainer.propTypes = {
    loading: PropTypes.bool.isRequired,
    onSubmit: PropTypes.func.isRequired
};


