import React from 'react';
import { HeaderBar } from "./header";
import { LandingPage } from "./landing";

/**
 * The actual main application
 * @return {*} The main application component
 * @constructor
 */
export function UI() {
    return (
        <div className="ui">
            <HeaderBar />
            <LandingPage />
        </div>
    );
}
