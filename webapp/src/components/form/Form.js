// @flow

import React from 'react';
import {Form as SemanticForm, Header} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

type Props = {
    onSubmit: () => void,
    labelKey?: string,
    children?: any
}

export function Form({onSubmit, labelKey, children} : Props) {
    const onSubmitWrapper = (e) => {
        e.preventDefault();
        onSubmit();
    };

    let label;
    if (labelKey !== undefined) {
        label = (
            <Header dividing as="h4">
                <Interpolate i18nKey={labelKey} />
            </Header>
        );
    }

    return (
        <SemanticForm action="#" onSubmit={onSubmitWrapper}>
            {label}
            {children}
        </SemanticForm>
    );
}
