// @flow
import {createSagas} from "redux-box";
import {call, put} from 'redux-saga/effects'
import { httpClient } from "../api";

/** The name of the module */
const USER_EXISTS_MODULE = "AUTHENTICATION/USER_EXISTS";

/** The action key for checking if a user exists */
const CHECK_USER_ACTION = USER_EXISTS_MODULE + "/CHECK_USER";
/** The action key for indicating whether a user exists or not */
const USER_EXISTS_ACTION = USER_EXISTS_MODULE + '/USER_EXISTS';

/** The system is still loading whether the user exists */
export const USER_EXISTS_STATUS_LOADING = 'loading';
/** The email address is known */
export const USER_EXISTS_STATUS_KNOWN = 'known';
/** The email address is unknown */
export const USER_EXISTS_STATUS_UNKNOWN = 'unknown';

/** The shape of the state for this module */
type State = {
    status?: string,
    email?: string
}

const actions = {
    checkUserExists: (email: string) => ({ type: CHECK_USER_ACTION, email })
};

const mutations = {
    [CHECK_USER_ACTION]: (state: State, action: {type: string, email: string}) => {
        state.status = USER_EXISTS_STATUS_LOADING;
        state.email = action.email;
    },
    [USER_EXISTS_ACTION]: (state: State, action: {type: string, status: string}) => {
        state.status = action.status;
    }
};

const sagas = createSagas({
    [CHECK_USER_ACTION]: function*(action) {
        try {
            yield call(httpClient.head, `/users/emails/${action.email}`);
            yield put({
                type: USER_EXISTS_ACTION,
                status: USER_EXISTS_STATUS_KNOWN
            });
        } catch (e) {
            yield put({
                type: USER_EXISTS_ACTION,
                status: USER_EXISTS_STATUS_UNKNOWN
            });

        }
    }
});

export const userExistsModule = {
    name: USER_EXISTS_MODULE,
    state: {},
    actions,
    mutations,
    sagas
};
