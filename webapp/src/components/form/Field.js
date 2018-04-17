// @flow

import React from 'react';
import {Form, Label} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

type Props = {
    errorKey?: string,
    labelKey: string,
    type: string,
    autoFocus?: boolean,
    name: string,
    value?: string,
    readOnly: boolean,
    onChange: (string) => void
}

export function Field({
                          errorKey,
                          labelKey,
                          type,
                          autoFocus,
                          name,
                          value,
                          readOnly,
                          onChange
                      }: Props) {
    const hasError = errorKey !== undefined;

    const onChangeWrapper = (e) => {
        onChange(e.target.value);
    };

    let errorMessage;

    if (hasError) {
        errorMessage = (
            <Label basic pointing color="red">
                <Interpolate i18nKey={errorKey} />
            </Label>
        );
    }

    return (
        <Form.Field required error={hasError}>
            <label>
                <Interpolate i18nKey={labelKey} />
            </label>
            <input type={type}
                   autoFocus={autoFocus}
                   name={name}
                   value={value}
                   readOnly={readOnly}
                   onChange={onChangeWrapper} />
            {errorMessage}
        </Form.Field>
    );
}
