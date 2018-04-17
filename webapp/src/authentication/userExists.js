// @flow
import {createSagas} from "redux-box";

const STATUS_LOADING = 'loading';

type State = {
    status?: string
}

const state: State = {

};

const actions = {
    checkUserExists: (email: string) => ({ type: "AUTHENTICATION/CHECK_USER", email })
};

const mutations = {
    'AUTHENTICATION/CHECK_USER': (state: State, action: {type: string, email: string}) => {
        state.status = STATUS_LOADING;
    }
};

const sagas = createSagas({
    'AUTHENTICATION/CHECK_USER': function*(action) {
    }
});

export const userExistsModule = {
    name: "AUTHENTICATION/CHECK_USER",
    state,
    actions,
    mutations,
    sagas
};
