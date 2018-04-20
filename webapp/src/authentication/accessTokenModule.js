// @flow
import {createSagas} from "redux-box";

/** The name of the module */
const ACCESS_TOKEN_MODULE = "AUTHENTICATION/ACCESS_TOKEN";

/** The action key for receiving an access token */
export const RECEIVED_ACCESS_TOKEN_ACTION = ACCESS_TOKEN_MODULE + "/RECEIVED_ACCESS_TOKEN";

/** The shape of the state for this module */
type State = {
    token?: string
}

/** The shape of the action for receiving a user profile */
type ReceivedAccessTokenAction = {
    token: string
}

const actions = {
};

const mutations = {
    [RECEIVED_ACCESS_TOKEN_ACTION]: (state: State, action: ReceivedAccessTokenAction) => {
        state.token = action.token;
    }
};

const sagas = createSagas({
});

export const accessTokenModule = {
    name: ACCESS_TOKEN_MODULE,
    state: {},
    actions,
    mutations,
    sagas
};
