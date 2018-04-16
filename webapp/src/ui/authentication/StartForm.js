import React from 'react';
import PropTypes from 'prop-types';
import { Form, Button, Header, Label } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';

/**
 * Form for when we are starting authentication
 * @constructor
 */
export class StartForm extends React.Component {
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

        let missingEmailErrorMessage;

        if (missingEmailError) {
            missingEmailErrorMessage = (
                <Label basic pointing color="red">
                    Email Address was not provided
                </Label>
            );
        }

        return (
            <Form action="#" onSubmit={this._onSubmit}>
                <Header dividing as="h4">
                    <Interpolate i18nKey="authentication.start.label" />
                </Header>
                <Form.Field required error={missingEmailError}>
                    <label>
                        <Interpolate i18nKey="authentication.start.email.label" />
                    </label>
                    <input type="email"
                           autoFocus
                           name="email"
                           placeholder=""
                           value={email}
                           readOnly={loading}
                           onChange={this._onEmailChanged} />
                    {missingEmailErrorMessage}
                </Form.Field>
                <Button type="submit" primary loading={loading}>
                    <Interpolate i18nKey="authentication.start.submit" />
                </Button>
            </Form>
        );
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

StartForm.propTypes = {
    loading: PropTypes.bool.isRequired,
    onSubmit: PropTypes.func.isRequired
};


