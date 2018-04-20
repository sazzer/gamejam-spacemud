// @flow

import React from 'react';
import {Message} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

type Props = {
    titleKey: string,
    messageKey: string
}

export function ErrorMessage({titleKey, messageKey}: Props) {
    return (
        <Message error>
            <Message.Header>
                <Interpolate i18nKey={titleKey} />
            </Message.Header>
            <p>
                <Interpolate i18nKey={messageKey} />
            </p>
        </Message>
    );
}
