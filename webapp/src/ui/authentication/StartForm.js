import React from 'react';
import PropTypes from 'prop-types';
import { Form, Button, Header, Label } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';

export function StartForm({loading, email, missingEmailError, onEmailChanged, onSubmit}) {
    let missingEmailErrorMessage;

    if (missingEmailError) {
        missingEmailErrorMessage = (
            <Label basic pointing color="red">
                <Interpolate i18nKey="authentication.start.email.validation.missing" />
            </Label>
        );
    }

    return (
        <Form action="#" onSubmit={onSubmit}>
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
                       onChange={onEmailChanged} />
                {missingEmailErrorMessage}
            </Form.Field>
            <Button type="submit" primary loading={loading}>
                <Interpolate i18nKey="authentication.start.submit" />
            </Button>
        </Form>
    );
}

StartForm.propTypes = {
    loading: PropTypes.bool.isRequired,
    email: PropTypes.string.isRequired,
    missingEmailError: PropTypes.bool.isRequired,
    onEmailChanged: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired
};

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


