import React from 'react';
import { Menu, Item } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';

/**
 * The Header Bar for the entire application
 * @constructor
 */
export function HeaderBar() {
    return (
        <Menu attached borderless className="spacemud_header">
            <Item className="header">
                <Interpolate i18nKey="page.header" />
            </Item>

            <Menu inverted compact className="right">
            </Menu>
        </Menu>
    );
}
