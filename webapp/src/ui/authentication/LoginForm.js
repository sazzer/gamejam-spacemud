import React from 'react';
import PropTypes from 'prop-types';
import { Form, Button, Header } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';

/**
 * Form for when we are logging in
 * @constructor
 */
export class LoginForm extends React.Component {
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

        return (
            <Form action="#" onSubmit={this._onSubmit}>
                <Header dividing as="h4">
                    <Interpolate i18nKey="authentication.login.label" />
                </Header>
                <Form.Field required>
                    <label>
                        <Interpolate i18nKey="authentication.login.email.label" />
                    </label>
                    <input type="email"
                           name="email"
                           value={email}
                           readOnly={true} />
                </Form.Field>
                <Form.Field required error={missingPasswordError}>
                    <label>
                        <Interpolate i18nKey="authentication.login.password.label" />
                    </label>
                    <input type="password"
                           name="password"
                           autoFocus
                           value={password}
                           readOnly={loading}
                           onChange={this._onPasswordChanged} />
                </Form.Field>
                <Button type="submit" primary loading={loading}>
                    <Interpolate i18nKey="authentication.login.submit" />
                </Button>
            </Form>
        );
    }

    /**
     * Handler for when the password is changed
     * @private
     */
    _onPasswordChangedHandler(e) {
        this.setState({
            password: e.target.value,
            missingPasswordError: false
        })
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler(e) {
        e.preventDefault();
        if (this.state.password === "") {
            this.setState({
                missingPasswordError: true
            });
        } else {
            this.props.onSubmit(this.state.password);
        }
    }
}

LoginForm.propTypes = {
    loading: PropTypes.bool.isRequired,
    email: PropTypes.string.isRequired,
    onSubmit: PropTypes.func.isRequired
};


