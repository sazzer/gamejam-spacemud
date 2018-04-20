// @flow
import {createSagas} from "redux-box";

/** The name of the module */
const USER_PROFILE_MODULE = "USERS/USER_PROFILE";

/** The action key for receiving a user profile */
export const RECEIVED_USER_PROFILE_ACTION = USER_PROFILE_MODULE + "/RECEIVED_PROFILE";

/** The shape of the state for this module */
type State = {
    email?: string,
    displayName?: string,
    selfLink?: string
}

/** The shape of the action for receiving a user profile */
type ReceivedUserProfileAction = {
    type: string,
    email: string,
    displayName: string,
    links: {self: string}
}

const actions = {
};

const mutations = {
    [RECEIVED_USER_PROFILE_ACTION]: (state: State, action: ReceivedUserProfileAction) => {
        state.email = action.email;
        state.displayName = action.displayName;
        state.selfLink = action.links.self;
    }
};

const sagas = createSagas({
});

export const userProfileModule = {
    name: USER_PROFILE_MODULE,
    state: {},
    actions,
    mutations,
    sagas
};
