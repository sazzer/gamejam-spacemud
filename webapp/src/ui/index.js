import React from 'react';
import 'semantic-ui-css/semantic.min.css';
import { HeaderBar } from "./header";

/**
 * The actual main application
 * @return {*} The main application component
 * @constructor
 */
export function UI() {
    return (
        <div className="ui">
            <HeaderBar />
        </div>
    );
}
