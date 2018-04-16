import React from 'react';
import { shallow } from 'enzyme';
import { HeaderBar } from '../HeaderBar';

/**
 * Set up the component being tested
 */
function setup({} = {}) {
    const wrapper = shallow(<HeaderBar />);

    return {
        wrapper
    };
}

describe('Rendering', () => {
    it('renders the expected HTML', () => {
        const { wrapper } = setup({});

        expect(wrapper).toMatchSnapshot()
    });
});
