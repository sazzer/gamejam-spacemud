// @flow
import {createSagas} from "redux-box";
import {call, put} from 'redux-saga/effects'
import { httpClient } from "../api";
import {RECEIVED_ACCESS_TOKEN_ACTION} from "./accessTokenModule";
import {RECEIVED_USER_PROFILE_ACTION} from "../users/userProfile";

/** The name of the module */
const AUTHENTICATE_USER = "AUTHENTICATION/AUTHENTICATE";

/** The action key for authenticating the user */
const AUTHENTICATE_USER_ACTION = AUTHENTICATE_USER + "/AUTHENTICATE";
/** The action key for success when authenticating the user */
const AUTHENTICATE_USER_SUCCESS_ACTION = AUTHENTICATE_USER + "/AUTHENTICATE_SUCCESS";
/** The action key for failure when authenticating the user */
const AUTHENTICATE_USER_FAILURE_ACTION = AUTHENTICATE_USER + "/AUTHENTICATE_FAILURE";

/** The system is still waiting for a response on authenticating the user */
export const AUTHENTICATE_STATUS_LOADING = 'loading';
/** The user was authenticated successfully */
export const AUTHENTICATE_STATUS_SUCCESS = 'success';
/** The user failed to authenticate */
export const AUTHENTICATE_STATUS_FAILURE = 'failure';

/** The shape of the state for this module */
type State = {
    status?: string,
    error?: string
}

const actions = {
    authenticate: (email: string, password: string) => ({ type: AUTHENTICATE_USER_ACTION, email, password })
};

const mutations = {
    [AUTHENTICATE_USER_ACTION]: (state: State, action: {type: string, email: string}) => {
        state.status = AUTHENTICATE_STATUS_LOADING;
        state.error = undefined;
    },
    [AUTHENTICATE_USER_SUCCESS_ACTION]: (state: State) => {
        state.status = AUTHENTICATE_STATUS_SUCCESS;
    },
    [AUTHENTICATE_USER_FAILURE_ACTION]: (state: State, action: {error: string}) => {
        state.status = AUTHENTICATE_STATUS_FAILURE;
        state.error = action.error;
    }
};

const sagas = createSagas({
    [AUTHENTICATE_USER_ACTION]: function*(action) {
        try {
            const response = yield call(httpClient.post, `/authenticate`, {
                email: action.email,
                password: action.password
            });
            yield put({
                type: AUTHENTICATE_USER_SUCCESS_ACTION
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
            if (response.data && response.data.instance) {
                yield put({
                    type: AUTHENTICATE_USER_FAILURE_ACTION,
                    error: e.response.data.instance
                });
            } else {
                yield put({
                    type: AUTHENTICATE_USER_FAILURE_ACTION,
                    error: 'tag:grahamcox.co.uk,2018,spacemud/problems/unknown'
                });
            }
        }
    }
});

export const authenticateModule = {
    name: AUTHENTICATE_USER,
    state: {},
    actions,
    mutations,
    sagas
};
