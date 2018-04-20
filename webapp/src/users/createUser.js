// @flow
import {createSagas} from "redux-box";
import {call, put} from 'redux-saga/effects'
import {httpClient} from "../api";
import {RECEIVED_ACCESS_TOKEN_ACTION} from "../authentication";
import {RECEIVED_USER_PROFILE_ACTION} from "./userProfile";

/** The name of the module */
const CREATE_USER_MODULE = "USERS/CREATE_USER";

/** The action key for checking if a user exists */
const CREATE_USER_ACTION = CREATE_USER_MODULE + "/CREATE_USER";
/** The action key for checking if a user exists */
const CREATE_USER_SUCCESS_ACTION = CREATE_USER_MODULE + "/CREATE_USER/SUCCESS";
/** The action key for checking if a user exists */
const CREATE_USER_FAILURE_ACTION = CREATE_USER_MODULE + "/CREATE_USER/FAILURE";

/** The system is still trying to create the user */
export const CREATE_USER_STATUS_LOADING = 'loading';
/** The user creation was a success */
export const CREATE_USER_STATUS_SUCCESS = 'success';
/** The user creation was a failure */
export const CREATE_USER_STATUS_FAILURE = 'failure';

/** The shape of the state for this module */
type State = {
    status?: string,
    error?: string
}

const actions = {
    createUser: (email: string, password: string, displayName: string) => ({ type: CREATE_USER_ACTION, email, password, displayName })
};

const mutations = {
    [CREATE_USER_ACTION]: (state: State) => {
        state.status = CREATE_USER_STATUS_LOADING;
        state.error = undefined;
    },
    [CREATE_USER_SUCCESS_ACTION]: (state: State) => {
        state.status = CREATE_USER_STATUS_SUCCESS;
        state.error = undefined;
    },
    [CREATE_USER_FAILURE_ACTION]: (state: State, action: {error: string}) => {
        state.status = CREATE_USER_STATUS_FAILURE;
        state.error = action.error;
    }
};

const sagas = createSagas({
    [CREATE_USER_ACTION]: function*(action) {
        try {
            const response = yield call(httpClient.post, '/users', {
                email: action.email,
                password: action.password,
                displayName: action.displayName
            });
            yield put({
                type: CREATE_USER_SUCCESS_ACTION
            });
            yield put({
                type: RECEIVED_USER_PROFILE_ACTION,
                email: response.data.email,
                displayName: response.data.displayName,
                links: {
                    self: response.data._links.self.href
                }
            });
            yield put({
                type: RECEIVED_ACCESS_TOKEN_ACTION,
                token: response.data._embedded.token.access_token
            });
        } catch (e) {
            const response = e.response;
            if (response.status === 409 && e.response.data.instance) {
                yield put({
                    type: CREATE_USER_FAILURE_ACTION,
                    error: e.response.data.instance
                });
            } else {
                yield put({
                    type: CREATE_USER_FAILURE_ACTION,
                    error: 'tag:grahamcox.co.uk,2018,spacemud/problems/unknown'
                });
            }
        }
    }
});

export const createUserModule = {
    name: CREATE_USER_MODULE,
    state: {},
    actions,
    mutations,
    sagas
};
