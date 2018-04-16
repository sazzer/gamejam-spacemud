import React from 'react';
import PropTypes from 'prop-types';
import { Form, Button, Header } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';

/**
 * Form for when we are starting authentication
 * @constructor
 */
export class StartForm extends React.Component {
    /** Default state for the component */
    state = {
        email: ""
    };

    /** Pre-bound event handlers */
    _onEmailChanged = this._onEmailChangedHandler.bind(this);
    _onSubmit = this._onSubmitHandler.bind(this);

    /**
     * Render the component as needed
     */
    render() {
        const { loading } = this.props;
        const { email } = this.state;

        return (
            <Form action="#" onSubmit={this._onSubmit}>
                <Header dividing as="h4">
                    <Interpolate i18nKey="authentication.start.label" />
                </Header>
                <Form.Field>
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
            email: e.target.value
        })
    }

    /**
     * Handler for when the form is submitted
     * @private
     */
    _onSubmitHandler(e) {
        e.preventDefault();
        this.props.onSubmit(this.state.email);
    }
}

StartForm.propTypes = {
    loading: PropTypes.bool.isRequired,
    onSubmit: PropTypes.func.isRequired
};


